package com.exam.web.exam;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.AutoInject;
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
import com.exam.entity.exam.Score;
import com.exam.service.exam.ScoreService;
import com.exam.web.BaseController;

@Security
@ShowLabel("考生首页")
@Controller("/exam")
public class IndexController extends BaseController{
	
	@AutoInject
	private ScoreService scoreService;

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
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			@SuppressWarnings("unchecked")
			org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
			if(user==null){
				return index(mv);
			}
			Examinee examinee = user.getOtherInfo();
			Score score = scoreService.get(id, examinee.getId());
			if(score!=null){
				mv.setErrorMessage("您已经参加过这次考试，并且已经提交！");
				return index(mv);
			}
			examineeSession = new ExamineeSession();
			examineeSession.setId(examinee.getId());
			examineeSession.setChoice(examSession.isChoice());
			examineeSession.setCode(examinee.getCode());
			examineeSession.setUsername(examinee.getUsername());
			examineeSession.setLongTime(0);
			examineeSession.setLrLayout(true);
			examSession.addExaminee(examineeSession);
		}
		int longTime = examSession.getLongTime();//这是分钟
		longTime = longTime * 60;//变成秒
		long startTime = examineeSession.getExamDate();//开始考试时间,这是秒
		long examingEndTime = (startTime + longTime)*1000;//变成毫秒
		String examingEndTimeStr = DateUtils.parse(new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",java.util.Locale.UK), new Date(examingEndTime));//变成计时器能够读懂的str
		mv.addValue("examSession", examSession);
		mv.addValue("examineeSession", examineeSession);
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
	
	@ShowLabel("临时保存")
	@MappingMethod("/temp-save")
	public ModelAndView tempSave(ModelAndView mv){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			return mv.printJSON("{\"message\":\"由于连接超时或重复登录,您目前已经与友好断开!\"}");
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			return mv.printJSON("{\"message\":\"由于连接超时或重复登录,您目前已经与友好断开!\"}");
		}
		Integer id = mv.getValue("examSessionId", Integer.class);
		if(id==null){
			return mv.printJSON("{\"message\":\"由于您的考试信息缺失,请您暂停考试!\"}");
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		ExamineeSession examineeSession = examSession.getExaminee(examinee.getId());
		if(examineeSession==null){
			return mv.printJSON("{\"message\":\"您已经超过考试时间,系统已经为您保存了考试信息!\"}");
		}
		String tempContent = mv.getValue("tempContent", String.class);
		examineeSession.setTempContent(tempContent);
		examineeSession.setLongTime(examineeSession.getLongTime()+10);
		examSession.addExaminee(examineeSession);
		return mv.printJSON("");
	}
	
	@ShowLabel("改变布局")
	@MappingMethod("/change-layout")
	public ModelAndView changeLayout(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return index(mv);
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			return index(mv);
		}
		examineeSession.setLrLayout(!examineeSession.isLrLayout());
		examSession.addExaminee(examineeSession);
		int longTime = examSession.getLongTime();//这是分钟
		longTime = longTime * 60;//变成秒
		long startTime = examineeSession.getExamDate();//开始考试时间,这是秒
		long examingEndTime = (startTime + longTime)*1000;//变成毫秒
		String examingEndTimeStr = DateUtils.parse(new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",java.util.Locale.UK), new Date(examingEndTime));//变成计时器能够读懂的str
		mv.addValue("examSession", examSession);
		mv.addValue("examineeSession", examineeSession);
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
