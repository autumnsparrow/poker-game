/**
 * @sparrow
 * @Mar 13, 2015   @1:45:34 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.achievement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sky.game.service.logic.ShopService;

/**
 * @author sparrow
 *
 */
public class TaskIdAndRoomIdMapper {

	
	/**
	 * 
	 */
	public TaskIdAndRoomIdMapper() {
		// TODO Auto-generated constructor stub
	}
	
	/*TotalTask1(3,"在任意赛场完成5局游戏",0),
	TotalTask2(4,"在任意赛场完成10局游戏",0),
	TotalTask3(5,"在任意赛场完成20局游戏",0),
	TotalTask4(6,"在任意赛场完成50局游戏",0),
	*/
	public static final Integer[]  anyTournamentsGameEnd={Integer.valueOf(12),Integer.valueOf(5)};
	public static final Integer[]  anyTournamentsGameEnd1={Integer.valueOf(12),Integer.valueOf(5),Integer.valueOf(32),Integer.valueOf(36),
		Integer.valueOf(37),Integer.valueOf(38),Integer.valueOf(39),Integer.valueOf(40),Integer.valueOf(41),};
	public static final Map<Integer,Integer[]> roomIdGameEnd=new HashMap<Integer, Integer[]>();
	/**
	 ArderTask1(16,"初来乍到场获胜20局",0),
	ArderTask2(17,"初来乍到场获胜30局",0),
	ArderTask3(18,"初来乍到场获胜50局",0),
	
	ArderTask4(19,"风光无限场获胜20局",0),
	ArderTask5(20,"风光无限场获胜30局",0),
	ArderTask6(21,"风光无限场获胜50局",0),
	
	ArderTask7(22,"平步青云场获胜20局",0),
	ArderTask8(23,"平步青云场获胜30局",0),
	ArderTask9(24,"平步青云场获胜50局",0),
	ArderTask10(25,"平步青云场获胜100局",0),
	
	ArderTask11(26,"一鸣惊人场获胜20局",0),
	ArderTask12(27,"一鸣惊人场获胜30局",0),
	ArderTask13(28,"一鸣惊人场获胜50局",0),
	ArderTask14(29,"一鸣惊人场获胜100局",0),
	 * 
	 */
	
	static{
		roomIdGameEnd.put(Integer.valueOf(1000), new Integer[]{
			Integer.valueOf(16),Integer.valueOf(17),Integer.valueOf(18)
		});
		
		roomIdGameEnd.put(Integer.valueOf(1001), new Integer[]{
			Integer.valueOf(19),Integer.valueOf(20),Integer.valueOf(21)
		});
		
		roomIdGameEnd.put(Integer.valueOf(1002), new Integer[]{
			Integer.valueOf(22),Integer.valueOf(23),Integer.valueOf(24),Integer.valueOf(25)
		});
		
		roomIdGameEnd.put(Integer.valueOf(1003), new Integer[]{
			Integer.valueOf(26),Integer.valueOf(27),Integer.valueOf(28),Integer.valueOf(29)
		});
	}
	
	
	private static int lengthOfArrays(Integer [] a){
		return a!=null?a.length:0;
	}
	
	
	private static Integer[]  add(Integer[]  one,Integer[]  two){
		int size=(one!=null?one.length:0)+(two!=null?two.length:0);
		Integer[]  a=new Integer[size];
		int offset=0;
		int length=lengthOfArrays(one);
		if(length>0){
			System.arraycopy(one, 0, a, 0, length);
			offset=length;
		}
		length=lengthOfArrays(two);
		if(length>0){
			System.arraycopy(two, 0, a, offset, length);
		}
		return a;
	}
	
	public static Integer[]  getGameEndTaskIdByRoomId(int roomId){
		Integer[] ret=null;
		Integer [] taskIds=roomIdGameEnd.get(Integer.valueOf(roomId));
		
		ret=taskIds;//add(taskIds, anyTournamentsGameEnd);
		
		return ret;
	}
	
	
	/**
	 * 
	 TotalTask7(15,"在注册第一周内完成50场淘汰赛，并晋级半决赛",0),

	IndianaTask1(8,"夺宝场1000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask2(9,"夺宝场2000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask3(10,"夺宝场5000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask4(11,"夺宝场10000金币场完成一场比赛并晋级半决赛",0),
	 * 
	 */
	private static final Integer[]  anyHalfFinalEliminationTournament={Integer.valueOf(15)};
	private static final Map<Integer,Integer[]> halfFinalEliminationTournament=new HashMap<Integer, Integer[]>();
	static{
		halfFinalEliminationTournament.put(Integer.valueOf(3004),new Integer[]{Integer.valueOf(11)} );
		halfFinalEliminationTournament.put(Integer.valueOf(2006),new Integer[]{Integer.valueOf(10)} );
		halfFinalEliminationTournament.put(Integer.valueOf(2005),new Integer[]{Integer.valueOf(9)} );
		halfFinalEliminationTournament.put(Integer.valueOf(2003),new Integer[]{Integer.valueOf(8)} );
		
	}
	
	public static Integer[]  getGameHalfFinalByRoomId(int roomId){
		Integer[] ret=add(anyHalfFinalEliminationTournament,halfFinalEliminationTournament.get(Integer.valueOf(roomId)));
		return ret;
	}

}
