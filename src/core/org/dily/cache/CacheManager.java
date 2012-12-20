package org.dily.cache;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import org.dily.cache.remote.InitiativeCache;
import org.dily.cache.remote.PassiveCache;
import org.dily.commons.EncodeUtils;
import org.dily.commons.PropertiesUitls;
import org.dily.commons.ThreadUtils;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 缓存管理器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:29:39
 * <br>项 目 信 息:dily:org.dily.cache.CacheManager.java
 */
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
		properties.clear();
		ThreadUtils.add(new CacheHandlerThread(),"缓存监控线程");
	}
	
	static class CacheHandlerThread implements Runnable {
		
		public CacheHandlerThread(){
			logger.debug("缓存生命周期监控处理线程已启动");
		}
		
		public void run() {
			clearCache();
		}
		
		private void clearCache(){
			while (true) {
				try {
					if(map==null||map.isEmpty()){
						Thread.sleep(10*60000);//如果没有信息，睡1分钟
						continue;
					}
					for(String name : map.keySet()){
						@SuppressWarnings("unchecked")
						Cache<Object,Object> cache = (Cache<Object, Object>) map.get(name);
						if(cache==null||cache.life()==null){
							continue;
						}
						long lifeMs = cache.life() * 1000l;
						long time = EncodeUtils.millisTime();
						if(!cache.isEmpty()){
							for(Element element : cache.getElements()){
								if(lifeMs<time-element.getAccessTime()){
									cache.remove(element.getKey());
									logger.debug(name+":缓存<"+element.getKey()+">生命周期已达到销毁期限，已被销毁！");
								}
							}
						}
					}
					Thread.sleep(60000);//定时执行
				} catch (Exception ex) {
					ex.printStackTrace();
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//定时执行
				}
			}
		}
	}
	
	/**
	 * 获得缓存监控数据
	 * @return
	 */
	public static Map<String,int[]> getCacheReport(){
		Map<String,int[]> mapReport = new HashMap<String, int[]>();
		for(String key : map.keySet()){
			int[] values = new int[]{map.get(key).rated(),map.get(key).size()};
			mapReport.put(key, values);
		}
		return mapReport;
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
	 * 自动根据类型配置获取缓存实例
	 * @param name
	 * @param maxSize
	 * @param life
	 * @return
	 */
	public static synchronized Cache<?,?> getCacheByType(String name,int maxSize,Long life){
		if(CacheConfig.cacheType.equalsIgnoreCase("remote")){
			return getRemoteCache(name,maxSize,life);
		}else{
			return getDefaultCache(name,maxSize,life);
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
		return getDefaultCache(name, maxSize,null);
	}
	
	/**
	 * 默认先进先出
	 * @param name
	 * @param maxSize
	 * @param life
	 * @return
	 */
	public static synchronized Cache<?,?> getDefaultCache(String name,int maxSize,Long life){
		if(map.get(name)==null){
			Cache<?,?> cache = null;
			try {
				cache = new Cache<Object, Object>(new DefaultCache(name, maxSize,life));
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
		return getRemoteCache(name, maxSize,null);
	}
	
	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @param maxSize
	 * @param life
	 * @return
	 */
	public static synchronized Cache<?,?> getRemoteCache(String name,int maxSize,Long life){
		if(map.get(name)==null){
			RemoteCache passiveCache = null;
			try {
				passiveCache = new PassiveCache(name, maxSize,life);
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
					initiativeCache = new Cache<Object,Object>(new InitiativeCache(name, maxSize,passiveCache));
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
