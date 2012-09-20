package com.exam.web.exam;

import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.ChoiceTypeQuestion;
import com.exam.entity.exam.Exam;
import com.exam.entity.exam.Question;
import com.exam.service.exam.ChoiceTypeQuestionService;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.QuestionService;
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
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/exam/list.jsp"));
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
		Exam exam = mv.getBean(Exam.class);
		if(exam!=null && exam.getTitle()!=null){
			exam.setTitle("%"+exam.getTitle()+"%");
		}
		Collection<Question> questions = questionService.getAll();
		String json = '['+JsonUitls.getBeansJson(questions,false)+']';
		mv.printJSON(json);
	}
	
	@ShowLabel("获取理论题库数据")
	@MappingMethod
	public void qcdata(ModelAndView mv){
		Exam exam = mv.getBean(Exam.class);
		if(exam!=null && exam.getTitle()!=null){
			exam.setTitle("%"+exam.getTitle()+"%");
		}
		Collection<ChoiceTypeQuestion> choiceQuestions = choiceTypeQuestionService.getAll();
		String json = '['+JsonUitls.getBeansJson(choiceQuestions,false)+']';
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
			if(exam!=null&&exam.getChoice()!=null){
				if(exam.getChoice()){
					Collection<ChoiceTypeQuestion> choiceQuestions = choiceTypeQuestionService.getAll();
					mv.addValue("choiceQuestions", choiceQuestions);
				}else{
					Collection<Question> questions = questionService.getAll();
					mv.addValue("questions", questions);
				}
			}
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
		}
	}

}
