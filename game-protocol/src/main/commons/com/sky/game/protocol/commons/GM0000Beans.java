/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.List;

import com.sky.game.context.annotation.HandlerNamespaceExtraType;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.service.domain.GameBlockadeMessage;
import com.sky.game.service.logic.game.GameBlockadeMessageService;

/**
 * @author sparrow
 *
 */
public class GM0000Beans {

	/**
	 * 
	 */
	public GM0000Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerNamespaceExtraType(namespace="LLGameMessage")
	public static class Extra {
		String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
	}
	
	/**
	 * invite the players of the same blockade level.
	 * {@link GameBlockadeMessageService#inviteSameLevelPlayers(Long, Long[], String);
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GM0001")
	public static class GM0001Request{
		Long gameBlockadeUserId;
		List<Long> toGameBlockadeUserId;
		String message;
		public Long getGameBlockadeUserId() {
			return gameBlockadeUserId;
		}
		public void setGameBlockadeUserId(Long gameBlockadeUserId) {
			this.gameBlockadeUserId = gameBlockadeUserId;
		}
		public List<Long> getToGameBlockadeUserId() {
			return toGameBlockadeUserId;
		}
		public void setToGameBlockadeUserId(List<Long> toGameBlockadeUserId) {
			this.toGameBlockadeUserId = toGameBlockadeUserId;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}
	@HandlerResponseType(transcode="GM0001",responsecode="MG0001")
	public static class MG0001Response{
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
	}
	
	/**
	 * {@link GameBlockadeMessageService#getGameBlockadeMessages(Long, Long)
	 * @author sparrow
	 *
	 */
	@HandlerRequestType(transcode="GM0002")
	public static class GM0002Request{
		Long userId;
		Long roomId;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getRoomId() {
			return roomId;
		}
		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}
		
	}
	@HandlerResponseType(transcode="GM0002",responsecode="MG0002")
	public static class MG0002Response{
		List<GameBlockadeMessage> messages;

		public List<GameBlockadeMessage> getMessages() {
			return messages;
		}

		public void setMessages(List<GameBlockadeMessage> messages) {
			this.messages = messages;
		}
		
	}
	
	/**
	 * 
	 * 
	 * @author sparrow
	 *{@link GameBlockadeMessageService#updateMessageReadState(Long[])}
	 *
	 */
	@HandlerRequestType(transcode="GM0003")
	public static class GM0003Request{
		List<Long> messageMapIds;

		public List<Long> getMessageMapIds() {
			return messageMapIds;
		}

		public void setMessageMapIds(List<Long> messageMapIds) {
			this.messageMapIds = messageMapIds;
		}
		
	}
	@HandlerResponseType(transcode="GM0003",responsecode="MG0003")
	public static class MG0003Response{
		boolean valid;

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
	}
	
	
}
