package com.wisdom.core.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.wisdom.core.security.OnlineUserCache;
import com.wisdom.core.security.OnlineUserEventPublisher;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.domain.UserDetailsImpl;
import com.wisdom.core.security.resource.SecurityUtils;

/**
 * 功 能 描 述:<br>
 * 安全过滤器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-1-7下午04:54:58
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.security.filter.FilterSecurityInterceptor.java
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{
	
	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private boolean observeOncePerRequest = true;
	private boolean onlineUser = false;
	
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		onLoginAddOnLineCache(request);
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	/**
	 * 在登录时放入在线用户缓存
	 * @param servletRequest
	 */
	private void onLoginAddOnLineCache(ServletRequest servletRequest){
		if(isOnlineUser()){
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			User user = SecurityUtils.getCurrentUser();
			String username = (String) request.getSession().getAttribute(OnlineUserEventPublisher.LOGIN_USERNAME);
			if(user!=null){
				if(username == null){
					request.getSession().setAttribute(OnlineUserEventPublisher.LOGIN_USERNAME, user.getUsername());
				}
				if(user.getOperatorIp()==null){
					user.setOperatorIp(SecurityUtils.getCurrentUserIp());
					UserDetails userDetails = new UserDetailsImpl(user);
					SecurityUtils.saveUserDetailsToContext(userDetails, request);
					OnlineUserCache.put(user);
				}
			}
		}
	}
	
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
	    return this.securityMetadataSource;
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }

    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }
	
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null) && observeOncePerRequest) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }
            InterceptorStatusToken token = super.beforeInvocation(fi);
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            super.afterInvocation(token, null);
        }
    }
    
    public boolean isObserveOncePerRequest() {
        return observeOncePerRequest;
    }

    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
        this.observeOncePerRequest = observeOncePerRequest;
    }

	public boolean isOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(boolean onlineUser) {
		this.onlineUser = onlineUser;
	}
    
}
