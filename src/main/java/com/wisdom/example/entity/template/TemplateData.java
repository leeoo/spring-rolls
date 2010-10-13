package com.wisdom.example.entity.template;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.orm.domain.BaseEntity;

/**
 * 功能描述： 填充模板用数据 <br>
 * 代码作者：<b>CaoYang</b> <br>
 * 创建日期：<b>2009-10-19</b> <br>
 * 创建时间：<b>下午03:21:10</b> <br>
 * 文件结构：<b>spring:com.wisdom.example.entity/TemplateData.java</b>
 */
@SimpleEntity(tableName="t_template_data",isUseIDCreator=true)
public class TemplateData extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -2822248814009587135L;
	private Long id;
	private Long templateId;
	
	@NotNull(message="数据名称不能为空！")
	@Size(min=2,max=20,message="数据名称最少输入{min}个字符最多不超过{max}个字符")
	private String dataName;
	
	@NotNull(message="SQL语句不能为空！")
	@Size(min=2,max=1000,message="SQL语句最少输入{min}个字符最多不超过{max}个字符")
	private String sqlValue;
	
	private String description;
	
	private boolean isUniqueResult;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsUniqueResult() {
		return isUniqueResult;
	}

	public void setIsUniqueResult(boolean isUniqueResult) {
		this.isUniqueResult = isUniqueResult;
	}

	public String getSqlValue() {
		return sqlValue;
	}

	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}
}
