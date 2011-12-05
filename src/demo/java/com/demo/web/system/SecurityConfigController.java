package com.demo.web.system;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.OnlineUserCache;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

/**
 * 功 能 描 述:<br>
 * 安全信息配置
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-5上午11:33:24
 * <br>项 目 信 息:paramecium:com.demo.web.system.SecurityConfigController.java
 */
@Security
@ShowLabel("安全信息配置")
@Controller("/system/config/security")
public class SecurityConfigController extends BaseController{
	
	private final static Log logger = LoggerFactory.getLogger();

	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("iocSecurity", SecurityConfig.iocSecurity);
		mv.addValue("sessionControl", SecurityConfig.sessionControl);
		mv.addValue("loginExceptionPage", SecurityConfig.loginExceptionPage);
		mv.addValue("anonymousExceptionPage", SecurityConfig.anonymousExceptionPage);
		mv.addValue("authorizationExceptionPage", SecurityConfig.authorizationExceptionPage);
		mv.addValue("userKickExceptionPage", SecurityConfig.userKickExceptionPage);
		mv.addValue("userDisabledExceptionPage", SecurityConfig.userDisabledExceptionPage);
		mv.addValue("sessionExpiredExceptionPage", SecurityConfig.sessionExpiredExceptionPage);
		mv.addValue("ipAddressExceptionPage", SecurityConfig.ipAddressExceptionPage);
		mv.addValue("userKillExceptionPage", SecurityConfig.userKillExceptionPage);
		return mv.forward(getPage("/security/list.jsp"));
	}
	
	/**
	 * 需要持久化需另行开发
	 * @param mv
	 * @return
	 */
	@ShowLabel("保存配置")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		try{
			SecurityConfig.iocSecurity = mv.getValue("iocSecurity", boolean.class);
			SecurityConfig.sessionControl = mv.getValue("sessionControl", boolean.class);
			SecurityConfig.loginExceptionPage = mv.getValue("loginExceptionPage", String.class);
			SecurityConfig.anonymousExceptionPage = mv.getValue("anonymousExceptionPage", String.class);
			SecurityConfig.authorizationExceptionPage = mv.getValue("authorizationExceptionPage", String.class);
			SecurityConfig.userKickExceptionPage = mv.getValue("userKickExceptionPage", String.class);
			SecurityConfig.userDisabledExceptionPage = mv.getValue("userDisabledExceptionPage", String.class);
			SecurityConfig.sessionExpiredExceptionPage = mv.getValue("sessionExpiredExceptionPage", String.class);
			SecurityConfig.ipAddressExceptionPage = mv.getValue("ipAddressExceptionPage", String.class);
			SecurityConfig.userKillExceptionPage = mv.getValue("userKillExceptionPage", String.class);
			OnlineUserCache.killAll();
			mv.setSuccessMessage("保存成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("保存失败!");
		}
		return mv.redirect(getRedirect("/system/config/security/list"));
	}
	
}
