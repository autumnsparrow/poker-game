/**
 * 
 */
package com.sky.game.texas.domain.configuration;


/**
 * @author sparrow
 *
 */
public class GameTexasGameConfiguration {
	
	
	int smallBlinderChips;
	int bigBlinderChips;
	int maxChips;
	int minChips;
	
	int maxTables;
	String name;
	
	

	/**
	 * 
	 */
	public GameTexasGameConfiguration() {
		// TODO Auto-generated constructor stub
		this.name="House";
		this.smallBlinderChips=100;
		this.bigBlinderChips=200;
		this.minChips=50;
		this.maxChips=20000;
		this.maxTables=5;
	}


	public int getSmallBlinderChips() {
		return smallBlinderChips;
	}


	public void setSmallBlinderChips(int smallBlinderChips) {
		this.smallBlinderChips = smallBlinderChips;
	}


	public int getBigBlinderChips() {
		return bigBlinderChips;
	}


	public void setBigBlinderChips(int bigBlinderChips) {
		this.bigBlinderChips = bigBlinderChips;
	}


	public int getMaxChips() {
		return maxChips;
	}


	public void setMaxChips(int maxChips) {
		this.maxChips = maxChips;
	}


	public int getMinChips() {
		return minChips;
	}


	public void setMinChips(int minChips) {
		this.minChips = minChips;
	}


	public int getMaxTables() {
		return maxTables;
	}


	public void setMaxTables(int maxTables) {
		this.maxTables = maxTables;
	}
	
	
	
	

}
