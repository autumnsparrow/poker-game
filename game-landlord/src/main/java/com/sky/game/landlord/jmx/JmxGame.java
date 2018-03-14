/**
 * @sparrow
 * @Feb 11, 2015   @11:18:15 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class JmxGame  extends JmxObject{
	
	String detail;
	
	boolean exception;
	
	String ex;
	
	StringBuffer buffer;
	
	StringBuffer detailBuffer;

	/**
	 * 
	 */
	public JmxGame() {
		// TODO Auto-generated constructor stub
		buffer=new StringBuffer();
		detailBuffer=new StringBuffer();
	}

	public String getDetail() {
	//	return "<![CDATA["+detailBuffer.toString()+"]]>";
		return detailBuffer.toString();
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

		//buffer.append(CronUtil.getFormatedDateNow()).append(" - ").append(GameUtil.getStackTrace(t)).append("\n");
	}
	
	
	public void appaend(String message){
		detailBuffer.append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\r\n");

		//	detailBuffer.append("#").append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\n");
		//detailBuffer.append(message);
	}
	
	

}
