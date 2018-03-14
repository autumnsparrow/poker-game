package com.sky.poke.bean;

import com.sky.poke.util.PokeHelper;


// 牌对象
public class Poke {
	// 一副牌，用一个long型字段来表示
	// 一个byte 8 bit
	// 00000000 每一个bit能够代表一张牌
	// 8字节中的前几字节代表 3-a的牌，后面跟随小王和大王
	// 例如第一字节代表3和4的所有牌
	// 每一位分别为黑桃3，红桃3，梅花3，方片3，黑桃4，红桃4，梅花4，方片4
	private long pokeValue;
	// 所有牌出现的次数
	private int[] appears;
	// 牌的int值数组
	private int[] pokeValueArray;
	// 最大出现的次数
	private int maxAppear;
	// 最大出现的次数对应的最小的index
	private int minIndexOfMaxAppear;
	// 牌数
	private int length;
	// 牌型
	private int type;

	public Poke(long pokeValue) {

		this.pokeValue = pokeValue;
		
		init();
	}
	
	private void init(){
		
		this.length = Long.bitCount(pokeValue);
		// 初始化每张牌出现的次数和相关的信息
		initAppear();
		
		initPokeValueArray();
		// 计算牌型
		initPokeType();
	}
	
	private void initPokeType() {
		
		this.type = PokeHelper.getPokeType(this);
	}

	public int getType() {
		return type;
	}

	private void initPokeValueArray(){
		
		int index = 0;
		
		this.pokeValueArray = new int[this.length];
		
		for(int i = 0 ; i < 54 ; i ++){
			
			long bitValue = pokeValue >>> i & 0x1;
			
			if(bitValue == 1){
				
				this.pokeValueArray[index ++] = i;
			}
		}
	}
	
	private void initAppear(){
		// 初始化牌出现的次数
		this.appears = new int[15];
		// 3 -- A
		for (int i = 0; i < 13; i++) {

			int bitValue = (int) (pokeValue >>> (i * 4) & 0xF);

			switch (bitValue) {
			case 0xF:

				appears[i] = 4;
				
				if(maxAppear < 4){
					
					minIndexOfMaxAppear = i;
					maxAppear = 4;
				}
				break;
			case 0x7:
			case 0xD:
			case 0xB:
			case 0xE:
				appears[i] = 3;
				
				if(maxAppear < 3){
					
					minIndexOfMaxAppear = i;
					maxAppear = 3;
				}
				break;
			case 0x0:
				appears[i] = 0;
				break;
			case 0x1:
			case 0x2:
			case 0x4:
			case 0x8:
				appears[i] = 1;
				
				if(maxAppear < 1){
					
					minIndexOfMaxAppear = i;
					maxAppear = 1;
				}
				break;

			default:
				appears[i] = 2;

				if(maxAppear < 2){
					
					minIndexOfMaxAppear = i;
					maxAppear = 2;
				}
				break;
			}
		}
		// 小王
		if((pokeValue >>> 52 & 0x1) == 0x1){
			
			this.appears[13] = 1;
			
			if(maxAppear < 1){
				
				minIndexOfMaxAppear = 13;
				maxAppear = 1;
			}
		}
		//特例话处理  333+444+5555的情况
		int appers3=0;
		for(int i=0;i<this.appears.length;i++){
			if(appears[i]==3){
				appers3++;	
			}
		}
		if(appers3>1&&maxAppear==4){
			maxAppear=3;
		}
		// 大王
		if((pokeValue >>> 53 & 0x1) == 0x1){
			
			this.appears[14] = 1;
			
			if(maxAppear < 1){
				
				minIndexOfMaxAppear = 14;
				maxAppear = 1;
			}
		}
	}

	// 出牌
	public Poke playCards(Poke poke) {

		if (poke == null) {

			return this;
		}
		// 判断有该手牌
		if ((pokeValue ^ poke.getPokeValue()) == pokeValue
				- poke.getPokeValue()) {

			this.pokeValue = this.pokeValue - poke.getPokeValue();
			// 重新计算相应的值
			this.init();

			return this;
		}
		// TODO
		return null;
	}

	public int[] getPokeValueArray() {
		return pokeValueArray;
	}

	public int getMaxAppear() {
		return maxAppear;
	}

	public int getLength() {
		return length;
	}

	public int getMinIndexOfMaxAppear() {
		return minIndexOfMaxAppear;
	}

	// 发地主牌
	public Poke addPoke(Poke landLord) {

		if (landLord == null) {

			return this;
		}
		// 没有牌
		if ((pokeValue | landLord.getPokeValue()) == pokeValue
				+ landLord.getPokeValue()) {

			this.pokeValue = this.pokeValue + landLord.getPokeValue();
			// 重新计算相应的值
			this.init();
			
			return this;
		}

		return null;
	}

	// 获取每张牌出现次数的数组
	public int[] getAppears() {

		return appears;
	}
	
	

	// 获取牌的值
	public int[] pokeValueArray() {

		return pokeValueArray;
	}

	public long getPokeValue() {
		return pokeValue;
	}

	public static void main(String[] args) {

		String ss="00600000000000";
		
		System.out.println(PokeHelper.convertToPokeValue(ss));
		long a = PokeHelper.convertToPokeValue(ss);

		Poke poke = new Poke(a);
//
		int[] appears = poke.getAppears();
//		System.out.println(poke.getLength());
		System.out.println(poke.getMaxAppear());
//		System.out.println(appears);
//		System.out.println(poke.getMinIndexOfMaxAppear());
//		int[] pokeValueArray = poke.getPokeValueArray();
//		System.out.println(pokeValueArray);
		System.out.println(poke.getType());
//		
//		System.out.println("中♠♥");
	}

}
