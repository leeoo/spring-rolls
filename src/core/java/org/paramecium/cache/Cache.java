package org.paramecium.cache;

import java.util.Collection;

/**
 * 功 能 描 述:<br>
 * 缓存接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:02
 * <br>项 目 信 息:paramecium:org.paramecium.cache.Cache.java
 */
public interface Cache {

	public void clear();
	
	public int size();
	
	public void put(Object key,Object value);
	
	public Object get(Object key);
	
	public void remove(Object key);
	
	public Collection<?> getKeys();
	
	public Collection<?> getValues();
	
	public boolean isEmpty();
	
	
}
