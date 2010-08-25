package com.wisdom.core.security.resource;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.wisdom.core.security.domain.Resource;
/**
 * <b>业务说明</b>:授权资源静态载入(启动服务时一次性加载)<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-17<br>
 * <b>建立时间</b>: 下午08:40:40<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.security.resourceStaticInvocationSecurityMetadataSource.java<br>
 */
@SuppressWarnings("unchecked")
public class StaticInvocationSecurityMetadataSource implements FactoryBean {

	/**
	 * 手动构造DefaultFilterInvocationSecurityMetadataSource，避免破坏源代码.
	 */
	public Object getObject() throws Exception {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = buildRequestMap();
		DefaultFilterInvocationSecurityMetadataSource definitionSource = new DefaultFilterInvocationSecurityMetadataSource(requestMap);
		return definitionSource;
	}

	public Class getObjectType() {
		return DefaultFilterInvocationSecurityMetadataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	protected LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() throws Exception {
		Collection<Resource> reses= SecurityResourceCache.getAllCache();
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> distMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		for (Resource res:reses) {
			distMap.put(new AntPathRequestMatcher(res.getPath()), getConfigAttributes(res.getName()));
		}
		return distMap;
	}
	
	private Collection<ConfigAttribute> getConfigAttributes(String... value){
		//SecurityConfig.createListFromCommaDelimitedString(value[0]);
		return SecurityConfig.createList(value);
	}
	
}
