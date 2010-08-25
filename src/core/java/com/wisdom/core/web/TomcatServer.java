package com.wisdom.core.web;
/**
 * 功能描述(Description):<br><b>
 * tomcat嵌入服务
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-4-19下午02:38:00</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.web.TomcatServer.java</b>
 */
public class TomcatServer extends BaseWebServerEmbed {
	public String DEFAULT_PATH = "/web";

	public String[] getContextsAbsolutePath() {
		return new String[] { getSingleContextAbsolutePath() };
	}

	private String getSingleContextAbsolutePath() {
		String path = System.getProperty("user.dir") + DEFAULT_PATH;
		return path;
	}

	public String[] getContextsMappingPath() {
		return new String[] { "/" };
	}
	
	public TomcatServer(String svrName,String path,int port,String coding){
		if(path!=null){
			DEFAULT_PATH=path;
		}
		if(svrName!=null){
			super.DEFAULT_SERVER_NAME=svrName;
		}
		if(port!=0){
			super.DEFAULT_PORT=port;
		}
		if(coding!=null){
			super.DEFAULT_ENCODING=coding;
		}
		initEmbedded();
		initShutdownHook();
	}
	
	public TomcatServer(String svrName,int port,String coding){
		super(svrName,port,coding);
	}
	
	public TomcatServer() {
		super();
	}
	
	public String getTomcatPath() {
		try {
			return System.getProperty("user.dir") + "/server";
		} catch (Exception e) {
			return "/D:\\tomcat-embed";
		}
	}

}
