package org.paramecium.cache;

import java.rmi.RemoteException;
import java.util.Collection;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * 缓存封装类，封装带有分布式缓存功能
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2012-1-4下午08:11:21</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.cache.Cache.java</b>
 */
@SuppressWarnings("unchecked")
public class Cache<KEY extends Object,VALUE extends Object> {
	
	private RemoteCache cache;
	private final static Log logger = LoggerFactory.getLogger();
	
	public Cache(RemoteCache cache){
		this.cache = cache;
	}
	public synchronized void put(KEY key, VALUE value) {
		try {
			cache.put(key, value);
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized void clear() {
		try {
			cache.clear();
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized void remove(KEY key) {
		try {
			cache.remove(key);
		} catch (RemoteException e) {
			logger.error(e);
		}
	}
	
	public synchronized VALUE get(KEY key) {
		try {
			return (VALUE) cache.get(key);
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized Collection<KEY> getKeys() {
		try {
			return (Collection<KEY>) cache.getKeys();
		} catch (RemoteException e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized Collection<VALUE> getValues() {
		try {
			return (Collection<VALUE>) cache.getValues();
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
