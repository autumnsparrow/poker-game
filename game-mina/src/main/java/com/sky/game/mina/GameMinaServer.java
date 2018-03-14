package com.sky.game.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.integration.jmx.IoServiceMBean;

import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.j256.simplejmx.server.JmxServer;
import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.SpringContext;
import com.sky.game.context.message.impl.ice.IceServantAsyncMessageHandler;
import com.sky.game.context.message.impl.ice.IceServantMessageHandler;
import com.sky.game.mina.util.MinaContextConfiguration;

public class GameMinaServer {
	
	//创建日志
	private final static Logger LOGGER = LoggerFactory.getLogger(GameMinaServer.class);
	
	//建立一个收发消息的任务
	private static Executor taskExceutor=null;
	
	public static MinaContextConfiguration config ;
	
	public static void main(String[] args) throws IOException {
		
		SpringContext.init("classpath:META-INF/spring/applicationContext.xml");
		
		config=MinaContextConfiguration.getInstance();
		//创建IO池
		SimpleIoProcessorPool<NioSession> pool = 
	         new SimpleIoProcessorPool<NioSession>(NioProcessor.class, config.getProcessorPoolNumbers());

		IoAcceptor acceptor = new NioSocketAcceptor(pool);
//		LoggingFilter loggingFilter = new LoggingFilter();   
//		loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);// 一个新的session被创建时触发   
//		loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);// 一个新的session打开时触发   
//		loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);// 一个session被关闭时触发   
//		loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);// 接收到数据时触发   
//		loggingFilter.setMessageSentLogLevel(LogLevel.NONE);// 数据被发送后触发   
//		loggingFilter.setSessionIdleLogLevel(LogLevel.INFO);// 一个session空闲了一定时间后触发   
//		loggingFilter.setExceptionCaughtLogLevel(LogLevel.INFO);// 当有异常抛出时触发   
//		acceptor.getFilterChain().addLast("logger", loggingFilter);
		//自定义过滤器
	/*	acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new MyProtocolCodecFactory(Charset.forName("UTF-8"))));*/
		//设置数据将被读取的缓冲区大小
		//acceptor.getSessionConfig().setReadBufferSize(1024*8);
		
		//5分钟内没有读写就设置为空闲通道
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
		
		//连接池设置
		/*DefaultIoFilterChainBuilder filterChainBuilder = acceptor.getFilterChain();   
		filterChainBuilder.addLast("threadPool", new ExecutorFilter(
				Executors.newCachedThreadPool()));*/
		acceptor.setHandler(new GameMinaProtocolHandler());
		
		taskExceutor=Executors.newFixedThreadPool(config.getTaskExecutorThreadNumbers());
		
		//创建jmx监控
		JmxServer jmxServer=(JmxServer)SpringContext.getBean("jmxServer");
		MBeanServer  mBeanServer=jmxServer.getMBeanServer();
		
		IoServiceMBean serviceMBean = new IoServiceMBean(acceptor);
		// create a JMX ObjectName. This has to be in a specific format.
		try {
			ObjectName acceptorName = new ObjectName(acceptor.getClass()
					.getPackage().getName()
					+ ":type=acceptor,name="
					+ acceptor.getClass().getSimpleName() + "@" + config.getMinaServerPort());

			// register the bean on the MBeanServer. Without this line, no JMX
			// will happen for
			// this acceptor.
			
			mBeanServer.registerMBean(serviceMBean, acceptorName);
		} catch (MalformedObjectNameException e) {
			LOGGER.error("JmxServer begin happened exception,exception="+e);
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			LOGGER.error("JmxServer begin happened exception,exception="+e);
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			LOGGER.error("JmxServer begin happened exception,exception="+e);
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			LOGGER.error("JmxServer begin happened exception,exception="+e);
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOGGER.error("JmxServer begin happened exception,exception="+e);
			e.printStackTrace();
		}
		
		//获得mina服务器的端口号
		acceptor.bind(new InetSocketAddress(config.getMinaServerPort()));
		//acceptor.bind(new InetSocketAddress(config.getMinaServerPort()+100));
		GameContextGlobals.init(GameMinaServer.class.getResource(config.getContextConf()));	
		
		
		 List<Ice.Object> servants=new LinkedList<Ice.Object>();
	      servants.add(new IceServantMessageHandler());
	      servants.add(new IceServantAsyncMessageHandler());
	      GameContextGlobals.getIceServerManager().setServants(servants);
	      
	      
	      GameContextGlobals.getIceServerManager().start();
			
		
	}
	
	
	public  static void execute(Runnable task){
		taskExceutor.execute(task);
	}

	
}
