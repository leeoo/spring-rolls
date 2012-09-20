package com.exam.web.exam;

import java.io.File;

import org.paramecium.commons.PathUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.entity.exam.Question;
import com.exam.service.exam.QuestionService;
import com.exam.web.BaseController;

@Security
@ShowLabel("速录考试题库")
@Controller("/exam/question")
public class QuestionController extends BaseController{
	
	@AutoInject
	private QuestionService questionService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getExamPage("/question/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		Question question = mv.getBean(Question.class);
		if(question!=null && question.getTitle()!=null){
			question.setTitle("%"+question.getTitle()+"%");
		}
		page = questionService.getAll(page,question);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			Question question = questionService.get(id);
			mv.addValue("question", question);
		}
		return mv.forward(getExamPage("/question/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		Question question = mv.getBean("question",Question.class);
		try {
			question.setChoice(false);
			if(question.getId()==null){
				questionService.save(question);
				if(question.getAudioPath()!=null&&!question.getAudioPath().trim().isEmpty()){
					renameAudioFile(question.getAudioPath());
				}
			}else{
				questionService.update(question);
				String oldAudioPath = mv.getValue("oldAudioPath", String.class);
				if(oldAudioPath!=null&&!oldAudioPath.trim().isEmpty()&&(question.getAudioPath()==null||question.getAudioPath().trim().isEmpty())){
					deleteAudioFile(oldAudioPath);//如果以前有，现在没有，就说明删除。基本不成立.因为没有可能修改audioPath
				}else if(question.getAudioPath()!=null&&oldAudioPath!=null&&!oldAudioPath.equals(question.getAudioPath())){
					deleteAudioFile(oldAudioPath);//以前和现在都不为空，但还是文件名不同,删除以前的，给现在的临时改名
					renameAudioFile(question.getAudioPath());
				}else if((oldAudioPath==null||oldAudioPath.trim().isEmpty())&&(question.getAudioPath()!=null&&!question.getAudioPath().trim().isEmpty())){
					renameAudioFile(question.getAudioPath());//以前是空的，现在上传了
				}
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("question", question);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getExamPage("/question/input.jsp"));
		}
		return mv.redirect(getRedirect("/exam/question/list"));
	}
	
	private void renameAudioFile(String fileName){
		String dTempName = PathUtils.getWebRootPath() + "//upload//temp";
		String dAudioName = PathUtils.getWebRootPath() + "//upload//audio";
		File dir = new File(dAudioName);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File tempFile = new File(dTempName+"//"+fileName);
		File audioFile = new File(dAudioName+"//"+fileName);
		tempFile.renameTo(audioFile);
	}
	
	private void deleteAudioFile(String fileName){
		String dAudioName = PathUtils.getWebRootPath() + "//upload//audio";
		File audioFile = new File(dAudioName+"//"+fileName);
		audioFile.delete();
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				questionService.delete(ids);
			}
		} catch (Exception e) {
		}
	}
	
}
