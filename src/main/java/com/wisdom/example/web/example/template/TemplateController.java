package com.wisdom.example.web.example.template;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.wisdom.core.utils.Page;
import com.wisdom.core.utils.ScheduledThreadUtils;
import com.wisdom.example.commons.HtmlFileUtils;
import com.wisdom.example.commons.ValidationUtils;
import com.wisdom.example.entity.template.Template;
import com.wisdom.example.service.template.TemplateService;
import com.wisdom.example.service.template.TemplateThreadService;
/**
 * 功能描述：
 * 模板管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-17</b>
 * <br>创建时间：<b>上午11:04:31</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example/TemplateController.java</b>
 */
@Controller
@RequestMapping("/example/template")
public class TemplateController {
	@Resource
	private TemplateService templateService;
	
	@Resource
	private TemplateThreadService templateThreadService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		request.setAttribute("page",templateService.getAllTemplate(page));
		return "/example/template/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			Template template=templateService.getTemplateById(id);
			request.setAttribute("template",template);
		}
		return "/example/template/input";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Template template,HttpServletRequest request) throws Exception{
		List<String> errors=ValidationUtils.validator(template);
		if(errors!=null){
			request.setAttribute("errors", errors);
			return "/errors/error";
		}
		String webPath=request.getSession().getServletContext().getRealPath("");
		if(template.getId()!=null){
			templateService.updateTemplate(template,webPath);
		}else{
			templateService.saveTemplate(template,webPath);
		}
		return "redirect:/example/template/list/1.htm";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder)throws ServletException {
		binder.registerCustomEditor(byte[].class, "file",new ByteArrayMultipartFileEditor());
	}

	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			String webPath=request.getSession().getServletContext().getRealPath("");
			templateService.deleteTemplate(id,webPath);
		}
		return "redirect:/example/template/list/"+no+".htm";
	}
	
	@RequestMapping("/download/{no}-{id}")
	public void download(@PathVariable int no,@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(id!=null){
			String webPath=request.getSession().getServletContext().getRealPath("");
			Template template=templateService.getTemplateById(id);
			String fileName=webPath+template.getTemplatePath();
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", "attachment;filename="+HtmlFileUtils.getFileName(fileName));
			FileInputStream inStream = new FileInputStream(fileName);
			OutputStream outStream = response.getOutputStream();
			int bytesRead = 0;
	        byte[] buffer = new byte[100];
	        while ( (bytesRead = inStream.read(buffer)) > 0) {
	        	outStream.write(buffer, 0, bytesRead);
	        }           
	        outStream.flush();
	        outStream.close();
	        inStream.close();
		}
	}
	
	@RequestMapping("/create")
	public String createHtml(HttpServletRequest request){
		String webPath=request.getSession().getServletContext().getRealPath("");
		templateThreadService.setWebRootPath(webPath);
		ScheduledThreadUtils.start(templateThreadService);
		return "redirect:/example/template/list/1.htm";
	}
	
}
