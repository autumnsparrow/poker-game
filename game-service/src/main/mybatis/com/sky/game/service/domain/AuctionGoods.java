/**
 * 
 */
package com.sky.game.service.domain;

import java.util.Date;

/**
 * @author Administrator
 *  
 *  拍卖物品
 */
public class AuctionGoods {

	/**
	 * 
	 */
	public AuctionGoods() {
		// TODO Auto-generated constructor stub
	}
	long id	;//拍买的唯一一条物品标志
	String name	;//拍卖的物品名称
	int startPrice	;//起拍价格
	String outType;//拍卖方式
	long leftTime	;//剩余时间
	String description;//物品描述
	String useArea	;//使用范围
	String owner	;//拥有者
	int nowPrice;//当前价格
	int ownFen	;//携带积分
	Date startTime;        //开始时间
	Date endTime;        //结束时间用来和开始时间相结合计算出剩余时间
	String imgUrl;// 图片
	int smallAddPrice;//最小加价
	
	long   ownerId;  //拥有者编号
	String tradeType;   //交易货币类型
	Date effectTime;   //有效时间，流拍时间
	
	
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
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

	public String getDescription() {
		return description;
	}
	public long getLeftTime() {
		return leftTime;
	}
	public void setLeftTime(long leftTime) {
		this.leftTime = leftTime;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUseArea() {
		return useArea;
	}
	public void setUseArea(String useArea) {
		this.useArea = useArea;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}
	public int getOwnFen() {
		return ownFen;
	}
	public void setOwnFen(int ownFen) {
		this.ownFen = ownFen;
	}
	public int getNowPrice() {
		return nowPrice;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public void setNowPrice(int nowPrice) {
		this.nowPrice = nowPrice;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getSmallAddPrice() {
		return smallAddPrice;
	}
	public void setSmallAddPrice(int smallAddPrice) {
		this.smallAddPrice = smallAddPrice;
	}
	
}
