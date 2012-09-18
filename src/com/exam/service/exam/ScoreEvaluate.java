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
		ScoreEvaluate evaluate = new ScoreEvaluate("日本“购买”钓鱼岛触发中国香港保钓人士再闯钓鱼岛决心，香港保钓委员会14日表示，8月到钓鱼岛宣示主权期间被撞毁的“启丰二号”已经完成基本的安全维修，香港保钓船“启丰二号”计划将于9月18日再次前往钓鱼岛宣示主权。此外，本月下旬，台湾部分民意代表和渔民也将登陆钓鱼岛。", 100, 1, 1, 1, 1);
		System.out.println(evaluate.getScore("日本“购买”钓鱼岛触发中国香钓鱼岛决心，香港保钓委员会14日表示，8月到钓鱼岛宣示主权期间被撞毁的“启丰二号”成基本的安全维修，香港保钓船“启丰二号”计划将于9月18日再次前往钓鱼岛宣权。此外，本月下旬，台湾部分民意代表和渔民也将登陆钓鱼岛。"));
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
		float totalSoc = ContentUtils.getContentSoc(source, cn, en, pun, num);
		float errorSoc = ContentUtils.compareSoc(source, target, cn, en, pun, num);
		float socore;
		if (errorSoc >= totalSoc){
			socore = 0.0f;
		}else{
			socore = (totalSoc - errorSoc) * fullScore / totalSoc;
		}
		return new BigDecimal(socore).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	
	
}
