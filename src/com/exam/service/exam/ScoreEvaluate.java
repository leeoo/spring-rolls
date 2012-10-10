package com.exam.service.exam;

import java.math.BigDecimal;

import com.exam.commons.ContentUtils;


/**
 * 判分系统,通过不同算法，比对原文的相似度.
 * 
 * @author caoyang
 * 
 */
public class ScoreEvaluate {
	
	private String source;
	private int fullScore = 100;
	private int cn = 1;
	private int en = 1;
	private int pun = 1;
	private int num = 1;
	
	public static void main(String[] args) {
		/*ScoreEvaluate evaluate = new ScoreEvaluate("我真认识你你是草小认识的", 100, 1, 1, 1, 1);
		System.out.println(evaluate.getScore("我草小认识你小是草你"));*/
		String source = "该负责人表示，领海基线以外200海里范围属于国家专属经济区。在专属经济区进行海洋开采、调查、勘察，需报国家海洋局批准。外国船只特别是军事测量船如非法进入这一海域，海监可行使执法职能采取驱离措施。如对方拒绝撤离，海监船只能向上级汇报。如危及我国领海基线以外12海里范围领海，则由海军处置。这名负责人说，日本海上保安厅承担海警的职责，与中国的海监队有所不同，类似韩国的海警厅和美国的海洋自卫队，“他们的船上都装备有武器，而海监船上没有。”";
		String target = "该负责人表示，领海基线以外在海里范围属于国小家专属经济区。在专属经济区进行海洋开采、调查、勘察，需报国家海洋局批准。外国船只特别是军事测量船如非法进入这一海域，海监可行使执法职能采取驱离措施。如对方拒绝撤离，海监船只能向上级汇报。如危及我国领海基线以外12海里范围领海，则由海军处置。这名负责人说，日与警的职责，与中国的海监队有所不同，类似韩国的海警厅和美国的海洋自卫队，“他们的船上都装备有武器，而海监船上没有。”";
		ScoreEvaluate evaluate = new ScoreEvaluate(source, 100, 1, 1, 1, 1);
		System.out.println(evaluate.getScore(target));
	}
	
	/**
	 * 构造函数
	 * @param source原文
	 * @param fullScore满分
	 * @param cn中文权重
	 * @param en英文权重
	 * @param pun标点权重
	 * @param chart字符权重
	 */
	public ScoreEvaluate(String source,int fullScore,int cn,int en,int pun,int num){
		this.source = source;
		this.fullScore = fullScore;
		this.cn = cn;
		this.en = en;
		this.pun = pun;
		this.num = num;
	}
	
	public int getScore(String target){
		if(target==null||target.trim().isEmpty()){
			return 0;
		}
		float totalSoc = ContentUtils.getTotalSoc(source, cn, en, pun, num);
		float errorSoc = ContentUtils.getErrorScore(source, target, cn, en, pun, num);
		float socore;
		if (errorSoc >= totalSoc){
			socore = 0.0f;
		}else{
			socore = (totalSoc - errorSoc) * fullScore / totalSoc;
		}
		return new BigDecimal(socore).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	
	
}
