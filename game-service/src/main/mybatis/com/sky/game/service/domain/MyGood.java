package com.sky.game.service.domain;

public class MyGood {

	public MyGood() {
		// TODO Auto-generated constructor stub
	}
	long id;
	String name;
	String goodsDis;
	int goodsNum;
	String iconImg;
	long itemId;
	int itemType;// 区别物品类型 2表示实物  3表示元宝
	
	String index;
	
	
	
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getIndex() {
		String index=null;
		if(getItemId()==1002){
			if(getGoodsNum()<6000){
				int a=6000-getGoodsNum();
				index="再收集 "+a+"元宝就可以兑换50元手机充值卡";
			}
			else if(getGoodsNum()<11000 && getGoodsNum()>6000){
				int a=11000-getGoodsNum();
				index="再收集 "+a+"元宝就可以兑换100元手机充值卡";
			}
			else if(getGoodsNum()<20000 && getGoodsNum()>11000){
				int a=20000-getGoodsNum();
				index="再收集 "+a+"元宝就可以兑换200元手机充值卡";
			}
			else{index="可以去兑换中心兑换相应物品";}
		}else if(getItemId()==7000 || getItemId()==7100 || getItemId()==7200){
			index="可以通过闯关赛合成该物品";
		}else if(getItemId()==1003){
			index="可以报名超级闯关赛和购买道具";
		}else{
			index="可以在比赛中获得";
		}
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
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
	public String getGoodsDis() {
		return goodsDis;
	}
	public void setGoodsDis(String goodsDis) {
		this.goodsDis = goodsDis;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public MyGood(long id, String name, String goodsDis, int goodsNum,
			String iconImg) {
		super();
		this.id = id;
		this.name = name;
		this.goodsDis = goodsDis;
		this.goodsNum = goodsNum;
		this.iconImg = iconImg;
	}
	@Override
	public String toString() {
		return "MyGood [id=" + id + ", name=" + name + ", goodsDis=" + goodsDis
				+ ", goodsNum=" + goodsNum + ", iconImg=" + iconImg + "]";
	}
}
	

