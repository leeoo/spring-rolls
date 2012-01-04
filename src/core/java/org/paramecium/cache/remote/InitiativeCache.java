package org.paramecium.cache.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

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
public class InitiativeCache extends UnicastRemoteObject implements Cache {
	
	private static final long serialVersionUID = 2195981385896026876L;
	private final static Log logger = LoggerFactory.getLogger();
	private Cache cache;
	protected int maxSize = 500;
	protected String name;
	
	public InitiativeCache(String name,int initSize,Cache cache) throws RemoteException{
		this.maxSize = initSize;
		this.name = name;
		this.cache = cache;
	}
	
	
	public synchronized void put(Object key, Object value) {
		if(this.maxSize < size()){
			remove(cache.peek());
		}
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache remoteCache = (Cache) Naming.lookup(ip.concat(this.name));
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
		try {
			cache.put(key, value);
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized void clear() {
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache remoteCache = (Cache) Naming.lookup(ip.concat(this.name));
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
		try {
			cache.clear();
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized void remove(Object key) {
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length<1){
			for(String ip : CacheConfig.synchClientIps){
				try {
					Cache remoteCache = (Cache) Naming.lookup(ip.concat(this.name));
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
		try {
			cache.remove(key);
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized Object get(Object key) {
		return cache.get(key);
	}

	public synchronized Collection<Object> getKeys() {
		return cache.getKeys();
	}

	public synchronized Collection<Object> getValues() {
		return cache.getValues();
	}

	public synchronized boolean isEmpty() {
		return cache.isEmpty();
	}

	public synchronized int size() {
		return cache.size();
	}

	public synchronized Object peek() {
		return cache.peek();
	}
	
}
