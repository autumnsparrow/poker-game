/**
 * 
 */
package com.sky.game.service.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class UserHead {

	/**
	 * 
	 */
	public UserHead() {
		// TODO Auto-generated constructor stub
	}
	List<Head> CommonList;	
	List<Head> vipHeadList	;
	public List<Head> getCommonList() {
		return CommonList;
	}
	public void setCommonList(List<Head> commonList) {
		CommonList = commonList;
	}
	public List<Head> getVipHeadList() {
		return vipHeadList;
	}
	public void setVipHeadList(List<Head> vipHeadList) {
		this.vipHeadList = vipHeadList;
	}
}
