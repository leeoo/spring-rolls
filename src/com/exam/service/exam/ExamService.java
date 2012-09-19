package com.exam.service.exam;

import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.AutoInject;
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
	
	@AutoInject
	private ScoreService scoreService;
	
	public ExamService(){
		ormDao.setValidation(true);
	}
	
	public void save(Exam exam) throws Exception{
		ormDao.insert(exam);
	}
	
	public void update(Exam exam) throws Exception{
		ormDao.update(exam);
	}
	
	public void updateExaming(int id) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_exam SET status=1 WHERE id=?",id);
	}
	
	public void updateExamed(int id) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_exam SET status=-1 WHERE id=?",id);
	}
	
	public void delete(String... ids) throws Exception{
		Exam exam = null;
		Date date = DateUtils.getCurrentDateTime();
		for(String id : ids){
			exam = get(Integer.parseInt(id));
			if(date.after(exam.getStartDate()) && date.before(exam.getEndDate())){
				throw new RuntimeException("您删除的考试正在进行中...请尝试其他操作!");
			}
			ormDao.delete(Integer.parseInt(id));
			scoreService.deleteByExamId(Integer.parseInt(id));
		}
	}
	
	public Exam get(int id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page,Exam exam){
		return ormDao.select(page,exam);
	}

}
