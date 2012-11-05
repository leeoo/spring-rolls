package com.exam.service.exam;

import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.JdbcTemplateFactory;
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
		Date date = DateUtils.getCurrentDateTime();
		String type = JdbcTemplateFactory.dbTypes.values().iterator().next();
		String sqlFill = "DATEDIFF(?,reg_date)";
		if(type.equalsIgnoreCase("h2")){
			sqlFill = "DATEDIFF(d,reg_date,?)";
		}
		String sqlA = "DELETE FROM t_score WHERE examinee_id IN(SELECT id FROM t_examinee WHERE can_days!=0 AND can_days<"+sqlFill+")";
		String sqlB = "DELETE FROM t_examinee WHERE can_days!=0 AND can_days<"+sqlFill;
		ormDao.getGenericJdbcDao().executeDMLByArray(sqlA,date);
		ormDao.getGenericJdbcDao().executeDMLByArray(sqlB,date);
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
	
	public Examinee get(String code){
		return (Examinee)ormDao.getGenericJdbcDao().queryUniqueByArray("SELECT * FROM t_examinee WHERE code=?",Examinee.class,code);
	}
	
	public Examinee get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page,Examinee examinee){
		return ormDao.select(page,examinee);
	}
	
}
