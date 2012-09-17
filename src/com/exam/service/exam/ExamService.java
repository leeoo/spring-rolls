package com.exam.service.exam;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Exam;

@ShowLabel("考试业务类")
@Service
@Transactional
public class ExamService {
	
	private GenericOrmDao<Exam, Integer> ormDao = new GenericOrmDao<Exam, Integer>(Exam.class);
	
	public ExamService(){
		ormDao.setValidation(true);
	}
	
	public void save(Exam exam) throws Exception{
		ormDao.insert(exam);
	}
	
	public void update(Exam exam) throws Exception{
		ormDao.update(exam);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public Exam get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page){
		return ormDao.select(page);
	}

}
