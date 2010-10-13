package com.wisdom.example.web.example.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.utils.HtmlUtils;
import com.wisdom.example.commons.ValidationUtils;
import com.wisdom.example.entity.template.Template;
import com.wisdom.example.entity.template.TemplateData;
import com.wisdom.example.service.template.TemplateDataService;
import com.wisdom.example.service.template.TemplateService;
/**
 * 功能描述：
 * 填充网页模板数据管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-19</b>
 * <br>创建时间：<b>下午05:21:21</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example/TemplateDataController.java</b>
 */
@Controller
@RequestMapping("/example/template/{templateId}/data")
public class TemplateDataController {
	@Resource
	private TemplateDataService templateDataService;
	@Resource
	private TemplateService templateService;

	@RequestMapping("/list")
	public String list(@PathVariable Long templateId,HttpServletRequest request){
		Template template=templateService.getTemplateById(templateId);
		String webPath=request.getSession().getServletContext().getRealPath("");
		String temp = null;
		String data = "";
		try {
			File file=new File(webPath+template.getTemplatePath());
			FileInputStream fileInputStream=new FileInputStream(file);
			InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader); 
			while((temp = bufferedReader.readLine())!=null){
				data=data+temp;
			}
			data=HtmlUtils.htmlEscape(data);
			try{bufferedReader.close();}catch(Exception e){}
			try{inputStreamReader.close();}catch(Exception e){}
			try{fileInputStream.close();}catch(Exception e){}
		} catch (Exception e) {}
		request.setAttribute("templateDatas",templateDataService.getAllTemplateDataByTemplateId(templateId));
		request.setAttribute("templateContext",data);
		request.setAttribute("template",template);
		request.setAttribute("templateId",templateId);
		return "/example/template/data/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long templateId,@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			TemplateData templateData=templateDataService.getTemplateDataById(id);
			request.setAttribute("templateData",templateData);
		}
		request.setAttribute("templateId",templateId);
		return "/example/template/data/input";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@PathVariable Long templateId,TemplateData templateData,HttpServletRequest request) throws Exception{
		List<String> errors=ValidationUtils.validator(templateData);
		if(errors!=null){
			request.setAttribute("errors", errors);
			return "/errors/error";
		}
		templateData.setTemplateId(templateId);
		if(templateData.getId()!=null){
			templateDataService.updateTemplateData(templateData);
		}else{
			templateDataService.saveTemplateData(templateData);
		}
		request.setAttribute("templateId",templateId);
		return "redirect:/example/template/"+templateId+"/data/list.htm";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long templateId,@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			templateDataService.deleteTemplateData(id);
		}
		request.setAttribute("templateId",templateId);
		return "redirect:/example/template/"+templateId+"/data/list.htm";
	}
	
}
