package com.exam.service.exam;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.security.UserDetails;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.Examinee;
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
		Score validation = get(score.getExamId(),score.getExamineeId());
		if(validation==null){
			ormDao.insert(score);
			return;
		}
		throw new RuntimeException("您已经参加过相同的考试，不能再次提交!");
	}
	
	public void update(Score score) throws Exception{
		ormDao.update(score);
	}
	
	public void deleteByExamineeId(int examineeId) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_score WHERE examinee_id=?",examineeId);
	}
	
	public void deleteByExamId(int examId) throws Exception{
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_score WHERE exam_id=?",examId);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public Score get(int examId,int examineeId){
		return (Score) ormDao.getGenericJdbcDao().queryUniqueByArray("SELECT * FROM t_score WHERE exam_id=? AND examinee_id=?", Score.class, examId,examineeId);
	}
	
	public Score get(int id){
		return ormDao.select(id);
	}
	
	public Page getAllByCurrentExaminee(Page page){
		@SuppressWarnings("unchecked")
		org.paramecium.security.UserDetails<Examinee> user = (UserDetails<Examinee>) SecurityUitls.getLoginUser();
		if(user==null){
			return page;
		}
		Examinee examinee = user.getOtherInfo();
		if(examinee==null){
			return page;
		}
		return ormDao.getGenericJdbcDao().queryPageBeansByArray("SELECT id,start_date,long_time,score,examinee_id,exam_id FROM t_score WHERE examinee_id=? ORDER BY start_date DESC", Score.class, page, examinee.getId());
	}
	
	public Page getMapScoreByExamineeId(Page page,int examineeId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("e.title title,");
		sql.append("e.score full_score,");
		sql.append("e.long_time exam_long_time,");
		sql.append("s.start_date start_date,");
		sql.append("s.long_time long_time,");
		sql.append("s.score score,");
		sql.append("s.id id");
		sql.append(" FROM t_score s LEFT JOIN t_exam e ON s.exam_id = e.id WHERE s.examinee_id = ?");
		return ormDao.getGenericJdbcDao().queryPageMapsByArray(sql.toString(), page, examineeId);
	}
	
	public Page getMapScoreByExamId(Page page,int examId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("e.code code,");
		sql.append("e.username username,");
		sql.append("s.start_date start_date,");
		sql.append("s.long_time long_time,");
		sql.append("s.score score,");
		sql.append("s.id id");
		sql.append(" FROM t_score s LEFT JOIN t_examinee e ON s.examinee_id = e.id WHERE s.exam_id = ? ORDER BY s.score DESC,s.long_time ASC");
		return ormDao.getGenericJdbcDao().queryPageMapsByArray(sql.toString(), page,examId);
	}
	
	public Page getAll(Page page){
		return ormDao.select(page);
	}
	
}
