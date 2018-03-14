/**
 * 
 */
package com.sky.game.service.domain;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class MyPrice {

	/**
	 * 
	 */
	public MyPrice() {
		// TODO Auto-generated constructor stub
	}
 
		long   id;	          //列唯一标志            user_item 表中的id
		long   itemId;        //item 对应的id
		String name;	      // 奖品名称
		String priceDis;	  // 奖品描述
		int    priceNum;	  // 个人背包中奖品数量
		Timestamp getTime;	      // 获取时间
		String iconImg;       // 奖品图片
        
		int type;             // 1电话卡  2是实物

		int resDay;           // 奖品领取每日剩余量
		String gainType;      //奖品获得方式
		public int getResDay() {
			return resDay;
		}
		public void setResDay(int resDay) {
			this.resDay = resDay;
		}

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPriceDis() {
			return priceDis;
		}
		public void setPriceDis(String priceDis) {
			this.priceDis = priceDis;
		}
		public int getPriceNum() {
			return priceNum;
		}
		public void setPriceNum(int priceNum) {
			this.priceNum = priceNum;
		}
		
		public Timestamp getGetTime() {
			return getTime;
		}
		public void setGetTime(Timestamp getTime) {
			this.getTime = getTime;
		}
		public String getIconImg() {
			return iconImg;
		}
		public void setIconImg(String iconImg) {
			this.iconImg = iconImg;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		
		public String getGainType() {
			return "兑换获取";
		}
		public void setGainType(String gainType) {
			this.gainType = gainType;
		}
		
		@Override
		public String toString() {
			return "MyPrice [id=" + id + ", name=" + name + ", priceDis="
					+ priceDis + ", priceNum=" + priceNum + ", getTime="
					+ getTime + ", resDay=" + resDay + ",iconImg=" + iconImg + "]";
		}	
}
