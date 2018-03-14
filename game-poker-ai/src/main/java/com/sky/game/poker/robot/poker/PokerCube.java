/**
 * 
 */
package com.sky.game.poker.robot.poker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.poker.robot.RobotAiLevels;
import com.sky.game.poker.robot.ai.IPokerCubeAnalyzer;
import com.sky.game.poker.robot.ai.PokerAnalyzerSchema;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.ai.PokerCubeTypes;
import com.sky.game.poker.robot.ai.impl.CardStateDivsion;
import com.sky.poke.util.PokeHelper;

/**
 * @author sparrow
 * 
 * 
 *         express the hands of poker: cub
 * 
 *         the cube initialized state should be like that:
 * 
 *         JK 2 A ...... 3 +--------+ +-------------------------------+ | 1 1 1
 *         1 | 1 1 1 1 | x 1 1 1 | x 1 1 1 +-------+
 *         +-------------------------------+
 * 
 *         x: Cube Invalid, never change this value. 1: Cube Valid
 * 
 * 
 * 
 *         the cube can express a pack.
 * 
 *         shuffles the cubes:
 * 
 * 
 * 
 * 
 *         00 unkown 01 self 02 right 03 left
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class PokerCube extends PokerCubeMeta {

	private static final Log logger = LogFactory.getLog(PokerCube.class);

	public static final String ZERO = "00000000000000";

	// public static final PokerCube
	// POKRECUBE_ZEOR=PokerCube.getPokerCubeByHex(ZERO);

	public static enum Cube {

		// cube
		DemissionInconsecutiveX(2), //
		DemissionConsecutiveX(12), DemissionY(4),
		// initialized state of cube
		StateValid(1), StateInvalid(0x80),
		// position of the cube,inconsecutive
		PositionInconsecutiveMin(0), PositionJoker(0), Position2(1), PositionInconsecutiveMax(
				2),
		// position of the cube ,consecutive
		PositionConsecutiveMin(0), PositionA(0), PositionK(1), PositionQ(2), PositionJ(
				3), Position10(4), Position9(5), Position8(6), Position7(7), Position6(
				8), Position5(9), Position4(10), Position3(11), PositionConsecutiveMax(
				12),
		// shuffles
		ShufflesA(0x02), ShufflesB(0x03), ShufflesC(0x04), ShufflesRemains(0x05), Spade(
				0x08), Heart(0x04), Diamond(0x02), Club(0x01), ConsectiveSingle(
				0x01), ConsectivePair(0x02), ConsectiveTriple(0x03), ConsectiveQuplex(
				0x04)

		;

		public byte value;

		private Cube(int i) {
			this.value = (byte) i;
		}

	};

	public static final int Red = 0;
	public static final int Black = 1;
	// cube bytes= 14*4+14*5 = 14*9 = 180bytes.
	// inconsecutive matrix
	byte inconsecutive[][];
	// consecutive matrix
	byte consecutive[][];
	// the number of the same ranks.
	byte statics[];
	// the solo consecutive
	byte soloConsecutive[];
	// the pair consecutive
	byte pairConsecutive[];
	// the triple consecutive
	byte tripleConsecutive[];
	// the quaplex
	byte qualplex[];

	HashMap<Byte, CubePosition> cubePositions;

	/**
	 * initialize the poker cube
	 * 
	 * 
	 * 
	 * 
	 */
	public PokerCube() {
		// TODO Auto-generated constructor stub
		inconsecutive = new byte[Cube.DemissionY.value][Cube.DemissionInconsecutiveX.value];
		consecutive = new byte[Cube.DemissionY.value][Cube.DemissionConsecutiveX.value];

		statics = new byte[Cube.DemissionInconsecutiveX.value
				+ Cube.DemissionConsecutiveX.value];

		soloConsecutive = new byte[Cube.DemissionInconsecutiveX.value
				+ Cube.DemissionConsecutiveX.value];
		pairConsecutive = new byte[Cube.DemissionInconsecutiveX.value
				+ Cube.DemissionConsecutiveX.value];
		tripleConsecutive = new byte[Cube.DemissionInconsecutiveX.value
				+ Cube.DemissionConsecutiveX.value];
		qualplex = new byte[Cube.DemissionInconsecutiveX.value
				+ Cube.DemissionConsecutiveX.value];

		cubePositions = new HashMap<Byte, PokerCube.CubePosition>();
		cubeZeroState();
		cubePositionState();
		// logger.info("Cube Position:\n"+toString());
		// System.out.println(toString());
		cubeInitailizedState();
	}

	/**
	 * record the cube position state
	 * 
	 * 
	 * 
	 */
	private void cubePositionState() {
		int k = 0;

		cubePositions.clear();

		for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				if (inconsecutive[i][j] == Cube.StateInvalid.value)
					continue;
				byte position = (byte) k++;

				inconsecutive[i][j] = position;
				cubePositions.put(Byte.valueOf(position), new CubePosition(
						position, j, i, false));
			}
		}
		for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				byte position = (byte) k++;
				consecutive[i][j] = position;
				cubePositions.put(Byte.valueOf(position), new CubePosition(
						position, j, i, true));
			}
		}

		/*
		 * for (int i = 0; i < Cube.DemissionY.value; i++) { for (int j = 0; j <
		 * Cube.DemissionInconsecutiveX.value; j++) { if (inconsecutive[i][j] ==
		 * Cube.StateInvalid.value) continue; byte position = (byte) k++;
		 * 
		 * inconsecutive[i][j] = position;
		 * cubePositions.put(Byte.valueOf(position), new CubePosition( position,
		 * j, i, false)); } for (int j = 0; j <
		 * Cube.DemissionConsecutiveX.value; j++) { byte position = (byte) k++;
		 * consecutive[i][j] = position;
		 * cubePositions.put(Byte.valueOf(position), new CubePosition( position,
		 * j, i, true)); } }
		 */

	}

	/**
	 * 
	 * 
	 * 
	 */
	private void cubeInitailizedState() {

		// reset state of inconsecutive cube

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				inconsecutive[i][j] = Cube.StateValid.value;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {
				consecutive[i][j] = Cube.StateValid.value;
			}
		}

		inconsecutive[2][0] = Cube.StateInvalid.value;
		inconsecutive[3][0] = Cube.StateInvalid.value;

	}

	/**
	 * 
	 * fill zero into the cube.
	 * 
	 */
	private void cubeZeroState() {
		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				inconsecutive[i][j] = 0x0;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {
				consecutive[i][j] = 0x0;
			}
		}

		inconsecutive[2][0] = Cube.StateInvalid.value;
		inconsecutive[3][0] = Cube.StateInvalid.value;
	}

	/**
	 * reset the consecutive cubes and record the consecutive flag.
	 * 
	 * 
	 */
	private void consecutiveCube() {

		// reset the cube
		for (int i = 0; i < soloConsecutive.length; i++) {
			soloConsecutive[i] = 0x0;
		}

		for (int i = 0; i < pairConsecutive.length; i++) {
			pairConsecutive[i] = 0x0;
		}

		for (int i = 0; i < tripleConsecutive.length; i++) {
			tripleConsecutive[i] = 0x0;
		}

		for (int i = 0; i < qualplex.length; i++) {
			qualplex[i] = 0x0;
		}

		for (int i = 2; i < statics.length; i++) {
			if (statics[i] >= 1) {
				soloConsecutive[i] = 0x01;
			} else {
				soloConsecutive[i] = 0x00;
			}
		}

		for (int i = 2; i < pairConsecutive.length; i++) {
			if (statics[i] >= 2) {
				pairConsecutive[i] = 0x01;
			} else {
				pairConsecutive[i] = 0x00;
			}
		}

		for (int i = 2; i < tripleConsecutive.length; i++) {
			if (statics[i] >= 3) {
				tripleConsecutive[i] = 0x01;
			} else {
				tripleConsecutive[i] = 0x00;
			}
		}

		for (int i = 1; i < qualplex.length; i++) {
			if (statics[i] >= 4) {
				qualplex[i] = 0x01;
			} else {
				qualplex[i] = 0x00;
			}
		}

		if (statics[0] == 2) {
			qualplex[0] = 0x01;
		}

	}

	/**
	 * statics the cube
	 * 
	 */
	private void staticsCube() {

		for (int i = 0; i < statics.length; i++) {
			statics[i] = 0x0;
		}

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {

				if (inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (inconsecutive[i][j] != 0x0) {
						statics[j]++;
					}
				}
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {
				if (consecutive[i][j] != 0x0) {
					statics[j + 2]++;
				}
			}
		}

	}

	/**
	 * 
	 * operator ~ the reverse of the cube
	 * 
	 * 
	 * @param value
	 */
	public void reverseCube(byte value) {

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (inconsecutive[i][j] != 0x0) {
						inconsecutive[i][j] = 0x0;
					} else {
						inconsecutive[i][j] = value;
					}
				}
				// inconsecutive[i][j]=Cube.StateValid.value;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (consecutive[i][j] != 0x0) {
					consecutive[i][j] = 0x0;
				} else {
					consecutive[i][j] = value;
				}

			}
		}

	}

	/**
	 * 
	 * 
	 * @return size of the element in cube
	 */
	public int remains() {
		this.staticsCube();
		byte size = 0x0;
		for (int i = 0; i < statics.length; i++) {
			size += statics[i];
		}
		return size;
	}

	/**
	 * 
	 * @return if the cube is empty.
	 */
	public boolean isEmpty() {

		return remains() == 0x0;
	}

	/**
	 * 
	 * 
	 * @author sparrow
	 * 
	 */
	public static class CubePosition {
		int sequence;
		int x;
		int y;
		boolean consecutive;

		public CubePosition(int sequence, int x, int y) {
			super();
			this.sequence = sequence;
			this.x = x;
			this.y = y;
		}

		public CubePosition(int sequence, int x, int y, boolean consecutive) {
			super();
			this.sequence = sequence;
			this.x = x;
			this.y = y;
			this.consecutive = consecutive;
		}

	}

	/**
	 * how to shuffles the cube: the cube has 56 positions.
	 * 
	 * 
	 */
	public void shuffles(int level) {
		// fill zero in the cube
		cubeZeroState();
		// record the position in cube
		cubePositionState();
		// initialize the cube state .
		cubeInitailizedState();

		// replace the numbers.
		// Random rnd=new Random(56);
		
		int[] shuffledCards = null;//PokeHelper.shufflePokeCheat(); 
		
			shuffledCards=	shufflesCards(level);
		

		// determain the left card section?
		// int start=(int) (Math.random()*50);

		for (int i = 0; i < shuffledCards.length - 3; i++) {
			Byte p = Byte.valueOf((byte) shuffledCards[i]);
			CubePosition cp = cubePositions.get(p);

			if (cp.consecutive) {
				consecutive[cp.y][cp.x] = (byte) (i % 3 + 2);
			} else {
				if (inconsecutive[cp.y][cp.x] != Cube.StateInvalid.value)
					inconsecutive[cp.y][cp.x] = (byte) (i % 3 + 2);

			}

		}

		for (int i = shuffledCards.length - 3; i < shuffledCards.length; i++) {
			Byte p = Byte.valueOf((byte) shuffledCards[i]);
			CubePosition cp = cubePositions.get(p);

			if (cp.consecutive) {
				consecutive[cp.y][cp.x] = (byte) (5);
			} else {
				if (inconsecutive[cp.y][cp.x] != Cube.StateInvalid.value)
					inconsecutive[cp.y][cp.x] = (byte) (5);
			}

		}

	}

	/**
	 * get clone of zero
	 * 
	 * @return a clone of zero cube.
	 */
	public PokerCube getCloneZero() {
		PokerCube pc = this.clone();
		pc.dump(this);
		pc.cubeHextoCube(PokerCube.ZERO);
		return pc;
	}

	/**
	 * 
	 * PokerCubeResult=PokerCubeSelf+PokerCube(non-zero value replaced with
	 * value)
	 * 
	 * 
	 * @param hands
	 *            The PokerCube that non-zero value need replace.
	 * @param value
	 *            The value PokerCube will be replaced.
	 * @return
	 */
	public PokerCube addPokerCubeWithValue(PokerCube hands, byte value) {
		PokerCube hand = this.getCloneZero();

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (hands.inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (hands.inconsecutive[i][j] != 0x0) {
						hand.inconsecutive[i][j] = value;
					} else {
						hand.inconsecutive[i][j] = inconsecutive[i][j];
					}
				}

			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (hands.consecutive[i][j] != 0x0) {
					hand.consecutive[i][j] = value;
				} else {
					hand.consecutive[i][j] = consecutive[i][j];
				}

			}
		}

		return hand;
	}

	/**
	 * 
	 * PokerCubeResult=PokerCubeSelf+PokerCube
	 * 
	 * 
	 * @param hands
	 * @return
	 */
	public PokerCube addPokerCube(PokerCube hands) {

		PokerCube hand = this.getCloneZero();

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (hands.inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (hands.inconsecutive[i][j] != 0x0) {
						hand.inconsecutive[i][j] = hands.inconsecutive[i][j];
					} else {
						hand.inconsecutive[i][j] = inconsecutive[i][j];
					}

				}

			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (hands.consecutive[i][j] != 0x0) {
					hand.consecutive[i][j] = hands.consecutive[i][j];
				} else {
					hand.consecutive[i][j] = consecutive[i][j];
				}

			}
		}

		return hand;
	}

	private static final String HEX = "0123456789abcdef";

	/**
	 * 
	 * 
	 * 
	 */
	public void replaceCubeValue() {
		this.cubeHexToCubeByValue(this.cubeToHexString(), (byte) 0x01);
	}

	/**
	 * Hex to Cube
	 * 
	 * @param hex
	 * @param value
	 */
	public void cubeHexToCubeByValue(String hex, byte value) {
		// clear the cube.
		this.cubeInitailizedState();
		int hexes[] = new int[hex.length()];
		String lowerHex = hex.toLowerCase();
		for (int i = 0; i < hex.length(); i++) {
			for (int j = 0; j < HEX.length(); j++) {
				if (lowerHex.charAt(i) == HEX.charAt(j)) {
					hexes[i] = j;
					break;
				}
			}
		}

		int k = 0;
		for (int j = Cube.DemissionConsecutiveX.value - 1; j >= 0; j--) {
			int h = hexes[k++];
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				if ((byte) (h & suits[i]) == suits[i]) {
					consecutive[i][j] = value;
				} else {

					consecutive[i][j] = 0x0;
				}
			}

		}
		for (int j = Cube.DemissionInconsecutiveX.value - 1; j >= 0; j--) {
			int h = hexes[k++];
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value) {

					if ((byte) (h & suits[i]) == suits[i]) {
						inconsecutive[i][j] = value;
					} else {

						inconsecutive[i][j] = 0x0;
					}
				}
			}

		}

	}

	/**
	 * Hex to PokerCube
	 * 
	 * @param hex
	 */
	public void cubeHextoCube(String hex) {
		cubeHexToCubeByValue(hex, (byte) 0x01);

	}

	private static final byte[] suits = new byte[] { Cube.Club.value,
			Cube.Diamond.value, Cube.Heart.value, Cube.Spade.value, };

	/**
	 * 
	 * PokerCube to Hex
	 * 
	 * @return
	 */
	public String cubeToHexString() {
		StringBuffer buffer = new StringBuffer();

		for (int j = Cube.DemissionConsecutiveX.value - 1; j >= 0; j--) {
			int hex = 0x0;
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				if (consecutive[i][j] != 0x0) {
					hex = hex | suits[i];
				}
			}
			buffer.append(Integer.toHexString(hex));
		}

		for (int j = Cube.DemissionInconsecutiveX.value - 1; j >= 0; j--) {
			int hex = 0x0;
			for (int i = 0; i < Cube.DemissionY.value; i++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value
						&& inconsecutive[i][j] != 0x0) {
					hex = hex | suits[i];
				}
			}
			buffer.append(Integer.toHexString(hex));
		}

		return buffer.toString();
	}

	/**
	 * replace the poker cube with other values.
	 * 
	 * 
	 * @param value
	 */
	public void replaceValue(byte value) {
		// PokerCube hand = this.getCloneZero();

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (inconsecutive[i][j] > 0x0) {
						inconsecutive[i][j] = value;
					} else {
						inconsecutive[i][j] = 0x0;
					}
				}
				// inconsecutive[i][j]=Cube.StateValid.value;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (consecutive[i][j] > 0x0) {
					consecutive[i][j] = 0x0;
				} else {

					consecutive[i][j] = value;
				}

			}
		}

	}

	/**
	 * remove a poker cube from the poker cube
	 * 
	 * @param mask
	 */
	public void maskPokerCubeWithValue(PokerCube mask) {

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (mask.inconsecutive[i][j] == 1) {
						inconsecutive[i][j] = 0x0;
					}
				}
				// inconsecutive[i][j]=Cube.StateValid.value;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (mask.consecutive[i][j] == 0x01) {
					consecutive[i][j] = 0x0;
				}

			}
		}

	}

	/**
	 * mask the PokerCube with value , only the
	 * 
	 * 
	 * @param value
	 * @return
	 */
	public PokerCube maskPokerCubeWithValue(byte value) {
		PokerCube hand = this.getCloneZero();

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				if (inconsecutive[i][j] != Cube.StateInvalid.value) {
					if (inconsecutive[i][j] != value) {
						hand.inconsecutive[i][j] = 0x0;
					} else {
						hand.inconsecutive[i][j] = value;
					}
				}
				// inconsecutive[i][j]=Cube.StateValid.value;
			}
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {

				if (consecutive[i][j] != value) {
					hand.consecutive[i][j] = 0x0;
				} else {

					hand.consecutive[i][j] = value;
				}

			}
		}

		return hand;
	}

	private int[] randArrays(int size, int loop) {
		int[] arrays = new int[size];
		for (int i = 0; i < size; i++) {
			arrays[i] = i;
		}
		
		Random r = new Random();
		for (int i = 0; i < loop; i++) {
			int p = r.nextInt(size);
			// poistion.
			int temp = arrays[i % size];
			arrays[i % size] = arrays[p];
			arrays[p] = temp;
		}
		logger.info("size:"+size+""+Arrays.toString(arrays));
		return arrays;

	}

	private Integer[] randomSection() {
		List<Integer> lst = new LinkedList<Integer>();
		lst.add(Integer.valueOf(2));

		int remians = 52;
		Random r = new Random();
		while (remians > 0) {
			int p = 1 + r.nextInt(10);

			if (remians - p > 0) {
				lst.add(Integer.valueOf(p));
			} else {
				lst.add(Integer.valueOf(remians));
			}
			remians = remians - p;
		}

		return lst.toArray(new Integer[] {});

	}

	
	private static final int[][] sectors={
		{24,20,10 },
		{2,20,32},
		{2,12,20,20},
		{14,10,20,10},
		{54},
		{54},
		{3,5,7,9,11,13,6}
		
	};
	/**
	 * shuffles the cards.
	 * 
	 * @return
	 */
	private int[] shufflesCards(int level) {
		int numbers[] = new int[54];// randArrays(54, 1000);

		// 22,
		int[] sections =sectors[level];// new int[] { 54 };
		//Integer[] sections=randomSection();
		/* comment by sparrow at 20:22 2015-3-16*/
		int offset = 0;
		for (int i = 0; i < sections.length; i++) {
			int[] number = randArrays(sections[i], 30);
			for (int j = 0; j < number.length; j++)
				numbers[offset + j] = number[j] + offset;
			offset += number.length;
		} 

		/*for(int i=0;i<numbers.length;i++){
			numbers[i]=i;
		}*/
		// logger.info(Arrays.toString(numbers));
		return numbers;
	}

	/**
	 * 
	 * 
	 * @param b
	 * @return
	 */
	private String byteToString(byte b) {
		String value = "--";
		if (b == Cube.StateInvalid.value) {
			value = "XX";
		} else if (b != 0x0) {
			value = String.format("%02x", b);
		}
		return value;

	}

	@Override
	public String toString() {
		this.calculate();
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\nCubes:")
				.append(" (Position=" + "ABC".charAt(getDeckPosition()))
				.append("),");
		if (getPokerCubeType() != null)
			buffer.append("(PokerCubeType=" + getPokerCubeType() + ")");

		buffer.append(",(Landlord:" + isBelongToLandloard() + ")");
		buffer.append("\n+-----------------------------------------------+\n");

		for (int i = 0; i < Cube.DemissionY.value; i++) {
			buffer.append("| ");
			for (int j = 0; j < Cube.DemissionInconsecutiveX.value; j++) {
				buffer.append(" ").append(byteToString(inconsecutive[i][j]));

			}
			buffer.append(" * ");
			for (int j = 0; j < Cube.DemissionConsecutiveX.value; j++) {
				buffer.append(" ").append(byteToString(consecutive[i][j]));

			}
			buffer.append(" |\n");
		}
		buffer.append("|-----------------------------------------------|      [SameRankSize     ]\n| ");
		for (int i = 0; i < statics.length; i++) {
			if (i == 2) {
				buffer.append(" * ");
			}
			buffer.append(" ").append(byteToString(statics[i]));
		}
		buffer.append(" |\n");
		buffer.append("|-----------------------------------------------|      [Hex              ]\n");
		buffer.append("|           " + this.cubeToHexString()
				+ "                      |\n");
		buffer.append("|-----------------------------------------------|      [SoloConsecutive  ]"
				+ consectiveSolo().toString() + "\n| ");

		for (int i = 0; i < soloConsecutive.length; i++) {
			if (i == 2) {
				buffer.append(" * ");
			}
			buffer.append(" ").append(byteToString(soloConsecutive[i]));
		}
		buffer.append(" |\n");
		buffer.append("|-----------------------------------------------|      [PairConsecutive  ]"
				+ consectivePair().toString() + "\n| ");
		for (int i = 0; i < pairConsecutive.length; i++) {
			if (i == 2) {
				buffer.append(" * ");
			}
			buffer.append(" ").append(byteToString(pairConsecutive[i]));
		}
		buffer.append(" |\n");
		buffer.append("|-----------------------------------------------|      [TripleConsecutive]"
				+ consectiveTriple().toString() + "\n| ");
		for (int i = 0; i < tripleConsecutive.length; i++) {
			if (i == 2) {
				buffer.append(" * ");
			}
			buffer.append(" ").append(byteToString(tripleConsecutive[i]));
		}
		buffer.append(" |\n");

		buffer.append("|-----------------------------------------------|      [Bomb             ]\n| ");
		for (int i = 0; i < qualplex.length; i++) {
			if (i == 2) {
				buffer.append(" * ");
			}
			buffer.append(" ").append(byteToString(qualplex[i]));
		}
		buffer.append(" |\n");

	/*	CardStateDivsion state = new CardStateDivsion(this);
		PokerAnalyzerSchema[] schemas = state.getPokerAnalyzerSchemas();

		for (PokerAnalyzerSchema schema : schemas) {
			buffer.append("|           "
					+ String.format("%03d - %03d", schema.getPokerCubeHands(),
							schema.getPokerCubeValues())
					+ "                           |\n");
		}
*/
		buffer.append("+-----------------------------------------------+\n");

		return buffer.toString();
	}

	/**
	 * using for the cube recognize
	 * 
	 * @return
	 */
	public ConsecutiveCompare consectiveSolo() {
		ConsecutiveCompare compare = new ConsecutiveCompare(soloConsecutive);
		return compare;
	}

	/**
	 * using for the cube consecutive recognize
	 * 
	 * @return
	 */
	public ConsecutiveCompare consectivePair() {
		ConsecutiveCompare compare = new ConsecutiveCompare(pairConsecutive);
		return compare;
	}

	/**
	 * using for the cube consecutive recongnize
	 * 
	 * @return
	 */
	public ConsecutiveCompare consectiveTriple() {

		ConsecutiveCompare compare = new ConsecutiveCompare(tripleConsecutive);
		return compare;
	}

	public ConsecutiveCompare consectiveQuplex() {
		ConsecutiveCompare compare = new ConsecutiveCompare(qualplex);
		return compare;
	}

	/**
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public PokerCube subPokerCubeByStatics(byte[] s) {
		this.calculate();
		PokerCube hand = this.getCloneZero();

		// hands.staticsCube();
		// checking could the cube sub
		byte[] results = new byte[statics.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = 0x0;
		}

		for (int i = 0; i < statics.length; i++) {
			results[i] = (byte) (statics[i] - s[i]);
		}
		// checking if all the result >=0;
		boolean canSub = true;
		for (int i = 0; i < results.length; i++) {
			if (results[i] < 0) {
				canSub = false;
				break;

			}
		}

		if (canSub) {

			//
			for (int i = 0; i < Cube.DemissionInconsecutiveX.value; i++) {
				if (s[i] > 0) {
					byte size = s[i];
					for (int j = 0; j < Cube.DemissionY.value; j++) {
						if (this.inconsecutive[j][i] > 0) {
							if (size == 0)
								continue;
							this.inconsecutive[j][i] = 0x0;
							hand.inconsecutive[j][i] = 0x01;
							size--;
						}
					}
				}

			}

			for (int i = 0; i < Cube.DemissionConsecutiveX.value; i++) {

				if (s[i + 2] > 0) {
					byte size = s[i + 2];
					for (int j = 0; j < Cube.DemissionY.value; j++) {
						if (this.consecutive[j][i] > 0) {
							if (size == 0)
								continue;
							this.consecutive[j][i] = 0x0;
							hand.consecutive[j][i] = 0x01;
							size--;
						}
					}
				}
			}

		}

		return hand;
	}

	/**
	 * sub the cube by the statics
	 * 
	 */
	public PokerCube subPokerCube(PokerCube hands) {
		hands.calculate();
		PokerCube hand = this.subPokerCubeByStatics(hands.statics);

		return hand;
	}

	public void calculate() {
		this.staticsCube();
		this.consecutiveCube();
	}

	public PokerCube subPokerCubeByPokerCubeType(PokerCubeType t) {
		PokerCubeTypes types = IPokerCubeAnalyzer.pokerCubeTypes[t.state];
		return subPokerCubeByConsectiveCompare(types.cube,
				types.consecutiveLength);
	}

	public PokerCube subPokerCubeByConsectiveCompare(PokerCube.Cube cube,
			int size) {
		this.calculate();

		ConsecutiveCompare compare = null;

		switch (cube.value) {
		case 0x01:
			compare = consectiveSolo();
			break;
		case 0x02:
			compare = consectivePair();
			break;
		case 0x03:
			compare = consectiveTriple();
			break;
		case 0x04:
			compare = consectiveQuplex();
			break;

		default:
			break;
		}

		PokerCube hand = this.subPokerCubeByStatics(compare.getStatics(
				cube.value, size));
		return hand;

	}

	public boolean isSmallestSingle() {
		calculate();
		
		return soloConsecutive[13]==1&&remains()==1;
	}

	public boolean isPokerCubeBomb() {
		calculate();
		int size = 0;
		for (int i = 1; i < qualplex.length; i++) {
			if (qualplex[i] > 0) {
				size++;
			}
		}
		return size == 1 && remains() == 4;
	}

	public boolean containsBombOrRocket() {
		calculate();
		int size = 0;
		for (int i = 1; i < qualplex.length; i++) {
			if (qualplex[i] > 0) {
				size++;
			}
		}

		return size > 0 || qualplex[0] > 0;
	}

	public boolean isPokerCubeRocket() {

		calculate();
		return qualplex[0] > 0 && remains() == 2;
	}

	/**
	 * clone the cube
	 * 
	 */
	public PokerCube clone() {
		String hex = this.cubeToHexString();

		PokerCube cube = new PokerCube();
		// dump the meta information.
		cube.dump(this);
		cube.cubeHexToCubeByValue(hex, (byte) 0x01);

		return cube;
	}

	/**
	 * 
	 * Single ,Pair,Triples
	 * 
	 * @return
	 */

	public PokerCube getSmallestNonconsecutive() {
		this.calculate();
		// soloConsecutive[0]
		String hex = this.cubeToHexString();
		StringBuffer buffer = new StringBuffer();
		int position = 0;
		for (int i = 0; i < hex.length(); i++) {
			if (hex.charAt(i) == '0') {
				buffer.append('0');
			} else if (hex.charAt(i) > '0') {
				buffer.append(hex.charAt(i));
				position = i;
				break;
			}
		}

		for (int i = position; i < hex.length(); i++) {
			buffer.append("0");
		}

		PokerCube cube = new PokerCube();
		cube.setPokerCubeType(pokerCubeType);
		cube.setDeckPoistion(getDeckPosition());
		cube.cubeHextoCube(buffer.toString());
		return cube;
	}

	/**
	 * get consecutive smallest.
	 * 
	 * @return
	 */
	public PokerCube getSmallestConsecutive() {
		this.calculate();
		String hex = this.cubeToHexString();
		StringBuffer buffer = new StringBuffer();
		int position = 0;
		boolean flag = false;

		for (int i = 0; i < hex.length(); i++) {
			if (hex.charAt(i) > '0' && !flag) {
				buffer.append(hex.charAt(i));
				flag = true;
			} else {
				buffer.append('0');
			}
		}

		PokerCube cube = new PokerCube();

		cube.setPokerCubeType(pokerCubeType);
		cube.setDeckPoistion(getDeckPosition());
		cube.cubeHextoCube(buffer.toString());
		return cube;
	}

	/**
	 * return the cube mask inconsecutive.
	 * 
	 * @return
	 */
	public PokerCube maskInConsecutiveCube() {
		this.calculate();
		String hex = this.cubeToHexString();
		String maskHex = hex.substring(0, hex.length() - 2);
		maskHex = maskHex + "00";
		PokerCube pokerCube = this.getCloneZero();
		pokerCube.cubeHextoCube(maskHex);

		return pokerCube;
	}

	/**
	 * 
	 * 
	 * 
	 */
	private void removeInconsecutiveBomb() {

		byte[] ss = new byte[statics.length];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = (byte) 0x00;
		}
		if (statics[0] == 0x02)
			ss[0] = 0x02;
		if (statics[1] == 0x04)
			ss[1] = 0x04;

		subPokerCubeByStatics(ss);
	}

	/**
	 * 
	 * get the cube from the in-consecutive cube.
	 * 
	 * @param cube
	 * @param staticsValue
	 * @return
	 */
	private PokerCube getLargerPokerCubeFromInconsecutive(PokerCube cube,
			byte staticsValue) {

		PokerCube cloneOfParent = this.clone();
		cloneOfParent.calculate();
		// remove the bombs
		// cloneOfParent.omitCombinations(new
		// PokerCubeType[]{PokerCubeType.Bomb});
		// omit the bombs
		// byte[] ss=new byte[statics.length];
		// for(int i=0;i<ss.length;i++){
		// ss[i]=(byte)0x00;
		// }
		// if(cloneOfParent.statics[0]==0x02)
		// ss[0]=0x02;
		// if(cloneOfParent.statics[1]==0x04)
		// ss[1]=0x04;
		//
		// cloneOfParent.subPokerCubeByStatics(ss);
		// cloneOfParent.subPokerCubeByPokerCubeType(PokerCubeType.Bomb);
		// cloneOfParent.omitCombinations(new
		// PokerCubeType[]{PokerCubeType.Bomb});
		cloneOfParent.removeInconsecutiveBomb();
		cloneOfParent.calculate();

		cube.calculate();
		PokerCube pc = PokerCube.getPokerCubeByHex(PokerCube.ZERO);
		byte s[] = new byte[statics.length];

		// chekcing the triples with single or pairs

		boolean larger = false;
		// special process the inconsecutive matrix.
		if (cube.getPokerCubeType().compareTo(PokerCubeType.Pairs) == 0
				|| cube.getPokerCubeType().compareTo(PokerCubeType.Triple) == 0
				|| cube.getPokerCubeType().compareTo(PokerCubeType.Single) == 0) {
			// checking if 2,joker,bomb.
			// s=new byte[statics.length];
			for (int i = 0; i < s.length; i++)
				s[i] = (byte) 0x0;

			// if the 2 is bomb,we check the joker first

			if (cloneOfParent.statics[1] >= staticsValue
					&& cube.statics[1] == 0 && cube.statics[0] == 0) {
				s[1] = staticsValue;
				larger = true;
			}

			// using the jokers
			// when the single.
			if (!larger) {

				// compare the two jokers.
				if (cube.getPokerCubeType().compareTo(PokerCubeType.Single) == 0) {
					if (cube.statics[0] == 1 && cloneOfParent.statics[0] == 1) {
						// must be cloneOfParent
						if (cloneOfParent.inconsecutive[1][0] == 0x01) {
							s[0] = staticsValue;
							larger = true;
						}
					} else if (cube.statics[0] == 0
							&& cloneOfParent.statics[0] > 0) {
						s[0] = staticsValue;
						larger = true;
					}

				}
			}
		}

		if (cube.getPokerCubeType().compareTo(PokerCubeType.TriplesWithSingle) == 0) {
			cloneOfParent
					.omitCombinations(new PokerCubeType[] { PokerCubeType.Pairs });
			cloneOfParent.calculate();
			PokerCube currentSingles = cloneOfParent
					.subPokerCubeByConsectiveCompare(Cube.ConsectiveSingle, 1);
			// currentSingles=currentSingles.maskInConsecutiveCube();
			if (cloneOfParent.statics[1] >= Cube.ConsectiveTriple.value) {
				s[1] = Cube.ConsectiveTriple.value;
				if (currentSingles.remains() > 0) {
					PokerCube attache = currentSingles
							.getSmallestNonconsecutive();
					attache.calculate();

					for (int i = 0; i < cloneOfParent.statics.length; i++) {
						if (s[i] == 0)
							s[i] = attache.statics[i];
					}
					larger = true;
				}
			}

		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.TriplesWithPair) == 0) {
			PokerCube currentPairs = cloneOfParent
					.subPokerCubeByConsectiveCompare(Cube.ConsectivePair, 1);
			// currentSingles=currentSingles.maskInConsecutiveCube();
			if (cloneOfParent.statics[1] >= Cube.ConsectiveTriple.value) {
				s[1] = Cube.ConsectiveTriple.value;
				if (currentPairs.remains() > 0) {
					PokerCube attache = currentPairs
							.getSmallestNonconsecutive();
					attache.calculate();

					for (int i = 0; i < cloneOfParent.statics.length; i++) {
						if (s[i] == 0)
							s[i] = attache.statics[i];
					}
					larger = true;
				}
			}

		}
		boolean isBomb = false;

		// checking if has the bombs.
		if (!larger) {
			if (qualplex[1] == 1) {
				s[1] = 4;
				isBomb = true;
				larger = true;
			} else if (qualplex[0] == 1) {
				s[0] = 2;
				isBomb = true;
				larger = true;
			} else {
				if (cube.getPokerCubeType().compareTo(PokerCubeType.Bomb) != 0) {
					for (int i = 2; i < qualplex.length; i++) {
						if (qualplex[i] > 0) {
							s[i] = 4;
							isBomb = true;
							larger = true;
						}
					}
				}
			}
		}

		if (larger) {
			pc = subPokerCubeByStatics(s);
			if (isBomb)
				pc.setPokerCubeType(PokerCubeType.Bomb);
		}
		return pc;

	}

	/**
	 * 
	 * When get the combinations ,omit some card types.
	 * 
	 * only effective when the poker cube consecutive part matrix. CAN NOT
	 * affect the poker cube in-consecutive part matrix.
	 * 
	 * @param pokerCubeTypes
	 */
	public void omitCombinations(PokerCubeType[] pokerCubeTypes) {
		for (PokerCubeType pokerCubeType : pokerCubeTypes) {
			this.subPokerCubeByPokerCubeType(pokerCubeType);

		}
	}

	/**
	 * 
	 * find the larger poker cube then sub
	 * 
	 * @param cube
	 * @return
	 */
	public PokerCube getLargerPokerCube(PokerCube cube, boolean shouldoOmit) {
		PokerCube pc = this.getCloneZero();

		PokerCube pCube = this.clone();
		cube.calculate();

		boolean larger = false;

		ConsecutiveCompare compare = null;
		//
		//
		// compare the consecutive matrix
		//
		byte[] s = null;
		byte staticsValue = 0x0;
		if (cube.getPokerCubeType().compareTo(PokerCubeType.Pairs) == 0) {
			// when find the pairs should remove the triples and bombs
			if (shouldoOmit)
				pCube.omitCombinations(new PokerCubeType[] {
						PokerCubeType.Bomb, PokerCubeType.ConsecutivePairs,
						PokerCubeType.ConsecutiveSingle, PokerCubeType.Triple });

			// then calculate the cube ,find the pairs.
			pCube.calculate();
			compare = pCube.consectivePair();

			larger = compare.compare(cube.consectivePair());
			staticsValue = PokerCube.Cube.ConsectivePair.value;

		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutivePairs) == 0) {
			// when find the pairs should remove the triples and bombs
			if (shouldoOmit)
				pCube.omitCombinations(new PokerCubeType[] { PokerCubeType.Bomb });

			// then calculate the cube ,find the pairs.

			// then calculate the cube ,find the pairs.
			pCube.calculate();
			compare = pCube.consectivePair();
			larger = compare.compare(cube.consectivePair());
			staticsValue = PokerCube.Cube.ConsectivePair.value;

		} else if (cube.getPokerCubeType().compareTo(PokerCubeType.Single) == 0) {
			// when find the pairs should remove the triples and bombs,pairs
			if (shouldoOmit)
				pCube.omitCombinations(new PokerCubeType[] {
						PokerCubeType.Bomb, PokerCubeType.ConsecutivePairs,
						PokerCubeType.ConsecutiveSingle, PokerCubeType.Triple,
						PokerCubeType.Pairs });

			// then calculate the cube ,find the pairs.
			pCube.calculate();

			compare = pCube.consectiveSolo();
			larger = compare.compare(cube.consectiveSolo());
			staticsValue = PokerCube.Cube.ConsectiveSingle.value;

		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutiveSingle) == 0) {
			if (shouldoOmit)
				pCube.omitCombinations(new PokerCubeType[] { PokerCubeType.Bomb });
			// then calculate the cube ,find the pairs.
			pCube.calculate();

			compare = pCube.consectiveSolo();
			larger = compare.compare(cube.consectiveSolo());
			staticsValue = PokerCube.Cube.ConsectiveSingle.value;

		} else if (cube.getPokerCubeType().compareTo(PokerCubeType.Triple) == 0
				|| cube.getPokerCubeType().compareTo(
						PokerCubeType.ConsecutiveTriples) == 0) {
			if (shouldoOmit)
				pCube.omitCombinations(new PokerCubeType[] { PokerCubeType.Bomb });
			pCube.calculate();

			compare = pCube.consectiveTriple();
			larger = compare.compare(cube.consectiveTriple());
			staticsValue = PokerCube.Cube.ConsectiveTriple.value;

		} else if (cube.getPokerCubeType().compareTo(PokerCubeType.Bomb) == 0) {
			compare = consectiveQuplex();
			larger = compare.compare(cube.consectiveQuplex());
			staticsValue = PokerCube.Cube.ConsectiveQuplex.value;

		}

		if (larger) {
			s = compare.getComparedStatics(staticsValue);
		}

		if (larger) {
			if (pc == null || pc.isEmpty())
				pc = pCube.subPokerCubeByStatics(s);
		}
		// TODO: checking the triples with attaches.
		// checking the triples with attaches.

		if (!larger) {
			if (cube.getPokerCubeType() == PokerCubeType.TriplesWithPair
					|| cube.getPokerCubeType() == PokerCubeType.TriplesWithSingle) {
				pc = getTripleWithAttaches(cube);
				if (!pc.isEmpty()) {
					larger = true;
				}
			}
		}

		// if can't found the attaches or the first check failed.
		if (!larger) {
			pc = getLargerPokerCubeFromInconsecutive(cube, staticsValue);
			if (!pc.isEmpty()) {
				larger = true;
			}
		}

		// if (!larger) {
		// logger.info(" No larger:" + cube.getPokerCubeType());
		// }

		if (pc != null && pc.getPokerCubeType() == null)
			pc.setPokerCubeType(cube.getPokerCubeType());

		return pc;
	}

	public static PokerCube getPokerCubeByHex(String hex) {
		PokerCube cube = new PokerCube();
		cube.cubeHextoCube(hex);
		return cube;
	}

	/**
	 * 
	 * 
	 * @param cube
	 * @return
	 */
	private PokerCube getTripleWithAttaches(PokerCube cube) {
		PokerCube pc = this.getCloneZero();
		// remove the in-consecutive.

		boolean consecutive = false;
		boolean solo = false;

		if (cube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutiveTriplesWithSingles) == 0) {
			consecutive = true;
			solo = true;
		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.ConsecutiveTriplesWithPairs) == 0) {
			consecutive = true;
			solo = false;
		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.TriplesWithSingle) == 0) {
			consecutive = false;
			solo = true;
		} else if (cube.getPokerCubeType().compareTo(
				PokerCubeType.TriplesWithPair) == 0) {
			consecutive = false;
			solo = false;
		}

		if (cube.getPokerCubeType().compareTo(PokerCubeType.TriplesWithSingle) == 0
				|| cube.getPokerCubeType().compareTo(
						PokerCubeType.ConsecutiveTriplesWithSingles) == 0
				|| cube.getPokerCubeType().compareTo(
						PokerCubeType.TriplesWithPair) == 0
				|| cube.getPokerCubeType().compareTo(
						PokerCubeType.ConsecutiveTriplesWithPairs) == 0) {
			int size = cube.consectiveTriple().sumOfElements();
			PokerCube cloneCube = cube.clone();

			// the clone cube sperate the triples and singles.
			PokerCube triples = cloneCube.subPokerCubeByConsectiveCompare(
					Cube.ConsectiveTriple, size);
			PokerCube singles = cloneCube;
			ConsecutiveCompare compare = null;
			boolean larger = false;
			if (singles.remains() > 0) {
				PokerCube cloneOfParent = this.clone();

				// remove the bombs
				cloneOfParent
						.omitCombinations(new PokerCubeType[] { PokerCubeType.Bomb });
				cloneOfParent.calculate();
				// checking if current triples larger than the triples
				compare = cloneOfParent.consectiveTriple();
				triples.calculate();
				ConsecutiveCompare tripleCompare = triples.consectiveTriple();
				larger = compare.compare(tripleCompare);
				if (larger) {
					// clone the cube

					// remove the bombs

					// get the smallest of the triples

					PokerCube cloneTriples = cloneOfParent
							.subPokerCubeByStatics(compare
									.getComparedStatics(Cube.ConsectiveTriple.value));

					pc = consecutive ? cloneTriples.getSmallestConsecutive()
							: cloneTriples.getSmallestNonconsecutive();
					// remove the pairs
					// if the attaches is pairs then get it.
					if (!solo) {
						// cloneOfParent.omitCombinations(new
						// PokerCubeType[]{PokerCubeType.Bomb,PokerCubeType.ConsecutiveTriples});
						// get the pairs
						cloneOfParent = cloneOfParent
								.subPokerCubeByConsectiveCompare(
										Cube.ConsectivePair, 1);
					} else {
						// filter the pairs
						cloneOfParent
								.omitCombinations(new PokerCubeType[] { PokerCubeType.ConsecutiveTriples });
						cloneOfParent.subPokerCubeByConsectiveCompare(
								Cube.ConsectivePair, 1);
					}

					// mask the joker and 2
					cloneOfParent = cloneOfParent.maskInConsecutiveCube();
					// if left single larger the size attaches.
					if (cloneOfParent.remains() >= size) {
						int attacheSize = size;

						while (attacheSize > 0) {
							// find the smallest single attaches.
							PokerCube attachSingle = cloneOfParent
									.getSmallestNonconsecutive();
							cloneOfParent.subPokerCube(attachSingle);
							attachSingle.calculate();
							pc = pc.addPokerCube(attachSingle);
							attacheSize--;
						}
					}

				}

			}
		}
		if (!pc.isEmpty())
			pc.setPokerCubeType(cube.getPokerCubeType());
		return pc;
	}

	public boolean isPokerCubeType(PokerCubeType pokerCubeType) {
		return this.getPokerCubeType() != null ? this.pokerCubeType
				.compareTo(pokerCubeType) == 0 : false;
	}

	public static PokerCube getPokerCubeByType(PokerCubeType pokerCubeType,
			List<PokerCube> cubes) {
		PokerCube pokerCube = PokerCube.getPokerCubeByHex(PokerCube.ZERO);
		for (PokerCube cube : cubes) {
			if (cube.isPokerCubeType(pokerCubeType)) {
				pokerCube = cube;
				break;
			}
		}
		return pokerCube;
	}

	/**
	 * Must find out the current PokerCubeType.
	 * 
	 * Bomb(0), ConsecutiveTriples(1), Triple(2), ConsecutivePairs(3),
	 * ConsecutiveSingle(4), Pairs(5), Single(6), TriplesWithSingle(7),
	 * TriplesWithPair(8), ConsecutiveTriplesWithSingles(9),
	 * ConsecutiveTriplesWithPairs(10), QuplexWithSingle(11), QuplexWithPair(12)
	 * Inconsecutive(11)
	 * 
	 * @param pokerCube
	 * @return
	 */
	private boolean rocket() {
		return statics[0] == 2 && this.remains() == 2;
	}

	private boolean bomb() {
		int size = remains();
		boolean bomb = false;
		if (size == 4) {
			for (int i = 1; i < statics.length; i++) {
				if (statics[i] == 4) {
					bomb = true;
					break;
				}
			}
		}
		return bomb;
	}

	public int numberOfPairs(){
		int sum=0;
		for (int i = 1; i < this.statics.length; i++) {
			if (this.statics[i] == 2) {
				sum++;
			}
		}
		return sum;
	}
	
	public int numberOfSingles(){
		int sum=0;
		for (int i = 1; i < this.statics.length; i++) {
			if (this.statics[i] == 1) {
				sum++;
			}
		}
		return sum;
	}
	
	public boolean quplexWithPair() {
		int size = remains();
		boolean ret = false;
		if (size == 8) {
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 4) {
					ret = true;
					break;
				}
			}
			if (ret) {
				int numberOfPairs=numberOfPairs();
				if(numberOfPairs!=2){
					ret=false;
				}
			}
		}
		return ret;
	}
	
	public boolean quplexWithSingle() {
		int size = remains();
		boolean ret = false;
		if (size == 6) {
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 4) {
					ret = true;
					break;
				}
			}
			if (ret) {
				int numberOfSingles=numberOfSingles();
				if(numberOfSingles!=2){
					ret=false;
				}
			}
		}
		return ret;
	}
	
	private boolean tripleWithPair(){
		
		int size = remains();
		boolean ret = false;
		if (size == 5) {
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					ret = true;
					break;
				}
			}
			if (ret) {
				int numberOfPairs=numberOfPairs();
				if(numberOfPairs!=1){
					ret=false;
				}
			}
		}
		return ret;
	}

	private boolean tripleWithSingle(){
		
		int size = remains();
		boolean ret = false;
		if (size == 4) {
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					ret = true;
					break;
				}
			}
			if (ret) {
				if(numberOfSingles()!=1){
					ret=false;
				}
			}
		}
		return ret;
	}
	
	
	private boolean triple(){
		
		int size = remains();
		boolean ret = false;
		if (size == 3) {
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					ret = true;
					break;
				}
			}
			
		}
		return ret;
	}
	public boolean consecTriples(){
		int size = remains();
		boolean ret = false;
		if (size %3==0&&size>3) {
			int sum=0;
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					sum++;
				}else{
					sum=0;
				}
				if(sum==size/3){
					break;
				}
			}
			if(sum>1&&size==sum*3){
				ret=true;
			}
			
		}
		return ret;
	}

	public boolean consecTriplesWithPair(){
		int size = remains();
		boolean ret = false;
		if(size%5==0&&size>5){
			int sum=0;
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					sum++;
				}else{
					sum=0;
				}
				if(sum==size/5){
					break;
				}
			}
			if(sum>1){
				int left=size-sum*3;
				if(numberOfPairs()==left/2){
					ret=true;
				}
			}
		}
		
		return ret;
	}
	
	public boolean consecTriplesWithSingle(){
		int size = remains();
		boolean ret = false;
		if(size%4==0&&size>4){
			int sum=0;
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 3) {
					sum++;
				}else{
					sum=0;
				}
				if(sum==size/4){
					break;
				}
			}
			if(sum>1){
				int left=size-sum*3;
				if(numberOfSingles()==left){
					ret=true;
				}
			}
			
		}
		return ret;
	}
	
	
	public boolean pair(){
		int size = remains();
		boolean ret = false;
		if(size==2){
			int sum=0;
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 2) {
					sum++;
				}
			}
			if(sum==1){
				ret=true;
			}
			
		}
		return ret;
	}
	
	public boolean consecpair(){
		int size = remains();
		boolean ret = false;
		if(size>=6){
			int sum=0;
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 2) {
					sum++;
				}else{
					sum=0;
				}
				if(sum==size/2){
					break;
				}
			}
			if(sum>1&&sum*2==size){
				ret=true;
			}
		}	
		
		return ret;
	}
	
	public boolean consecpsingle(){
		int size = remains();
		boolean ret = false;
		if(size>=5){
			int sum=0;
			
			for (int i = 1; i < this.statics.length; i++) {
				if (this.statics[i] == 1) {
					
					sum++;
					
				}else{
					sum=0;
				}
				
				if(sum==size)
					break;
			}
			if(sum>1&&sum==size){
				ret=true;
			}
			
		}
		return ret;
	}
	
	private boolean single(){
		return remains()==1;
	}
	
	//08214828000000
	public PokerCubeType judgePokerCubeType() {
		// calculate the poker cube.

		PokerCubeType pt = null;
		this.calculate();


		// rocket
		if (pt == null&&rocket()) {
			pt = PokerCubeType.Rocket;
		}
		// bomb
		if (pt == null&&bomb()) {
			// judge poker cube type is bomb
			pt = PokerCubeType.Bomb;
			
		}
		if(pt==null&&quplexWithPair()){
			pt=PokerCubeType.QuplexWithPair;
		}
		if(pt==null&&quplexWithSingle()){
			pt=PokerCubeType.QuplexWithSinge;
		}

		if(pt==null&&tripleWithPair()){
			pt=PokerCubeType.TriplesWithPair;
		}
		if(pt==null&&tripleWithSingle()){
			pt=PokerCubeType.TriplesWithSingle;
		}
		if(pt==null&&triple()){
			pt=PokerCubeType.Triple;
		}
		if(pt==null&&consecTriples()){
			pt=PokerCubeType.ConsecutiveTriples;
		}
		if(pt==null&&consecTriplesWithPair()){
			pt=PokerCubeType.ConsecutiveTriplesWithPairs;
			
			
		}
		if(pt==null&&consecTriplesWithSingle()){
			pt=PokerCubeType.ConsecutiveTriplesWithSingles;
		}
		
		if(pt==null&&pair()){
			pt=PokerCubeType.Pairs;
		}
		
		if(pt==null&&consecpair()){
			pt=PokerCubeType.ConsecutivePairs;
		}
		
		if(pt==null&&consecpsingle()){
			pt=PokerCubeType.ConsecutiveSingle;
		}
		
		
		if(pt==null&&single()){
			
			pt=PokerCubeType.Single;
		}
		
		if(pt==null&&isEmpty()){
			pt=PokerCubeType.Empty;
		}

		
		this.pokerCubeType = pt;
		
		return pt;

	}

	

	public boolean gt(PokerCube pokercube) {
		boolean ret = false;
		// ensure the the pokercube is the same type.
		this.judgePokerCubeType();
		PokerCubeType pt = pokercube.judgePokerCubeType();
		if (pt.isPokerCubeType(pokerCubeType)) {
			//

			int location = -1;
			if (statics[0] > 0) {
				location = 0;
			} else {

				// get the max statics[i]>0 location.
				int max = 0;
				for (int i = 1; i < statics.length; i++) {
					if (statics[i] > 0) {

						// max=statics[i];
						if (statics[i] > max) {
							max = statics[i];
							location = i;
						}
						break;
					}
				}
			}
			int locationOfPc = -1;

			if (pokercube.statics[0] > 0) {
				locationOfPc = 0;
			} else {
				int max = 0;
				for (int i = 1; i < statics.length; i++) {
					if (pokercube.statics[i] > max) {
						max = pokercube.statics[i];
						locationOfPc = i;
					}

				}
			}
			if (location == 0 && locationOfPc == 0) {
				// compare the joker
				ret = inconsecutive[1][0] > 0;
			} else {
				if (location < locationOfPc) {
					ret = true;
				} else {
					ret = false;
				}
			}

		}

		return ret;
	}

	public long getLongValue() {
		String hex = cubeToHexString();
		StringBuffer buffer = new StringBuffer();
		for (int i = hex.length() - 1; i >= 0; i--)
			buffer.append(hex.charAt(i));

		long v = Long.parseLong(buffer.toString(), 16);
		return v;
	}
	
	public int getPokerCubeValue(){
		String hex=cubeToHexString();
		int value=0;
		for(int i=0;i<hex.length();i++){
			if(hex.charAt(i)!='0'){
				value+=i;
			}
		}
		
		return value;
	}
	
	private static byte[]  POKRE_VALUES={16,15,14,13,12,11,10,9,8,7,6,5,4,3};
	
	
	// all values in range the consecutive elements.
	public byte[]  getValues(){
		this.staticsCube();
		byte[]  r=new byte[0];//new byte[this.remains()];
		int loc=0;
		int len=0;
		
		byte[] levelBytes=null;
		switch (this.pokerCubeType.state) {
		case 6:
			levelBytes=soloConsecutive;
			len=this.remains();
			break;
		case 5:
			levelBytes=pairConsecutive;
			len=this.remains()/2;
			
			break;
		case 2:
			levelBytes=tripleConsecutive;
			len=this.remains()/3;
		case 0:
			levelBytes=qualplex;
			len=this.remains()/4;
			break;

		default:
			break;
		}
		
		r=new byte[len];
		for(int i=levelBytes.length-1;i>=0;i--){
			
			if(levelBytes[i]==1){
				r[loc++]=POKRE_VALUES[i];
			}
		}
		return r;
		
	}
	
}
