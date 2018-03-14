/**
 * 
 */
package com.sky.game.service.domain;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class AwardRecord {

	/**
	 * 
	 */
	public AwardRecord() {
		// TODO Auto-generated constructor stub
	}

	String name; //奖品名称，如：1元电话卡，iphone60
	String orderId; //订单号
	int   state;//订单状态，表示：是否已发货，等信息.
	String description;//描述信息
	Timestamp time;//获得该奖品的时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDescription() {
		String description=null;
		int state=getState();
		if(state==1){
			description="未发货";
		}
		if(state==2){
			description="已发货";
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
