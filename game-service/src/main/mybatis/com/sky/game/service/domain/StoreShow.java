/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class StoreShow {
	/**
	 * 
	 */
	public StoreShow() {
		// TODO Auto-generated constructor stub
	}
	 long    id;
     String  iconImg;
     long    total;
     int     giftGold;
     long    itemId;
     int     price;
     String  name;
     int giftVip;
     String description;
 	int paymentChannel;
     public int getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(int paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGiftGold() {
		return giftGold;
	}
	public void setGiftGold(int giftGold) {
		this.giftGold = giftGold;
	}
	public int getGiftVip() {
		return giftVip;
	}
	public void setGiftVip(int giftVip) {
		this.giftVip = giftVip;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
