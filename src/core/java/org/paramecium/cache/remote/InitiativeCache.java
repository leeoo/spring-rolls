package org.paramecium.cache.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;

import org.paramecium.cache.BaseCache;
import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheConfig;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 分布式集群环境中的主动缓存,即缓存服务器，主动通知其他被动缓存主机
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-31下午03:58:36
 * <br>项 目 信 息:paramecium:org.paramecium.cache.remote.InitiativeCache.java
 */
@SuppressWarnings("unchecked")
public class InitiativeCache <KEY extends Object,VALUE extends Object> extends BaseCache<KEY, VALUE>{
	
	private static final long serialVersionUID = 2195981385896026876L;
	private final static Log logger = LoggerFactory.getLogger();
	private Cache<KEY,VALUE> cache;
	
	public InitiativeCache(String name,int initSize,Cache<KEY,VALUE> cache){
		this.maxSize = initSize;
		this.name = name;
		this.cache = cache;
	}
	
	
	public synchronized void put(KEY key, VALUE value) {
		if(this.maxSize < size()){
			remove(cache.peek());
		}
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache<KEY,VALUE> remoteCache = (Cache<KEY, VALUE>) Naming.lookup(ip.concat(this.name));
					remoteCache.put(key, value);
				} catch (MalformedURLException e) {
					logger.error(e);
				} catch (RemoteException e) {
					logger.error(e);
				} catch (NotBoundException e) {
					logger.error(e);
				}
			}
		}
		cache.put(key, value);
	}
	
	public synchronized void clear() {
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache<KEY,VALUE> remoteCache = (Cache<KEY, VALUE>) Naming.lookup(ip.concat(this.name));
					remoteCache.clear();
				} catch (MalformedURLException e) {
					logger.error(e);
				} catch (RemoteException e) {
					logger.error(e);
				} catch (NotBoundException e) {
					logger.error(e);
				}
			}
		}
		cache.clear();
	}
	
	public synchronized void remove(KEY key) {
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache<KEY,VALUE> remoteCache = (Cache<KEY, VALUE>) Naming.lookup(ip.concat(this.name));
					remoteCache.remove(key);
				} catch (MalformedURLException e) {
					logger.error(e);
				} catch (RemoteException e) {
					logger.error(e);
				} catch (NotBoundException e) {
					logger.error(e);
				}
			}
		}
		cache.remove(key);
	}
	
	public synchronized VALUE get(KEY key) {
		return cache.get(key);
	}

	public synchronized Collection<KEY> getKeys() {
		return cache.getKeys();
	}

	public synchronized Collection<VALUE> getValues() {
		return cache.getValues();
	}

	public synchronized boolean isEmpty() {
		return cache.isEmpty();
	}

	public synchronized int size() {
		return cache.size();
	}

	public synchronized KEY peek() {
		return cache.peek();
	}
	
}
