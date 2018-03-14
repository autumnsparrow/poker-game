/**
 * 
 * @Date:Nov 19, 2014 - 5:33:33 PM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;

import com.sky.game.context.util.GameUtil;
import com.sky.game.mina.MinaConnector;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.mina.packet.Packet;
import com.sky.game.mina.packet.PacketException;
import com.sky.game.mina.packet.PacketTypes;
import com.sky.game.protocol.commons.GU0001Beans;
import com.sky.game.protocol.commons.GU0002Beans;

/**
 * @author sparrow
 * 
 */
public class MobileUser implements Runnable ,IoFutureListener<IoFuture>{
	
	private static final Log logger=LogFactory.getLog(MobileUser.class);

	public String deviceId;
	public String username;
	public String password;
	public String token;
	public SessionContext context;
	MinaConnector connector;

	/**
	 * 
	 */
	public MobileUser(MinaConnector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}

	public MobileUser(MinaConnector connector, String username,
			String password, String deviceId) {
		super();
		this.connector = connector;
		this.username = username;
		this.password = password;
		this.deviceId = deviceId;

//		IoSession session = connector.connect("127.0.0.1", 4064);
//
//		this.context = SessionContext.getContext(session);
		
	}

	public void connect() {

		 connector.connect("127.0.0.1", 4064,this);
		

		
	}

	public void binding() {
		String content = deviceId + "~kkkkkk";
		Packet p = new Packet(content,
				PacketTypes.DeviceBindingPacketType.getType());

		try {
			MinaConnector.requestAsync(p, context.getSession());
		} catch (PacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void register(){
		
			GU0001Beans.Request req=GameUtil.obtain(GU0001Beans.Request.class);
			req.setAccount(username);
			req.setPassword(password);
			
			ProxyMinaMessageInvoker.asyncInvoke(context, req);
			
			context.getSession().close(false);
		
	}
	
	private void login() {
		GU0002Beans.Request request = new GU0002Beans.Request();
		request.setAccount(username);
		request.setPassword(password);
		request.setDeviceId(deviceId);
		// ValueHolder<GU0001Beans.Response> holder=new
		// ValueHolder<GU0001Beans.Response>();
		ProxyMinaMessageInvoker.asyncInvoke(context, "GU0002", request);
	}

	public void onBinded() {
		logger.info("device - "+deviceId+" binded!");
		//login();
		register();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		connect();

	}

	
	public static MobileUser  obtain(MinaConnector connector, String username,
			String password, String deviceId){
		return new MobileUser(connector, username, password, deviceId);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.core.future.IoFutureListener#operationComplete(org.apache.mina.core.future.IoFuture)
	 */
	public void operationComplete(IoFuture future) {
		// TODO Auto-generated method stub
		//
		// 
		logger.info("Connected "+future.getSession());
		context = SessionContext.getContext(future.getSession());
		context.mobileUser = this;
		
	}
}
