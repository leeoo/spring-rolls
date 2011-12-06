package com.demo.web.system;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.IpAddressVoter;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

/**
 * 功 能 描 述:<br>
 * IP地址配置
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-5上午11:33:47
 * <br>项 目 信 息:paramecium:com.demo.web.system.IpAddressConfigController.java
 */
@Security
@ShowLabel("IP地址配置")
@Controller("/system/config/ip")
public class IpAddressConfigController extends BaseController{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("ips", IpAddressVoter.getIpAddressList());
		mv.addValue("enabled", IpAddressVoter.isEnabled());
		mv.addValue("include", IpAddressVoter.isInclude());
		return mv.forward(getPage("/ip/list.jsp"));
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
			IpAddressVoter.setInclude(mv.getValue("include", boolean.class));
			IpAddressVoter.setEnabled(mv.getValue("enabled", boolean.class));
			mv.setSuccessMessage("保存成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("保存失败!");
		}
		return mv.redirect(getRedirect("/system/config/ip/list"));
	}

	/**
	 * 需要持久化需另行开发
	 * @param mv
	 * @return
	 */
	@ShowLabel("添加IP地址")
	@MappingMethod
	public ModelAndView add(ModelAndView mv){
		try{
			String ip = mv.getValue("ip", String.class);
			IpAddressVoter.put(ip);
			mv.setSuccessMessage("添加成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("添加失败!");
		}
		return mv.redirect(getRedirect("/system/config/ip/list"));
	}
	
	/**
	 * 需要持久化需另行开发
	 * @param mv
	 * @return
	 */
	@ShowLabel("移除IP地址")
	@MappingMethod
	public ModelAndView remove(ModelAndView mv){
		try{
			String ip = mv.getValue("ip", String.class);
			IpAddressVoter.remove(ip);
			mv.setSuccessMessage("移除成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("移除失败!");
		}
		return mv.redirect(getRedirect("/system/config/ip/list"));
	}

}
