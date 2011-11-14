package org.paramecium.security.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.security.UserDetails;
/**
 * 功能描述(Description):<br><b>
 * 安全基础标签
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-11-9下午08:58:46</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.taglib.BaseSecurityTag.java</b>
 */
public abstract class BaseSecurityTag extends TagSupport{

	private static final long serialVersionUID = -2577561634290619391L;
	
	/**
	 * 由于SecurityUitls.getLoginUser没有经过MVC跳转后的JSP上使用，系统不会再将用户信息放入线程中.
	 * 所以JSP直接使用必须通过session获取用户信息;
	 * 将线程信息放入filter中，防止JSP等非Controller区域无法获取登录信息
	 * @return
	 */
	public UserDetails<?> getUserDetails(){
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		return SecurityUitls.getLoginUser(request.getSession().getId());
	}

}
