package org.paramecium.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 功 能 描 述:<br>
 * 缓存基本功能实现实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:29:51
 * <br>项 目 信 息:paramecium:org.paramecium.cache.BaseCache.java
 */
public abstract class BaseCache implements RemoteCache,Cloneable {

	private static final long serialVersionUID = 8139192731446878665L;
	private ConcurrentMap<Object,Element> map = new ConcurrentSkipListMap<Object,Element>();
	protected int maxSize = 500;
	protected String name;
	
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
