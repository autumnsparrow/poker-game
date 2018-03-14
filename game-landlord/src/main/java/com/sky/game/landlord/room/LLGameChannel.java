/**
 * @sparrow
 * @Dec 29, 2014   @1:52:57 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.BlockadeTournamentConfig;
import com.sky.game.landlord.blockade.config.EliminationTournamentConfig;
import com.sky.game.landlord.blockade.config.FreeTournamentConfig;
import com.sky.game.landlord.blockade.config.LLGameTournamentConfig;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.jmx.JmxGame;
import com.sky.game.landlord.jmx.JmxRoom;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.BTRoom;
import com.sky.game.protocol.commons.GL0000Beans.BTRoomLevel;
import com.sky.game.protocol.commons.GL0000Beans.LG0014Response;
import com.sky.game.protocol.commons.GL0000Beans.LG5002Response;
import com.sky.game.protocol.commons.GL0000Beans.ETRoom;
import com.sky.game.protocol.commons.GL0000Beans.LG5001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG5003Response;
import com.sky.game.protocol.commons.GL0000Beans.PEnrollTicket;
import com.sky.game.protocol.commons.GL0000Beans.FtRoom;
import com.sky.game.protocol.commons.GL0000Beans.PReward;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.Item;

/**
 * @author sparrow
 *
 */
public class LLGameChannel implements IIdentifiedObject{
	private static final Log logger=LogFactory.getLog(LLGameChannel.class);
	
	Long id;
	
	// area free
	LLGameTournamentConfig config;
	
	List<LLFTRoom> ftRooms;
	Map<Long,LLFTRoom> ftRoomsMap;
	
	
	List<LLETRoom> etRooms;
	Map<Long,LLETRoom> etRoomsMap;
	
	
	
	List<LLETRoom> etVipRooms;
	Map<Long,LLETRoom> etVipRoomsMap;
	
	
	List<LLBTRoom> btRooms;
	Map<Long,LLBTRoom> btRoomsMap;
	
	
	Map<Long,Item> items;
	
	
	public void destory(){
		
		LLU.clearAllHandlers(this);
		for(LLFTRoom r:ftRooms){
			LLU.clearAllHandlers(r);
			//LLU.removeHandler(r, strings);
		}
		
		for(LLETRoom r:etVipRooms){
			LLU.clearAllHandlers(r);
		}
		
		for(LLETRoom r:etRooms){
			LLU.clearAllHandlers(r);
		}
		for(LLBTRoom r:btRooms){
			LLU.clearAllHandlers(r);
		}
	}
	
	private void log(String message){
		logger.info(GameUtil.formatId(id,this)+" - "+message);
	}

	/**
	 * 
	 */
	public LLGameChannel(Long id,LLGameTournamentConfig config) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.config=config;
		
		// create the free tournament
		
		
		
		log("Loading rooms ....");
		loadFtRooms();
		loadEtRooms();
		loadBtRooms();
		loadEtVipRooms();
		
		//testGL5001();
		
		
		//logger.info("Done!");
		LLU.regeisterHandler(this, "GL5000","GL5001","GL5002","GL5003");
//		GameContextGlobals.registerEventHandler("GL5000", this);
//		GameContextGlobals.registerEventHandler("GL5001", this);
//		GameContextGlobals.registerEventHandler("GL5002", this);
	
		GameUtil.gameLife("numbers://"+id, "0 0/5 * * * *", this, "notifyNumbers").start();
	}
	
	
	private void testGL5000(){

		GL0000Beans.GL5000Request req=LLU.o(GL0000Beans.GL5000Request.class);
		req.setId(Long.valueOf(1));
		
		
		GL0000Beans.LG5000Response resp=wrapFtRoomResp();
		logger.info(GameContextGlobals.getJsonConvertor().format(req));
		logger.info(GameContextGlobals.getJsonConvertor().format(resp));
	}
	
//	private void testGL5001(){
//		GL0000Beans.GL5001Request req=LLU.o(GL0000Beans.GL5001Request.class);
//		req.setId(Long.valueOf(1));
//		req.setVip(false);
//		GL0000Beans.LG5001Response resp=wrapEtRoomResp();
//		
//		
//		logger.info(GameContextGlobals.getJsonConvertor().format(req));
//		logger.info(GameContextGlobals.getJsonConvertor().format(resp));
//		
//	}
	
	private void testGL5002(){
		GL0000Beans.GL5002Request req=LLU.o(GL0000Beans.GL5002Request.class);
		req.setId(Long.valueOf(1));
		//GL0000Beans.BTRoomListResponse resp=wr
	}
	
	
	/**
	 * @return
	 */
	private LG5001Response wrapEtRoomResp(Long userId) {
		// TODO Auto-generated method stub
		
		LG5001Response resp=LLU.o(LG5001Response.class);
		List<ETRoom> rooms=GameUtil.getList();
		resp.setRooms(rooms);
		
		for(EliminationTournamentConfig c:config.getEt()){
			ETRoom room=LLU.o(ETRoom.class);
			room.setRoomId(c.getRoomId());
			logger.info("loading room: "+room.getRoomId());
			room.setRobot(c.isEnableRobot());
			room.setRoomName(c.getRoomName());
			room.setDescription(c.getTournamentDescription());
			
			
			List<PEnrollTicket> tickets=GameUtil.getList();
			room.setEnrollTickets(tickets);
			for(EnrollTicket e:c.getEnrollTickets()){
				PEnrollTicket obj=LLU.o(PEnrollTicket.class);
				Item h=items.get(Long.valueOf(e.getItemId()));
				obj.wrap(h);
				obj.setAmount(e.getItemAmount());
				tickets.add(obj);
				
			}
			
			List<PReward> reward=GameUtil.getList();
			room.setRewards(reward);
			for(Reward r:c.getRewards()){
				PReward obj=LLU.o(PReward.class);
				Item h=items.get(Long.valueOf(r.getItemId()));
				obj.wrap(h);
				obj.setRank(r.getRanking());
				obj.setAmount(r.getItemAmount());
				reward.add(obj);
				
			}
			
			room.setMatchBeginPlayerNumbers(String.valueOf(c.getPlayerNumberControl().getMinPlayerNumber()));
			
			LLETRoom r=etRoomsMap.get(c.getId());

			if(r.gameLife!=null)
				room.setMatchTime(r.gameLife.getFormatedTime());
			else
				room.setMatchTime("");
			LG0014Response lg0014Resp=r.getOnlinePlayerNumbers(userId);
			room.setOnlinePlayerNumbers(String.valueOf(lg0014Resp.getNumbers()));
			room.setTimeup(lg0014Resp.getTimeRemains());
			room.setEnrolled(lg0014Resp.isEnrolled());
			
			
			
			rooms.add(room);
			
			//room.setEnrollTickets(enrollTickets);
		}
		
		return resp;
	}
	
	
	private LG5001Response wrapEtVipRoomResp(Long userId) {
		// TODO Auto-generated method stub
		
		LG5001Response resp=LLU.o(LG5001Response.class);
		List<ETRoom> rooms=GameUtil.getList();
		resp.setRooms(rooms);
		
		for(EliminationTournamentConfig c:config.getVip()){
			ETRoom room=LLU.o(ETRoom.class);
			room.setRobot(c.isEnableRobot());
			room.setRoomId(c.getRoomId());
			room.setRoomName(c.getRoomName());
			room.setDescription(c.getTournamentDescription());
			
			
			List<PEnrollTicket> tickets=GameUtil.getList();
			room.setEnrollTickets(tickets);
			for(EnrollTicket e:c.getEnrollTickets()){
				PEnrollTicket obj=LLU.o(PEnrollTicket.class);
				Item h=items.get(Long.valueOf(e.getItemId()));
				obj.wrap(h);
				obj.setAmount(e.getItemAmount());
				tickets.add(obj);
				
			}
			
			List<PReward> reward=GameUtil.getList();
			room.setRewards(reward);
			for(Reward r:c.getRewards()){
				PReward obj=LLU.o(PReward.class);
				Item h=items.get(Long.valueOf(r.getItemId()));
				obj.wrap(h);
				obj.setAmount(r.getItemAmount());
				obj.setRank(r.getRanking());
				reward.add(obj);
				
			}
			
			room.setMatchBeginPlayerNumbers(String.valueOf(c.getPlayerNumberControl().getMinPlayerNumber()));
			
//			Date matchTime=CronUtil.getScheduledTime(c.getTimePattern().getMatch().getStart());
//			String d=CronUtil.getFormatedDate(matchTime);
//			room.setMatchTime(d);
			
			
			LLETRoom r=etVipRoomsMap.get(c.getId());
//			room.setOnlinePlayerNumbers(String.valueOf(r.onlinePlayerNumbers()));
//			room.setTimeup(800000);
//			room.setEnrolled(false);
			room.setMatchTime(r.gameLife.getFormatedTime());
			LG0014Response lg0014Resp=r.getOnlinePlayerNumbers(userId);
			room.setOnlinePlayerNumbers(String.valueOf(lg0014Resp.getNumbers()));
			room.setTimeup(lg0014Resp.getTimeRemains());
			room.setEnrolled(lg0014Resp.isEnrolled());
			
			
			rooms.add(room);
			
			//room.setEnrollTickets(enrollTickets);
		}
		
		return resp;
	}
	
	
	private LG5002Response wrapBtRoomResp(){
		LG5002Response o=LLU.o(LG5002Response.class);
		List<BTRoom> rooms=GameUtil.getList();
		o.setRooms(rooms);
		
		for(BlockadeTournamentConfig c:config.getBt()){
			BTRoom room=LLU.o(BTRoom.class);
			room.setId(c.getId());
			room.setRoomName(c.getRoomName());
			room.setDescription(c.getTournamentDescription());
			room.setMatchActive(c.getTimeController().getEnroll().getStart());
			room.setMatchExpired(c.getTimeController().getEnroll().getEnd());
			
			
			BTRoomLevel level=LLU.o(BTRoomLevel.class);
			room.setCurrentLevel(level);
			//List<BTRoomLevelDetail> levels=GameUtil.getList();
			//room.setLevels(levels);
			List<PEnrollTicket> enrollTickets=GameUtil.getList();
			room.setEnrollTickets(enrollTickets);
			room.setInitIntegral(c.getInitIntegral());
			
			for(EnrollTicket ticket:c.getEnrollTickets()){
				PEnrollTicket obj=LLU.o(PEnrollTicket.class);
				Item h=items.get(Long.valueOf(ticket.getItemId()));
				obj.wrap(h);
				obj.setAmount(ticket.getItemAmount());
				enrollTickets.add(obj);
			}
//			
//			
//			for(Stage  s:c.getStages()){
//				BTRoomLevelDetail detail=LLU.o(BTRoomLevelDetail.class);
//				//detail.setBase(s.);
//				detail.setLevel(String.valueOf(s.getId()));
//				detail.setIntegral(s.getMinIntegeral());
//				detail.setMatchTime(s.getMatch().getStart());
//				PReward obj=LLU.o(PReward.class);
//				Reward r=s.getReward();
//				Item h=items.get(Long.valueOf(r.getItemId()));
//				obj.wrap(h);
//				obj.setRank(r.getRanking());
//				detail.setReward(obj);
//				
//				levels.add(detail);
//			}
//			
			LLBTRoom r=btRoomsMap.get(c.getId());
			
			room.setOnlinePlayerNumbers(String.valueOf(r.onlinePlayerNumbers()));
			rooms.add(room);
			//room.setOnlinePlayerNumbers();
			
		}
		
		return o;
	}


	private void loadFtRooms(){
		ftRooms=GameUtil.getList();
		List<FreeTournamentConfig> configs=config.getFt();
		for(FreeTournamentConfig c:configs){
			Long id=Long.valueOf(c.getRoomId());
			
			ftRooms.add(new LLFTRoom(id,c));
		}
		// list to map.
		ftRoomsMap=GameUtil.getMap();
		for(LLFTRoom o:ftRooms){
			ftRoomsMap.put(o.getId(), o);
		}
		
	}
	
	private void loadEtRooms(){
		etRooms=GameUtil.getList();
		List<EliminationTournamentConfig> configs=config.getEt();
		for(EliminationTournamentConfig c:configs){
			Long id=Long.valueOf(c.getRoomId());
			etRooms.add(new LLETRoom(id,c));
		}
		
		etRoomsMap=GameUtil.getMap();
		for(LLETRoom o:etRooms){
			etRoomsMap.put(o.getId(), o);
		}
	}
	
	private void loadEtVipRooms(){
		etVipRooms=GameUtil.getList();
		List<EliminationTournamentConfig> configs=config.getVip();
		for(EliminationTournamentConfig c:configs){
			Long id=Long.valueOf(c.getRoomId());
			etVipRooms.add(new LLETRoom(id,c));
		}
		
		etVipRoomsMap=GameUtil.getMap();
		for(LLETRoom o:etVipRooms){
			etVipRoomsMap.put(o.getId(), o);
		}
	}
	
	private void loadBtRooms(){
		btRooms=GameUtil.getList();
		List<BlockadeTournamentConfig> configs=config.getBt();
		for(BlockadeTournamentConfig c:configs){
			// sort the 
			c.init();
			Long id=Long.valueOf(c.getRoomId());
			btRooms.add(new LLBTRoom(id, c));
		}
		
		btRoomsMap=GameUtil.getMap();
		for(LLBTRoom o:btRooms){
			btRoomsMap.put(o.getId(), o);
		}
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private GL0000Beans.LG5000Response wrapFtRoomResp(){
		GL0000Beans.LG5000Response resp=LLU.o(GL0000Beans.LG5000Response.class);
		List<FtRoom> rooms=GameUtil.getList();
		resp.setRooms(rooms);
		for(FreeTournamentConfig ftc:config.getFt()){
			FtRoom o=LLU.o(FtRoom.class);
			o.setBase(ftc.getBase());
			o.setId(ftc.getId());
			o.setName(ftc.getRoomName());
			o.setRestrict(String.format("%d金币", LLU.exp(ftc, "enrollRestrict.minCoins")));
			LLFTRoom room=ftRoomsMap.get(ftc.getId());
			int numberOnline=room.onlinePlayerNumbers();
			
			o.setOnlinePlayers(numberOnline);
			rooms.add(o);
		}
		
		return resp;
	}
	
	private synchronized void loadItems(){
		//load items
		if(items==null){
				List<Item> heads=GSPP.getItems();
				items=GameUtil.getMap();
				
				for(Item h:heads){
					items.put(Long.valueOf(h.getId()), h);
				}
		}
	}
	
	@RegisterEventHandler(transcode="GL5000")
	public void gl5000EventHandle(MinaEvent evt){
		// return the area list.
		//GL0000Beans.FTRoomListRequest obj=LLU.evtAsObj(evt);
		loadItems();
		// request object don't use
		BasePlayer basePlayer=GSPP.getPlayer(evt.token);
		basePlayer.setChannelId(this.id);
		
		GL0000Beans.LG5000Response resp=wrapFtRoomResp();
		
		
		sendBrokerMessage(basePlayer.getUserId(),resp, false);
	}
	
	
	@RegisterEventHandler(transcode="GL5001")
	public void gl5001EventHandle(MinaEvent evt){
		// return the area list.
		//GL0000Beans.FTRoomListRequest obj=LLU.evtAsObj(evt);
		try {
			loadItems();
			// request object don't use
			BasePlayer basePlayer=GSPP.getPlayer(evt.token);
			basePlayer.setChannelId(this.id);
			GL0000Beans.GL5001Request obj=LLU.evtAsObj(evt);
			
			GL0000Beans.LG5001Response resp=obj.isVip()?wrapEtVipRoomResp(basePlayer.getUserId()):wrapEtRoomResp(basePlayer.getUserId());
			resp.setVip(obj.isVip());
			sendBrokerMessage(basePlayer.getUserId(),resp, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@RegisterEventHandler(transcode="GL5002")
	public void gl5002EventHandle(MinaEvent evt){
		// return the area list.
		//GL0000Beans.FTRoomListRequest obj=LLU.evtAsObj(evt);
		loadItems();
		// request object don't use
		BasePlayer basePlayer=GSPP.getPlayer(evt.token);
		basePlayer.setChannelId(this.id);
		// for one player the data should different.
		GL0000Beans.LG5002Response resp=wrapBtRoomResp();
		sendBrokerMessage(basePlayer.getUserId(),resp, false);
	}
	
	@RegisterEventHandler(transcode="GL5003")
	public void gl5003EventHandler(MinaEvent evt){
		
		BasePlayer basePlayer=GSPP.getPlayer(evt.token);
		basePlayer.setChannelId(this.id);
		gl5003(basePlayer.getUserId());
	}
	
	
	public void notifyNumbers(){
		for(BasePlayer p:ProtocolGlobalContextRemote.instance().getOnlinePlayers()){
			if(p.getChannelId()==this.id){
				gl5003(p.getUserId());
			}
		}
	}
	public void gl5003(long userId){
		GL0000Beans.LG5003Response resp=LLU.o(LG5003Response.class);
		int numbers=0;
		for(FreeTournamentConfig ftc:config.getFt()){
			LLFTRoom room=ftRoomsMap.get(ftc.getId());
			numbers=numbers+(room.onlinePlayerNumbers());
			
		}
		resp.setFtNumbers(numbers);
		numbers=0;
		for(EliminationTournamentConfig c:config.getVip()){
			
			LLETRoom r=etVipRoomsMap.get(c.getId());
			numbers=numbers+r.onlinePlayerNumbers();
		}
		
		resp.setEtVipNumbers(numbers);
		numbers=0;
		for(EliminationTournamentConfig c:config.getEt()){
			
			LLETRoom r=etRoomsMap.get(c.getId());
			numbers=numbers+r.onlinePlayerNumbers();
		}
		
		resp.setEtNumbers(numbers);
		numbers=0;	
		
		for(BlockadeTournamentConfig c:config.getBt()){
			LLBTRoom r=btRoomsMap.get(c.getId());
			numbers=numbers+r.onlinePlayerNumbers();
		}
		
		resp.setBtNumbers(numbers);
		numbers=0;
		sendBrokerMessage(userId,resp, false);
	}
	
	public  void  sendBrokerMessage(long playerId,Object obj, boolean request) {
		MinaEvent evt=MinaEvent.obtainMinaEvent(playerId, obj,request);
		// TODO Auto-generated method stub
		evt.sendMessage();
	}
	
	
	
	public JmxRoom getJmxRoom(long roomId,boolean vip){
		Long k=Long.valueOf(roomId);
		//JmxRoom o=LLU.o(JmxRoom.class);
		LLETRoom r=vip==false?etRoomsMap.get(k):etVipRoomsMap.get(k);
		
		return r.getJmxRoom();
	}
	
	
	public void destroyTeam(long roomId,boolean vip,long teamId){
		Long k=Long.valueOf(roomId);
		//JmxRoom o=LLU.o(JmxRoom.class);
		LLETRoom r=vip==false?etRoomsMap.get(k):etVipRoomsMap.get(k);
		LLTeam team=r.teams.get(Long.valueOf(teamId));
		team.destroy();
	}
	
	public String teamSorted(long roomId,boolean vip,long teamId){
		Long k=Long.valueOf(roomId);
		//JmxRoom o=LLU.o(JmxRoom.class);
		LLETRoom r=vip==false?etRoomsMap.get(k):etVipRoomsMap.get(k);
		LLTeam team=r.teams.get(Long.valueOf(teamId));
		LLGamePlayer[]  players=team.getSortedGamePlayerInTeam(false);
		//team.destroy();
		StringBuffer buf=new StringBuffer();
		for(LLGamePlayer p:players){
			buf.append("(player=").append(p.id).append(",chips=").append(p.chips).append(",rank=").append(p.rank).append(")\n");
		}
		
		return buf.toString();
	}
	
	public String checkGame(long roomId,long userId){
		String xml=null;
		Long k=Long.valueOf(roomId);
	//	LLBTRoom r=btRoomsMap.get(k);
		BasePlayer p=GSPP.getPlayerById(userId);
		LLGamePlayer llp=p.getGamePlayer(k);
		LLDeck deck=llp.getObject(llp.deck);
		
		
			if(deck.game!=null){
			JmxGame game=deck.game.getJmxGame();
			if(game!=null){
				xml=GameContextGlobals.getXmlJsonConvertor().format(game);
			}
			}
		
		return xml;
	}
	
	
	public void destoryGame(long roomId,boolean vip,long teamId,long deckId){
		Long k=Long.valueOf(roomId);
		//JmxRoom o=LLU.o(JmxRoom.class);
		LLETRoom r=vip==false?etRoomsMap.get(k):etVipRoomsMap.get(k);
		LLTeam team=r.teams.get(Long.valueOf(teamId));
		team.endGame(deckId);
	}
	
	
 
}
