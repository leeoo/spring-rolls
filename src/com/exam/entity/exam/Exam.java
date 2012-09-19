package com.exam.entity.exam;

import java.io.Serializable;
import java.util.Date;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

/**
 * 一场考试
 * @author caoyang
 */
@Entity(tableName = "t_exam", orderBy = "id DESC")
public class Exam implements Serializable {
	
	private static final long serialVersionUID = -1438114372794324572L;
	
	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(isDynamicWhere=true)
	@NotNull
	@Length(min=10,max=500)
	@ShowLabel("考试描述")
	private String title;// 考试描述
	
	@Column
	private int score = 100;// 总分
	
	@Column
	private int cnProportion = 1;// 中文权重
	
	@Column
	private int enProportion = 1;// 英文权重
	
	@Column
	private int punProportion = 1;// 标点权重
	
	@Column
	private int numProportion = 1;// 数字权重
	
	@Column(isDynamicWhere=true)
	private Integer status;// 考试状态 0为没有开始,1正在进行,-1已经过期
	
	@ShowLabel("考试开始时间")
	@Column
	@NotNull
	private Date startDate;// 开始时间
	
	@ShowLabel("考试结束时间")
	@Column
	@NotNull
	private Date endDate;// 结束时间
	
	@Column
	private int longTime = 60;// 考试时长，分钟,最小一分钟
	
	@ShowLabel("题库")
	@Column
	@NotNull
	private Integer questionId;// 题库ID
	
	@Column(isDynamicWhere=true)
	@NotUpdate
	private Boolean choice;//是否是理论考试及选择题

	private String miniTitle;//带...的
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCnProportion() {
		return cnProportion;
	}

	public void setCnProportion(int cnProportion) {
		this.cnProportion = cnProportion;
	}

	public int getEnProportion() {
		return enProportion;
	}

	public void setEnProportion(int enProportion) {
		this.enProportion = enProportion;
	}

	public int getPunProportion() {
		return punProportion;
	}

	public void setPunProportion(int punProportion) {
		this.punProportion = punProportion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
	}

	public int getNumProportion() {
		return numProportion;
	}

	public void setNumProportion(int numProportion) {
		this.numProportion = numProportion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Boolean getChoice() {
		return choice;
	}

	public void setChoice(Boolean choice) {
		this.choice = choice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMiniTitle() {
		miniTitle = title;
		if(miniTitle.length()>20){
			return miniTitle.substring(0, 20).concat("...");
		}
		return miniTitle;
	}

}
