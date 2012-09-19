package com.exam.service.exam;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.QuestionChoice;

@ShowLabel("选择题业务类")
@Service
@Transactional
public class QuestionChoiceService {

	private GenericOrmDao<QuestionChoice, Integer> ormDao = new GenericOrmDao<QuestionChoice, Integer>(QuestionChoice.class);
	
	public QuestionChoiceService(){
		ormDao.setValidation(true);
	}
	
	public void save(QuestionChoice question) throws Exception{
		ormDao.insert(question);
	}
	
	public void update(QuestionChoice question) throws Exception{
		ormDao.update(question);
	}
	
	public void deleteByQuestionId(int questionId) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_question_choice WHERE question_id=?",questionId);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public QuestionChoice get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page){
		return ormDao.select(page);
	}
	
}
