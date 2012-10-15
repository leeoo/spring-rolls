package com.exam.entity.exam;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
		for(int id : map.keySet()){
			System.out.println("id:"+id+" value:");
			for(String value:map.get(id)){
				System.out.print(value);
			}
			System.out.println();
			System.out.println("=============================");
		}
	}
	
	/**
	 * 根据答案文本内容重新获得选择题原型
	 * @param choiceContent
	 * @return
	 */
	public static Map<Integer,String[]> buildChoiceMap(String choiceContent){
		if(choiceContent==null||choiceContent.trim().isEmpty()){
			return null;
		}
		Map<Integer,String[]> map = new HashMap<Integer, String[]>();
		for(String portion : choiceContent.split(";")){//获得每道题
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
	
	public int getScore(Map<Integer,String[]> choiceMap){
		
		return 0;
	}
	
}
