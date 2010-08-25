
package com.wisdom.core.security.resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.wisdom.core.security.domain.Resource;

/**
 * <b>业务说明</b>:授权资源动态载入<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-17<br>
 * <b>建立时间</b>: 下午08:38:25<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.security.resourceDynamicInvocationSecurityMetadataSource.java<br>
 */
public class DynamicInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{


	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap(){
		Collection<Resource> reses= SecurityResourceCache.getAllCache();
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> distMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		for (Resource res:reses) {
			distMap.put(new AntPathRequestMatcher(res.getPath()), getConfigAttributes(res.getName()));
		}
		return distMap;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : buildRequestMap().entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : buildRequestMap().entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

	public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

	private Collection<ConfigAttribute> getConfigAttributes(String... value){
		return SecurityConfig.createList(value);
	}

}