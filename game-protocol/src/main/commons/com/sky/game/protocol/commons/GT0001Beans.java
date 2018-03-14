/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.domain.BaseRequest;


/**
 * @author Administrator
 *
 */
public class GT0001Beans {

	/**
	 * 
	 * retrieve the houses from the area.
	 * 
	 * 
	 * 
	 */
	public GT0001Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode="GT0001")
	public static class Request extends BaseRequest implements IIdentifiedObject{
		Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Request [id=" + id + "]";
		}
		
		
	}
	
	@HandlerResponseType(responsecode="TG0001",transcode="GT0001")
	public static class Response {
		List<House> houses;

		public List<House> getHouses() {
			return houses;
		}

		public void setHouses(List<House> houses) {
			this.houses = houses;
		}

		@Override
		public String toString() {
			return "Response [houses=" + houses + "]";
		}
		

	};
	
	
	public static class House{
		long id;
		String name;
		int smallBlind;
		int bigBlind;
		long maxChips;
		long minChips;
		int onlinePlayNumber;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getSmallBlind() {
			return smallBlind;
		}
		public void setSmallBlind(int smallBlind) {
			this.smallBlind = smallBlind;
		}
		public int getBigBlind() {
			return bigBlind;
		}
		public void setBigBlind(int bigBlind) {
			this.bigBlind = bigBlind;
		}
		public long getMaxChips() {
			return maxChips;
		}
		public void setMaxChips(long maxChips) {
			this.maxChips = maxChips;
		}
		public long getMinChips() {
			return minChips;
		}
		public void setMinChips(long minChips) {
			this.minChips = minChips;
		}
		public int getOnlinePlayNumber() {
			return onlinePlayNumber;
		}
		public void setOnlinePlayNumber(int onlinePlayNumber) {
			this.onlinePlayNumber = onlinePlayNumber;
		}
		@Override
		public String toString() {
			return "GamTexasHouseBean [id=" + id + ", name=" + name
					+ ", smallBlind=" + smallBlind + ", bigBlind=" + bigBlind
					+ ", maxChips=" + maxChips + ", minChips=" + minChips
					+ ", onlinePlayNumber=" + onlinePlayNumber + "]";
		}
		
	}
	
	@HandlerNamespaceExtraType(namespace="GameTexas")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
	}
	
	
	public static class State{
		int state;
		String description;
		
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		
		@Override
		public String toString() {
			return "State [state=" + state + ", description=" + description
					+ "]";
		}
		
		
		private State() {
			super();
			// TODO Auto-generated constructor stub
		}
		private State(int state, String description) {
			super();
			this.state = state;
			this.description = description;
			//this.timestamp=System.currentTimeMillis();//LLGlobalIdGenerator.g.getId(LLIdTypes.IdTypeMinaEvent.type) ;
		}
		
		public static State obtain(int state, String description){
			return new State(state,description);
		}
		
	}
}
