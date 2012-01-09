package org.paramecium.cache;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import org.paramecium.cache.remote.InitiativeCache;
import org.paramecium.cache.remote.PassiveCache;
import org.paramecium.commons.PropertiesUitls;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 缓存管理器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:29:39
 * <br>项 目 信 息:paramecium:org.paramecium.cache.CacheManager.java
 */
@SuppressWarnings("unchecked")
public class CacheManager {
	
	private final static Log logger = LoggerFactory.getLogger();
	private static Map<String,Cache<?,?>> map = new HashMap<String,Cache<?,?>>();
	
	static{
		Map<String,String> properties = PropertiesUitls.get("/cache.properties");
		String defaultCacheSizeStr = properties.get("defaultCacheSize");
		CacheConfig.defaultCacheSize = defaultCacheSizeStr ==null ? 500 : Integer.parseInt(defaultCacheSizeStr);
		String cacheType = properties.get("cacheType");
		CacheConfig.cacheType = cacheType==null||cacheType.isEmpty() ? "default" : cacheType;
		if(CacheConfig.cacheType.equalsIgnoreCase("remote")){
			String rmiPortStr = properties.get("rmiPort");
			CacheConfig.rmiPort = rmiPortStr ==null ? 1099 : Integer.parseInt(rmiPortStr);
			try {
				LocateRegistry.createRegistry(CacheConfig.rmiPort);
			} catch (RemoteException e) {
				logger.error(e);
			}
			CacheConfig.localServerIp = properties.get("localServerIp");
			if(CacheConfig.localServerIp!=null&&!CacheConfig.localServerIp.isEmpty()){
				System.setProperty("java.rmi.server.hostname",CacheConfig.localServerIp);
			}
			String synchClientIp = properties.get("synchClientIp");
			if(synchClientIp!=null){
				if(synchClientIp.indexOf(',')>0){
					CacheConfig.synchClientIps = synchClientIp.split(",");
					for(int i=0 ; i<CacheConfig.synchClientIps.length;i++){
						CacheConfig.synchClientIps[i] = "//".concat(CacheConfig.synchClientIps[i]).concat("/");
					}
				}else{
					CacheConfig.synchClientIps = new String[]{"//".concat(synchClientIp).concat("/")};
				}
			}
		}
	}
	
	/**
	 * 自动根据类型配置获取缓存实例
	 * @param name
	 * @return
	 */
	public static synchronized Cache<?,?> getCacheByType(String name){
		if(CacheConfig.cacheType.equalsIgnoreCase("remote")){
			return getRemoteCache(name);
		}else{
			return getDefaultCache(name);
		}
	}

	/**
	 * 自动根据类型配置获取缓存实例
	 * @param name
	 * @param maxSize
	 * @return
	 */
	public static synchronized Cache<?,?> getCacheByType(String name,int maxSize){
		if(CacheConfig.cacheType.equalsIgnoreCase("remote")){
			return getRemoteCache(name,maxSize);
		}else{
			return getDefaultCache(name,maxSize);
		}
	}
	
	/**
	 * 默认先进先出
	 * @param name
	 * @return
	 */
	
	public static synchronized Cache<?,?> getDefaultCache(String name){
		return getDefaultCache(name, CacheConfig.defaultCacheSize);
	}

	/**
	 * 默认先进先出
	 * @param name
	 * @param maxSize
	 * @return
	 */
	public static synchronized Cache<?,?> getDefaultCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> cache = null;
			try {
				cache = new Cache(new DefaultCache(name, maxSize));
			} catch (RemoteException e) {
				logger.error(e);
			}
			map.put(name, cache);
		}
		return map.get(name);
	}
	

	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @return
	 */
	public static synchronized Cache<?,?> getRemoteCache(String name){
		return getRemoteCache(name, CacheConfig.defaultCacheSize);
	}
	
	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @param maxSize
	 * @return
	 */
	public static synchronized Cache<?,?> getRemoteCache(String name,int maxSize){
		if(map.get(name)==null){
			RemoteCache passiveCache = null;
			try {
				passiveCache = new PassiveCache(name, maxSize);
			} catch (RemoteException e1) {
				logger.error(e1);
			}//被动接受缓存更新
			if(CacheConfig.localServerIp!=null && !CacheConfig.localServerIp.isEmpty() && passiveCache!= null){
				try {
					Naming.rebind("//".concat(CacheConfig.localServerIp.concat(":"+CacheConfig.rmiPort+"/")).concat(name), passiveCache);//发布被动接口
				} catch (MalformedURLException e) {
					logger.error(e);
				} catch (RemoteException e) {
					logger.error(e);
				}
			}
			Cache<?,?> initiativeCache = null;
			try {
				if(passiveCache!=null){
					initiativeCache = new Cache(new InitiativeCache(name, maxSize,passiveCache));
				}
			} catch (RemoteException e) {
				logger.error(e);
			}//将缓存自身服务端放入本地缓存，如有变化，通知其他被动缓存主机。
			if(initiativeCache != null){
				map.put(name, initiativeCache);
			}
		}
		return map.get(name);
	}
	
}
