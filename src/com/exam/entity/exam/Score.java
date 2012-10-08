package com.exam.entity.exam;

import java.io.Serializable;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;

/**
 * 分数记录
 * @author caoyang
 *
 */
@Entity(tableName = "t_score", orderBy = "start_date DESC")
public class Score implements Serializable{

	private static final long serialVersionUID = 5646052367350264820L;

	@PrimaryKey
	@Column
	private Integer id;
	
	@Column
	@NotUpdate
	private Date startDate = DateUtils.getCurrentDateTime();
	
	@Column
	private int longTime;// 所耗费时长，秒,最小一分钟
	
	@Column
	private String context;//答题正文,如果是选择题，直接录问题和所答答案
	
	@Column
	private int score;//分数
	
	@Column
	@NotUpdate
	private int examineeId;//考生信息id
	
	@Column
	@NotUpdate
	private int examId;//考试信息id

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getExamineeId() {
		return examineeId;
	}

	public void setExamineeId(int examineeId) {
		this.examineeId = examineeId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
