package com.exam.test;

import org.paramecium.ioc.ApplicationContext;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.QuestionChoice;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.QuestionChoiceService;
import com.exam.service.exam.QuestionService;

public class Main {

	public static void main(String[] args) throws Exception{
		/*ExamineeService examineeService = (ExamineeService) ApplicationContext.getBean("examineeService");
		ScoreService scoreService = (ScoreService) ApplicationContext.getBean("scoreService");*/
		/*Examinee examinee = new Examinee();
		examinee.setCode("12091701");
		examinee.setUsername("小飞");
		examinee.setPassword("123456");
		examinee.setCanDays(2);
		examinee.setTel("13012345678");
		examinee.setCard("210120198956464541");
		examineeService.save(examinee);
		Score score = new Score();
		score.setExamId(1);
		score.setExamineeId(1);
		score.setContext("的说法是打发第三方asdfas");
		score.setLongTime(1402);
		score.setScore(68);
		scoreService.save(score);*/
		/*Examinee examinee = examineeService.get(1);
		Score score = scoreService.get(1);
		examinee.setCode("4412321");
		examineeService.update(examinee);
		score.setContext("sadfasdfsadf");
		scoreService.update(score);*/
		/*examineeService.delete(1);*/
		/*ExamService examService = (ExamService) ApplicationContext.getBean("examService");
		Exam exam = new Exam();
		exam.setCharProportion(1);
		exam.setCnProportion(2);
		exam.setEndDate(DateUtils.parse("2012-12-12 00:00:00"));
		exam.setEnProportion(3);
		exam.setLongTime(1024);
		exam.setPunProportion(2);
		exam.setQuestionId(1);
		exam.setScore(100);
		exam.setScoreSource(true);
		exam.setStartDate(DateUtils.parse("2012-9-18 00:00:00"));
		exam.setStatus(1);
		exam.setTitle("一次历史性的考试，我很理解你");
		examService.save(exam);*/
		/*QuestionService questionService = (QuestionService) ApplicationContext.getBean("questionService");
		Question question = new Question();
		question.setAudioPath("d:/a/b/sdf.mp3");
		question.setChoice(false);
		question.setTextContent("哈傻傻地发");
		question.setTitle("考试，不能出错了的说法阿斯顿");
		questionService.save(question);*/
		QuestionChoiceService questionChoiceService = (QuestionChoiceService) ApplicationContext.getBean("questionChoiceService");
		QuestionChoice questionChoice = new QuestionChoice();
		questionChoice.setAnswer("A");
		questionChoice.setaOption("aaaaaa");
		questionChoice.setbOption("bbb");
		questionChoice.setcOption("ccc");
		questionChoice.setdOption("ddd");
		questionChoice.seteOption("eee");
		questionChoice.setfOption("fff");
		questionChoice.setgOption("ggg");
		questionChoice.sethOption("hhhh");
		questionChoice.setMulti(true);
		questionChoice.setQuestionId(1);
		questionChoice.setScore(5);
		questionChoice.setTitle("阿斯顿发水电费打发斯蒂芬");
		questionChoiceService.save(questionChoice);
		ApplicationContext.destroy();
	}

}
