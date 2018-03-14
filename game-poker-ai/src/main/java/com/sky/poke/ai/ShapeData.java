package com.sky.poke.ai;

/**
 * Function: 牌型数据
 * 
 * ClassName:ShapeData Date: 2013-6-20 下午05:22:39
 * 
 * @author chshyin
 * @version
 * @since JDK 1.6 Copyright (c) 2013, palm-commerce All Rights Reserved.
 */
public class ShapeData {
	// 最小数量
	private byte minimun;
	// 需要比较的对子数量
	private byte compareDNum;
	// 需要比较的个子数量
	private byte compareSNum;
	
	public ShapeData(byte minimun, byte compareSNum, byte compareDNum) {
		super();
		this.minimun = minimun;
		this.compareDNum = compareDNum;
		this.compareSNum = compareSNum;
	}

	public byte getMinimun() {
		return minimun;
	}

	public void setMinimun(byte minimun) {
		this.minimun = minimun;
	}

	public byte getCompareDNum() {
		return compareDNum;
	}

	public void setCompareDNum(byte compareDNum) {
		this.compareDNum = compareDNum;
	}

	public byte getCompareSNum() {
		return compareSNum;
	}

	public void setCompareSNum(byte compareSNum) {
		this.compareSNum = compareSNum;
	}

}
