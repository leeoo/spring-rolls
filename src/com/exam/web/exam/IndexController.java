package com.exam.web.exam;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.EncodeUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.Exam;
import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.Examinee;
import com.exam.entity.exam.ExamineeSession;
import com.exam.entity.exam.ExamingCache;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.QuestionChoice;
import com.exam.entity.exam.Score;
import com.exam.service.exam.ChoiceScoreEvaluate;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.QuestionChoiceService;
import com.exam.service.exam.QuestionService;
import com.exam.service.exam.ScoreEvaluate;
import com.exam.service.exam.ScoreService;
import com.exam.web.BaseController;

@Security
@ShowLabel("考生首页")
@Controller("/exam")
public class IndexController extends BaseController{
	private final static Log logger = LoggerFactory.getLogger();
	@AutoInject
	private ScoreService scoreService;
	@AutoInject
	private ExamService examService;
	@AutoInject
	private QuestionService questionService;
	@AutoInject
	private QuestionChoiceService questionChoiceService;

	@ShowLabel("登录成功后友好界面")
	@MappingMethod
	public ModelAndView index(ModelAndView mv){
		mv.addValue("loginExaminee", SecurityThread.get().getOtherInfo());
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
	
	@ShowLabel("获取考生成绩")
	@MappingMethod("/score-data")
	public void scoreData(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		page = scoreService.getAllByCurrentExaminee(page);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("开始考试")
	@MappingMethod
	public ModelAndView examing(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return mv.redirect(getRedirect("/exam/index"));
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		if(examSession==null){
			mv.setErrorMessage("该考试已经结束！");
			return mv.redirect(getRedirect("/exam/index"));
		}
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			@SuppressWarnings("unchecked")
			org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
			if(user==null){
				return mv.redirect(getRedirect("/exam/index"));
			}
			Examinee examinee = user.getOtherInfo();
			Score score = scoreService.get(id, examinee.getId());
			if(score!=null){
				mv.setErrorMessage("您已经参加过这次考试，并且已经提交！");
				return mv.redirect(getRedirect("/exam/index"));
			}
			examineeSession = new ExamineeSession();
			examineeSession.setId(examinee.getId());
			examineeSession.setChoice(examSession.isChoice());
			examineeSession.setCode(examinee.getCode());
			examineeSession.setUsername(examinee.getUsername());
			examineeSession.setLongTime(0);
			examineeSession.setLrLayout(true);
			if(examSession.isChoice()){//如果是选择题
				Collection<QuestionChoice> choices = examSession.getQuestionChoices();
				int choiceId = choices.iterator().next().getId();//如果是第一次进入获得第一题
				examineeSession.setTempContent(String.valueOf(choiceId));//获得第一题
				examineeSession.initMenu(choices);//初始化菜单
			}
			examSession.addExaminee(examineeSession);
		}
		int longTime = examSession.getLongTime();//这是分钟
		longTime = longTime * 60;//变成秒
		longTime = longTime - (int)(EncodeUtils.millisTime()/1000-examineeSession.getExamDate());
		long startTime = mv.getValue("dateTime", long.class);
		if(startTime==0){
			startTime = examineeSession.getExamDate();//开始考试时间,这是秒
		}else{
			startTime = startTime/1000;
		}
		long examingEndTime = (startTime + longTime)*1000;//变成毫秒
		String examingEndTimeStr = DateUtils.parse(new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",java.util.Locale.UK), new Date(examingEndTime));//变成计时器能够读懂的str
		mv.addValue("examSession", examSession);
		mv.addValue("examineeSession", examineeSession);
		mv.addValue("examingEndTime", examingEndTimeStr);//考试结束时间
		if(examSession.isChoice()){
			String choiceIdStr = examineeSession.getTempContent();
			int choiceId = 0 ;
			try{
				choiceId = Integer.parseInt(choiceIdStr);
			}catch (Exception e) {//如果转型失败，放入默认第一题
				Collection<QuestionChoice> choices = examSession.getQuestionChoices();
				choiceId = choices.iterator().next().getId();//如果是第一次进入获得第一题
			}
			mv.addValue("choiceMenu", examineeSession.getChoiceMenu(choiceId));//放入当前菜单
			mv.addValue("choice", examSession.getQuestionChoice(choiceId));//放入当前选择题
			mv.addValue("choiceMenus", examineeSession.getChoiceMenus());//放入整个菜单列表
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
			return mv.redirect(getRedirect("/exam/index"));
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		ExamineeSession examineeSession = examSession.getExaminee();
		if(examineeSession==null){
			return mv.redirect(getRedirect("/exam/index"));
		}
		examineeSession.setLrLayout(!examineeSession.isLrLayout());
		examSession.addExaminee(examineeSession);
		return examing(mv);
	}
	
	@ShowLabel("保存成绩")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return examing(mv);
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return examing(mv);
		}
		Integer id = mv.getValue("examSessionId", Integer.class);
		if(id==null){
			mv.setErrorMessage("由于您的考试信息缺失,请您暂停考试!");
			return examing(mv);
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		if(examSession==null){
			mv.setErrorMessage("考试已经结束，请联系管理员!");
			return examing(mv);
		}
		ExamineeSession examineeSession = examSession.getExaminee(examinee.getId());
		if(examineeSession==null){
			mv.setErrorMessage("您已经超过考试时间,系统已经为您保存了考试信息!");
			return examing(mv);
		}
		ScoreEvaluate scoreEvaluate = new ScoreEvaluate(
				examSession.getTextContent(), examSession.getScore(), 
				examSession.getCnProportion(), examSession.getEnProportion(),
				examSession.getPunProportion(), examSession.getNumProportion());
		Score score = new Score();
		score.setExamId(examSession.getId());
		score.setExamineeId(examineeSession.getId());
		int longTime = (int)(EncodeUtils.millisTime()/1000-examineeSession.getExamDate());
		if(examSession.getLongTime()*60<longTime){
			longTime = examSession.getLongTime()*60;
		}
		score.setLongTime(longTime);
		score.setStartDate(new Date(examineeSession.getExamDate()*1000));//进入考试的时间
		try {
			if(examSession.isChoice()){//如果是选择题
				String tempContent = ChoiceScoreEvaluate.buildChoiceContext(examineeSession.getChoices());//将选项原型变为文本
				score.setContext(tempContent);
				ChoiceScoreEvaluate choiceScoreEvaluate = new ChoiceScoreEvaluate(examSession.getQuestionChoices(), examSession.getScore());
				int finalScore = choiceScoreEvaluate.getScore(examineeSession.getChoices());//通过算法获得分数
				score.setScore(finalScore);
			}else{
				String tempContent = mv.getValue("tempContent", String.class);
				score.setContext(tempContent);
				int finalScore = scoreEvaluate.getScore(tempContent);//通过算法获得分数
				score.setScore(finalScore);
			}
			scoreService.save(score);
			examSession.removeExamineeSession(examinee.getId());
			mv.setSuccessMessage("您的考试已经结束，系统正在为您评分，请耐心等待！");
		} catch (Exception e) {
			logger.error(e);
			logger.error("<考试异常暂存日志>考号:"+examinee.getCode()+" 耗时:"+score.getLongTime()+"秒 得分:"+score.getScore()+"内容:"+score.getContext());
			mv.setErrorMessage("您的考试保存时出现错误，如果您等待一段时间让然没有相关成绩，请联系相关人员!");
		}
		return mv.redirect(getRedirect("/exam/index"));
	}
	
	
	@ShowLabel("临时保存")
	@MappingMethod("/temp-save")
	public ModelAndView tempSave(ModelAndView mv){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			return mv.printJSON("由于连接超时或重复登录,您目前已经与友好断开!");
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			return mv.printJSON("由于连接超时或重复登录,您目前已经与友好断开!");
		}
		Integer id = mv.getValue("examSessionId", Integer.class);
		if(id==null){
			return mv.printJSON("由于您的考试信息缺失,请您暂停考试!");
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		if(examSession==null){
			return mv.printJSON("考试已经结束，请联系管理员!");
		}
		ExamineeSession examineeSession = examSession.getExaminee(examinee.getId());
		if(examineeSession==null){
			return mv.printJSON("您已经超过考试时间,系统已经为您保存了考试信息!");
		}
		if(examSession.isChoice()){
			String answer = mv.getValue("answer");
			int status = mv.getValue("status",int.class);
			Integer choiceId = mv.getValue("choiceId", Integer.class);
			if(choiceId!=null){
				examineeSession.addChoices(choiceId,status, answer);
				examineeSession.setTempContent(String.valueOf(choiceId));//当前写到某题记录
			}
		}else{
			String tempContent = mv.getValue("tempContent", String.class);
			examineeSession.setTempContent(tempContent);
		}
		examineeSession.setLongTime(examineeSession.getLongTime()+10);
		examSession.addExaminee(examineeSession);
		return mv.printJSON("");
	}
	
	@ShowLabel("跳转选择题")
	@MappingMethod("/choice")
	public ModelAndView choice(ModelAndView mv){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return examing(mv);
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return examing(mv);
		}
		Integer id = mv.getValue("examSessionId", Integer.class);
		if(id==null){
			mv.setErrorMessage("由于您的考试信息缺失,请您暂停考试!");
			return examing(mv);
		}
		ExamSession examSession = ExamingCache.getExamSession(id);
		if(examSession==null){
			mv.setErrorMessage("考试已经结束，请联系管理员!");
			return examing(mv);
		}
		ExamineeSession examineeSession = examSession.getExaminee(examinee.getId());
		if(examineeSession==null){
			mv.setErrorMessage("您已经超过考试时间,系统已经为您保存了考试信息!");
			return examing(mv);
		}
		Integer choiceId = mv.getValue("choiceId", Integer.class);
		if(choiceId!=null){
			mv.addValue("choiceMenu", examineeSession.getChoiceMenu(choiceId));//放入当前菜单
			mv.addValue("choice", examSession.getQuestionChoice(choiceId));//放入当前选择题
		}
		mv.addValue("choiceMenus", examineeSession.getChoiceMenus());//放入菜单
		return mv.forward(getExamPage("/examing/choice.jsp"));
	}
	
	
	@ShowLabel("查看成绩")
	@MappingMethod
	public ModelAndView score(ModelAndView mv){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return mv.redirect(getRedirect("/exam/index"));
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			mv.setErrorMessage("由于连接超时或重复登录,您目前已经与友好断开!");
			return mv.redirect(getRedirect("/exam/index"));
		}
		Integer examId = mv.getValue("examId", Integer.class);
		if(examId==null){
			mv.setErrorMessage("考试信息不存在！");
			return mv.redirect(getRedirect("/exam/index"));
		}
		Exam exam = examService.get(examId);
		if(exam==null){
			mv.setErrorMessage("考试信息不存在！");
			return mv.redirect(getRedirect("/exam/index"));
		}
		Score score = scoreService.get(examId, examinee.getId());
		if(score==null){
			mv.setErrorMessage("成绩信息不存在！");
			return mv.redirect(getRedirect("/exam/index"));
		}
		mv.addValue("exam", exam);
		mv.addValue("score", score);
		if(exam.getChoice()!=null&&exam.getChoice()){
			Collection<QuestionChoice> questionChoices = questionChoiceService.getAllByQuestionId(exam.getQuestionId());
			mv.addValue("questionChoices", questionChoices);
			mv.addValue("anwserChoices", ChoiceScoreEvaluate.buildChoiceMap(score.getContext()));//构建正确答案
			return mv.forward(getExamPage("/score/detail_c.jsp"));
		}else{
			Question question = questionService.get(exam.getQuestionId());
			mv.addValue("question", question);
			return mv.forward(getExamPage("/score/detail_q.jsp"));
		}
	}
	
}
