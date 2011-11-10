package org.paramecium.security.taglib;

import javax.servlet.jsp.JspException;
/**
 * 功 能 描 述:<br>
 * 登录验证标签
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-9下午04:20:29
 * <br>项 目 信 息:paramecium:org.paramecium.security.taglib.LoginAuthorizeTag.java
 */
public class LoginAuthorizeTag extends BaseSecurityTag{

	private static final long serialVersionUID = 7826057529010322820L;
	private boolean ifNotLogin = false;
	
	public int doStartTag() throws JspException {
		if(!this.ifNotLogin && getUserDetails()!=null){//判断是否登录
			return EVAL_BODY_INCLUDE;
		}else if(this.ifNotLogin && getUserDetails()==null){//判断是否没有登录
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public boolean isIfNotLogin() {
		return ifNotLogin;
	}

	public void setIfNotLogin(boolean ifNotLogin) {
		this.ifNotLogin = ifNotLogin;
	}

}
