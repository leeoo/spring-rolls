package org.paramecium.cache.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.paramecium.cache.Element;
import org.paramecium.cache.RemoteCache;

/**
 * 功 能 描 述:<br>
 * 分布式集群环境中的被动缓存,即由其他主机调用,启动分布式缓存管理器时，要将自身发布到集群分支中。
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-31下午03:57:03
 * <br>项 目 信 息:paramecium:org.paramecium.cache.remote.PassiveCache.java
 */
public class PassiveCache extends UnicastRemoteObject implements RemoteCache {
	
	private static final long serialVersionUID = 6255867402368962167L;
	private ConcurrentMap<Object,Element> map = new ConcurrentSkipListMap<Object,Element>();
	protected int maxSize = 500;
	protected String name;
	
	public PassiveCache(String name,int initSize) throws RemoteException{
		this.maxSize = initSize;
		this.name = name;
	}
	public synchronized void clear() {
		map.clear();
	}

	public synchronized Object get(Object key) {
		Element element = map.get(key);
		return element == null?null:element.getValue();
	}

	public synchronized Collection<Object> getKeys() {
		return map.keySet();
	}

	public synchronized Collection<Object> getValues() {
		if(map == null || map.isEmpty()){
			return null;
		}
		Collection<Element> elements = map.values();
		Collection<Object> values = new ArrayList<Object>();
		for(Element element : elements){
			values.add(element.getValue());
		}
		return values;
	}

	public synchronized boolean isEmpty() {
		return map.isEmpty();
	}

	public synchronized void put(Object key, Object value) {
		if(this.maxSize < size()){
			remove(peek());
		}
		Element element = new Element(key, value);
		map.put(key, element);
	}

	public synchronized void remove(Object key) {
		map.remove(key);
	}

	public synchronized int size() {
		return map.size();
	}
	
	public synchronized Object peek() {
		return map.keySet().isEmpty()?null:map.keySet().iterator().next();
	}
	
}
