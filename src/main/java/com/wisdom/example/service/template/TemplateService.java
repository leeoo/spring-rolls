package com.wisdom.example.service.template;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.orm.SimpleOrmGenericDao;
import com.wisdom.core.utils.Page;
import com.wisdom.example.commons.FileUtils;
import com.wisdom.example.commons.HtmlFileUtils;
import com.wisdom.example.dao.template.TemplateDao;
import com.wisdom.example.entity.template.Template;
import com.wisdom.example.entity.template.TemplateData;
/**
 * 功能描述：
 * 模板管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-17</b>
 * <br>创建时间：<b>上午09:54:13</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service/TemplateService.java</b>
 */
@Service
@Transactional
public class TemplateService implements TemplateDao{
	
	private SimpleOrmGenericDao<Template, Long> templateDao;
	private SimpleOrmGenericDao<TemplateData, Long> templateDataDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		templateDao = new SimpleOrmGenericDao<Template, Long>(dataSource,Template.class);
		templateDataDao = new SimpleOrmGenericDao<TemplateData, Long>(dataSource,TemplateData.class);
	}
	
	/**
	 * 功能描述：保存模板信息
	 * <br>@param template模板信息
	 * <br>@param webPath服务目录地址
	 * <br>@throws Exception
	 */
	public void saveTemplate(Template template,String webPath)throws Exception{
		templateDao._save(template);
		copy(template.getFile(), webPath+template.getTemplatePath(),null);
	}
	
	/**
	 * 功能描述：修改模板信息
	 * <br>@param template模板信息
	 * <br>@param webPath服务目录地址
	 * <br>@throws Exception
	 */
	public void updateTemplate(Template template,String webPath)throws Exception{
		Template templateOld=getTemplateById(template.getId());
		templateDao.update(template);
		copy(template.getFile(), webPath+template.getTemplatePath(),webPath+templateOld.getTemplatePath());
	}
	
	/**
	 * 功能描述：删除模板信息，关联删除所属模板填充用数据，并将模板文件物理删除。
	 * <br>@param id模板主键
	 * <br>@param webPath服务目录地址
	 * <br>@throws Exception
	 */
	public void deleteTemplate(Long id,String webPath)throws Exception{
		Template template=getTemplateById(id);
		templateDao.delete(id);
		templateDataDao.deleteByProperty("templateId", id);
		try{
			FileUtils.forceDelete(new File(webPath+template.getTemplatePath()));
		}catch (Exception e) {}
	}
	
	/**
	 * 功能描述：通过主键获得模板信息
	 * <br>@param id主键
	 * <br>@return 模板信息
	 */
	@Transactional(readOnly=true)
	public Template getTemplateById(Long id){
		return templateDao.get(id);
	}
	
	/**
	 * 功能描述：获得所有模板信息
	 * <br>@return 模板信息集合
	 */
	@Transactional(readOnly=true)
	public List<Template> getAllTemplate(){
		return (List<Template>) templateDao.getAll();
	}

	/**
	 * 功能描述：通过分页对象获得模板分页信息
	 * <br>@param page 分页对象
	 * <br>@return 模板分页信息
	 */
	@Transactional(readOnly=true)
	public Page getAllTemplate(Page page){
		return templateDao.getAll(page);
	}
	
	/**
	 * 功能描述：文件拷贝，将上传后的模板文件流生成物理文件，并通过新老文件比较，自动判断是否修改文件。
	 * <br>@param file文件流
	 * <br>@param newPath新文件
	 * <br>@param oldPath旧文件
	 * <br>@throws Exception
	 */
	private void copy(byte[] file,String newPath,String oldPath)throws Exception {
		if(file!=null&&file.length>0&&newPath!=null){
			if(oldPath!=null&&!newPath.equals(oldPath)){
				try{
					FileUtils.forceDelete(new File(oldPath));
				}catch (Exception e) {}
			}
			FileUtils.mkDirectory(HtmlFileUtils.getFilePath(newPath));
			FileOutputStream fos = new FileOutputStream(newPath);
			fos.write(file);
			fos.flush();
	        fos.close();
		}
	}
	
}
