package com.exam.web.exam;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.Examinee;
import com.exam.service.exam.ExamineeService;
import com.exam.web.BaseController;

@Security
@ShowLabel("考生信息")
@Controller("/exam/examinee")
public class ExamineeController extends BaseController{
	
	@AutoInject
	private ExamineeService examineeService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/examinee/list.jsp"));
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
