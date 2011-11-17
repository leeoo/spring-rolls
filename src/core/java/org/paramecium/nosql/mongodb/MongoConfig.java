package org.paramecium.nosql.mongodb;

import java.net.UnknownHostException;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
/**
 * 功 能 描 述:<br>
 * MongoDB连接配置
 *
	1.安装
		1.1 下载后，解压即可，版本号偶数为稳定版，单数为开发版。如1.6.3就是稳定版，1.7.2就是开发版。目前是1.8。如果32位版本，只能处理2G数据。
	
	2.启动服务
		2.1 进入bin目录后，执行mongod命令，可以启动服务。默认端口是27017。
				可以指定db物理文件的存放位置，已经log日志文件的存放位置。例如:mongod --dbpath d:\mongodb\data --logpath d:\mongodb\log\mongodb.log
		    同时可以将启动信息存在配置文件中，如建立init.conf文件，然后写入内容如：
		    port=12321
		    dbpath=d:\mongodb\data
		    logpath=d:\mongodb\log\mongodb.log
		    启动命令为mongod --config d:\mongodb\init.conf 效果一样。
		    当指定log文件时，启动的信息将会出现日志文件中，而不会在控制台打印。
		    如果mongod --config d:\mongodb\init.conf --install 则会加入系统启动服务中。
		2.2 启动成功后，可以通过浏览器http://127.0.0.1:13321查看mongodb的信息。web端口地址=数据端口+1000，即27017的数据端口对应的web端口就是28017。
	
	3.客户端连接服务
		3.1 执行mongo 127.0.0.1:12321/mydb启动。
				如果切换数据库可以使用use mydb2，然后通过db命令查看当前数据库。
		3.2 mongo只能插入小于4M的数据
 *
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-20下午02:13:15
 * <br>项 目 信 息:paramecium:org.paramecium.nosql.mongodb.MongoConfig.java
 */
public class MongoConfig {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private String url = "127.0.0.1";
	
	private int port = 27017;
	
	private String dbName;
	
	private Mongo mongo;
	
	public String toString(){
		return url+":"+port+"/"+dbName;
	}
	
	public MongoConfig(String dbName){
		this.dbName = dbName;
	}
	
	public MongoConfig(String url,int port,String dbName){
		if(url!=null){
			this.url = url;
		}
		this.port = port;
		if(dbName==null){
			throw new MongoException("MongoDB配置数据库名称不能为空!");
		}
		this.dbName = dbName;
	}
	
	public DB getDB(){
		try {
			if(mongo==null){
				mongo = new Mongo(url, port);
			}
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (MongoException e) {
			logger.error(e);
		}
 		return mongo.getDB(dbName);
	}
	
}
