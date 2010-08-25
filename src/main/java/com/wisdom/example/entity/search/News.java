package com.wisdom.example.entity.search;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.wisdom.core.annotation.DynamicWhere;
import com.wisdom.core.annotation.Index;
import com.wisdom.core.annotation.NotMapping;
import com.wisdom.core.annotation.Reference;
import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.annotation.TextWord;
import com.wisdom.core.annotation.Unique;
import com.wisdom.core.orm.domain.BaseEntity;
/**
 * 功能描述：展示搜索引擎用新闻实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-7</b>
 * <br>创建时间：<b>下午03:40:04</b>
 * <br>文件结构：<b>spring:com.wisdom.example.entity.search.News.java</b>
 */
@SimpleEntity(tableName="t_news_info",orderBy="publish_date DESC,id ASC",where="enabled=1",isUseIDCreator=true)
@Index(indexName="index_news")
public class News extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -5403519156042660571L;
	
	private Long id;
	
	@NotNull(message="标题不能为空")
	@Size(min=2,max=50,message="标题最少输入{min}个字符最多不超过{max}个字符")
	@TextWord
	@Unique
	private String title;
	
	@Past(message="发布日期必须小于当前日期")
	private Date publishDate;
	
	@NotMapping
	@DynamicWhere(comparison=">=",comparisonField="publishDate")
	private Date publishDateBegin;
	
	@NotMapping
	@DynamicWhere(comparison="<=",comparisonField="publish_date")
	private Date publishDateEnd;
	
	@Future(message="过期日期必须大于当前日期")
	private Date durability;
	
	private boolean enabled=true;
	
	@NotMapping
	private String state;

	@Reference(refTableName="t_system_user_info",refViewFieldName="cnname")
	@NotMapping
	private String username;
	
	@NotNull(message="作者不能为空")
	@Size(min=2,max=20,message="作者最少输入{min}个字符最多不超过{max}个字符")
	@TextWord
	private String auth;
	
	@NotNull(message="新闻内容不能为空")
	@Size(min=2,max=20000,message="新闻内容最少输入{min}个字符最多不超过{max}个字符")
	@TextWord
	private String context;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getDurability() {
		return durability;
	}
	public void setDurability(Date durability) {
		this.durability = durability;
	}

	public String getState() {
		if(enabled){
			state="有效";
		}else{
			state="过期";
		}
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getPublishDateBegin() {
		return publishDateBegin;
	}
	public void setPublishDateBegin(Date publishDateBegin) {
		this.publishDateBegin = publishDateBegin;
	}
	public Date getPublishDateEnd() {
		return publishDateEnd;
	}
	public void setPublishDateEnd(Date publishDateEnd) {
		this.publishDateEnd = publishDateEnd;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
