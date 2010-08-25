package com.wisdom.example.web.example.logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.logger.LoggerCache;
import com.wisdom.core.logger.LoggerService;
import com.wisdom.core.logger.LoggerThreadService;
import com.wisdom.core.utils.Page;
import com.wisdom.core.utils.ScheduledThreadUtils;

/**
 * <b>业务说明</b>:日志管理控制类<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 下午03:29:33<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.example.web.example.loggerLoggerController.java<br>
 */
@Controller
@RequestMapping("/example/logger")
public class LoggerController {
	
	@Resource
	private LoggerService loggerService;
	
	@Resource
	private LoggerThreadService loggerThreadService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		page=loggerService.getAllLoggers(page);
		request.setAttribute("page", page);
		return "/example/logger/list";
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			loggerService.deleteLogger(id);
		}
		return "redirect:/example/logger/list/"+no+".htm";
	}
	
	@RequestMapping("/load")
	public String load(){
		ScheduledThreadUtils.start(loggerThreadService);
		return "redirect:/example/logger/list/1.htm";
	}

	@RequestMapping("/input")
	public String input(HttpServletRequest request){
		request.setAttribute("loggerLevers",LoggerThreadService.allLoggerLever);
		return "/example/logger/input";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(String[] levers){
		LoggerCache.setLoggerLever(levers);
		return "redirect:/example/logger/list/1.htm";
	}

	public LoggerService getLoggerService() {
		return loggerService;
	}

	public void setLoggerService(LoggerService loggerService) {
		this.loggerService = loggerService;
	}

	public LoggerThreadService getLoggerThreadService() {
		return loggerThreadService;
	}

	public void setLoggerThreadService(LoggerThreadService loggerThreadService) {
		this.loggerThreadService = loggerThreadService;
	}
	
	
	
}
