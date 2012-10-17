package com.exam.web.exam;

import java.math.BigDecimal;
import java.util.Collection;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.ExamineeSession;
import com.exam.entity.exam.ExamingCache;
import com.exam.web.BaseController;

@Security
@ShowLabel("在线监考")
@Controller("/exam/invigilate")
public class InvigilateController extends BaseController{

	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/invigilate/list.jsp"));
	}
	
	@ShowLabel("监控报表")
	@MappingMethod
	public ModelAndView report(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return mv.redirect(getRedirect("/exam/invigilate/list"));
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		if(examSession==null){
			mv.setErrorMessage("考试已经结束，不能进行监考!");
			return mv.redirect(getRedirect("/exam/invigilate/list"));
		}
		Collection<ExamineeSession> examineeSessions = examSession.getExamineeSessions();
		if(!examSession.isChoice()){
			if(examineeSessions!=null && !examineeSessions.isEmpty()){
				StringBuffer buffer = new StringBuffer();
				for(ExamineeSession examineeSession : examineeSessions){
					String content = examineeSession.getTempContent();
					int count = 0;
					if(content!=null){
						count = content.length();
					}
					int time = examineeSession.getLongTime();
					if(time!=0){
						float f = (float)count/(float)time;
						BigDecimal decimal = new BigDecimal(f*60f).setScale(0, BigDecimal.ROUND_HALF_UP);
						count = decimal.intValue();
					}
					buffer.append(",['").append(examineeSession.getUsername()).append("',").append(count).append("]");
				}
				if(buffer.length()>0){
					buffer.delete(0, 1);
				}
				if(examineeSessions.size()<=20){
					mv.addValue("width", 800);
				}else{
					mv.addValue("width", 800+30*(examineeSessions.size()-20));
				}
				mv.addValue("data", buffer.toString());
			}else{
				mv.addValue("width", 800);
				mv.addValue("data", "['没有考生参加',0]");
			}
			return mv.forward(getExamPage("/invigilate/report_q.jsp"));
		}else{
			if(examineeSessions!=null && !examineeSessions.isEmpty()){
				StringBuffer buffer = new StringBuffer();
				for(ExamineeSession examineeSession : examineeSessions){
					buffer.append(",['").append(examineeSession.getUsername()).append("',").append(examineeSession.getChoices().size()).append("]");
				}
				if(buffer.length()>0){
					buffer.delete(0, 1);
				}
				if(examineeSessions.size()<=20){
					mv.addValue("width", 800);
				}else{
					mv.addValue("width", 800+30*(examineeSessions.size()-20));
				}
				mv.addValue("data", buffer.toString());
			}else{
				mv.addValue("width", 800);
				mv.addValue("data", "['没有考生参加',0]");
			}
			return mv.forward(getExamPage("/invigilate/report_c.jsp"));
		}
	}
	
}
