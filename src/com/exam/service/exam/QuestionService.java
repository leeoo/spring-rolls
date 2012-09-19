package com.exam.service.exam;

import java.util.Collection;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Question;

@ShowLabel("速录考试题库业务类")
@Service
@Transactional
public class QuestionService {
	
	private GenericOrmDao<Question, Integer> ormDao = new GenericOrmDao<Question, Integer>(Question.class);
	
	public QuestionService(){
		ormDao.setValidation(true);
	}
	
	public void save(Question question) throws Exception{
		ormDao.insert(question);
	}
	
	public void update(Question question) throws Exception{
		ormDao.update(question);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public Question get(int id){
		return ormDao.select(id);
	}
	
	public Collection<Question> getAll(){
		Question question = null;
		return ormDao.select(question);
	}
	
	public Page getAll(Page page,Question question){
		return ormDao.select(page,question);
	}

}
