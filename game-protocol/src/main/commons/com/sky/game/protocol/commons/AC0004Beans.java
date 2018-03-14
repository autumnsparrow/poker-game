/**
 * 
 */
package com.sky.game.protocol.commons;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
/**
 * @author Administrator
 *
 * 我的拍买行--上拍--确定 Beans
 */
public class AC0004Beans {

	/**
	 * 
	 */
	public AC0004Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "AC0004")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String token;//一个标志
		long id		;//物品列表的id
		String type	;//	拍卖方式
		String chooseCoin	;//	货币选择
		int price	;//出售价格
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getChooseCoin() {
			return chooseCoin;
		}
		public void setChooseCoin(String chooseCoin) {
			this.chooseCoin = chooseCoin;
		}
	}
	
	@HandlerResponseType(responsecode="CA0004",transcode="AC0004")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
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
	}
}
