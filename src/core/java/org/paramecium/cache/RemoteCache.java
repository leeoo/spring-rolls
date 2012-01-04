package org.paramecium.cache;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * 功 能 描 述:<br>
 * 远程缓存接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:02
 * <br>项 目 信 息:paramecium:org.paramecium.cache.RemoteCache.java
 */
public interface RemoteCache extends Remote ,Serializable{

	public void clear() throws RemoteException;
	
	public int size() throws RemoteException;
	
	public void put(Object key,Object value) throws RemoteException;
	
	public Object get(Object key) throws RemoteException;
	
	public void remove(Object key) throws RemoteException;
	
	public Collection<Object> getKeys() throws RemoteException;
	
	public Collection<Object> getValues() throws RemoteException;
	
	public boolean isEmpty() throws RemoteException;
	
	public Object peek() throws RemoteException;
	
}
