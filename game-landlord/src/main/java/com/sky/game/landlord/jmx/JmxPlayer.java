/**
 * @sparrow
 * @Feb 11, 2015   @11:14:16 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class JmxPlayer  {
	long id;
	String description;
	
	
	String detail;
	
	String state;
	
	
	boolean exception;
	
	String ex;
	
	StringBuffer buffer;
	
	StringBuffer detailBuffer;	
	
	/**
	 * 
	 */
	public JmxPlayer() {
		// TODO Auto-generated constructor stub
		buffer=new StringBuffer();
		detailBuffer=new StringBuffer();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		 return detailBuffer.toString();
		// return "<![CDATA["+detailBuffer.toString()+"]]>";
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public String getEx() {
		return this.buffer.toString();
	}

	public void setEx(String ex) {
		this.ex = ex;
	}
	
	public void append(Throwable t){
		this.exception=true;
		buffer.append(CronUtil.getFormatedDateNow()).append(" - ").append(GameUtil.getStackTrace(t)).append("\r\n");

	}
	
	public void append(String message){
		detailBuffer.append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\r\n");

	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	
	

}
