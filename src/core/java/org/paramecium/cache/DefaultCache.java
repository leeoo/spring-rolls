package org.paramecium.cache;

import java.rmi.RemoteException;


/**
 * 功 能 描 述:<br>
 * 默认功能缓存实现，一旦缓存数量超出，则销毁最早放入的内容
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:23
 * <br>项 目 信 息:paramecium:org.paramecium.cache.DefaultCache.java
 */
public class DefaultCache extends BaseCache{

	private static final long serialVersionUID = -8179064197236303233L;
	
	public DefaultCache(String name,int initSize) throws RemoteException{
		this.maxSize = initSize;
		this.name = name;
	}
	
	public synchronized void put(Object key, Object value) {
		if(this.maxSize < size()){
			remove(peek());
		}
		super.put(key, value);
	}
	
}
