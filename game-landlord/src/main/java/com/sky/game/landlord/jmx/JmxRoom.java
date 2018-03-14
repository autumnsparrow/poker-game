/**
 * @sparrow
 * @Feb 11, 2015   @11:22:53 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.jmx;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class JmxRoom {
	
	//List<JmxTeam> teams;
	@JsonIgnore
	Map<Long,JmxTeam> teams;
	
	List<JmxTeam> jmxTeams;
	/**
	 * 
	 */
	public JmxRoom() {
		// TODO Auto-generated constructor stub
		teams=GameUtil.getMap();
	}
	public Map<Long, JmxTeam> getTeams() {
		return teams;
	}
	public void setTeams(Map<Long, JmxTeam> teams) {
		this.teams = teams;
	}
	
	

	
	public void add(JmxTeam team){
		this.teams.put(Long.valueOf(team.id), team);
	}
	
	public void remove(JmxTeam team){
		this.teams.remove(Long.valueOf(team.id));
	}
	public List<JmxTeam> getJmxTeams() {
		return GameUtil.getMapAsList(this.teams);
	}
	public void setJmxTeams(List<JmxTeam> jmxTeams) {
		this.jmxTeams = jmxTeams;
	}
}
