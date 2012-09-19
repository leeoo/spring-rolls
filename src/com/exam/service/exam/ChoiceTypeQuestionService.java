package com.exam.service.exam;

import java.util.Collection;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.ChoiceTypeQuestion;

@ShowLabel("基础考试题库业务类")
@Service
@Transactional
public class ChoiceTypeQuestionService {
	
	private GenericOrmDao<ChoiceTypeQuestion, Integer> ormDao = new GenericOrmDao<ChoiceTypeQuestion, Integer>(ChoiceTypeQuestion.class);
	
	@AutoInject
	private QuestionChoiceService questionChoiceService;
	
	public ChoiceTypeQuestionService(){
		ormDao.setValidation(true);
	}
	
	public void save(ChoiceTypeQuestion question) throws Exception{
		ormDao.insert(question);
	}
	
	public void update(ChoiceTypeQuestion question) throws Exception{
		ormDao.update(question);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
			questionChoiceService.deleteByQuestionId(Integer.parseInt(id));
		}
	}
	
	public ChoiceTypeQuestion get(int id){
		return ormDao.select(id);
	}
	
	public Collection<ChoiceTypeQuestion> getAll(){
		ChoiceTypeQuestion question = new ChoiceTypeQuestion();
		return ormDao.select(question);
	}
	
	public Page getAll(Page page,ChoiceTypeQuestion question){
		return ormDao.select(page,question);
	}

}
