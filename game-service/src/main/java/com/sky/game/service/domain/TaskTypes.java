package com.sky.game.service.domain;

/**
 * 
 * @author sparrow
 *
 */
public enum TaskTypes {
	Undefined(0,"",0),
	
	TotalTask1(3,"在任意赛场完成5局游戏",0),
	TotalTask2(4,"在任意赛场完成10局游戏",0),
	TotalTask3(5,"在任意赛场完成20局游戏",0),
	TotalTask4(6,"在任意赛场完成50局游戏",0),
	
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
	TotalTask6(12,"在注册第一周内完成300局游戏",0),
	
	TotalTask7(15,"在注册第一周内完成50场淘汰赛，并晋级半决赛",0),

	IndianaTask1(8,"夺宝场1000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask2(9,"夺宝场2000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask3(10,"夺宝场5000金币场完成一场比赛并晋级半决赛",0),
	IndianaTask4(11,"夺宝场10000金币场完成一场比赛并晋级半决赛",0),
	
	TotalTask5(1,"任意赛场获得一次冠军",0),
	Task(30,"完成一次闯关赛",0)
	
	
	;
	public int type;
	public String description;
	public int roomId;
	/**
	 * @param value
	 * @param description
	 */
	private TaskTypes(int type, String description,int roomId) {
		this.type = type;
		this.description = description;
		this.roomId=roomId;
	}
	
	
	
	
	
	
	
	
	
	public static TaskTypes types[]={Undefined,
		TotalTask5,
		Undefined,
		TotalTask1,
		TotalTask2,
		TotalTask3,
		TotalTask4,
		Undefined,
		IndianaTask1,
		IndianaTask2,
		IndianaTask3,
		IndianaTask4,
		TotalTask6,
		Undefined,
		Undefined,
		TotalTask7,
		ArderTask1,
		ArderTask2,
		ArderTask3,
		ArderTask4,
		ArderTask5,
		ArderTask6,
		ArderTask7,
		ArderTask8,
		ArderTask9,
		ArderTask10,
		ArderTask11,
		ArderTask12,
		ArderTask13,
		ArderTask14,
		Task};
	
	public static TaskTypes getTaskTypes(int loc){
		return types[loc];
	}
}
	


