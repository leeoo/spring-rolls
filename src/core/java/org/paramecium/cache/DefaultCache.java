package org.paramecium.cache;


/**
 * 功 能 描 述:<br>
 * 默认功能缓存实现，一旦缓存数量超出，则销毁最早放入的内容
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:23
 * <br>项 目 信 息:paramecium:org.paramecium.cache.DefaultCache.java
 */
public class DefaultCache<KEY extends Object,VALUE extends Object> extends BaseCache<KEY, VALUE>{

	private static final long serialVersionUID = -8179064197236303233L;
	
	public DefaultCache(String name,int initSize){
		this.maxSize = initSize;
		this.name = name;
	}
	
	public synchronized void put(KEY key, VALUE value) {
		if(this.maxSize < size()){
			remove(this.index.peek());
		}
		super.put(key, value);
	}
	
}
