package com.wisdom.example.web.example.remoting;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.example.service.remoting.TestRemotingService;

/**
 * 功能描述(Description):<br><b>
 * 远程调用
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-8-27下午09:56:12</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0</b>
 * <br>包及类名(Package Class): <b>com.wisdom.example.web.example.remoting.RemotingController.java</b>
 */
@Controller
@RequestMapping("/example/remoting")
public class RemotingController {
	
	@Resource
	private TestRemotingService testRemotingService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request){
		request.setAttribute("list",testRemotingService.getAllTestData());
		return "/example/remoting/list";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(HttpServletRequest request) throws Exception{
		String remotingValue = request.getParameter("remotingValue");
		testRemotingService.createTestData(remotingValue);
		return "redirect:/example/remoting/list.htm";
	}
	
	@RequestMapping("/delete/{value}")
	public String delete(@PathVariable String value) throws Exception{
		testRemotingService.removeTestData(value);
		return "redirect:/example/remoting/list.htm";
	}
	
}
