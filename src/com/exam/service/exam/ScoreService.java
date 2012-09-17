package com.exam.service.exam;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Score;

@ShowLabel("分数业务类")
@Service
@Transactional
public class ScoreService {

	private GenericOrmDao<Score, Integer> ormDao = new GenericOrmDao<Score, Integer>(Score.class);
	
	public ScoreService(){
		ormDao.setValidation(true);
	}
	
	public void save(Score score) throws Exception{
		ormDao.insert(score);
	}
	
	public void update(Score score) throws Exception{
		ormDao.update(score);
	}
	
	public void delete(int id) throws Exception{
		ormDao.delete(id);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public Score get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page){
		return ormDao.select(page);
	}
	
}
