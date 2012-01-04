package org.paramecium.cache.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import org.paramecium.cache.CacheConfig;
import org.paramecium.cache.RemoteCache;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 分布式集群环境中的主动缓存,即缓存服务器，主动通知其他被动缓存主机
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-31下午03:58:36
 * <br>项 目 信 息:paramecium:org.paramecium.cache.remote.InitiativeCache.java
 */
public class InitiativeCache extends UnicastRemoteObject implements RemoteCache {
	
	private static final long serialVersionUID = 2195981385896026876L;
	private final static Log logger = LoggerFactory.getLogger();
	private RemoteCache cache;
	protected int maxSize = 500;
	protected String name;
	
	public InitiativeCache(String name,int initSize,RemoteCache cache) throws RemoteException{
		this.maxSize = initSize;
		this.name = name;
		this.cache = cache;
	}
	
	
	public synchronized void put(Object key, Object value) {
		if(this.maxSize < size()){
			try {
				remove(cache.peek());
			} catch (RemoteException e) {
				logger.error(e);
			}
		}
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length>0){
			for(String ip : CacheConfig.synchClientIps){
				try {
					RemoteCache remoteCache = (RemoteCache) Naming.lookup(ip.concat(this.name));
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
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length>0){
			for(String ip : CacheConfig.synchClientIps){
				try {
					RemoteCache remoteCache = (RemoteCache) Naming.lookup(ip.concat(this.name));
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
		if(CacheConfig.synchClientIps!=null && CacheConfig.synchClientIps.length>0){
			for(String ip : CacheConfig.synchClientIps){
				try {
					RemoteCache remoteCache = (RemoteCache) Naming.lookup(ip.concat(this.name));
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
		try {
			return cache.get(key);
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized Collection<Object> getKeys() {
		try {
			return cache.getKeys();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized Collection<Object> getValues() {
		try {
			return cache.getValues();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized boolean isEmpty() {
		try {
			return cache.isEmpty();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return true;
	}

	public synchronized int size() {
		try {
			return cache.size();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return 0;
	}

	public synchronized Object peek() {
		try {
			return cache.peek();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}
	
}
