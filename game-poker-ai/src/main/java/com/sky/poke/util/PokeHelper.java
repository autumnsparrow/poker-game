package com.sky.poke.util;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.poke.ai.handler.CAIHandler;
import com.sky.poke.bean.Poke;
import com.sky.poke.regulation.PokeRegulation;
import com.sky.poke.regulation.impl.PokeRegulationImpl;

public class PokeHelper {

	private static PokeRegulation pokeRegulation = new PokeRegulationImpl();

	private static Logger logger = LoggerFactory.getLogger(PokeHelper.class);
	// AIPoker中的值对应的牌的顺序 3 - A - 2 - 小王，大王
	private static byte[] CAIPOKER_ARRAY = new byte[] { 3, 16, 29, 42, 4, 17,
			30, 43, 5, 18, 31, 44, 6, 19, 32, 45, 7, 20, 33, 46, 8, 21, 34, 47,
			9, 22, 35, 48, 10, 23, 36, 49, 11, 24, 37, 50, 12, 25, 38, 51, 13,
			26, 39, 52, 1, 14, 27, 40, 2, 15, 28, 41, 53, 54 };

	// 洗一副牌
	// 返回牌的序号
	// 返回0-53 ，分别代表梅方红黑3-梅方红黑2 小王，大王
	// 0 -53 为long型值的0-53位的值
	public static int[] shufflePoke() {

		int[] pokeArray = initPoke();

		Random random = new Random();
		for (int j = 0; j < 3; j++) {
			// 洗牌
			for (int i = pokeArray.length - 1; i >= 0; i--) {
				// 从数组的最后一位开始随机
				int num = random.nextInt(i + 1);
				// 将随机出来的牌的位置和当前位置的替换
				swap(pokeArray, num, i);
			}
		}
		return pokeArray;
	}

	public static int[] shufflePokeCheat() {

		int[] pokeArray = initPoke();
		Random random = new Random();
		int numA = random.nextInt(3);
		int num2 = 0;
		if (numA == 0) {
			num2 = random.nextInt(3);
		} else if (numA == 1) {
			num2 = random.nextInt(2);
		}
		
		int poker = random.nextInt(2);// 0大王 1小王
		int j = pokeArray.length - 10 + numA;
		for (int i = 0; i < pokeArray.length; i++) {
			if (i % 3 == 0) {
				for (; j < pokeArray.length - 2 - num2;) {
					swap(pokeArray, i, j);
					break;
				}
				if (j == pokeArray.length - 2 - num2) {
					swap(pokeArray, i, pokeArray.length - 1 - poker);
				}
				j++;
			}
		}
		
		for (int k = 0; k < 5; k++) {
			// 洗牌
			for (int i = pokeArray.length - 1; i >= 0; i--) {
				// 从数组的最后一位开始随机
				int num = random.nextInt(i + 1);
				// 将随机出来的牌的位置和当前位置的替换
				if ((i % 3 != 0 || i >= pokeArray.length / 2)
						&& (num % 3 != 0 || num >= pokeArray.length / 2)) {
					swap(pokeArray, num, i);
				}
			}
		}
		
		return pokeArray;

	}

	// 将洗好的牌发掉
	public static long[] dealPoke(int[] randomCards) {
		long[] pokeValues = new long[4];

		// 前3副手牌
		for (int i = 0; i < 3; i++) {

			// pokeValues[i] = toLongValue(randomCards, i * 17, (i + 1) * 17);
			pokeValues[i] = onePlayerPoke(randomCards, i);
		}
		// 地主牌
		pokeValues[3] = toLongValue(randomCards, 51, 54);

		return pokeValues;
	}

	private static long onePlayerPoke(int[] randomCards, int index) {

		long value = 0;

		for (int i = 0; i < 17; i++) {

			value = value | (long) 0x1 << randomCards[i * 3 + index];
		}
		/** 临时测试修改发牌规则开始 不洗牌 **/
		// for (int i = 0; i < 17; i++) {
		//
		// value = value | (long) 0x1 << randomCards[17 * index + i];
		// }

		/** 临时测试修改发牌规则开始 ***/
		return value;
	}

	// 洗牌+发牌 返回长度4的long数组，分别代表3副手牌和1副地主牌
	public static long[] dealPoke() {

		 return dealPoke(shufflePoke());
		//return RobotAiAdapter.shuffles();
	}

	public static long[] dealPokeCheat() {
		return dealPoke(shufflePokeCheat());
		//return RobotAiAdapter.shufflesCheat();
	}

	public static long toLongValue(int[] randomCards, int beginIndex,
			int endIndex) {

		long l = 0;

		for (int i = beginIndex; i < endIndex; i++) {

			l = l | (long) 0x1 << randomCards[i];
		}

		return l;
	}

	public static long convertToPokeValue(String pokeStr) {

		return Long.parseLong(pokeStr, 16);
	}

	public static String formatToPokeStr(long pokeValue) {

		String pokeStr = Long.toHexString(pokeValue);

		if (pokeStr.length() < 14) {

			int len = 14 - pokeStr.length();

			StringBuilder builder = new StringBuilder(pokeStr);

			for (int i = 0; i < len; i++) {

				builder.insert(0, "0");
			}

			return builder.toString();
		}

		return pokeStr;
	}

	/**
	 * 查找指定角标的指定数量的牌
	 * 
	 * @param poke
	 * @param index
	 * @param len
	 * @return
	 */
	public static long findPokeAtAppearIndex(Poke poke, int index, int len) {

		if (len < 0 || index > 14) {

			return -1;
		}

		int[] appears = poke.getAppears();
		// 没有足够的牌
		if (appears[index] < len) {

			return -1;
		}
		// 小王
		if (index == 13) {

			return (long) 0x1 << 52;
			// 大王
		} else if (index == 14) {

			return (long) 0x1 << 53;
		} else {
			// 3 - k - a - 2
			int startValue = index * 4;
			int endValue = (index + 1) * 4;

			long returnValue = 0;

			int num = 0;

			for (int value : poke.getPokeValueArray()) {

				if (value >= endValue) {

					break;
				}

				if (value >= startValue) {

					num++;

					returnValue |= (long) 0x1 << value;

					if (num >= len) {

						return returnValue;
					}
				}
			}
		}

		return -1;
	}

	// 获取牌型 可以判断是否有炸弹
	public static int getPokeType(Poke poke) {
		// 错误牌型
		if (poke == null) {

			return PokeRegulation.UNKNOWN;
		}

		return pokeRegulation.judeRegulation(poke.getAppears(),
				poke.getLength(), poke.getMaxAppear());
	}

	// 比较两手牌的大小
	// 第二手牌大返回1
	// 相同返回 0 （不会有该情况）
	// 第一手牌大返回-1
	public static int comparePoke(Poke firstPoke, int firstPokeType,
			Poke secondPoke, int secondPokeType) {
		// 没有出牌或者为空 或者 另一手牌为火箭
		if (firstPokeType == PokeRegulation.UNKNOWN
				|| firstPokeType == PokeRegulation.EMPTY
				|| secondPokeType == PokeRegulation.ROCKET) {
			// TODO log
			return 1;
		}
		// 没有出牌或者为空 或者 另一手牌为火箭
		if (secondPokeType == PokeRegulation.UNKNOWN
				|| secondPokeType == PokeRegulation.EMPTY
				|| firstPokeType == PokeRegulation.ROCKET) {
			// TODO log
			return -1;
		}
		// 如果有一手牌为炸弹另一手非炸弹，则炸弹大
		if (firstPokeType == PokeRegulation.BOMB
				&& secondPokeType != PokeRegulation.BOMB) {

			return -1;
		}
		if (secondPokeType == PokeRegulation.BOMB
				&& firstPokeType != PokeRegulation.BOMB) {

			return 1;
		}
		// 其它情况则需要两手牌牌数相同
		if (firstPoke.getLength() != secondPoke.getLength()) {
			// TODO log
			return 1;
		}
		// 判断出现次数最多的牌的最小的一个
		if (firstPoke.getMinIndexOfMaxAppear() < secondPoke
				.getMinIndexOfMaxAppear()) {

			return 1;
		} else if (firstPoke.getMinIndexOfMaxAppear() == secondPoke
				.getMinIndexOfMaxAppear()) {

			return 0;
		} else {

			return -1;
		}
	}

	// 将pokeValue转换为ai计算需要的牌点数组
	public static byte[] convertToCAIPoker(long pokeValue) {

		byte[] pokers = new byte[Long.bitCount(pokeValue)];

		int index = 0;

		for (int i = 0; i < 54; i++) {

			long bitValue = pokeValue >>> i & 0x1;

			if (bitValue == 1) {

				pokers[index++] = CAIPOKER_ARRAY[i];
				// System.out.println(CAIPoker.formatPokeValue(CAIPOKER_ARRAY[i]));
			}
		}

		return pokers;
	}

	// sijunjie add try catch 20130715
	public static long formatCAIpokerToLong(byte[] CAIPokers) {
		try {
			if (CAIPokers == null) {

				return 0;
			}

			long value = 0;

			for (byte aiPoker : CAIPokers) {

				if (aiPoker > 0 && aiPoker < 55) {

					int index = findAIPokerIndex(aiPoker);

					value = value | (1l << index);

				}
			}

			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static int findAIPokerIndex(byte aiPoker) {

		for (int i = 0; i < CAIPOKER_ARRAY.length; i++) {

			if (CAIPOKER_ARRAY[i] == aiPoker) {

				return i;
			}
		}

		return -1;
	}

	public static String showValue(int identity) {

		String colorStr = "♣♦♥♠";
		String[] valueArray = { "3", "4", "5", "6", "7", "8", "9", "10", "J",
				"Q", "K", "A", "2" };
		// 3 - K - A - 2
		if (identity < 52) {

			int valueIndex = identity / 4;
			int colorIndex = identity % 4;

			return colorStr.charAt(colorIndex) + valueArray[valueIndex];
		}

		return identity == 52 ? "joker" : "JOKER";
	}

	public static String showPokeValue(int[] randomCards) {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < randomCards.length; i++) {

			builder.append(showValue(randomCards[i]) + " ");
		}

		return builder.toString();
	}

	// 将index1和index2的角标的元素互换位置
	private static void swap(int[] array, int index1, int index2) {

		int a = array[index1];

		array[index1] = array[index2];

		array[index2] = a;
	}

	private static int[] initPoke() {

		int[] pokeArray = new int[54];

		for (int i = 0; i < pokeArray.length; i++) {

			pokeArray[i] = i;
		}

		return pokeArray;
	}

	// extandIndo 附加信息
	// 长度6 0,14,1,17,0,16 代表 我是农民 ，手牌14张 下一家地主 17张牌 上家农民，16张牌
	public static Poke getOneSendCardBiggerButleast(Poke playerPoke,
			Poke preOnePoke, byte[] extandInfo, byte lastOutIndex) {

		byte[] holdPokers = convertToCAIPoker(playerPoke.getPokeValue());

		byte[] outPokers = preOnePoke == null ? new byte[0]
				: convertToCAIPoker(preOnePoke.getPokeValue());

		byte[] pokers = CAIHandler.SearchHint(holdPokers,
				(byte) holdPokers.length, outPokers, (byte) outPokers.length,
				extandInfo, lastOutIndex);

		if (pokers != null) {

			StringBuilder builder = new StringBuilder();
			for (byte b : pokers) {

				builder.append(b).append(" ");
			}
			logger.info("getOneSendCardBiggerButleast:result byte array:"
					+ builder);
		}

		Poke poke = new Poke(formatCAIpokerToLong(pokers));

		return poke;
	}
	
	

	private static PokerCube convertPokerCube(long poke) {
		String currentPlayerHex = PokerCube.ZERO;
		
			currentPlayerHex = PokeHelper.formatToPokeStr(poke);
		StringBuffer buffer = new StringBuffer();
		for (int i = currentPlayerHex.length() - 1; i >= 0; i--) {
			buffer.append(currentPlayerHex.charAt(i));
		}

		PokerCube pokerCube = PokerCube.getPokerCubeByHex(buffer.toString());
		// logger.info("HEX="+currentPlayerHex+" reverse "+buffer.toString()+"  "+pokerCube.toString());
		return pokerCube;
	}
	
	
	
	
	// extandIndo 附加信息
	// 长度6 0,14,1,17,0,16 代表 我是农民 ，手牌14张 下一家地主 17张牌 上家农民，16张牌
	// 新增 下一家 出的所有手牌 和 上一家出的所有手牌
	
	public static PokerCube oldGetOneSendCardBiggerButLeastPokerCube(PokerCube player,PokerCube preOne,byte[] extra,PokerCube[] myout,
			PokerCube[] rightOut,PokerCube[] leftOut){
		
		PokerCube p=null;
		byte lastOutIndex=preOne==null?(byte)-1:(byte)preOne.getDeckPosition();
		try {

			byte[] holdPokers = convertToCAIPoker(player.getLongValue());

			byte[] outPokers = preOne == null ? new byte[0]
					: convertToCAIPoker(preOne.getLongValue());

			byte[][] myOutPokers = convertToCAIPokerCubeArray(myout);
			byte[][] rightOutPokers = convertToCAIPokerCubeArray(rightOut);
			byte[][] leftOutPokers = convertToCAIPokerCubeArray(leftOut);

			byte[] pokers = null;
			
			pokers = CAIHandler.SearchHint(holdPokers,
					(byte) holdPokers.length, outPokers,
					(byte) outPokers.length, extra, lastOutIndex,
					myOutPokers, rightOutPokers, leftOutPokers);

			if (pokers != null) {

				StringBuilder builder = new StringBuilder();
				for (byte b : pokers) {

					builder.append(b).append(" ");
				}
				logger.debug("getOneSendCardBiggerButleast:result byte array:"
						+ builder);
			}
			
			long v=formatCAIpokerToLong(pokers);
			
			//formatToPokeStr(poke);
			return convertPokerCube(v);

		} catch (Exception e) {

			logger.error(
					"getOneSendCardBiggerButleast exception:preOnePoke:"
							+ (preOne == null ? 0 : preOne
									.cubeToHexString()) + ",lastOutIndex:"
							+ lastOutIndex , e);
			e.printStackTrace();
			//return RobotAiAdapter.getZeroPoker();
			throw new RuntimeException(e);
		}
		
	}
	
	private static Poke oldGetOneSendCardBiggerButLeast(Poke playerPoke,
			Poke preOnePoke, byte[] extandInfo, byte lastOutIndex,
			Poke[] myOutPokes, Poke[] rightOutPokes, Poke[] leftOutPokes,Poke basePoke){
		try {

			byte[] holdPokers = convertToCAIPoker(playerPoke.getPokeValue());

			byte[] outPokers = preOnePoke == null ? new byte[0]
					: convertToCAIPoker(preOnePoke.getPokeValue());

			byte[][] myOutPokers = convertToCAIPokerArray(myOutPokes);
			byte[][] rightOutPokers = convertToCAIPokerArray(rightOutPokes);
			byte[][] leftOutPokers = convertToCAIPokerArray(leftOutPokes);

			byte[] pokers = null;

			pokers = CAIHandler.SearchHint(holdPokers,
					(byte) holdPokers.length, outPokers,
					(byte) outPokers.length, extandInfo, lastOutIndex,
					myOutPokers, rightOutPokers, leftOutPokers);

			if (pokers != null) {

				StringBuilder builder = new StringBuilder();
				for (byte b : pokers) {

					builder.append(b).append(" ");
				}
				logger.debug("getOneSendCardBiggerButleast:result byte array:"
						+ builder);
			}

			Poke poke = new Poke(formatCAIpokerToLong(pokers));

			return poke;

		} catch (Exception e) {

			logger.error(
					"getOneSendCardBiggerButleast exception:preOnePoke:"
							+ (preOnePoke == null ? 0 : preOnePoke
									.getPokeValue()) + ",lastOutIndex:"
							+ lastOutIndex + ",myPokers:"
							+ formatPokers(myOutPokes) + ",rightOutPokers:"
							+ formatPokers(rightOutPokes)
							+ ",leftOutPokers:"
							+ formatPokers(leftOutPokes), e);
			e.printStackTrace();
			//return RobotAiAdapter.getZeroPoker();
			throw new RuntimeException(e);
		}
	}
	
	public static Poke getOneSendCardBiggerButleast(Poke playerPoke,
			Poke preOnePoke, byte[] extandInfo, byte lastOutIndex,
			Poke[] myOutPokes, Poke[] rightOutPokes, Poke[] leftOutPokes,Poke basePoke) {
		//
		int rightLeftCards = extandInfo[3];
		int leftLeftCards = extandInfo[5];
		boolean oldAiDealer = true;
		if (rightLeftCards <= 2 || leftLeftCards <= 2) {
			oldAiDealer = true;
		}
//	
		if (!oldAiDealer) {
			Poke poker = null;
			try {
//				poker = RobotAiAdapter.getOneSendCardBiggerButleast(playerPoke,
//						preOnePoke, extandInfo, lastOutIndex, myOutPokes,
//						rightOutPokes, leftOutPokes,basePoke);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(
						"getOneSendCardBiggerButleast exception:preOnePoke:"
								+ (preOnePoke == null ? 0 : preOnePoke
										.getPokeValue()) + ",lastOutIndex:"
								+ lastOutIndex + ",myPokers:"
								+ formatPokers(myOutPokes) + ",rightOutPokers:"
								+ formatPokers(rightOutPokes)
								+ ",leftOutPokers:"
								+ formatPokers(leftOutPokes), e);
				e.printStackTrace();
				return oldGetOneSendCardBiggerButLeast(playerPoke, preOnePoke, extandInfo, lastOutIndex, myOutPokes, rightOutPokes, leftOutPokes, basePoke);
			}
			return poker;
		} else {

			 return oldGetOneSendCardBiggerButLeast(playerPoke, preOnePoke, extandInfo, lastOutIndex, myOutPokes, rightOutPokes, leftOutPokes, basePoke);
			
		}

	}

	private static String formatPokers(Poke[] pokers) {

		StringBuilder builder = new StringBuilder();
		try {
			if (pokers != null) {

				for (Poke poke : pokers) {

					builder.append(poke.getPokeValue()).append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	private static byte[][] convertToCAIPokerArray(Poke[] rightOutPokes) {

		StringBuilder builder = new StringBuilder();

		if (rightOutPokes == null) {

			return new byte[0][];
		}

		byte[][] pokers = new byte[rightOutPokes.length][];
		// sijunjie add try catch
		try {
			for (int i = 0; i < pokers.length; i++) {

				pokers[i] = convertToCAIPoker(rightOutPokes[i].getPokeValue());
				builder.append(rightOutPokes[i].getPokeValue()).append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pokers;
	}
	private static byte[][] convertToCAIPokerCubeArray(PokerCube[] pCubes) {

	//	StringBuilder builder = new StringBuilder();

		if (pCubes == null) {

			return new byte[0][];
		}

		byte[][] pokers = new byte[pCubes.length][];
		// sijunjie add try catch
		try {
			for (int i = 0; i < pokers.length; i++) {

				pokers[i] = convertToCAIPoker(pCubes[i].getLongValue());
			//	builder.append(rightOutPokes[i].getPokeValue()).append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pokers;
	}

	public static Poke getOneSendCardBiggerButleast(Poke playerPoke,
			Poke preOnePoke) {

		return pokeRegulation.getOneSendCardBiggerButleast(playerPoke,
				preOnePoke);
	}

	public static List<Poke> getAllSamePokes(Poke playerPoke, int type) {
		return pokeRegulation.getAllSamePoke(playerPoke, type);
	}

	public static Poke playsPokes(Poke playerpoke, Poke playpoke) {
		if (playpoke == null) {

			return playerpoke;
		}
		// 判断有该手牌
		if ((playerpoke.getPokeValue() ^ playpoke.getPokeValue()) == playerpoke
				.getPokeValue() - playpoke.getPokeValue()) {
			return new Poke(playerpoke.getPokeValue() - playpoke.getPokeValue());
			// 重新计算相应的值

		} else {
			return playerpoke;
		}

	}

	public static int getshoushu(Poke playerpoke) {
		int shoushu = 0;
		int[] appear = new int[playerpoke.getAppears().length];
		int[] pokeappear = playerpoke.getAppears();
		// a) 先确定火箭
		if (pokeappear[13] == 1 || pokeappear[14] == 1) {
			shoushu++;
			System.out.println("包含一个飞机");
			appear[13] = 1;
			appear[14] = 1;
		}
		// b) 再确定炸弹
		for (int i = 0; i < 13; i++) {
			if (pokeappear[i] == 4) {
				appear[i] = 4;
				shoushu++;
				System.out.println("包含一个炸弹");
			}
		}
		// c) 再确定三条
		for (int i = 0; i < 13; i++) {
			if (pokeappear[i] == 3) {
				appear[i] = 3;
				shoushu++;
				System.out.println("包含一个三条");
			}
		}
		// d) 再确定三顺
		int lian = 0;
		for (int i = 3; i < 12; i++) {
			if (appear[i] == 3 && appear[i - 1] == 3) {
				lian++;
			} else {
				if (lian > 0) {
					shoushu = shoushu - lian;
					System.out.println("包含一个三顺，连续" + lian + "张");
					lian = 0;
				}
			}
		}
		// e) 再确定单顺
		// f) 再确定双顺
		// g) 再确定对子
		// h) 再确定单牌

		return shoushu;
	}

	/**
	 * 返回机器人抢地主叫分量规则如下: 当ai的牌型里 A+2的数量大于等于4，并且有单王的情况下，则叫3分
	 * A+2的数量大于等于3，并且有2个王的情况下，则叫3分 A+2的数量大于等于4，有大王，并且有顺，有三张的情况下，则叫3分
	 * A+2的数量大于等于3，有大王，并且有顺，有三张的情况下，的情况下，则叫3分
	 * 
	 * A+2的数量大于等于4，没有王的情况下，则叫2分 A+2的数量大于等于3，有单王的情况下，则叫2分
	 * 
	 * A+2的数量大于等于3，并且有顺，有三张的情况下，则叫1分
	 * 
	 * @param playerpoke
	 * @return
	 */
	public static int landOwner(Poke playerpoke) {
		logger.info("PokeHelper.landOwner start");
		if (playerpoke == null || playerpoke.getPokeValue() == 0) {
			logger.info("PokeHelper.landOwner playerpoke is null");
			return 0;
		}
		int[] app = playerpoke.getAppears();
		int socre = 0;
		int totalAand2 = app[11] + app[12];
		int joker = app[13];
		int Joker = app[14];
		boolean shunzi = checkShunzi(playerpoke);
		boolean sanzhang = checkSanZhang(playerpoke);
		logger.info("PokeHelper.landOwner totalAand2==" + totalAand2
				+ "  joker=" + joker + "  Joker=" + Joker + " shunzi=" + shunzi
				+ "  sanzhang=" + sanzhang);
		if (totalAand2 >= 4 && (joker == 1 || Joker == 1)) {
			// A+2的数量大于等于4，并且有单王的情况下，则叫3分
			socre = 3;
		} else if (totalAand2 >= 4 && Joker == 1 && shunzi && sanzhang) {
			// A+2的数量大于等于4，有大王，并且有顺，有三张的情况下，则叫3分
			socre = 3;
			return socre;
		}
		if (totalAand2 >= 3 && (Joker == 1 && shunzi && sanzhang)) {
			// A+2的数量大于等于3，并且有2个王的情况下，则叫3分
			socre = 3;
			return socre;
		}
		if (totalAand2 >= 4 && (joker == 0 && Joker == 0)) {
			// A+2的数量大于等于4，没有王的情况下，则叫2分
			socre = 2;
			return socre;
		}
		if (totalAand2 >= 3 && (joker == 1 || Joker == 1)) {
			// A+2的数量大于等于3，并且有2个王的情况下，则叫3分
			socre = 3;
			return socre;
		}
		if (totalAand2 >= 3 && (joker == 1 || Joker == 1)) {
			// A+2的数量大于等于4，没有王的情况下，则叫2分
			socre = 2;
			return socre;
		}
		if (totalAand2 >= 3 && shunzi && sanzhang && joker == 0 && Joker == 0) {
			// A+2的数量大于等于4，没有王的情况下，则叫2分
			socre = 2;
			return socre;
		}
		return socre;
	}

	private static boolean checkShunzi(Poke playerpoke) {
		if (playerpoke == null || playerpoke.getPokeValue() == 0) {
			return false;
		}
		int[] app = playerpoke.getAppears();
		boolean hasShunzi = false;
		for (int i = 0; i < app.length - 5; i++) {
			if (app[i] > 0 && app[i + 1] > 0 && app[i + 2] > 0
					&& app[i + 3] > 0 && app[i + 4] > 0) {
				hasShunzi = true;
				break;
			}
		}

		return hasShunzi;
	}

	private static boolean checkSanZhang(Poke playerpoke) {
		if (playerpoke == null || playerpoke.getPokeValue() == 0) {
			return false;
		}
		int[] app = playerpoke.getAppears();
		boolean hasShunzi = false;
		for (int i = 0; i < app.length - 2; i++) {
			if (app[i] == 3) {
				hasShunzi = true;
				break;
			}
		}

		return hasShunzi;
	}

	/**
	 * 判断是否该踢
	 * 
	 * @param playerpoke
	 * @return 1 表示踢 0表示不踢
	 */
	public static int kick(Poke playerpoke) {

		if (playerpoke == null || playerpoke.getPokeValue() == 0) {
			return 0;
		}
		int[] app = playerpoke.getAppears();
		int total = app[11] + app[12] + app[13] + app[14];
		logger.info("PokeHelper.kick totalAand2 joker Joker==" + total);
		if (total > 5) {
			return 1;
		} else {
			return 0;
		}

	}

	public static void main(String[] args) {

//		int[] pokeArray = initPoke();
//	//	System.out.println(showPokeValue(pokeArray));
//		Random random = new Random();
//		int numA = random.nextInt(3);
//		int num2 = 0;
//		if (numA == 0) {
//			num2 = random.nextInt(3);
//		} else if (numA == 1) {
//			num2 = random.nextInt(2);
//		}
//		int poker = random.nextInt(2);// 0大王 1小王
//		int j = pokeArray.length - 10 + numA;
//		for (int i = 0; i < pokeArray.length; i++) {
//			if (i % 3 == 0) {
//				for (; j < pokeArray.length - 2 - num2;) {
//					swap(pokeArray, i, j);
//					break;
//				}
//				if (j == pokeArray.length - 2 - num2) {
//					swap(pokeArray, i, pokeArray.length - 1 - poker);
//				}
//				j++;
//			}
//		}
//		System.out.println(numA);
//		System.out.println(num2);
//		System.out.println(poker);
//		System.out.println(showPokeValue(pokeArray));
		
		long[] pokeArray = dealPokeCheat();
		

//		long[] pokeArray = dealPoke(shufflePokeCheat());
//		System.out.println(pokeArray[0]);
//		System.out.println(pokeArray[1]);
//		System.out.println(pokeArray[2]);
//		System.out.println(pokeArray[3]);

		// int[] array = shufflePoke();
		//
		//System.out.println(showPokeValue((int[])pokeArray));
		//
		// // System.out.println(toLongValue(array, 0, array.length));
		//
		// long[] pokeValues = dealPoke(array);
		// Poke p1 = new Poke(pokeValues[0]);
		// System.out.println(showPokeValue(p1.pokeValueArray()));
		//
		// byte[] aiPokers = convertToCAIPoker(pokeValues[0]);
		//
		// long formatvalue = formatCAIpokerToLong(aiPokers);
		//
		// System.out.println(formatvalue == pokeValues[0]);
		//
		// Poke p2 = new Poke(pokeValues[1]);
		// System.out.println(showPokeValue(p2.pokeValueArray()));
		// Poke p3 = new Poke(pokeValues[2]);
		// System.out.println(showPokeValue(p3.pokeValueArray()));
		// // 2 ,15, 28, 41
		// System.out.println(formatCAIpokerToLong(new byte[] { 41, 41 }));
		// for (long l : pokeValues) {
		//
		// Poke p = new Poke(l);
		//
		// // System.out.println(showPokeValue(p.pokeValueArray()));
		//
		// System.out.println(formatToPokeStr(p.getPokeValue()));
		// }

		// System.out.println(showPokeValue(new Poke(pokeValues[0]).addPoke(new
		// Poke(pokeValues[3])).pokeValueArray()));
		//
		// Poke p1 = new Poke(13510798886871328l);
		//
		// System.out.println(findPokeAtAppearIndex(p1, 15, 1));

		// System.out.println((long)0x1 << 63 );
		//
		// long l1 = 8719541896399716352l;
		// long l2 = -9223372036854775808l;
		//
		// System.out.println(-503830140455059456l >>> 63 & 0x1);
		//
		// System.out.println(l1 - l2);
		// s
		// System.out.println(l1 ^ l2);
		// System.out.println(showPokeValue(p1.getPokeValueArray()));
		// String pokeStr = formatToPokeStr(13510798886871328l);
		// System.out.println(pokeStr);
		// System.out.println(convertToPokeValue(pokeStr));

	}

}
