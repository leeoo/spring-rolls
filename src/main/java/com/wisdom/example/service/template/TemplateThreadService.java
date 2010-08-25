package com.wisdom.example.service.template;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wisdom.example.commons.HtmlFileUtils;
import com.wisdom.example.entity.template.Template;
/**
 * 功能描述：
 * 模板生成静态页后台异步多线程服务
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-22</b>
 * <br>创建时间：<b>下午02:29:37</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service/TemplateThreadService.java</b>
 */
@Service
public class TemplateThreadService implements Runnable{
	@Resource
	private TemplateService templateService;
	
	@Resource
	private TemplateDataService templateDataService;
	
	private String webRootPath;
	
	/**
	 * 异步执行任务入口
	 */
	public void run() {
		List<Template> templates=templateService.getAllTemplate();
		creatHtml(webRootPath, templates);
	}
	
	/**
	 * 功能描述：
	 * 创建html静态页
	 * <br>@param rootPath服务路径地址
	 * <br>@param templates模板集合
	 */
	private void creatHtml(String webRootPath,List<Template> templates){
		for(Template template:templates){
			Map<String, Object> dataBean=templateDataService.getTemplateDatas(template.getId());
			HtmlFileUtils.createHtml(webRootPath+template.getTemplatePath(), dataBean, webRootPath+template.getHtmlPath());
		}
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public TemplateDataService getTemplateDataService() {
		return templateDataService;
	}

	public void setTemplateDataService(TemplateDataService templateDataService) {
		this.templateDataService = templateDataService;
	}


	public String getWebRootPath() {
		return webRootPath;
	}


	public void setWebRootPath(String webRootPath) {
		this.webRootPath = webRootPath;
	}

}
