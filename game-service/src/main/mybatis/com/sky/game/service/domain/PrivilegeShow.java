/**
 * 
 */
package com.sky.game.service.domain;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class PrivilegeShow {

	/**
	 * 
	 */
	public PrivilegeShow() {
		// TODO Auto-generated constructor stub
	}
    int    vipFen;//vip积分
    String vipLevel;//vip等级
    Date   loseTime;//过期时间
    List<Privilege> privilegeList;//特权列表
	/*String name	;//String	特权名称
	String iconStr	;//String	图片
    */	
	public int getVipFen() {
		return vipFen;
	}
	public void setVipFen(int vipFen) {
		this.vipFen = vipFen;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public Date getLoseTime() {
		return loseTime;
	}
	public void setLoseTime(Date loseTime) {
		this.loseTime = loseTime;
	}
	public List<Privilege> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<Privilege> privilegeList) {
		this.privilegeList = privilegeList;
	}
}
