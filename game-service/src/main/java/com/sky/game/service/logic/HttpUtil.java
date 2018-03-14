package com.sky.game.service.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 类名：https/https报文发送处理类 功能：https/https报文发送处理 版本：1.0 日期：2012-10-11 作者：中国银联UPMP团队
 * 版权：中国银联 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class HttpUtil {
	private static final Log logger = LogFactory.getLog(HttpUtil.class);

	// public static String encoding;
	//
	// private static final HttpClientConnectionManager connectionManager;
	//
	// private static final HttpClient client;
	//
	// static {
	//
	// HttpConnectionManagerParams params = loadHttpConfFromFile();
	//
	// connectionManager = new MultiThreadedHttpConnectionManager();
	//
	// connectionManager.setParams(params);
	//
	// client = new HttpClient(connectionManager);
	// }
	//
	// private static HttpConnectionManagerParams loadHttpConfFromFile(){
	// Properties p = new Properties();
	// try {
	// String properties=HttpUtil.class.getSimpleName().toLowerCase() +
	// ".properties";
	// InputStream
	// is=HttpUtil.class.getClassLoader().getResourceAsStream(properties);
	//
	//
	// p.load(is);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }finally{
	//
	// }
	//
	// encoding = p.getProperty("http.content.encoding");
	//
	// HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	// params.setConnectionTimeout(Integer.parseInt(p.getProperty("http.connection.timeout")));
	// params.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")));
	// params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getProperty("http.stale.check.enabled")));
	// params.setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")));
	// params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getProperty("http.default.max.connections.per.host")));
	// params.setMaxTotalConnections(Integer.parseInt(p.getProperty("http.max.total.connections")));
	//
	// params.setParameter(HttpMethodParams.RETRY_HANDLER, new
	// DefaultHttpMethodRetryHandler(0, false));
	// return params;
	// }
	//
	// public static String post(String url, String encoding, String content) {
	// try {
	// byte[] resp = post(url, content.getBytes(encoding));
	// if (null == resp)
	// return null;
	// return new String(resp, encoding);
	// } catch (UnsupportedEncodingException e) {
	// return null;
	// }
	// }
	//
	//
	// public static String post(String url, String content) {
	// return post(url, encoding, content);
	// }
	//
	//
	// public static byte[] post(String url, byte[] content) {
	// try {
	// byte[] ret = post(url, new ByteArrayRequestEntity(content));
	// return ret;
	// } catch (Exception e) {
	// return null;
	// }
	// }
	//
	// public static byte[] post(String url, RequestEntity requestEntity) throws
	// Exception {
	//
	// PostMethod method = new PostMethod(url);
	// method.addRequestHeader("Connection", "Keep-Alive");
	// method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
	// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
	// DefaultHttpMethodRetryHandler(5, false));
	//
	// method.setRequestEntity(requestEntity);
	// method.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
	//
	// try {
	// int statusCode = client.executeMethod(method);
	// if (statusCode != HttpStatus.SC_OK) {
	// logger.info("HttpClient.POST- status code="+statusCode);
	// return null;
	// }
	// return method.getResponseBody();
	//
	// } catch (SocketTimeoutException e) {
	// logger.info("HttpClient.POST- socket timeout="+e.getMessage());
	// return null;
	// } catch (Exception e) {
	// logger.info("HttpClient.POST- exception="+e.getMessage());
	// return null;
	// } finally {
	// method.releaseConnection();
	// }
	// }

	// Use custom message parser / writer to customize the way HTTP
	// messages are parsed from and written out to the data stream.

	private static CloseableHttpClient httpClient;
	static {
		PoolingHttpClientConnectionManager connManager=loadHttpConfFromFile();
		ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {

		    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		        // Honor 'keep-alive' header
		        HeaderElementIterator it = new BasicHeaderElementIterator(
		                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
		        while (it.hasNext()) {
		            HeaderElement he = it.nextElement();
		            String param = he.getName();
		            String value = he.getValue();
		            if (value != null && param.equalsIgnoreCase("timeout")) {
		                try {
		                	logger.info("Keep-Alive:"+value);
		                    return Long.parseLong(value) * 1000;
		                } catch(NumberFormatException ignore) {
		                	logger.info("HttpClient.POST -"+ignore.getMessage());
		                }
		            }
		        }
		      
		        
		            // otherwise keep alive for 30 seconds
		        return 1*60 * 1000;
		        
		    }

			

		};
		httpClient = HttpClients.custom().setConnectionManager(connManager)
				.build();
	}

	private static String encoding;

	private static PoolingHttpClientConnectionManager loadHttpConfFromFile() {
		Properties p = new Properties();
		try {
			String properties = HttpUtil.class.getSimpleName().toLowerCase()
					+ ".properties";
			InputStream is = HttpUtil.class.getClassLoader()
					.getResourceAsStream(properties);

			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		encoding = p.getProperty("http.content.encoding");

		// Client HTTP connection objects when fully initialized can be bound to
		// an arbitrary network socket. The process of network socket
		// initialization,
		// its connection to a remote address and binding to a local one is
		// controlled
		// by a connection socket factory.

		// SSL context for secure connections can be created either based on
		// system or application specific properties.
		SSLContext sslcontext = SSLContexts.createSystemDefault();
		// Use custom hostname verifier to customize SSL hostname verification.
		X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();

		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register(
						"https",
						new SSLConnectionSocketFactory(sslcontext,
								hostnameVerifier)).build();

		// Use custom DNS resolver to override the system DNS resolution.
		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host)
					throws UnknownHostException {
				if (host.equalsIgnoreCase("myhost")) {
					return new InetAddress[] { InetAddress
							.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};
		
		
		

		// Create a connection manager with custom configuration.
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry, dnsResolver);

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")))
				.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")))
				.setSoKeepAlive(Boolean.parseBoolean(p.getProperty("http.tcp.so.keepalive")))
				
				.build();
		// Configure the connection manager to use socket configuration either
		// by default or for a specific host.
		connManager.setDefaultSocketConfig(socketConfig);
		// connManager.setSocketConfig(new HttpHost("somehost", 80),
		// socketConfig);

		
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE)
				
				.setCharset(Charset.forName(encoding))
				.build();
		// Configure the connection manager to use connection configuration
		// either
		// by default or for a specific host.
		connManager.setDefaultConnectionConfig(connectionConfig);
		
		// connManager.setConnectionConfig(new HttpHost("somehost", 80),
		// ConnectionConfig.DEFAULT);

		// Configure total max or per route limits for persistent connections
		// that can be kept in the pool or leased by the connection manager.
		connManager.setMaxTotal(Integer.parseInt(p.getProperty("http.max.total.connections")));
		connManager.setDefaultMaxPerRoute(10);
		
		

		// PoolingHttpClientConnectionManager cm = new
		// PoolingHttpClientConnectionManager();
		// cm.setMaxTotal(100);
		//connManager.setMaxTotal(100);
		return connManager;

		//
		// HttpConnectionManagerParams params = new
		// HttpConnectionManagerParams();
		// params.setConnectionTimeout(Integer.parseInt(p.getProperty("http.connection.timeout")));
		// params.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")));
		// params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getProperty("http.stale.check.enabled")));
		// params.setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")));
		// params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getProperty("http.default.max.connections.per.host")));
		// params.setMaxTotalConnections(Integer.parseInt(p.getProperty("http.max.total.connections")));
		//
		// params.setParameter(HttpMethodParams.RETRY_HANDLER, new
		// DefaultHttpMethodRetryHandler(0, false));
		// return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @param url
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String content)  {
		HttpPost post = new HttpPost(url);

		
		String  resp=null;
		CloseableHttpResponse response=null;
		try {
			ByteArrayEntity requestEntity = new ByteArrayEntity(
					content.getBytes(encoding),
					ContentType.APPLICATION_OCTET_STREAM);
			post.setEntity(requestEntity);
			response= httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resp=EntityUtils.toString(entity, Charset.forName(encoding));
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("HttpClient.POST - " + ex.getMessage());

		} finally {
			
				try {
					if(response!=null)
					response.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.info("HttpClient.POST closing - " + e.getMessage());
				}
		}
		
		return resp;
			

	}
	
//	public static String post(String url, String content) throws Exception{
//		try {
//			byte[] resp = post(url, content.getBytes(encoding));
//			if (null == resp)
//				return null;
//			return new String(resp, encoding);
//		} catch (UnsupportedEncodingException e) {
//			return null;
//		}
//	}

}
