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
	
	public void delete() throws Exception{
		String sqlA = "DELETE FROM t_score WHERE examinee_id IN(SELECT id FROM t_examinee WHERE can_days!=0 AND can_days<DATEDIFF(NOW(),reg_date))";
		String sqlB = "DELETE FROM t_examinee WHERE can_days!=0 AND can_days<DATEDIFF(NOW(),reg_date)";
		ormDao.getGenericJdbcDao().executeDML(sqlA);
		ormDao.getGenericJdbcDao().executeDML(sqlB);
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
