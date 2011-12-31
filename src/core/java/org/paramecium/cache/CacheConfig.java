package org.paramecium.cache;

/**
 * 功 能 描 述:<br>
 * 缓存配置文件
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-31下午02:10:51
 * <br>项 目 信 息:paramecium:org.paramecium.cache.CacheConfig.java
 */
public class CacheConfig {
	
	public static int defaultCacheSize = 500;

	public static int rmiPort = 1099;
	
	public static String localServerIp;
	
	public static String[] synchClientIps;
	
	public static String cacheType = "default";

}
