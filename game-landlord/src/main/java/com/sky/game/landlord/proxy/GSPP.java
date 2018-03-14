/**
 * @sparrow
 * @Dec 29, 2014   @1:19:46 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.proxy;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.landlord.room.LLU;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GS0000Beans;
import com.sky.game.protocol.commons.GS0000Beans.GS0013Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0016Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0021Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0022Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0026Request;
import com.sky.game.protocol.commons.GS0000Beans.PGameBlockadeUser;
import com.sky.game.protocol.commons.GS0000Beans.SG0011Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0019Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0020Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0022Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0026Response;
import com.sky.game.protocol.commons.GU0002Beans;
import com.sky.game.protocol.commons.GS0000Beans.GS0009Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0010Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0011Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0012Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0014Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0017Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0018Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0019Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0020Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0023Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0024Request;
import com.sky.game.protocol.commons.GS0000Beans.GS0025Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0009Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0010Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0016Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0017Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0018Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0021Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0023Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0024Response;
import com.sky.game.protocol.commons.GS0000Beans.SG0025Response;
import com.sky.game.protocol.commons.GT0001Beans.State;
import com.sky.game.protocol.handler.GU0002Handler;
import com.sky.game.protocol.handler.game.GS0001Handler;
import com.sky.game.protocol.handler.game.GS0004Handler;
import com.sky.game.protocol.handler.game.GS0005Handler;
import com.sky.game.protocol.handler.game.GS0006Handler;
import com.sky.game.protocol.handler.game.GS0007Handler;
import com.sky.game.protocol.handler.game.GS0008Handler;
import com.sky.game.protocol.handler.game.GS0009Handler;
import com.sky.game.protocol.handler.game.GS0010Handler;
import com.sky.game.protocol.handler.game.GS0011Handler;
import com.sky.game.protocol.handler.game.GS0012Handler;
import com.sky.game.protocol.handler.game.GS0013Handler;
import com.sky.game.protocol.handler.game.GS0014Handler;
import com.sky.game.protocol.handler.game.GS0016Handler;
import com.sky.game.protocol.handler.game.GS0017Handler;
import com.sky.game.protocol.handler.game.GS0018Handler;
import com.sky.game.protocol.handler.game.GS0019Handler;
import com.sky.game.protocol.handler.game.GS0022Handler;
import com.sky.game.protocol.handler.game.GS0023Handler;
import com.sky.game.protocol.handler.game.GS0024Handler;
import com.sky.game.protocol.handler.game.GS0025Handler;
import com.sky.game.protocol.handler.game.GS0026Handler;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStaticsTypes;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.domain.GameSystemMessage;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.TaskTypes;
import com.sky.game.service.domain.UserAchievementStates;
import com.sky.game.service.domain.UserLandlordStates;
import com.sky.game.service.logic.game.GameUserItemService;
import com.sky.game.service.logic.game.GameUserPropertyService;

/**
 * GameSystemProtocolProxy
 * 
 * 
 * @author sparrow
 *
 */
public class GSPP {
	private static final Log logger=LogFactory.getLog(GSPP.class);

	/**
	 * 
	 */
	public GSPP() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * wrap the {@link GS0001Handler}
	 * @return list of item
	 */
	public static List<Item> getItems(){
		List<Item> items=null;
		GS0000Beans.GS0001Request req=LLU.o(GS0000Beans.GS0001Request.class);
		GS0000Beans.SG0001Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			items=resp.getItems();
		}
		
		return items;
		
	}
	
	public static BasePlayer getPlayerById(long id){
		BasePlayer player=null;
		try {
			player=ProtocolGlobalContextRemote.instance().getOnlinePlayer(id);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return player;
		
	}
	
	public static boolean isPlayerOnline(long id){
		return ProtocolGlobalContextRemote.instance().isOnline(Long.valueOf(id));
	}
	
	
	public static BasePlayer getPlayer(String token){
		BasePlayer player=null;
		try {
			player=ProtocolGlobalContextRemote.instance().getOnlinePlayer(token);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return player;
		
	}
	
	/**
	 * wrap the {@link GU0002Handler}
	 * @param username
	 * @param password
	 * @param deviceId
	 * @return
	 */
	public static long login(String username,String password,String deviceId){
		GU0002Beans.Request req=LLU.o(GU0002Beans.Request.class);
		req.setAccount(username);
		req.setDeviceId(deviceId);
		req.setPassword(password);
		GU0002Beans.Response resp=ProxyMessageInvoker.invoke(req);
		long userid=-1;
		if(resp!=null){
			userid=resp.getId();
		}
		
		return userid;
	}
	
	/**
	 * wrap the {@link GS0004Handler}
	 * get user information
	 * @param userId
	 * @return
	 */
	public static GameUser getGameUser(long userId){
		GameUser user=null;
		GS0000Beans.GS0004Request req=LLU.o(GS0000Beans.GS0004Request.class);
		req.setUserId(userId);
		GS0000Beans.SG0004Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			user=resp.getUser();
		}
		return user;
	}
	
	
	/**
	 * wrap the {@link GS0005Handler}
	 * @param userId
	 * @param min
	 * @return
	 */
	public static boolean validEnroll(long userId,int itemId,int min){
		boolean valid=false;
		GS0000Beans.GS0005Request req=LLU.o(GS0000Beans.GS0005Request.class);
		req.setUserId(userId);
		req.setMin(min);
		req.setItemId(itemId);
		GS0000Beans.SG0005Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			valid=resp.isValid();
		}
		
		return valid;
	}
	
	/**
	 * wrap the {@link GS0006Handler}
	 * enroll the game.
	 * 
	 * @param userId
	 * @param roomId
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public static boolean enroll(long userId,long roomId,long teamId,long itemId,int amount){
		boolean ret=false;
		GS0000Beans.GS0006Request req=LLU.o(GS0000Beans.GS0006Request.class);
		
		req.setUserId(userId);
		req.setTeamId(teamId);
		req.setItemId(itemId);
		req.setAmount(-1*amount);
		req.setRoomId(roomId);
		GS0000Beans.SG0006Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			ret=resp.isValid();
		}
		
		return ret;
		
	}
	
	
	/**
	 * wrap the {@link GS0007Handler}
	 * reward items
	 * @param userId
	 * @param roomId
	 * @param rank
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public static boolean reward(long userId,long roomId,long teamId,int rank,long itemId,int amount){
		boolean valid=false;
		GS0000Beans.GS0007Request req=LLU.o(GS0000Beans.GS0007Request.class);
		req.setUserId(userId);
		req.setTeamId(teamId);
		req.setRoomId(roomId);
		req.setRank(rank);
		req.setItemId(itemId);
		req.setAmount(amount);
		
		GS0000Beans.SG0007Response resp=ProxyMessageInvoker.invoke(req);
		
		if(resp!=null){
			valid=resp.isValid();
		}
		
		return valid;
	}
	
	/**
	 * wrap the {@link GS0008Handler}
	 * updateExp
	 * 
	 * @param userId
	 * @param roomId
	 * @param exp
	 * @return
	 * @see PropertyTypes
	 */
	public static boolean addExp(long userId,long roomId,int exp){
		boolean ret=false;
		GS0000Beans.GS0008Request req=LLU.o(GS0000Beans.GS0008Request.class);
		req.setRoomId(roomId);
		req.setUserId(userId);
		req.setPropertyId(PropertyTypes.Experience.id);
		req.setPropertyValue(Integer.valueOf(exp));
		GS0000Beans.SG0008Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			ret=resp.isValid();
		}
		
		return ret;
		
	}
	
	/**
	 * wrap the {@link GS0009Handler}
	 * @param roomId
	 * @param level
	 * @return
	 * @see PGameBlockadeUser
	 */
	public static List<PGameBlockadeUser> getGameBlockadeUsers(Long roomId,Long level){
		List<PGameBlockadeUser> users=null;
		GS0009Request req=GS0009Request.obtain(roomId, level);
		SG0009Response resp=ProxyMessageInvoker.invoke(req);
		if(resp!=null){
			users=resp.getUsers();
		}
		return users;
		
	}
	
	
	/**
	 * wrap the {@link GS0010Handler}
	 * @param userId
	 * @return blockade user information.
	 * @see PGameBlockadeUser
	 */
	public static PGameBlockadeUser getGameBlockadeUser(Long userId,Long roomId){
		PGameBlockadeUser user=null;
		
		GS0010Request request=GS0010Request.obtain(roomId, userId);
		SG0010Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			user=resp.getUser();
		}
		
		return user;
	}
	
	/**
	 * wrap the {@link GS0011Handler}
	 *
	 * @param userId player userid
	 * @param roomId blockade room id
	 * @param value  player blockade integral changes.
	 * @param level  player current level.
	 * @return if update the blockade integral success.
	 */
	public static boolean updateBlockadeIntegral(Long userId,Long roomId,Long value,Integer level){
		boolean ret=false;
		
		GS0011Request request=GS0011Request.obtain(roomId, userId, value, level);
		SG0011Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			ret=resp.isValid();
		}
		
		return ret;
	}
	
	/**
	 * GS0012 -
	 * <p>
	 * wrap the {@link GS0012Handler}
	 * @param userId
	 * @param types
	 * @param rate
	 */
	public static void updateTaskRate(long userId,int taskId,int rate){
		//TaskTypes types=TaskTypes.getTaskTypes(taskId);
		GS0012Request request=GS0012Request.obtain(userId, State.obtain((int)taskId, ""), rate);
		ProxyMessageInvoker.invoke(request);
	}
	
	
	/**
	 * GS0013 -
	 * wrap the {@link GS0013Handler }
	 * @param userId
	 * @param types
	 */
	public static void updateGameDataStatics(long userId,GameDataCategoryTypes category,GameDataStaticsTypes types){
		GS0013Request request=GS0013Request.obtain(userId, State.obtain(types.type, types.toString()),State.obtain(category.type, category.toString()));
		ProxyMessageInvoker.invoke(request);
	}
	
	/**
	 * GS0014 -
	 * 	update the user achivement {@link GS0014Handler}
	 * 
	 * @param userId
	 * @param achievementState
	 * @param landlord
	 * @see UserAchievementStates
	 * @see UserLandlordStates
	 */
	public static void updateGameAchievement(long userId,UserAchievementStates achievementState,UserLandlordStates landlord)
	{
		GS0014Request request=GS0014Request.obtain(userId, State.obtain(achievementState.state, achievementState.message), State.obtain(landlord.state, landlord.message));
		ProxyMessageInvoker.invoke(request);
	}
	
	

	
	/**
	 * <p>
	 * {@link GS0016Handler}
	 * {@link GS0017Handler}
	 * @see GS0016Request
	 * @see SG0016Response
	 * @see ProxyMessageInvoker
	 * <p>
	 * @param roomId  
	 * @return  List<GameEnroll>
	 */
	public static List<GameEnroll> getGameEnrollByRoomId(Long roomId){
		List<GameEnroll> gameEnrolls=null;
		GS0016Request request=LLU.o(GS0016Request.class);
		request.setRoomId(roomId);
		SG0016Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			gameEnrolls=resp.getGameEnrolles();
		}
		return gameEnrolls;
	}
	
	/**
	 * <p>
	 * {@link GS0017Handler}
	 * 
	 * @see GS0017Request
	 * @see SG0017Response
	 * <p>
	 * @param userId
	 * @return
	 */
	public static List<GameEnroll> getGameEnrollByUserId(Long userId){
		List<GameEnroll> gameEnrolls=null;
		GS0017Request request=LLU.o(GS0017Request.class);
		SG0017Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			gameEnrolls=resp.getGameEnrolles();
		}
		
		return gameEnrolls;
	}
	
	/**
	 * <p>
	 * {@link GS0018Handler}
	 * 
	 * @see GS0018Request
	 * @see SG0018Response
	 * <p>
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public static GameEnroll getGameEnrollByUserIdAndRoomId(Long userId,Long roomId){
		GameEnroll obj=null;
		GS0018Request request=LLU.o(GS0018Request.class);
		request.setRoomId(roomId);
		request.setUserId(userId);
		SG0018Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			obj=resp.getGameEnroll();
		}
		
		return obj;
	}
	
	
	/**
	 * <p>
	 * {@link GS0019Handler}
	 *
	 * @see GS0019Request
	 * @see SG0019Response
	 * <p>
	 * @param id
	 * @return
	 */
	public static boolean updateGameEnrollState(Long id){
		boolean obj=false;
		GS0019Request request=LLU.o(GS0019Request.class);
		request.setId(id);
		SG0019Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			obj=resp.isValid();
		}
		
		
		return obj;
	}
	
	/**
	 * <p>
	 *  {@link GS0020Handler}
	 *  
	 *  @see GS0020Request
	 *  @see SG0020Response
	 * <p>
	 * @param id
	 * @param teamId
	 * @return
	 */
	public static boolean updateGameEnrollByTeamId(Long id,Long teamId){
		boolean obj=false;
		GS0020Request request=LLU.o(GS0020Request.class);
		request.setId(id);
		request.setTeamId(teamId);
		SG0020Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			obj=resp.isValid();
		}
		return obj;
	}
	
	/**
	 * 
	 * <p>
	 * {@link GS0021Handler}
	 * @see GS0021Request
	 * @see SG0021Response
	 * <p>
	 * @param gbuId 
	 * @return
	 */
	public static int getUnreadMessageCount(Long gbuId){
		int obj=0;
		GS0021Request request =LLU.o(GS0021Request.class);
		request.setGbuToId(gbuId);
		SG0021Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			obj=resp.getTotal()==null?0:resp.getTotal().intValue();
		}
		return obj;
	}
	
	
	/**
	 * {@link GS0022Handler}
	 * @param message
	 * @see GS0022Request
	 * @see SG0022Response
	 */
	public static void sendSystemMessage(GameSystemMessage message){
		GS0022Request o=GS0022Request.wrap(message);
		ProxyMessageInvoker.invoke(o);
	}
	
	/**
	 * {@link GS0023Handler}
	 * @param id
	 * @param states
	 * @return
	 */
	public static boolean updateBlockadeUserStatus(Long id,int status){
		boolean ret=false;
		GS0023Request request=LLU.o(GS0023Request.class);
		request.setId(id);
		request.setStatus(Integer.valueOf(status));
		SG0023Response  resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			 ret=resp.isValid();
		}
		return ret;
	}
	
	/**
	 * {@link GS0024Handler#handler(GS0024Request, SG0024Response)}
	 * @param roomId
	 * @param level
	 * @return
	 */
	public static int selectBlockadeUserCountByRoomIdAndLevel(Long roomId,Integer level){
		int rows=0;
		GS0024Request request=LLU.o(GS0024Request.class);
		request.setRoomId(roomId);
		request.setLevel(level);
		SG0024Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			rows=resp.getCount();
		}
		return rows;
		
	}
	
	/**
	 * {@link GS0025Handler#handler(GS0025Request, SG0025Response)}
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public static boolean unEnroll(Long userId,Long roomId){
		boolean ret=false;
		GS0025Request request=LLU.o(GS0025Request.class);
		request.setRoomId(roomId);
		request.setUserId(userId);
		SG0025Response resp=ProxyMessageInvoker.invoke(request);
		if(resp!=null){
			ret=resp.isValid();
		}
		return ret;
		
	}
	
	
	/**
	 * {@link GS0026Handler#handler(GS0026Request, SG0025Response)}
	 * {@link GameUserPropertyService#updateUserProperty(Long, Long, Integer, com.sky.game.service.domain.UserLogTypes)}
	 * @return
	 */
	public static boolean updateUserProperty(long userId,int itemId,int itemValue){
		boolean ret=false;
		GS0026Request request=LLU.o(GS0026Request.class);
		request.setItemId(Integer.valueOf(itemId));
		request.setItemValue(Integer.valueOf(itemValue));
		request.setUserId(Long.valueOf(userId));
		
		SG0026Response resp=ProxyMessageInvoker.invoke(request);
		
		if(resp!=null){
			ret=resp.isValid();
		}
		
		return ret;
	}
	
	
}
