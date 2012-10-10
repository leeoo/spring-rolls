package com.exam.web.exam;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.ChoiceTypeQuestion;
import com.exam.service.exam.ChoiceTypeQuestionService;
import com.exam.web.BaseController;

@Security
@ShowLabel("理论考试题库")
@Controller("/exam/question_c")
public class ChoiceTypeQuestionController extends BaseController {

	@AutoInject
	private ChoiceTypeQuestionService choiceTypeQuestionService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/question_c/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		ChoiceTypeQuestion question = mv.getBean(ChoiceTypeQuestion.class);
		if(question!=null && question.getTitle()!=null){
			question.setTitle("%"+question.getTitle()+"%");
		}
		page = choiceTypeQuestionService.getAll(page,question);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			ChoiceTypeQuestion question = choiceTypeQuestionService.get(id);
			mv.addValue("question", question);
		}
		return mv.forward(getExamPage("/question_c/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		ChoiceTypeQuestion question = mv.getBean("question",ChoiceTypeQuestion.class);
		try {
			question.setChoice(true);
			question.setTextContent("该题库为理论考试,不能作为速录考试使用！");
			if(question.getId()==null){
				choiceTypeQuestionService.save(question);
			}else{
				choiceTypeQuestionService.update(question);
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("question", question);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/question_c/input.jsp"));
		}
		return mv.redirect(getRedirect("/exam/question_c/list"));
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				choiceTypeQuestionService.delete(ids);
			}
		} catch (Exception e) {
			mv.printJSON(e.getMessage());
		}
	}
	
}