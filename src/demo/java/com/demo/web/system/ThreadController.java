package com.demo.web.system;

import org.paramecium.commons.ThreadUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("线程监控")
@Controller("/system/thread")
public class ThreadController extends BaseController{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("startedThreads", ThreadUtils.getStartedThreads());
		mv.addValue("shutdownThreads", ThreadUtils.getShutdownThreads());
		return mv.forward(getPage("/thread/list.jsp"));
	}
	
	@ShowLabel("重新启动")
	@MappingMethod
	public ModelAndView restart(ModelAndView mv){
		try{
			int hashcode = mv.getValue("id", int.class);
			ThreadUtils.restart(hashcode);
			mv.setSuccessMessage("操作成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("操作失败!");
		}
		return mv.redirect(getRedirect("/system/thread/list"));
	}
	
	@ShowLabel("中断线程")
	@MappingMethod
	public ModelAndView shutdown(ModelAndView mv){
		try{
			int hashcode = mv.getValue("id", int.class);
			ThreadUtils.shutdown(hashcode);
			mv.setSuccessMessage("操作成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("操作失败!");
		}
		return mv.redirect(getRedirect("/system/thread/list"));
	}
	
	@ShowLabel("中断所有线程")
	@MappingMethod("/shutdown-all")
	public ModelAndView shutdownAll(ModelAndView mv){
		try{
			ThreadUtils.shutdownAll();
			mv.setSuccessMessage("操作成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("操作失败!");
		}
		return mv.redirect(getRedirect("/system/thread/list"));
	}
	
}
