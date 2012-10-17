package com.exam.web.exam;

import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.Exam;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.QuestionChoice;
import com.exam.entity.exam.QuestionChoiceExaminee;
import com.exam.entity.exam.Score;
import com.exam.service.exam.ChoiceScoreEvaluate;
import com.exam.service.exam.ChoiceTypeQuestionService;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.QuestionChoiceService;
import com.exam.service.exam.QuestionService;
import com.exam.service.exam.ScoreService;
import com.exam.web.BaseController;

@Security
@ShowLabel("考试管理")
@Controller("/exam/exam")
public class ExamController extends BaseController{
	
	@AutoInject
	private ExamService examService;
	
	@AutoInject
	private	QuestionService questionService;
	
	@AutoInject
	private	ChoiceTypeQuestionService choiceTypeQuestionService;
	
	@AutoInject
	private QuestionChoiceService questionChoiceService;
	
	@AutoInject
	private ScoreService scoreService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/exam/list.jsp"));
	}
	
	@ShowLabel("考生成绩界面")
	@MappingMethod
	public ModelAndView score(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/exam/list"));
		}
		mv.addValue("id", id);
		return mv.forward(getExamPage("/exam/score.jsp"));
	}
	
	@ShowLabel("获取成绩数据")
	@MappingMethod("score_data")
	public ModelAndView scoreData(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		Integer id = mv.getValue("id", Integer.class);
		if(id!=null){
			page = scoreService.getMapScoreByExamId(page,id);
		}
		String json = getJsonPageMapData(page);
		return mv.printJSON(json);
	}
	
	@ShowLabel("获取成绩详情")
	@MappingMethod("score_detail")
	public ModelAndView scoreDetail(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/exam/list"));
		}
		Score score = scoreService.get(id);
		if(score==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/exam/list"));
		}
		Exam exam = examService.get(score.getExamId());
		if(exam==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/exam/list"));
		}
		mv.addValue("exam", exam);
		mv.addValue("score", score);
		if(exam.getChoice()!=null&&exam.getChoice()){
			Collection<QuestionChoice> choices = questionChoiceService.getAllByQuestionId(exam.getQuestionId());
			Collection<QuestionChoiceExaminee> questionChoices = ChoiceScoreEvaluate.getQuestionChoiceExaminee(choices, score.getContext());
			mv.addValue("questionChoices", questionChoices);
			return mv.forward(getExamPage("/score/detail_c.jsp"));
		}else{
			Question question = questionService.get(exam.getQuestionId());
			mv.addValue("question", question);
			return mv.forward(getExamPage("/score/detail_q.jsp"));
		}
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		Exam exam = mv.getBean(Exam.class);
		if(exam!=null && exam.getTitle()!=null){
			exam.setTitle("%"+exam.getTitle()+"%");
		}
		page = examService.getAll(page,exam);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("获取速录题库数据")
	@MappingMethod
	public void qdata(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(5);
		page = questionService.getAll(page);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("获取理论题库数据")
	@MappingMethod
	public void qcdata(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(5);
		page = choiceTypeQuestionService.getAll(page);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		Boolean choice = mv.getValue("choice",Boolean.class);
		if(id!=null){
			Exam exam = examService.get(id);
			mv.addValue("exam", exam);
			if(!exam.getChoice()){
				return mv.forward(getExamPage("/exam/input.jsp"));
			}
			return mv.forward(getExamPage("/exam/input_c.jsp"));
		}
		if(choice!=null&&choice){
			//待开发-----------------------------------
			return mv.forward(getExamPage("/exam/input_c.jsp"));
		}
		return mv.forward(getExamPage("/exam/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		Exam exam = mv.getBean("exam",Exam.class);
		try {
			Date date = DateUtils.getCurrentDateTime();
			if(date.after(exam.getStartDate()) && date.before(exam.getEndDate())){
				exam.setStatus(1);
			}else if(date.after(exam.getEndDate())){
				exam.setStatus(-1);
			}else{
				exam.setStatus(0);
			}
			if(exam.getId()==null){
				examService.save(exam);
			}else{
				examService.update(exam);
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("exam", exam);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/exam/input.jsp"));
		}
		return mv.redirect(getRedirect("/exam/exam/list"));
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				examService.delete(ids);
			}
		} catch (Exception e) {
			mv.printJSON(e.getMessage());
		}
	}

}
