package com.exam.entity.exam;

import java.io.Serializable;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.orm.annotation.VirtualColumn.DYNAMIC_WHERE_COMPARISON;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

/**
 * 题库信息
 * @author caoyang
 */
@Entity(tableName = "t_question", orderBy = "id DESC",where="choice=0")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -2479730194663938800L;
	
	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(isDynamicWhere = true,comparison = DYNAMIC_WHERE_COMPARISON.LIKE)
	@NotNull
	@Length(min=3,max=500)
	@ShowLabel("题库描述")
	private String title;// 考试描述
	
	@Column
	@NotUpdate
	private boolean choice = false;//是否是选择题 
	
	@Column
	private String audioPath;//音频路径
	
	@Column
	@NotNull
	@ShowLabel("题库正文")
	private String textContent;//文本正文

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
