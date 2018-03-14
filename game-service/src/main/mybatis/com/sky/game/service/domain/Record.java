/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class Record {

	/**
	 * 
	 */
	public Record() {
		// TODO Auto-generated constructor stub
	}
	int scence;	//	场次
	int num1	;//	冠军
	int num2	;//	亚军
	int num3	;//	季军
	int riseMatch;	//	晋级决赛
	public int getScence() {
		return scence;
	}
	public void setScence(int scence) {
		this.scence = scence;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public int getRiseMatch() {
		return riseMatch;
	}
	public void setRiseMatch(int riseMatch) {
		this.riseMatch = riseMatch;
	}
	public Record(int scence, int num1, int num2, int num3, int riseMatch) {
		super();
		this.scence = scence;
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
		this.riseMatch = riseMatch;
	}
	
}
