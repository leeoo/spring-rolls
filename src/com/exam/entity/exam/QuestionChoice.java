package com.exam.entity.exam;

import java.io.Serializable;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

/**
 * 选择题信息
 * @author caoyang
 */
@Entity(tableName = "t_question_choice", orderBy = "id DESC")
public class QuestionChoice implements Serializable {
	
	private static final long serialVersionUID = 6202944811249291759L;

	@PrimaryKey
	@Column
	private int id;
	
	@Column
	@NotNull
	@Length(min=10,max=1000)
	@ShowLabel("题目")
	private String title;// 问题题目
	
	@Column
	private int proportion;//此题权重
	
	@Column
	@NotNull
	@Length(min=1,max=500)
	@ShowLabel("A选项")
	private String aOption;
	
	@Column
	@NotNull
	@Length(min=1,max=500)
	@ShowLabel("B选项")
	private String bOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("C选项")
	private String cOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("D选项")
	private String dOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("E选项")
	private String eOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("F选项")
	private String fOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("G选项")
	private String gOption;
	
	@Column
	@Length(min=1,max=500)
	@ShowLabel("H选项")
	private String hOption;
	
	@Column
	@NotNull
	@ShowLabel("正确选项")
	@Length(min=1,max=20)
	private String answer;
	
	@Column
	private boolean multi = false;//是否多选
	
	@Column
	@NotUpdate
	private int questionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getaOption() {
		return aOption;
	}

	public void setaOption(String aOption) {
		this.aOption = aOption;
	}

	public String getbOption() {
		return bOption;
	}

	public void setbOption(String bOption) {
		this.bOption = bOption;
	}

	public String getcOption() {
		return cOption;
	}

	public void setcOption(String cOption) {
		this.cOption = cOption;
	}

	public String getdOption() {
		return dOption;
	}

	public void setdOption(String dOption) {
		this.dOption = dOption;
	}

	public String geteOption() {
		return eOption;
	}

	public void seteOption(String eOption) {
		this.eOption = eOption;
	}

	public String getfOption() {
		return fOption;
	}

	public void setfOption(String fOption) {
		this.fOption = fOption;
	}

	public String getgOption() {
		return gOption;
	}

	public void setgOption(String gOption) {
		this.gOption = gOption;
	}

	public String gethOption() {
		return hOption;
	}

	public void sethOption(String hOption) {
		this.hOption = hOption;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getProportion() {
		return proportion;
	}

	public void setProportion(int proportion) {
		this.proportion = proportion;
	}
	
}
