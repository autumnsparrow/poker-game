/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 11:37:03 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * 
 * 
 * 
 +----+--------------+--------------------------------------------------------------------------------------+---------+------+-----------+
| id | name         | description                                                                          | icon_id | type | item_type |
+----+--------------+--------------------------------------------------------------------------------------+---------+------+-----------+
|  1 | 金币         | 金币                                                                                 |      25 |    3 |         1 |
|  2 | 蓝宝石       | 使用红宝石可以报名夺宝大师赛                                                         |      14 |    3 |         2 |
|  3 | 紫水晶       | 使用紫水晶可以报名夺宝大师赛                                                         |      24 |    3 |         2 |
|  4 | 绿宝石       | 使用绿宝石可以报名夺宝大师赛                                                         |      17 |    3 |         2 |
|  5 | 元宝         | 元宝可通过打比赛获得，收集元宝可在兑换中心兑换实物奖品。                             |      22 |    3 |         3 |
|  6 | iphone6      | iphone6实物奖品                                                                      |      12 |    2 |         5 |
|  7 | 液晶电视     | 液晶电视奖品                                                                         |      21 |    2 |         5 |
|  8 | ipad         | ipad奖品                                                                             |      10 |    2 |         5 |
|  9 | iphone5s     | iphone5s奖品                                                                         |      11 |    2 |         5 |
| 10 | 小米M4       | 小米手机奖品                                                                         |      19 |    2 |         5 |
| 11 | iphone60     | 实物iphone60                                                                         |      13 |    2 |         5 |
| 12 | 5元话费      | 物品可以为手机充值五元话费                                                           |       3 |    1 |         5 |
| 13 | 50元话费     | 物品可以为手机充值五十元话费                                                         |       6 |    1 |         5 |
| 14 | 100元话费    | 物品可以为手机充值一百元话费                                                         |       7 |    1 |         5 |
| 15 | 200元话费    | 物品可以为手机充值二百元话费                                                         |       8 |    1 |         5 |
| 16 | 小黄鸭       | 萌萌的                                                                               |      20 |    2 |         5 |
| 17 | tu1          | 男头像                                                                               |      30 |    3 |         8 |
| 18 | tu2          | 男头像                                                                               |      31 |    3 |         8 |
| 19 | tu3          | 女头像                                                                               |      32 |    3 |         8 |
| 20 | tu4          | 女头像                                                                               |      33 |    3 |         8 |
| 21 | tu5          | 女头像                                                                               |      34 |    3 |         8 |
| 22 | tu6          | 男头像                                                                               |      35 |    3 |         8 |
| 23 | 10元话费     | 物品可以为手机充值十元话费                                                           |       2 |    1 |         5 |
| 24 | vip1         | vip头像                                                                              |      36 |    3 |         6 |
| 25 | vip2         | vip头像                                                                              |      37 |    3 |         6 |
| 26 | common1      | 普通头像                                                                             |      38 |    3 |         7 |
+----+--------------+--------------------------------------------------------------------------------------+---------+------+-----------+
 * 
 * @author sparrow
 * 
 * 
 | 1001 | 金币                          | 平台通用货币，可用于报名比赛，购买物品。钱不是万能的，但没钱却是万万不能的                                                            |      25 |    3 |         1 |
| 1002 | 元宝                          | 元宝可以兑换很多精美的奖品,想要拥有它，只有参加比赛一条途径。                                                                         |      22 |    4 |         1 |
| 1003 | 钻石                          | 钻石可用于报名超级闯关赛和购买道具，钻石很久远，一颗永流传。                                                                          |      41 |    5 |         1 |
| 1004 | 奖券                          | 奖券                                                                                                                                  |      72 |    5 |         1 |
| 2500 | 绿宝石                        | 价值100金币，可用于报名比赛，充满生命气息的宝石，有个神秘组织将它作为赛场凭证。                                                       |      17 |    6 |         2 |
| 2501 | 蓝宝石                        | 价值500金币，可用于报名比赛，深海的瑰宝，价值连城，曾有人用它来报名神秘赛场的竟赛。                                                   |      14 |    6 |         2 |
| 2502 | 红宝石                        | 价值1000金币，可用于报名比赛，有人曾在火山口捡到一颗，凭之进入了神秘赛场。                                                            |      52 |    6 |         2 |
| 2503 | 水晶                          | 价值2000金币，可用于报名比赛，最早使用它的人已然不可查，但持有它，你可以顺利进入神秘赛场。                                            |      53 |    6 |         2 |
| 2504 | 蓝水晶                        | 价值5000金币，可用于报名比赛，曾有人用5000金币购得一颗，持之顺利进入了神秘赛场。                                                      |      15 |    6 |         2 |
| 2505 | 紫水晶  
 *
 */
public enum ItemTypes {
	Undefined(-1),
	Coin(1001),
	Gold(1002),
	BlueCrystal(2504),
	PurpleCrystal(2505),
	GreenDiamond(1003),
	;
	
	public int id;

	/**
	 * @param id
	 */
	private ItemTypes(int id) {
		this.id = id;
	}
	
	public boolean eq(ItemTypes t){
		return id==t.id;
	}
	
	public static ItemTypes getItemTypes(int loc){
		ItemTypes types=Undefined;
		switch (loc) {
		case 1001:
			types=Coin;
			break;
		case 2504:
			types=BlueCrystal;
			break;
		case 2505:
			types=PurpleCrystal;
			break;
		case 1003:
			types=GreenDiamond;
			break;
		case 1002:
			types=Gold;
			break;
		default:
			types=Undefined;
			break;
		}
		return types;
	}
	

}
