package org.paramecium.cache;


/**
 * 功 能 描 述:<br>
 * 高效的高并发缓存，不处理数量超出
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:23
 * <br>项 目 信 息:paramecium:org.paramecium.cache.DefaultCache.java
 */
public class ConcurrentCache<KEY extends Object,VALUE extends Object> extends BaseCache<KEY, VALUE>{

	private static final long serialVersionUID = -2879430807192956233L;

	public ConcurrentCache(String name){
		this.name = name;
	}
	
}
