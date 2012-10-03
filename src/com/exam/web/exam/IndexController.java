package com.exam.web.exam;

import java.util.Collection;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.ExamingCache;
import com.exam.web.BaseController;

@Security
@ShowLabel("考生首页")
@Controller("/exam")
public class IndexController extends BaseController{

	@ShowLabel("登录成功后友好界面")
	@MappingMethod
	public ModelAndView index(ModelAndView mv){
		mv.addValue("loginName", SecurityThread.get().getName());
		return mv.forward(getExamPage("/index.jsp"));
	}
	
	@ShowLabel("获取考试缓存信息")
	@MappingMethod("/examing-data")
	public void examingData(ModelAndView mv){
		Page page = new Page();
		page.setPageNo(1);
		Collection<ExamSession> examSessions = ExamingCache.getExamSessions();
		int count = examSessions.size();
		page.setPageSize(count);
		page.setTotalCount(count);
		String json = JsonUitls.getBeansJson(examSessions,false,DateUtils.DATE_TIME_FORMAT);
		json = ("{\"total\":\""+count+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
}
