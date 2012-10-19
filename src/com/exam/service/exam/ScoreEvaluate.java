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
		float score;
		if (errorSoc >= totalSoc){
			score = 0.0f;
		}else{
			score = (totalSoc - errorSoc) / totalSoc * fullScore;
		}
		return new BigDecimal(score).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	
	
}
