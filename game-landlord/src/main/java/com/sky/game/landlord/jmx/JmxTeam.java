/**
 * @sparrow
 * @Feb 11, 2015   @11:23:16 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import java.util.List;

import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class JmxTeam {
	
	Long id;
	List<JmxDeck> decks;
	
	
	String detail;
	
	String state;
	
	
	boolean exception;
	
	String ex;
	
	StringBuffer buffer;
	
	StringBuffer detailBuffer;

	/**
	 * 
	 */
	public JmxTeam() {
		// TODO Auto-generated constructor stub
		decks=GameUtil.getList();
		buffer=new StringBuffer();
		detailBuffer=new StringBuffer();
	}

	public List<JmxDeck> getDecks() {
		return decks;
	}

	public void setDecks(List<JmxDeck> decks) {
		this.decks = decks;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

}
