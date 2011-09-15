package org.paramecium.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功 能 描 述:<br>
 * 缓存基本功能实现实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:29:51
 * <br>项 目 信 息:paramecium:org.paramecium.cache.BaseCache.java
 */
public abstract class BaseCache<KEY extends Object,VALUE extends Object> implements Cache<KEY, VALUE>,Cloneable, Serializable {

	private static final long serialVersionUID = 8139192731446878665L;

	protected Map<KEY,Element<KEY,VALUE>> map = new ConcurrentHashMap<KEY,Element<KEY,VALUE>>();
	
	public int maxSize = 500;
	
	public String name;
	
	public synchronized void clear() {
		map.clear();
	}

	public synchronized Object get(KEY key) {
		Element<KEY,VALUE> element = map.get(key);
		return element == null?null:element.getValue();
	}

	public synchronized Collection<KEY> getKeys() {
		return map.keySet();
	}

	public synchronized Collection<VALUE> getValues() {
		Collection<Element<KEY,VALUE>> elements = map.values();
		if(map == null || map.isEmpty()){
			return null;
		}
		Collection<VALUE> values = new ArrayList<VALUE>();
		for(Element<KEY,VALUE> element : elements){
			values.add(element.getValue());
		}
		return values;
	}

	public synchronized boolean isEmpty() {
		return map.isEmpty();
	}

	public synchronized void put(KEY key, VALUE value) {
		Element<KEY,VALUE> element = new Element<KEY,VALUE>(key, value);
		map.put(key, element);
	}

	public synchronized void remove(Object key) {
		map.remove(key);
	}

	public synchronized int size() {
		return map.size();
	}

}
