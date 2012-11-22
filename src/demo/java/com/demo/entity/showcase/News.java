package com.demo.entity.showcase;

import java.io.Serializable;
import java.util.Date;

import org.apache.lucene.search.SortField.Type;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.Lazy;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.KeyWord;
import org.paramecium.search.annotation.SortWord;
import org.paramecium.search.annotation.TextWord;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

@Index("news")
@Entity(tableName="t_showcase_news",orderBy="publish_date DESC")
public class News implements Serializable{
	
	private static final long serialVersionUID = 171518455865141004L;

	@PrimaryKey
	@KeyWord
	@Column
	private Integer id;
	
	@NotNull(empty = true)
	@Length(min=2,max=50)
	@TextWord
	@ShowLabel("标题")
	@Column
	private String title;
	
	@Column
	@NotUpdate
	private Date publishDate = new Date();
	
	@SortWord(type=Type.LONG)
	private long longPublishDate;
	
	@NotNull(empty = true)
	@Length(min=2,max=999999)
	@ShowLabel("内容")
	@TextWord
	@Lazy
	@Column
	private String content;
	
	@ShowLabel("作者")
	@Length(min=2,max=10)
	@Column
	private String auth = "佚名";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public long getLongPublishDate() {
		this.longPublishDate = getPublishDate()!=null?getPublishDate().getTime():0l;
		return this.longPublishDate;
	}

	public void setLongPublishDate(long longPublishDate) {
		this.longPublishDate = getPublishDate()!=null?getPublishDate().getTime():0l;
	}

	
}
