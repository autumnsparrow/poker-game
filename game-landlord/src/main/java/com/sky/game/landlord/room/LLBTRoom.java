/**
 * @sparrow
 * @Dec 29, 2014   @4:37:10 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.BlockadeTournamentConfig;
import com.sky.game.landlord.blockade.config.BlockadeTournamentConfig.Stage;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.BTRoomLevelDetail;
import com.sky.game.protocol.commons.GL0000Beans.GL4013Request;
import com.sky.game.protocol.commons.GL0000Beans.GL4017Request;
import com.sky.game.protocol.commons.GL0000Beans.LG4013Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4016Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4017Response;
import com.sky.game.protocol.commons.GL0000Beans.PReward;
import com.sky.game.protocol.commons.GL0000Beans.Range;
import com.sky.game.protocol.commons.GS0000Beans.PGameBlockadeUser;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;

/**
 * 
 * Blockade Tournament Configuration:
 * 
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class LLBTRoom extends LLRoom {

	BlockadeTournamentConfig config;

	LLGameObjectMap<LLBTLevel> levels;
	

	/**
	 * @param config
	 */
	public LLBTRoom(Long id, BlockadeTournamentConfig config) {
		super(id);
		this.config = config;
		setId(Long.valueOf(config.getRoomId()));
		this.levels = LLU.getGameObjectMap();
		
		
		//this.minLevel=config.get
		LLU.regeisterHandler(this, "GL4013","GL4016","GL4017");
		

	}

	@Override
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub
		return config.getDeckConfig();
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.observe(player);

		// when the player join the room.
		// check if the level.
		PGameBlockadeUser user = null;
		user = GSPP.getGameBlockadeUser(player.getId(), getId());

		// before the user join the level,check the integral,whether the player
		// belongs that level.
		adjustPlayerIntegral(user);
		// calculate the level by the integral.

		Integer level = Integer.valueOf(user.getLevel().intValue());
		LLBTLevel btLevel = getLevel(level);// levels.get(level);

		btLevel.observer(player);

	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		LLBTLevel level = player.getObject(player.level);
		if (level != null) {
			level.unObserver(player);
		}

	}

	//
	// code region for the enroll interface.
	//
	//

	/**
	 * enroll a player by item id
	 * 
	 * @param player
	 * @param itemId
	 * @see LLBTRoom#enroll(LLGamePlayer, int)
	 */
	private void enrollByItem(LLGamePlayer player, Integer itemId) {
		Map<Integer, EnrollTicket> tickets = config.getTicketsMap();
		EnrollTicket ticket = tickets.get(itemId);
		// item amount
		int itemValue = ticket.getItemAmount();
		enroll(player, itemId, itemValue);
		logger.info("player[" + player.getId() + "] enroll ");
	}
	
	void itemNotEnough(LLGamePlayer player,Integer itemId){
		loadItems();
		Item item=items.get(itemId);
		if(item==null)
			throw new RuntimeException("can't find item by item id:"+itemId);
		sendException(player,
				LLRoomExceptionTypes.LLFEnrollItemValueNotEnough,String.format(LLRoomExceptionTypes.LLFEnrollItemValueNotEnough.message,item.getDescription()));
	}

	/**
	 * enroll the player into the game.
	 * 
	 * @param player
	 * @param amount
	 */
	private void enroll(LLGamePlayer player, Integer itemId, int amount) {

		boolean valid =itemId==0?true: GSPP.validEnroll(player.getId(), itemId, amount);
		if (!valid) {
			itemNotEnough(player, itemId);
		} else {

			GameUser user = GSPP.getGameUser(player.getId());
			if (user != null) {
				player.setExp(user.getExp());

				boolean ret =itemId==0?true: GSPP.enroll(player.getId(), getId(), getId(),
						itemId, amount);
				if (ret) {
					player.setChips(config.getInitIntegral());

					GL0000Beans.LG4005Response resp = LLU
							.o(GL0000Beans.LG4005Response.class);
					resp.setId(getId());
					resp.setState(LLRoomExceptionTypes.LLEnrollOk.getState());

					// should update the blockade integral to the protocol
					// server
					ret = GSPP
							.updateBlockadeIntegral(player.getId(), getId(),
									Long.valueOf(player.getChips()),
									Integer.valueOf(1));

					if (!ret){
						//resp.setState(LLRoomExceptionTypes.LLEnrollBloackdeUpdateFailed
						//		.getState());
						sendException(player,
								LLRoomExceptionTypes.LLEnrollBloackdeUpdateFailed);
						throw new RuntimeException(LLRoomExceptionTypes.LLEnrollBloackdeUpdateFailed.message);
					}

					player.sendBrokerMessage(resp, false);
					// sendException(player, LLRoomExceptionTypes.LLEnrollOk);
				}
			}
		}

	}

	//
	//
	// code region
	//

	public synchronized LLBTLevel getLevel(Integer level) {
		// Integer level=user==null?Integer.valueOf(0):user.getLevel();
		if (level == null)
			level = Integer.valueOf(0);
		LLBTLevel btLevel=null;
		btLevel=levels.get(Long.valueOf(level.intValue()));
		if(btLevel==null){
			btLevel=new LLBTLevel(this,level);
		}
		
		return btLevel;

	}

	/**
	 * GL4013 event handler.
	 * <p>
	 * check if the user alread enrolled.
	 *
	 * 
	 * @param evt
	 * @see MinaEvent
	 * @see GL4013Request
	 * @see LG4013Response
	 */
	@RegisterEventHandler(transcode = "GL4013")
	public void handleEnrollCheck(MinaEvent evt) {
		GL4013Request req = LLU.evtAsObj(evt);
		LLGamePlayer p = evtAsPlayer(evt);
		p.enableRobot = false;
		LG4013Response resp = LLU.o(LG4013Response.class);
		
		PGameBlockadeUser user = GSPP.getGameBlockadeUser(p.getId(),
				req.getId());
		
		if (user == null) {
			resp.setEnrolled(false);

		} else {
			resp.setEnrolled(true);
			p.setChips(user.getIntegral().intValue());
		}

		Integer level = user.getLevel();
		if (level == null || 0 == level.intValue()) {
			level = Integer.valueOf(1);
			resp.setEnrolled(false);
		}

		
		if(user.getIntegral()!=null&&user.getIntegral().intValue()<0){
			resp.setEnrolled(false);
		}

		if(user.getIntegral().intValue()<0){
			level = Integer.valueOf(1);
			resp.setEnrolled(false);
		}

		//　get the current stage by user intergal.
		Stage currentStage=getStageByPlayer(user.getIntegral().longValue());
		
		if(currentStage!=null&&currentStage.getId()!=level.intValue()){
			adjustPlayerIntegral(user);
		}
		
		LLBTLevel btLevel = getLevel(currentStage.getId());//getLevel(level) //getLevel(level);
		level=Integer.valueOf(currentStage.getId());
		
		p.level=btLevel;
		//Stage nextStage=btLevel.nextStage();
		
		if (currentStage != null) {
		
			resp.setBase(btLevel.stage.getBase());
			resp.setCurrentLevelMaxIntegral(btLevel.stage.getMaxIntegeral());
			resp.setLevel(currentStage.getId());
			resp.setIntegralRange(Range.obtain(btLevel.stage.getMinIntegeral(),
					btLevel.stage.getMaxIntegeral()));
			resp.setOnlinePlayerRange(Range.obtain(btLevel.players.size(),
					btLevel.playerSize()));
			resp.setPlayerIntegral(user.getIntegral().intValue());
			String contabexp=btLevel.stage.getMatch().getStart();
			//Date fixedDate=CronUtil.getScheduledTime(contabexp);
			long timeLeft=CronUtil.timeLeft(contabexp);
			timeLeft=timeLeft<0?0:timeLeft/1000;
			resp.setTimeLeft(timeLeft);// BTRoomLevelDetail
																	// detail=LLU.o(BTRoomLevelDetail.class);
			resp.setReward(btLevel.getReward());
			resp.setUnReadMessageCount(GSPP.getUnreadMessageCount(user.getId()));
			resp.setEnableExchange(btLevel.stage.isEnableExchange());

		}

		p.sendBrokerMessage(resp, false);

	}

	@RegisterEventHandler(transcode = "GL4016")
	public void handleBTLevel(MinaEvent evt) {
		loadItems();
		LLGamePlayer p = evtAsPlayer(evt);
		LG4016Response o = LLU.o(LG4016Response.class);

		List<BTRoomLevelDetail> levels = GameUtil.getList();
		o.setLevels(levels);
		for (Stage s : config.getStages()) {
			if(s.getId()==0)
				continue;
			BTRoomLevelDetail detail = LLU.o(BTRoomLevelDetail.class);
			// detail.setBase(s.);
			detail.setLevel(String.valueOf(s.getId()));
			//Stage stage=getStageByLevel(s.getId()+1);
			
				detail.setIntegral(Range.obtain(s.getMinIntegeral(), s.getMaxIntegeral()));
			
			
			LLBTLevel currentLevel=getLevel(Integer.valueOf(s.getId()));
			PGameBlockadeUser user=currentLevel.getFirstUser();
			if(user!=null){
				GameUser gameUser=GSPP.getGameUser(user.getUserId());
				if(gameUser!=null){
					detail.setFirstNickname(gameUser.getNickName());
				}
				else{
					detail.setFirstNickname("");
				}
			}
			
			
			//detail.setIntegral(s.getMinIntegeral());
			//getLevel(level)
			
			String startTime= CronUtil.getFormatedDate(CronUtil.getScheduledTime(s.getMatch().getStart()));
			detail.setMatchTime(startTime);
			PReward obj = LLU.o(PReward.class);
			Reward r = s.getReward();
			Item h = items.get(Long.valueOf(r.getItemId()));
			obj.wrap(h);
			obj.setRank(r.getRanking());
			obj.setAmount(r.getItemAmount());
			detail.setReward(obj);
			//int passedPlayers=GSPP.selectBlockadeUserCountByRoomIdAndLevel(id,Integer.valueOf(currentLevel.id.intValue()));
			detail.setPlayingPlayerNumbers(currentLevel.playerSize());

			levels.add(detail);
		}

		p.sendBrokerMessage(o, false);
	}

	//
	// code region -
	// which level the player current is.
	//

	public Stage getStageByLevel(Integer level) {
		Map<Integer, Stage> levelStageMap = config.getLevelStageMap();
		Stage nextStage = levelStageMap.get(level);

		return nextStage;
	}

	public Stage getStageByPlayer(Long integral) {

		// Long integral=user.getIntegral();
		Map<Integer, Stage> integralStageMap = config.getIntegralStageMap();
		Integer[] integrals = integralStageMap.keySet().toArray(
				new Integer[] {});
		Arrays.sort(integrals, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return  o2.intValue()-o1.intValue() ;
			}

		});
		Stage currentStage = null;
		// sort : high -- >low
		for (int i = 0; i < integrals.length; i++) {
			if (integral.intValue() >= integrals[i].intValue()) {
				currentStage = integralStageMap.get(integrals[i]);
				break;
			}
		}

		return currentStage;
	}

	/**
	 * <p>
	 * calculate the current user integral.check the level correct.
	 * 
	 * @param user
	 */
	public void adjustPlayerIntegral(PGameBlockadeUser user) {
		Integer level = user.getLevel();
		Stage currentStage = getStageByPlayer(user.getIntegral());

		if (currentStage != null) {
			if (level.intValue() != currentStage.getId()) {
				// the current level and the level from the database different.
				user.setLevel(currentStage.getId());
				// modify the result into the database.
				boolean ret = GSPP.updateBlockadeIntegral(user.getUserId(),
						user.getRoomId(), Long.valueOf(0), user.getLevel());
				if (!ret) {
					// TODO: the update blockade level failed!

				}
			}
		} else {
			// TODO: can't find the current user integral?

		}
	}

	@Override
	public void handleUserRankList(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.handleUserRankList(evt);
		LLGamePlayer player = evtAsPlayer(evt);

		// get the player team from the memory
		LLBTLevel l = player.getObject(player.level);
		if (l != null) {
			l.sendUserRanks(player);
		}

	}

	@Override
	public void handleEnroll(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.handleEnroll(evt);
		LLGamePlayer player = evtAsPlayer(evt);
		GL0000Beans.GL4005Request req = LLU.evtAsObj(evt);
		enrollByItem(player, Integer.valueOf(req.getItemId()));
	}

	// GL4009 -
	/**
	 * 
	 * @see GL4017Request
	 * @see LG4017Response
	 * @param evt
	 */
	@RegisterEventHandler(transcode="GL4017")
	public void handleSameLevelPlayerList(MinaEvent evt){
		LLGamePlayer player = evtAsPlayer(evt);

		// get the player team from the memory
		LLBTLevel l = player.getObject(player.level);
		if (l != null) {
			//l.sendUserRanks(player);
			List<GameUser> users=l.getLimitedGameUser(player.getId());
			LG4017Response o=LLU.o(LG4017Response.class);
			o.setUsers(users);
			GameUser owner=GSPP.getGameUser(player.getId());
			LLGameObjectMap<PGameBlockadeUser> pgbus=l.getIdUsers();
			PGameBlockadeUser pbu=pgbus.get(player.getId());
			if(pbu!=null)
				owner.setGbuId(pbu.getId());
			o.setOwner(owner);
			player.sendBrokerMessage(o, false);
			
		}
	}

}
