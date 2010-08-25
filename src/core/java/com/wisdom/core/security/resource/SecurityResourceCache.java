package com.wisdom.core.security.resource;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.wisdom.core.security.domain.Resource;
/**
 * 功能描述：权限及资源缓存
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:58:12</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.resource/SecurityResourceCache.java</b>
 */
public class SecurityResourceCache {
	private final static String SECURITY_CACHE_NAME="security_resource";
	private static Cache cache=CacheManager.getInstance().getCache(SECURITY_CACHE_NAME);
	
	public synchronized static void putCache(Resource resource){
		Element element=new Element(resource.getPath(), resource);
		cache.put(element);
	}  

	public synchronized static Resource getCache(String path){
		Element element = null;
		try {
			element = cache.get(path);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("ResourceCache failure: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return (Resource) element.getValue();
		}
	}  

	public synchronized static void removeCache(String path){
		cache.remove(path);
	}  

	public synchronized static void removeAllCache(){
		cache.removeAll();
		cache.clearStatistics();
		cache.flush();
	}  

	@SuppressWarnings("unchecked")
	public synchronized static Collection<Resource> getAllCache(){
		Collection<String> resources;
		Collection<Resource> resclist = new ArrayList<Resource>();
		try {
			resources = cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
		for (String resPath:resources) {
			Resource rd = getCache(resPath);
			resclist.add(rd);
		}
		return resclist;
	}
	
	public synchronized static GrantedAuthority[] getAuthoritysInCache(String path){
		GrantedAuthority[] gas={new GrantedAuthorityImpl(getCache(path).getName())};
		return gas;
	}
	
}
