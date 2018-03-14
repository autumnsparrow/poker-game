package com.sky.game.mina;

import java.io.InputStream;
import java.net.URL;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;


public class MinaContextConfiguration {
	
	private int debug;// 生产模式:0 调式模式:1
	private String minaServerHost;//mina服务器地址
	private int minaServerPort;//mina服务器端口
	
	private int taskExecutorThreadNumbers;
	
	private int processorPoolNumbers;
	
	private String publicKeyFile;
	
	private String privateKeyFile;
	
	private String password;
	
	static MinaContextConfiguration configuration=null;
	static final String MINA_CONTEXT_CONFIG="/META-INF/mina-context.conf";
	static{
		InputStream url=MinaContextConfiguration.class.getResourceAsStream(MINA_CONTEXT_CONFIG);
		configuration=GameContxtConfigurationLoader.loadConfiguration(url, MinaContextConfiguration.class);
	}
	public static MinaContextConfiguration getInstance(){
		return configuration;
	}
	
	public int getTaskExecutorThreadNumbers() {
		return taskExecutorThreadNumbers;
	}

	public void setTaskExecutorThreadNumbers(int taskExecutorThreadNumbers) {
		this.taskExecutorThreadNumbers = taskExecutorThreadNumbers;
	}

	public boolean getIsDebug() {
		return getDebug() == 1;
	}

	public String getVersion() {
		return null;
	}

	public void validate() {
		
	}

	public int getDebug() {
		return debug;
	}

	public void setDebug(int debug) {
		this.debug = debug;
	}

	public String getMinaServerHost() {
		return minaServerHost;
	}

	public void setMinaServerHost(String minaServerHost) {
		this.minaServerHost = minaServerHost;
	}

	public int getMinaServerPort() {
		return minaServerPort;
	}

	public void setMinaServerPort(int minaServerPort) {
		this.minaServerPort = minaServerPort;
	}

	public int getProcessorPoolNumbers() {
		return processorPoolNumbers;
	}

	public void setProcessorPoolNumbers(int processorPoolNumbers) {
		this.processorPoolNumbers = processorPoolNumbers;
	}

	public String getPublicKeyFile() {
		return publicKeyFile;
	}

	public void setPublicKeyFile(String publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
