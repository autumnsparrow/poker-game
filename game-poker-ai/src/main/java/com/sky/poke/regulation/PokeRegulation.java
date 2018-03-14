package com.sky.poke.regulation;

import java.util.List;

import com.sky.poke.bean.Poke;
import com.sky.poke.solution.CardSolution;


// 出牌规则
public interface PokeRegulation {
	/**
	 * 未知
	 */
	int UNKNOWN = -1;
	/**
	 * 未出牌
	 */
	int EMPTY = 0;
	/**
	 *  火箭
	 */
	int ROCKET = 1;
	/**
	 *  炸弹
	 */
	int BOMB = 2;
	/**
	 *  单牌
	 */
	int SINGLE = 3;
	/**
	 *  对牌
	 */
	int PAIR = 4;
	/**
	 *  三张牌
	 */
	int THREE = 5;
	/**
	 * 三带一
	 */
	int THREE_ONE = 6;
	/**
	 * 单顺
	 */
	int STRAIGHT = 7;
	/**
	 * 双顺
	 */
	int STRAIGHT_PAIR = 8;
	/**
	 * 三顺
	 */
	int STRAIGHT_THREE = 9;
	/**
	 * 飞机带翅膀
	 */
	int AIRCRAFT = 10;
	/**
	 * 四带二
	 */
	int FOUR_TWO = 11;
	/**
	 * 地主 上手出牌是农民
	 */
	int ROLE_LOADOWNER=1;
	/**
	 * 农民 上手出牌是地主
	 */
	int ROLE_FARMER_FORLOADOWDER=2;
	/**
	 * 农民 上手出牌是农民
	 */
	int ROLE_FARMER_FORFARMER=3;
	
	
	
	
	/**
	 * 判断出牌规则
	 * showValues 需要按照牌的大小来排序
	 * appears 为每张牌出现的次数
	 * 从3-a  小王，大王
	 * 总长度15
	 * @return
	 */
	int judeRegulation(int[] appears,int len,int maxAppearCount);
	/**
	 * 从所有牌中找到该牌型的并且大于上一次出牌的一次出牌,找不到返回null
	 * @param playerPoke 玩家的牌
	 * @param preOnePoke 上一手牌
	 * @return
	 */
	Poke getOneSendCardBiggerButleast(Poke playerPoke,Poke preOnePoke);
	
	/**
	 * 从玩家的牌中找出牌该牌型一样的所有牌
	 * @param playerPoke 玩家的牌
	 * @return
	 */
	List<Poke> getAllSamePoke(Poke playerPoke,int type);
	
	/**
	 * 
	 * @param solution  最优牌组合
	 * @param preOnePoke 上一手出牌
	 * @param  finishPoke 已经出的牌
	 * @param  role 出牌所处的角色   1 是表示地主  2 表示 农民 上手出牌也是农民  3 表示 农民 上手出牌是地主
	 * 
	 * @return
	 */
	Poke pushCardPoke(CardSolution solution,Poke preOnePoke,Poke finishPoke,int role);
}
