package com.exam.entity.exam;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.security.UserDetails;

public class ExamSession {
	
	private Integer id;
	
	private String title;// 考试描述
	
	private boolean choice;
	
	private boolean audio;//是否是音频
	
	private int score;
	
	private int cnProportion = 1;// 中文权重
	
	private int enProportion = 1;// 英文权重
	
	private int punProportion = 1;// 标点权重
	
	private int numProportion = 1;// 数字权重
	
	private int longTime;
	
	private Date startDate;// 开始时间
	
	private Date endDate;// 结束时间
	
	private String audioPath;//音频路径
	
	private String textContent;
	
	private Collection<QuestionChoice> questionChoices = new LinkedHashSet<QuestionChoice>();
	
	private Map<Integer,ExamineeSession> examinees = new HashMap<Integer, ExamineeSession>();
	
	/**
	 * 释放资源
	 */
	public void release(){
		if(examinees!=null && !examinees.isEmpty()){
			for(ExamineeSession examineeSession : examinees.values()){
				examineeSession.release();
			}
		}
		questionChoices.clear();
		examinees.clear();
		textContent = null;
	}
	
	/**
	 * 如果考完，提交后，就会清理该考生缓存
	 */
	public void removeExamineeSession(Integer examineeId){
		if(examineeId!=null){
			ExamineeSession examineeSession = examinees.get(id);
			examineeSession.release();
			examinees.remove(id);
		}
	}
	
	public void addQuestionChoice(QuestionChoice questionChoice){
		questionChoices.add(questionChoice);
	}
	
	public Collection<QuestionChoice> getQuestionChoices(){
		return questionChoices;
	}
	
	public void addExaminee(ExamineeSession examineeSession){
		examinees.put(examineeSession.getId(), examineeSession);
	}
	
	public ExamineeSession getExaminee(Integer id){
		return examinees.get(id);
	}
	
	public Collection<ExamineeSession> getExamineeSessions(){
		return examinees.values();
	}
	
	public ExamineeSession getExaminee(){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			return null;
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			return null;
		}
		return getExaminee(examinee.getId());
	}

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

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public boolean isAudio() {
		return audio;
	}

	public void setAudio(boolean audio) {
		this.audio = audio;
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

	public int getNumProportion() {
		return numProportion;
	}

	public void setNumProportion(int numProportion) {
		this.numProportion = numProportion;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
