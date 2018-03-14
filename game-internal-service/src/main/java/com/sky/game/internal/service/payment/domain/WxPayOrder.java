package com.sky.game.internal.service.payment.domain;

public class WxPayOrder {
	String appid;
	String partnerid;
	String prepayid;
	String expand;
	String noncestr;
	String stamp;
	String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "WxPayOrder [appid=" + appid + ", partnerid=" + partnerid
				+ ", prepayid=" + prepayid + ", expand=" + expand
				+ ", noncestr=" + noncestr + ", stamp=" + stamp + ", sign="
				+ sign + "]";
	}
	
}
