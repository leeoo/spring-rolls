package com.wisdom.example.service.template;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.orm.SimpleOrmGenericDao;
import com.wisdom.example.dao.template.TemplateDataDao;
import com.wisdom.example.entity.template.TemplateData;
/**
 * 功能描述：
 * '填充模板用数据'管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-19</b>
 * <br>创建时间：<b>下午05:08:33</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service/TemplateDataService.java</b>
 */
@Service
@Transactional
public class TemplateDataService implements TemplateDataDao{

	private SimpleOrmGenericDao<TemplateData, Long> templateDataDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		templateDataDao = new SimpleOrmGenericDao<TemplateData, Long>(dataSource,TemplateData.class);
	}
	
	/**
	 * 功能描述：保存'填充模板用数据'信息
	 * <br>@param templateData模板数据信息
	 * <br>@throws Exception
	 */
	public void saveTemplateData(TemplateData templateData)throws Exception{
		templateDataDao._save(templateData);
	}
	
	/**
	 * 功能描述：修改'填充模板用数据'信息
	 * <br>@param templateData
	 * <br>@throws Exception
	 */
	public void updateTemplateData(TemplateData templateData)throws Exception{
		templateDataDao.update(templateData);
	}
	
	/**
	 * 功能描述：根据主键删除'填充模板用数据'信息
	 * <br>@param id主键
	 * <br>@throws Exception
	 */
	public void deleteTemplateData(Long id)throws Exception{
		templateDataDao.delete(id);
	}
	
	/**
	 * 功能描述：通过主键获得'填充模板用数据'信息
	 * <br>@param id
	 * <br>@return '填充模板用数据'信息
	 */
	@Transactional(readOnly=true)
	public TemplateData getTemplateDataById(Long id){
		return templateDataDao.get(id);
	}
	
	/**
	 * 功能描述：根据模板信息主键获得其下所有用于'填充模板用数据'信息
	 * <br>@param templateId模板主键
	 * <br>@return '填充模板用数据'信息集合
	 */
	@Transactional(readOnly=true)
	public List<TemplateData> getAllTemplateDataByTemplateId(Long templateId){
		return (List<TemplateData>) templateDataDao.getAllByProperty("templateId", templateId);
	}
	
	/**
	 * 功能描述：通过模板主键，获得该模板所用到的所有数据。
	 * 系统将所属模板的所有‘填充模板用数据’的数据，转换成模板所需格式，并加以处理。
	 * <br>@param templateId模板主键
	 * <br>@return 生成模板用数据集合
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Map<String, Object> getTemplateDatas(Long templateId){
		List<TemplateData> datas=getAllTemplateDataByTemplateId(templateId);
		Map<String,Object> map=new LinkedHashMap<String, Object>();
		List<Map<String,Object>> temps=null;
		Map<String,Object> temp=null;
		for(TemplateData data:datas){
			if(data.getIsUniqueResult()){
				temp=templateDataDao.jdbcTemplate.findUniqueMapByArray(data.getSqlValue());
				map.put(data.getDataName(), temp);
			}else{
				temps=templateDataDao.jdbcTemplate.findListMapByArray(data.getSqlValue());
				map.put(data.getDataName(), temps);
			}
		}
		return map;
	}
	
}
