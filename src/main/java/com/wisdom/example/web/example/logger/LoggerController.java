package com.wisdom.example.web.example.logger;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wisdom.core.logger.JdbcThreadService;
import com.wisdom.core.logger.LoggerMatch;
import com.wisdom.core.logger.LoggerThreadService;
import com.wisdom.core.logger.domain.LoggerSomething;
import com.wisdom.core.logger.domain.LoggerSomewhere;
import com.wisdom.core.utils.Page;
import com.wisdom.core.utils.ScheduledThreadUtils;
import com.wisdom.example.commons.ValidationUtils;
import com.wisdom.example.service.logger.LoggerServiceImpl;
import com.wisdom.example.service.logger.MatchServiceImpl;

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
	private LoggerServiceImpl loggerService;
	
	@Resource
	private LoggerThreadService loggerThreadService;

	@Resource
	private JdbcThreadService jdbcThreadService;

	@Resource
	private MatchServiceImpl matchService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		try{
			page=loggerService.getAllLoggers(page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("page", page);
		return "/example/logger/list";
	}

	@RequestMapping("/sqllist/{no}")
	public String sqllist(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		try{
			page=loggerService.getAllSqlLoggers(page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("page", page);
		return "/example/logger/sqllist";
	}
	
	@RequestMapping("/sqldelete/{no}-{id}")
	public String sqldelete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			loggerService.deleteSqlLogger(id);
		}
		return "redirect:/example/logger/sqllist/"+no+".htm";
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
		ScheduledThreadUtils.start(jdbcThreadService);
		return "redirect:/example/logger/list/1.htm";
	}

	@RequestMapping("/somewheres")
	public String somewheres(HttpServletRequest request){
		request.setAttribute("somewheres",matchService.getAllLoggerSomewhere());
		return "/example/logger/somewheres";
	}

	@RequestMapping("/somethings")
	public String somethings(HttpServletRequest request){
		request.setAttribute("somethings",matchService.getAllLoggerSomething());
		return "/example/logger/somethings";
	}
	
	@RequestMapping(value="/inputSomewhere")
	public ModelAndView inputSomewhere(ModelAndView mav,HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		if(keyword!=null){
			LoggerSomewhere somewhere = matchService.getLoggerSomewhere(keyword);
			mav.addObject("somewhere", somewhere);
		}
		mav.setViewName("/example/logger/input_somewhere");
		return mav;
	}

	@RequestMapping(value="/saveSomewhere",method=RequestMethod.POST)
	public ModelAndView saveSomewhere(@ModelAttribute(value="somewhere")LoggerSomewhere somewhere,ModelAndView mav){
		List<String> errors=ValidationUtils.validator(somewhere);
		if(errors!=null){
			mav.addObject("errors",errors);
			mav.setViewName("/errors/error");
			return mav;
		}
		try{
			if(LoggerMatch.get(somewhere.getKeyword())!=null){
				matchService.updateLoggerSomewhere(somewhere);
			}else{
				matchService.saveLoggerSomewhere(somewhere);
			}
		}catch (Exception e) {
		}
		mav.setViewName("redirect:/example/logger/somewheres.htm");
		return mav;
	}
	
	@RequestMapping(value="/deleteSomewhere}")
	public ModelAndView deleteSomewhere(ModelAndView mav,HttpServletRequest request){
		try{
			String keyword = request.getParameter("keyword");
			matchService.deleteLoggerSomewhere(keyword);
		}catch (Exception e) {
		}
		mav.setViewName("redirect:/example/logger/somewheres.htm");
		return mav;
	}
	
	@RequestMapping(value="/inputSomething")
	public ModelAndView inputSomething(ModelAndView mav,HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		if(keyword!=null){
			LoggerSomething something = matchService.getLoggerSomething(keyword);
			mav.addObject("something", something);
		}
		mav.setViewName("/example/logger/input_something");
		return mav;
	}

	@RequestMapping(value="/saveSomething",method=RequestMethod.POST)
	public ModelAndView saveSomething(@ModelAttribute(value="something")LoggerSomething something,ModelAndView mav){
		List<String> errors=ValidationUtils.validator(something);
		if(errors!=null){
			mav.addObject("errors",errors);
			mav.setViewName("/errors/error");
			return mav;
		}
		try{
			if(LoggerMatch.get(something.getKeyword())!=null){
				matchService.updateLoggerSomething(something);
			}else{
				matchService.saveLoggerSomething(something);
			}
		}catch (Exception e) {
		}
		mav.setViewName("redirect:/example/logger/somethings.htm");
		return mav;
	}


	@RequestMapping(value="/deleteSomething")
	public ModelAndView deleteSomething(ModelAndView mav,HttpServletRequest request){
		try{
			String keyword = request.getParameter("keyword");
			matchService.deleteLoggerSomething(keyword);
		}catch (Exception e) {
		}
		mav.setViewName("redirect:/example/logger/somethings.htm");
		return mav;
	}

}
