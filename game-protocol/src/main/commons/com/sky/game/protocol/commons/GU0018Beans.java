/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class GU0018Beans {

	/**
	 * 
	 */
	public GU0018Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0018")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long   Id;       //user_items 的id
		long   itemId;    //物品id
		String phone;	 //电话
		String name	;    //名称
		String postCode	;    //邮编
		String district; // 地区
		String address	;//详细地址
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getPostCode() {
			return postCode;
		}
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public long getId() {
			return Id;
		}
		public void setId(long id) {
			Id = id;
		}
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		
	}

	@HandlerResponseType(responsecode = "UG0018", transcode = "GU0018")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	    long id;
	    int state;
	    String description;
	    
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public Response(long id, int state) {
			super();
			this.id = id;
			this.state = state;
		}
		@Override
		public String toString() {
			return "Response [id=" + id + ", state=" + state + "]";
		}
	};
}
