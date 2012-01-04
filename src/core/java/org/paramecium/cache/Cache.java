package org.paramecium.cache;

import java.rmi.RemoteException;
import java.util.Collection;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

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
