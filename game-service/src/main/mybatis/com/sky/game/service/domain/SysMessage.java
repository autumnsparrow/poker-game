package com.sky.game.service.domain;

public class SysMessage {

	public SysMessage() {
		// TODO Auto-generated constructor stub
	}
	long id;
	String title;
	String content;
	int type;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public SysMessage(long id, String title, String content,int type) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.type=type;
	}
	@Override
	public String toString() {
		return "SysMessage [id=" + id + ", title=" + title + ", content="
				+ content + ",type=" + type + "]";
	}
	
	
	
}
