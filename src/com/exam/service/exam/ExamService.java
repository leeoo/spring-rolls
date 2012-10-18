package com.exam.service.exam;

import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Exam;
import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.ExamingCache;

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
	
	public boolean isQuestionExist(int questionId){
		Object obj = ormDao.getGenericJdbcDao().queryUniqueColumnValueByArray("SELECT COUNT(1) FROM t_exam WHERE question_id=?", questionId);
		if(obj!=null){
			int count = Integer.parseInt(obj.toString());
			if(count>0){
				return true;
			}
		}
		return false;
	}
	
	public void updateWillExam(int id) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_exam SET status=0 WHERE id=?",id);
	}
	
	public void updateExamed(int id) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_exam SET status=-1 WHERE id=?",id);
	}
	
	/**
	 * 强制结束考试
	 * @param ids
	 * @throws Exception
	 */
	public void stop(String... ids) throws Exception{
		Date date = DateUtils.getCurrentDateTime();
		Exam exam = null;
		for(String id : ids){
			ExamSession session = ExamingCache.getExamSession(Integer.parseInt(id));
			if(session!=null){
				session.setEndDate(date);
				ExamingCache.addExamSession(session);//将缓存会话修改
				exam = get(Integer.parseInt(id));
				if(exam!=null){
					exam.setEndDate(date);
					exam.setStatus(-1);
					update(exam);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Exam> getExamings(){
		return (Collection<Exam>) ormDao.getGenericJdbcDao().query("SELECT * FROM t_exam WHERE status!=-1",Exam.class);
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
	
	public Page getAllExamedByStatus(Page page,int status){
		return ormDao.getGenericJdbcDao().queryPageBeansByArray("SELECT * FROM t_exam WHERE status=? ORDER BY end_date DESC", Exam.class, page, status);
	}
	
	public Page getAll(Page page,Exam exam){
		return ormDao.select(page,exam);
	}

}
