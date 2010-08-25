package com.wisdom.core.web;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Embedded;

/**
 * 
 * <B>功能描述:</B><br>
 * tomcat嵌入应用集成设置 <br>
 * <B>作 者:</B> 曹阳<br>
 * <B>创建时间:</B> Nov 19, 2007<br>
 * <B>创建日期:</B> 9:56:38 AM<br>
 * <B>项目名称:</B> osmix<br>
 * <B>文件路径:</B> org.cy.demo.core<br>
 * <B>文件名称:</B> BaseWebServerEmbed.java<br>
 * 
 */
public abstract class  BaseWebServerEmbed{
	public int DEFAULT_PORT = 80;
	public String DEFAULT_ENCODING = "UTF-8";
	public String DEFAULT_SERVER_NAME = "app_server";
	public Embedded tomcat;

	public BaseWebServerEmbed(String svrName,int port,String coding) {
		if(svrName!=null){
			DEFAULT_SERVER_NAME=svrName;
		}
		if(port!=0){
			DEFAULT_PORT=port;
		}
		if(coding!=null){
			DEFAULT_ENCODING=coding;
		}
		initEmbedded();
		initShutdownHook();
	}
	public BaseWebServerEmbed() {
		initEmbedded();
		initShutdownHook();
	}

	public abstract String[] getContextsAbsolutePath();

	public abstract String[] getContextsMappingPath();

	public abstract String getTomcatPath();

	public int getPort() {
		return DEFAULT_PORT;
	}

	public void start() {
		try {
			long startTime = System.currentTimeMillis();
			tomcat.start();
			System.err.println("Embedded Tomcat started in "
					+ (System.currentTimeMillis() - startTime) + " ms.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initEmbedded() {
		tomcat = new Embedded();
		tomcat.setCatalinaBase(getTomcatPath());

		Host host = tomcat.createHost("127.0.0.1", tomcat.getCatalinaHome()
				+ "/webapps");

		String[] contexts = getContextsMappingPath();
		String[] contextsPath = getContextsAbsolutePath();
		Context context = null;

		for (int i = 0; i < contexts.length; ++i) {
			context = tomcat.createContext(contexts[i], contextsPath[i]);
			host.addChild(context);
		}

		Engine engine = tomcat.createEngine();
		engine.setName(DEFAULT_SERVER_NAME);
		engine.addChild(host);
		engine.setDefaultHost(host.getName());

		tomcat.addEngine(engine);
		// 只能本机访问
		//Connector connector = tomcat.createConnector("127.0.0.1", getPort(),false);
		// 可从其它机器访问
		Connector connector = tomcat.createConnector((java.net.InetAddress) null, DEFAULT_PORT, false);
		connector.setURIEncoding(DEFAULT_ENCODING);
		tomcat.addConnector(connector);
	}

	public void initShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				stopTomcat();
			}
		});
	}

	public void stopTomcat() {
		try {
			tomcat.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
