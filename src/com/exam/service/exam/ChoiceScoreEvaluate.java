package com.exam.service.exam;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import com.exam.entity.exam.QuestionChoice;
import com.exam.entity.exam.QuestionChoiceExaminee;
/**
 * 选择题判分算法
 * @author temp
 *
 */
public class ChoiceScoreEvaluate {
	
	private Collection<QuestionChoice> questionChoices;
	private int fullScore = 100;
	private int fullProportion = 0;

	public ChoiceScoreEvaluate(Collection<QuestionChoice> questionChoices,int fullScore){
		if(questionChoices!=null){
			this.questionChoices = questionChoices;
			this.fullScore = fullScore;
			for(QuestionChoice choice : questionChoices){
				this.fullProportion += choice.getProportion();
			}
		}else{
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) {
		Map<Integer,String[]> map = buildChoiceMap("1:b,c;1:A;2:t;3:c;6:a;7:,d,f;8:a,b,");
		System.out.println(buildChoiceContext(map));
		/*for(int id : map.keySet()){
			System.out.println("id:"+id+" value:");
			for(String value:map.get(id)){
				System.out.print(value);
			}
			System.out.println();
			System.out.println("=============================");
		}*/
	}
	
	/**
	 * 根据答案文本内容重新获得选择题原型
	 * @param choiceContent
	 * @return
	 */
	public static Map<Integer,String[]> buildChoiceMap(String choiceContext){
		Map<Integer,String[]> map = new HashMap<Integer, String[]>();
		if(choiceContext==null||choiceContext.trim().isEmpty()){
			return map;
		}
		for(String portion : choiceContext.split(";")){//获得每道题
			int m = portion.indexOf(':');//取得id和答案的划分点
			if(m>0){
				String key = portion.substring(0,m);
				int id = Integer.parseInt(key);//获取id
				String value = portion.substring(m+1);//获取答案内容
				if(value.length()>0){//判断内容是否有效
					if(value.indexOf(',')<0){//判断是否为单选
						String[] values = new String[1];
						values[0] = value.toUpperCase();
						map.put(id, values);
					}else{//否则多选
						Collection<String> values = new HashSet<String>();
						for(String each:value.split(",")){
							if(!each.trim().isEmpty()){//过滤无用答案
								values.add(each.toUpperCase());
							}
						}
						if(!values.isEmpty()){
							map.put(id,values.toArray(new String[values.size()]));
						}
					}
				}
			}
		}
		return map;
	}
	
	public static Collection<QuestionChoiceExaminee> getQuestionChoiceExaminee(Collection<QuestionChoice> choices,String answer){
		Map<Integer,String> map = buildChoiceMap2(answer);
		Collection<QuestionChoiceExaminee> questionChoices = new LinkedList<QuestionChoiceExaminee>();
		if(choices!=null&&!choices.isEmpty()){
			for(QuestionChoice choice : choices){
				QuestionChoiceExaminee choiceExaminee = new QuestionChoiceExaminee(choice);
				String examineeAnswer = map.get(choice.getId());
				if(examineeAnswer!=null&&!examineeAnswer.trim().isEmpty()&&examineeAnswer.equals(choice.getAnswer())){
					choiceExaminee.setRight(true);
				}
				choiceExaminee.setExamineeAnswer(examineeAnswer);
				questionChoices.add(choiceExaminee);
			}
			return questionChoices;
		}
		return null;
	}
	
	public static Map<Integer,String> buildChoiceMap2(String choiceContext){
		Map<Integer,String> map = new HashMap<Integer, String>();
		if(choiceContext==null||choiceContext.trim().isEmpty()){
			return map;
		}
		for(String portion : choiceContext.split(";")){//获得每道题
			int m = portion.indexOf(':');//取得id和答案的划分点
			if(m>0){
				String key = portion.substring(0,m);
				int id = Integer.parseInt(key);//获取id
				String value = portion.substring(m+1);//获取答案内容
				if(value.length()>0){//判断内容是否有效
					if(value.indexOf(',')<0){//判断是否为单选
						map.put(id, value.toUpperCase());
					}else{//否则多选
						StringBuffer buffer = new StringBuffer();
						for(String each:value.split(",")){
							if(!each.trim().isEmpty()){//过滤无用答案
								buffer.append(',').append(each.toUpperCase());
							}
						}
						if(buffer.length()>0){
							buffer.delete(0, 1);
							map.put(id,buffer.toString());
						}
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 将选择题原型变为文本格式
	 * @param map
	 * @return
	 */
	public static String buildChoiceContext(Map<Integer,String[]> map){
		if(map==null||map.isEmpty()){
			return "";
		}
		StringBuffer buffer = new StringBuffer(",");
		for(int id : map.keySet()){
			buffer.append(id).append(':');
			for(String answer : map.get(id)){
				buffer.append(answer.toUpperCase()).append(',');
			}
			buffer.replace(buffer.length()-1, buffer.length(), ";");
		}
		buffer.delete(0, 1);
		return buffer.toString();
	}
	
	public int getScore(Map<Integer,String[]> choiceMap){
		if(choiceMap==null||choiceMap.isEmpty()){
			return 0;
		}
		int errorProportion = 0;
		for(QuestionChoice choice : questionChoices){
			int id = choice.getId();
			String[] answers = choiceMap.get(id);
			if(answers==null||answers.length<1){//如果答案为空，说明没有答题，直接扣分并开始下一题评分
				errorProportion += choice.getProportion();
			}else{
				mark:for(String answer : answers){
					if(choice.getAnswer().indexOf(answer)<0){//如果有一个答案与正确答案不同，错误，直接扣分
						errorProportion += choice.getProportion();
						break mark;
					}
				}
			}
		}
		float score = ((float)fullProportion - (float)errorProportion) / (float)fullProportion * fullScore;
		return new BigDecimal(score).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
}
