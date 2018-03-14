/**
 * 
 */
package com.sky.game.protocol.util;

/**
 * @author Administrator
 *
 */
public class Validate {

	/**
	 * 
	 */
	public Validate() {
		// TODO Auto-generated constructor stub
	}

	long userId;
	String phone;
    String val;
    long bornTime;
    
	public long getBornTime() {
		return bornTime;
	}
	public void setBornTime(long bornTime) {
		this.bornTime = bornTime;
	}


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Validate(long userId, String phone, String val, long bornTime) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.val = val;
		this.bornTime = bornTime;
	}
	@Override
	public String toString() {
		return "Validate [userId=" + userId + ", phone=" + phone + ", val="
				+ val + ", bornTime=" + bornTime + "]";
	}

}
