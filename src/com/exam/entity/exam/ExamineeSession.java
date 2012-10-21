package com.exam.entity.exam;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.paramecium.commons.EncodeUtils;

public class ExamineeSession implements Serializable{
	
	private static final long serialVersionUID = 2666071740777278933L;

	private Integer id;
	
	private String code;
	
	private String username;
	
	private String tempContent = "";// 临时内容，如果是选择题，则记录已经达到某题
	
	private ConcurrentMap<Integer,String[]> choices = new ConcurrentHashMap<Integer, String[]>();
	
	private ConcurrentMap<Integer,ChoiceMenu> choiceMenus = new ConcurrentSkipListMap<Integer, ChoiceMenu>();
	
	private int longTime;//经过多少时间(秒),防止断网
	
	private long examDate = EncodeUtils.millisTime()/1000;//当前秒数
	
	private boolean choice;
	
	private boolean lrLayout;// 是否左右布局
	
	/**
	 * 释放资源
	 */
	public synchronized void release(){
		choices.clear();
		choiceMenus.clear();
	}
	
	public synchronized void addChoices(Integer choiceId,int status,String... answer){
		if(choiceId!=null){
			choices.put(choiceId, answer);
			ChoiceMenu menu = choiceMenus.get(choiceId);
			if(status==0||status==1||status==-1){//必须符合三种中的状态
				menu.setStatus(status);//修改状态
			}
			choiceMenus.put(choiceId, menu);
		}
	}
	
	public String getAnswerChoices(Integer choiceId){
		if(choiceId!=null){
			String[] answers = choices.get(choiceId);
			if(answers!=null){
				StringBuffer buffer = new StringBuffer();
				for(String answer:answers){
					buffer.append(answer);
				}
				return buffer.toString();
			}
		}
		return null;
	}
	
	public ChoiceMenu getChoiceMenu(Integer choiceId){
		if(choiceId!=null){
			return choiceMenus.get(choiceId);
		}
		return null;
	}
	
	/**
	 * 初始化选择题菜单
	 * @param questionChoices
	 */
	public synchronized void initMenu(Collection<QuestionChoice> questionChoices){
		if(questionChoices!=null&&!questionChoices.isEmpty()&&choiceMenus.isEmpty()){
			QuestionChoice[] choices = questionChoices.toArray(new QuestionChoice[questionChoices.size()]);
			for(int i = 0;i<choices.length;i++){
				QuestionChoice choice =choices[i];
				ChoiceMenu choiceMenu = new ChoiceMenu();
				choiceMenu.setId(choice.getId());
				if(0==choices.length-1){//如果只有一道题，无需上下题
					//不用管
				}else if(i==0){//如果是第一题，不要上一题
					choiceMenu.setNextId(choices[i+1].getId());//其实就是0+1
				}else if(i==choices.length-1){//如果是最后一题，不要下一题
					choiceMenu.setPreviousId(choices[i-1].getId());
				}else{//前后都有题
					choiceMenu.setNextId(choices[i+1].getId());
					choiceMenu.setPreviousId(choices[i-1].getId());
				}
				choiceMenus.put(choice.getId(), choiceMenu);
			}
		}
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

	public Map<Integer, String[]> getChoices() {
		return choices;
	}

	public long getExamDate() {
		return examDate;
	}

	public void setExamDate(long examDate) {
		this.examDate = examDate;
	}

	public Map<Integer, ChoiceMenu> getChoiceMenus() {
		return choiceMenus;
	}

}
