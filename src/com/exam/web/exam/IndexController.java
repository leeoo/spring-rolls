package com.exam.web.exam;

import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.Examinee;
import com.exam.entity.exam.ExamineeSession;
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
	
	@ShowLabel("开始考试")
	@MappingMethod
	public ModelAndView examing(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return index(mv);
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		mv.addValue("examSession", examSession);
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			examineeSession = new ExamineeSession();
			examineeSession.setChoice(examSession.isChoice());
			@SuppressWarnings("unchecked")
			org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
			if(user==null){
				return index(mv);
			}
			Examinee examinee = user.getOtherInfo();
			examineeSession.setCode(examinee.getCode());
			examineeSession.setUsername(examinee.getUsername());
			examineeSession.setId(examinee.getId());
			examineeSession.setLongTime(0);
			examineeSession.setLrLayout(true);
			examSession.addExaminee(examineeSession);
		}
		mv.addValue("examineeSession", examineeSession);
		int longTime = examSession.getLongTime();//这是分钟
		longTime = longTime * 60;//变成秒
		long startTime = examineeSession.getExamDate();//开始考试时间,这是秒
		long examingEndTime = (startTime + longTime)*1000;//变成毫秒
		String examingEndTimeStr = DateUtils.parse(DateUtils.DATE_TIME_FORMAT, new Date(examingEndTime));//变成计时器能够读懂的str
		mv.addValue("examingEndTime", examingEndTimeStr);//考试结束时间
		if(examSession.isChoice()){
			return mv.forward(getExamPage("/examing/choice.jsp"));
		}else{
			if(examSession.getAudio()!=null&&examSession.getAudio()){
				return mv.forward(getExamPage("/examing/listen.jsp"));
			}else if(examineeSession.isLrLayout()){
				return mv.forward(getExamPage("/examing/look-l.jsp"));
			}else{
				return mv.forward(getExamPage("/examing/look-v.jsp"));
			}
		}
	}
	
	@ShowLabel("改变布局")
	@MappingMethod("/change-layout")
	public ModelAndView changeLayout(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return index(mv);
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		mv.addValue("examSession", examSession);
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			return index(mv);
		}
		examineeSession.setLrLayout(!examineeSession.isLrLayout());
		examSession.addExaminee(examineeSession);
		mv.addValue("examineeSession", examineeSession);
		int longTime = examSession.getLongTime();//这是分钟
		longTime = longTime * 60;//变成秒
		long startTime = examineeSession.getExamDate();//开始考试时间,这是秒
		long examingEndTime = (startTime + longTime)*1000;//变成毫秒
		String examingEndTimeStr = DateUtils.parse(DateUtils.DATE_TIME_FORMAT, new Date(examingEndTime));//变成计时器能够读懂的str
		mv.addValue("examingEndTime", examingEndTimeStr);//考试结束时间
		if(examSession.isChoice()){
			return mv.forward(getExamPage("/examing/choice.jsp"));
		}else{
			if(examSession.getAudio()!=null&&examSession.getAudio()){
				return mv.forward(getExamPage("/examing/listen.jsp"));
			}else if(examineeSession.isLrLayout()){
				return mv.forward(getExamPage("/examing/look-l.jsp"));
			}else{
				return mv.forward(getExamPage("/examing/look-v.jsp"));
			}
		}
	}
	
}
