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
public class CacheManager {
	
	private final static Log logger = LoggerFactory.getLogger();
	private static Map<String,Cache<?,?>> map = new HashMap<String,Cache<?,?>>();
	
	static{
		Map<String,String> properties = PropertiesUitls.get("/cache.properties");
		String defaultCacheSizeStr = properties.get("defaultCacheSize");
		CacheConfig.defaultCacheSize = defaultCacheSizeStr ==null ? 500 : Integer.parseInt(defaultCacheSizeStr);
		String rmiPortStr = properties.get("rmiPort");
		CacheConfig.rmiPort = rmiPortStr ==null ? 1099 : Integer.parseInt(rmiPortStr);
		CacheConfig.localServerIp = properties.get("localServerIp");
		String synchClientIp = properties.get("synchClientIp");
		if(synchClientIp!=null && synchClientIp.indexOf(',')>0){
			CacheConfig.synchClientIps = synchClientIp.split(",");
			for(int i=0 ; i<CacheConfig.synchClientIps.length;i++){
				CacheConfig.synchClientIps[i] = "//".concat(CacheConfig.synchClientIps[i]).concat("/");
			}
		}
	}
	
	/**
	 * 默认先进先出
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name){
		return getDefaultCache(name, CacheConfig.defaultCacheSize);
	}

	/**
	 * 默认先进先出
	 * @param name
	 * @param maxSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> cache = new DefaultCache<Object, Object>(name, maxSize);
			map.put(name, cache);
		}
		return map.get(name);
	}

	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getRemoteCache(String name){
		return getRemoteCache(name, CacheConfig.defaultCacheSize);
	}
	
	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @param maxSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getRemoteCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> passiveCache = new PassiveCache<Object, Object>(name, maxSize);//被动接受缓存更新
			if(CacheConfig.localServerIp!=null && !CacheConfig.localServerIp.isEmpty()){
				try {
					LocateRegistry.createRegistry(CacheConfig.rmiPort);
					Naming.rebind("//".concat(CacheConfig.localServerIp).concat("/").concat(name), passiveCache);//发布被动接口
				} catch (MalformedURLException e) {
					logger.error(e);
				} catch (RemoteException e) {
					logger.error(e);
				}
			}
			Cache<?,?> initiativeCache = new InitiativeCache<Object, Object>(name, maxSize);//将缓存自身服务端放入本地缓存，如有变化，通知其他被动缓存主机。
			map.put(name, initiativeCache);
		}
		return map.get(name);
	}
	
}
