/**
 * 
 */
package com.sky.game.service.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class ExchangeRecord {

	/**
	 * 
	 */
	public ExchangeRecord() {
		// TODO Auto-generated constructor stub
	}
    String imgUrl;
    String goodName;
    String time;
    int count;
    

    Date   date;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getTime() {
		SimpleDateFormat sim= new SimpleDateFormat();
		String time=sim.format(getDate());
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ExchangeRecord(String imgUrl, String goodName, String time, Date date) {
		super();
		this.imgUrl = imgUrl;
		this.goodName = goodName;
		this.time = time;
		this.date = date;
	}
	@Override
	public String toString() {
		return "ExchangeRecord [imgUrl=" + imgUrl + ", goodName=" + goodName
				+ ", time=" + time + ", date=" + date + "]";
	}
    
}
