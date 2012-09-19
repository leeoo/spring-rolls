package com.exam.service.exam;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Examinee;

@ShowLabel("考生业务类")
@Service
@Transactional
public class ExamineeService {
	
	private GenericOrmDao<Examinee, Integer> ormDao = new GenericOrmDao<Examinee, Integer>(Examinee.class);
	@AutoInject
	private ScoreService scoreService;
	
	public ExamineeService(){
		ormDao.setValidation(true);
	}
	
	public void save(Examinee examinee) throws Exception{
		ormDao.insert(examinee);
	}
	
	public void update(Examinee examinee) throws Exception{
		ormDao.update(examinee);
	}
	
	public void delete(int id) throws Exception{
		ormDao.delete(id);
		scoreService.deleteByExamineeId(id);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
			scoreService.deleteByExamineeId(Integer.parseInt(id));
		}
	}
	
	public Examinee get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page,Examinee examinee){
		return ormDao.select(page,examinee);
	}
	
}
