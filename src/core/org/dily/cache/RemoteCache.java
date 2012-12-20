package org.dily.cache;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * 功 能 描 述:<br>
 * 远程缓存接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:30:02
 * <br>项 目 信 息:dily:org.dily.cache.RemoteCache.java
 */
public interface RemoteCache extends Remote ,Serializable{

	/**
	 * 清理缓存
	 * @throws RemoteException
	 */
	public void clear() throws RemoteException;
	
	/**
	 * 获得当前缓存大小
	 * @return
	 * @throws RemoteException
	 */
	public int size() throws RemoteException;
	
	/**
	 * 放入缓存
	 * @param key
	 * @param value
	 * @throws RemoteException
	 */
	public void put(Object key,Object value) throws RemoteException;
	
	/**
	 * 根据key获得value值
	 * @param key
	 * @return
	 * @throws RemoteException
	 */
	public Object get(Object key) throws RemoteException;
	
	/**
	 * 移除一个缓存内容
	 * @param key
	 * @throws RemoteException
	 */
	public void remove(Object key) throws RemoteException;
	
	/**
	 * 获得所有key列表
	 * @return
	 * @throws RemoteException
	 */
	public Collection<Object> getKeys() throws RemoteException;
	
	/**
	 * 获得所有value列表
	 * @return
	 * @throws RemoteException
	 */
	public Collection<Object> getValues() throws RemoteException;
	
	/**
	 * 获得所有Element列表
	 * @return
	 * @throws RemoteException
	 */
	public Collection<Element> getElements() throws RemoteException;
	
	/**
	 * 判断是否为空
	 * @return
	 * @throws RemoteException
	 */
	public boolean isEmpty() throws RemoteException;
	
	/**
	 * 获得链表顶部内容
	 * @return
	 * @throws RemoteException
	 */
	public Object peek() throws RemoteException;

	/**
	 * 获得缓存生命周期,null则为永久有效,单位为秒
	 * @return
	 * @throws RemoteException
	 */
	public Long life() throws RemoteException;
	
	/**
	 * 获得缓存额定容量
	 * @return
	 * @throws RemoteException
	 */
	public int rated() throws RemoteException;
	
}
