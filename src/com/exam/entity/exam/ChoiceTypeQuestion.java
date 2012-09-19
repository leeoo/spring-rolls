package com.exam.entity.exam;

import org.paramecium.orm.annotation.Entity;

@Entity(tableName = "t_question", orderBy = "id DESC",where="choice=1")
public class ChoiceTypeQuestion extends Question{
	
	private static final long serialVersionUID = 655772021146286119L;

}
