package com.exam.entity.exam;

/**
 * 考生的选择答案，仅用于判断题的显示
 * @author caoyang
 *
 */
public class QuestionChoiceExaminee extends QuestionChoice{

	private static final long serialVersionUID = -4605148137360341305L;

	public QuestionChoiceExaminee(QuestionChoice questionChoice){
		setAnswer(questionChoice.getAnswer());
		setaOption(questionChoice.getaOption());
		setbOption(questionChoice.getbOption());
		setcOption(questionChoice.getcOption());
		setdOption(questionChoice.getdOption());
		seteOption(questionChoice.geteOption());
		setfOption(questionChoice.getfOption());
		setgOption(questionChoice.getgOption());
		sethOption(questionChoice.gethOption());
		setId(questionChoice.getId());
		setTitle(questionChoice.getTitle());
		setMulti(questionChoice.isMulti());
		setProportion(questionChoice.getProportion());
		setQuestionId(questionChoice.getQuestionId());
	}
	
	private boolean right = false;
	
	private String examineeAnswer;

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public String getExamineeAnswer() {
		return examineeAnswer;
	}

	public void setExamineeAnswer(String examineeAnswer) {
		this.examineeAnswer = examineeAnswer;
	}

}
