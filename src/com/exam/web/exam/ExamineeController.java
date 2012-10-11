package com.exam.web.exam;

import java.util.Collection;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.Exam;
import com.exam.entity.exam.Examinee;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.QuestionChoice;
import com.exam.entity.exam.Score;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.ExamineeService;
import com.exam.service.exam.QuestionChoiceService;
import com.exam.service.exam.QuestionService;
import com.exam.service.exam.ScoreService;
import com.exam.web.BaseController;

@Security
@ShowLabel("考生信息")
@Controller("/exam/examinee")
public class ExamineeController extends BaseController{
	
	@AutoInject
	private ExamineeService examineeService;
	
	@AutoInject
	private ExamService examService;
	
	@AutoInject
	private QuestionChoiceService questionChoiceService;
	
	@AutoInject
	private ScoreService scoreService;
	
	@AutoInject
	private	QuestionService questionService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/examinee/list.jsp"));
	}
	
	@ShowLabel("考生成绩界面")
	@MappingMethod
	public ModelAndView score(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			mv.setErrorMessage("该考生不存在！");
			return mv.redirect(getRedirect("/exam/examinee/list"));
		}
		mv.addValue("id", id);
		return mv.forward(getExamPage("/examinee/score.jsp"));
	}
	
	@ShowLabel("获取成绩数据")
	@MappingMethod("score_data")
	public ModelAndView scoreData(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			return mv.printJSON("");
		}
		page = scoreService.getMapScoreByExamineeId(page, id);
		String json = getJsonPageMapData(page);
		return mv.printJSON(json);
	}
	
	@ShowLabel("获取成绩详情")
	@MappingMethod("score_detail")
	public ModelAndView scoreDetail(ModelAndView mv){
		Integer id = mv.getValue("id", Integer.class);
		if(id==null){
			mv.setErrorMessage("该考生不存在！");
			return mv.redirect(getRedirect("/exam/examinee/list"));
		}
		Score score = scoreService.get(id);
		if(score==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/examinee/list"));
		}
		Exam exam = examService.get(score.getExamId());
		if(exam==null){
			mv.setErrorMessage("该考试不存在！");
			return mv.redirect(getRedirect("/exam/examinee/list"));
		}
		mv.addValue("exam", exam);
		mv.addValue("score", score);
		if(exam.getChoice()!=null&&exam.getChoice()){
			Collection<QuestionChoice> questionChoices = questionChoiceService.getAllByQuestionId(exam.getQuestionId());
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
		Examinee examinee = mv.getBean(Examinee.class);
		page = examineeService.getAll(page,examinee);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			Examinee examinee = examineeService.get(id);
			mv.addValue("examinee", examinee);
		}
		return mv.forward(getExamPage("/examinee/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		Examinee examinee = mv.getBean("examinee",Examinee.class);
		try {
			if(examinee.getId()==null){
				examineeService.save(examinee);
			}else{
				examineeService.update(examinee);
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("examinee", examinee);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/examinee/input.jsp"));
		}
		return mv.redirect(getRedirect("/exam/examinee/list"));
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				examineeService.delete(ids);
			}
		} catch (Exception e) {
		}
	}
	
	@ShowLabel("导入界面")
	@MappingMethod("input_imp")
	public void inputImp(ModelAndView mv){
		mv.forward(getExamPage("/examinee/input_imp.jsp"));
	}
	
	@ShowLabel("批量导入")
	@MappingMethod
	public ModelAndView imp(ModelAndView mv){
		try {
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/examinee/input_imp.jsp"));
		}
		return mv.redirect(getRedirect("/exam/examinee/list"));
	}
	
}
