/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 * 我的拍卖行
 */
public class MyAuctionGoods {

	/**
	 * 
	 */
	public MyAuctionGoods() {
		// TODO Auto-generated constructor stub
	}
	long id; //物品的唯一id
	String name		;//物品名称
	int lowValue	;//	最小价格
	int hightValue	;//	最大价格
	String description	;//	物品描述
	String pastTime	;//	过期时间
	int wonFen	;//	携带积分
	String  useArea	;//	使用范围
	String imgUrl;
	String  tradeType;//组装数组
	String  outType;//组装数组
	
	String[] tradeTypeArray;//交易货币类型
	String[] outTypeArray;//允许拍买方式
	
	
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
	public String[] getTradeTypeArray() {
		String array=getTradeType();
		if(array!=null){
		   tradeTypeArray=array.split(",");
		}
		return tradeTypeArray;
	}
	public void setTradeTypeArray(String[] tradeTypeArray) {
		this.tradeTypeArray = tradeTypeArray;
	}
	public String[] getOutTypeArray() {
		String array=getOutType();
		if(array!=null){
			outTypeArray=array.split(",");
		}
		return outTypeArray;
	}
	public void setOutTypeArray(String[] outTypeArray) {
		this.outTypeArray = outTypeArray;
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
	/*public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}*/
	
	public String getDescription() {
		return description;
	}
	public int getLowValue() {
		return lowValue;
	}
	public void setLowValue(int lowValue) {
		this.lowValue = lowValue;
	}
	public int getHightValue() {
		return hightValue;
	}
	public void setHightValue(int hightValue) {
		this.hightValue = hightValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPastTime() {
		return pastTime;
	}
	public void setPastTime(String pastTime) {
		this.pastTime = pastTime;
	}
	public int getWonFen() {
		return wonFen;
	}
	public void setWonFen(int wonFen) {
		this.wonFen = wonFen;
	}
	public String getUseArea() {
		return useArea;
	}
	public void setUseArea(String useArea) {
		this.useArea = useArea;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
