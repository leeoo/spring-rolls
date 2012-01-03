package org.paramecium.cache.remote;

import org.paramecium.cache.BaseCache;

/**
 * 功 能 描 述:<br>
 * 分布式集群环境中的被动缓存,即由其他主机调用,启动分布式缓存管理器时，要将自身发布到集群分支中。
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-31下午03:57:03
 * <br>项 目 信 息:paramecium:org.paramecium.cache.remote.PassiveCache.java
 */
public class PassiveCache <KEY extends Object,VALUE extends Object> extends BaseCache<KEY, VALUE>{
	
	private static final long serialVersionUID = 6255867402368962167L;
	
	public PassiveCache(String name,int initSize){
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
