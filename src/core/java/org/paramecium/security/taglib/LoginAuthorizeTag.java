package org.paramecium.security.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.security.UserDetails;
/**
 * 功 能 描 述:<br>
 * 
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-9下午04:20:29
 * <br>项 目 信 息:paramecium:org.paramecium.security.taglib.LoginAuthorizeTag.java
 */
public class LoginAuthorizeTag extends TagSupport{

	private static final long serialVersionUID = 7826057529010322820L;
	private boolean isNotLogin = false;
	
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		UserDetails<?> ud = SecurityUitls.getLoginUser(request.getSession().getId());
		if(!isNotLogin && ud!=null){//判断是否登录
			return EVAL_BODY_INCLUDE;
		}else if(isNotLogin && ud==null){//判断是否没有登录
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public boolean isNotLogin() {
		return isNotLogin;
	}

	public void setIsNotLogin(boolean isNotLogin) {
		this.isNotLogin = isNotLogin;
	}

}
