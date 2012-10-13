package com.exam.web.exam;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.EncodeUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;
import org.paramecium.validation.Validator;

import com.exam.entity.exam.ChoiceTypeQuestion;
import com.exam.entity.exam.QuestionChoice;
import com.exam.service.exam.ChoiceTypeQuestionService;
import com.exam.service.exam.QuestionChoiceService;
import com.exam.web.BaseController;

@Security
@ShowLabel("理论考试题库")
@Controller("/exam/question_c")
public class ChoiceTypeQuestionController extends BaseController {

	@AutoInject
	private ChoiceTypeQuestionService choiceTypeQuestionService;
	@AutoInject
	private QuestionChoiceService questionChoiceService;
	
	@SuppressWarnings("unchecked")
	private Cache<Integer, QuestionChoice> cache = (Cache<Integer, QuestionChoice>) CacheManager.getDefaultCache("QUESTION_CHOICE", 100);
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		cache.clear();
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
		cache.clear();
		if(id!=null){
			ChoiceTypeQuestion question = choiceTypeQuestionService.get(id);
			Collection<QuestionChoice> choices = questionChoiceService.getAllByQuestionId(id);
			if(choices!=null&&!choices.isEmpty()){
				for(QuestionChoice choice : choices){
					cache.put(choice.getId(), choice);
				}
			}
			mv.addValue("question", question);
			mv.addValue("choices", choices);
		}
		return mv.forward(getExamPage("/question_c/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		ChoiceTypeQuestion question = mv.getBean("question",ChoiceTypeQuestion.class);
		try {
			if(cache.isEmpty()){
				mv.addValue("question", question);
				mv.setErrorMessage("没有添加选择题，不能保存完整的题库。请添加选择题！");
				return mv.forward(getExamPage("/question_c/input.jsp"));
			}
			question.setChoice(true);
			question.setTextContent("该题库为理论考试,不能作为速录考试使用！");
			if(question.getId()==null){
				choiceTypeQuestionService.save(question,cache.getValues());
			}else{
				choiceTypeQuestionService.update(question,cache.getValues());
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("question", question);
			mv.addValue("choices", cache.getValues());
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/question_c/input.jsp"));
		}
		return mv.redirect(getRedirect("/exam/question_c/list"));
	}
	
	@ShowLabel("选项保存")
	@MappingMethod("/choice_save")
	public ModelAndView saveChoice(ModelAndView mv){
		try{
			QuestionChoice choice = mv.getBean("choice",QuestionChoice.class);
			if(choice!=null){
				Validator.validation(choice);
				choice.setId((int)EncodeUtils.millisTime());
				cache.put(choice.getId(),choice);
			}
		}catch (Exception e) {
			mv.setErrorMessage(e.getMessage());
		}
		ChoiceTypeQuestion question = mv.getBean("question",ChoiceTypeQuestion.class);
		mv.addValue("question", question);
		mv.addValue("choices", cache.getValues());
		return mv.forward(getExamPage("/question_c/input.jsp"));
	}
	
	@ShowLabel("选项删除")
	@MappingMethod("/choice_delete")
	public void deleteChoice(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		try {
			if(id!=null){
				cache.remove(id);
			}
		} catch (Exception e) {
			mv.printJSON(e.getMessage());
		}
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