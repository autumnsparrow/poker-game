package com.sky.game.service.domain;

import java.util.List;

public class Vips {

	public Vips() {
		// TODO Auto-generated constructor stub
	}
	List<Vip> vip;
	public List<Vip> getVip() {
		return vip;
	}
	public void setVip(List<Vip> vip) {
		this.vip = vip;
	}
	public Vips(List<Vip> vip) {
		super();
		this.vip = vip;
	}
	@Override
	public String toString() {
		return "Vips [vip=" + vip + "]";
	}
	
}
