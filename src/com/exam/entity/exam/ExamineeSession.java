package com.exam.entity.exam;

import java.util.HashMap;
import java.util.Map;

import org.paramecium.commons.EncodeUtils;

public class ExamineeSession {
	
	private Integer id;
	
	private String code;
	
	private String username;
	
	private String tempContent;// 临时内容
	
	private Map<Integer,String> choices = new HashMap<Integer, String>();
	
	private int longTime;//经过多少时间(秒),防止断网
	
	private int examDate = (int)EncodeUtils.millisTime()/1000;//当前秒数
	
	private boolean choice;
	
	private boolean lrLayout;// 是否左右布局
	
	/**
	 * 释放资源
	 */
	public void release(){
		choices.clear();
	}
	
	public void addChoices(int id,String answer){
		choices.put(id, answer);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTempContent() {
		return tempContent;
	}

	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
	}

	public boolean isLrLayout() {
		return lrLayout;
	}

	public void setLrLayout(boolean lrLayout) {
		this.lrLayout = lrLayout;
	}

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public int getExamDate() {
		return examDate;
	}

	public void setExamDate(int examDate) {
		this.examDate = examDate;
	}

	public Map<Integer, String> getChoices() {
		return choices;
	}

}
