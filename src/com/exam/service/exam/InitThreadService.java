package com.exam.service.exam;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.EncodeUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

import com.exam.entity.exam.Exam;
import com.exam.entity.exam.ExamSession;
import com.exam.entity.exam.ExamineeSession;
import com.exam.entity.exam.ExamingCache;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.QuestionChoice;
import com.exam.entity.exam.Score;

/**
 * 系统中的线程进入准备状态
 * @author caoyang
 */
@Service
public class InitThreadService {
	
	private final static Log logger = LoggerFactory.getLogger();
	private static int initCount = 0;
	@AutoInject
	private ExamineeService examineeService;
	@AutoInject
	private ExamService examService;
	@AutoInject
	private QuestionChoiceService questionChoiceService;
	@AutoInject
	private QuestionService questionService;
	@AutoInject
	private ScoreService scoreService;
	
	
	public void init(){
		logger.debug("Exam业务线程正在启动...");
		if(initCount==0){
			initCount++;
			clearUploadTempFile();
			clearExaminee();
			updateExamStatus();
			manageExam();
		}else{
			logger.warn("该实例已经执行了"+initCount+"次初始化,不能再次执行初始化任务!");
		}
	}
	
	/**
	 * 定时清理上传临时文件，每隔10小时执行即可
	 */
	private void clearUploadTempFile(){
		Thread thread = new Thread(new ClearUploadTempFileThread());
		thread.start();
	}
	
	/**
	 * 定时清理上传临时文件线程
	 * @author caoyang
	 *
	 */
	private class ClearUploadTempFileThread implements Runnable{
		private File tempPath = new File(PathUtils.getWebRootPath() + "//upload//temp");
		public void run() {
			logger.debug("<上传文件临时目录清理线程>已经启动...");
			while(true){
				try{
					if(tempPath.isDirectory()&&tempPath.exists()){
						if(tempPath.listFiles()==null||tempPath.listFiles().length<1){
							logger.debug(tempPath.getPath()+"目录下没有文件存在，无需清理!");
						}else{
							for(File file : tempPath.listFiles()){
								if(file.delete()){
									logger.debug(file.getPath()+"删除成功!");
								}else{
									logger.warn(file.getPath()+"删除失败，可能由于系统权限限制或指针被引用!");
								}
							}
						}
					}else{
						if(tempPath.mkdirs()){
							logger.debug(tempPath.getPath()+"创建成功!");
						}else{
							logger.warn(tempPath.getPath()+"创建失败，可能由于系统权限限制或指针被引用!");
						}
					}
					logger.debug("<上传文件临时目录清理线程>已经执行完毕，10小时之后再次运行!");
					Thread.sleep(1000*60*60*10);
				}catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}
	
	/**
	 * 定时清理过期的考生信息及成绩记录,每隔10小时执行即可
	 */
	private void clearExaminee(){
		Thread thread = new Thread(new ClearExamineeThread());
		thread.start();
	}
	
	/**
	 * 定时清理过期考生信息
	 * @author caoyang
	 *
	 */
	private class ClearExamineeThread implements Runnable{
		public void run() {
			logger.debug("<清理过期考生线程>已经启动...");
			while(true){
				try{
					examineeService.delete();
					logger.debug("<清理过期考生线程>已经执行完毕，10小时之后再次运行!");
					Thread.sleep(1000*60*60*10);
				}catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}
	
	/**
	 * 定时修改考试状态,每100秒执行一次即可，并且为考试状态为正在进行中的，开辟对应的缓存，如果该缓存存在则不做处理。
	 * 同时当状态为已结束时，将该考试对应的缓存清理（如果该考试缓存中存有考生的缓存，自动为该学生提交成绩，然后清除缓存）
	 */
	private void updateExamStatus(){
		Thread thread = new Thread(new UpdateExamStatusThread());
		thread.start();
	}
	
	private class UpdateExamStatusThread implements Runnable{
		
		public void run() {
			logger.debug("<考试监控线程>已经启动...");
			while(true){
				try{
					updateCache();
					updateDB();
					logger.debug("<考试监控线程>已经执行完毕，100秒钟之后再次运行!");
					Thread.sleep(1000*100);
				}catch (Exception e) {
					logger.error(e);
				}
			}
		}
		
		/**
		 * 先更新考试缓存中的正在进行的考试信息
		 */
		private void updateCache(){
			Collection<ExamSession> examSessions = ExamingCache.getExamSessions();
			if(examSessions!=null && !examSessions.isEmpty()){
				Date date = DateUtils.getCurrentDateTime();
				for(ExamSession examSession:examSessions){
					if(examSession.getEndDate().before(date)){//如果该考试已经过期
						Collection<ExamineeSession> examineeSessions = examSession.getExamineeSessions();//获取没有提交的用户
						if(examineeSessions!=null && !examineeSessions.isEmpty()){//如果让然有考生没有提交，视为自动提交
							if(!examSession.isChoice()){//如果是速录考试
								ScoreEvaluate scoreEvaluate = new ScoreEvaluate(
										examSession.getTextContent(), examSession.getScore(), 
										examSession.getCnProportion(), examSession.getEnProportion(),
										examSession.getPunProportion(), examSession.getNumProportion());
								for(ExamineeSession examineeSession : examineeSessions){
									Score score = new Score();
									score.setContext(examineeSession.getTempContent());
									score.setExamId(examSession.getId());
									score.setExamineeId(examineeSession.getId());
									score.setLongTime(examineeSession.getLongTime());
									score.setStartDate(new Date(examineeSession.getExamDate()*1000));//进入考试的时间
									int finalScore = scoreEvaluate.getScore(examineeSession.getTempContent());//通过算法获得分数
									score.setScore(finalScore);
									try {
										scoreService.save(score);
									} catch (Exception e) {
										logger.error(e);
									}
								}
							}else{//如果是选择题
								ChoiceScoreEvaluate choiceScoreEvaluate = new ChoiceScoreEvaluate(examSession.getQuestionChoices(), examSession.getScore());
								for(ExamineeSession examineeSession : examineeSessions){
									Score score = new Score();
									String context = ChoiceScoreEvaluate.buildChoiceContext(examineeSession.getChoices());//将选择题变成文本
									score.setContext(context);
									score.setExamId(examSession.getId());
									score.setExamineeId(examineeSession.getId());
									score.setLongTime(examineeSession.getLongTime());
									score.setStartDate(new Date(examineeSession.getExamDate()*1000));//进入考试的时间
									int finalScore = choiceScoreEvaluate.getScore(examineeSession.getChoices());//通过算法获得分数
									score.setScore(finalScore);
									try {
										scoreService.save(score);
									} catch (Exception e) {
										logger.error(e);
									}
								}
							}
						}
						examSession.release();//释放资源
						ExamingCache.removeExamSession(examSession.getId());//移除缓存
						try {
							examService.updateExamed(examSession.getId());//修改考试的状态
						} catch (Exception e) {
							logger.error(e);
						}
					}
				}
			}
		}
		
		/**
		 * 在更新数据库中已经可以考试的信息，并入缓存
		 */
		private void updateDB(){
			Collection<Exam> examings = examService.getExamings();
			if(examings!=null && !examings.isEmpty()){
				Date date = DateUtils.getCurrentDateTime();
				for(Exam examing : examings){
					if(!ExamingCache.isExist(examing.getId())){//如果没有进入缓存...
						if(date.after(examing.getStartDate()) && date.before(examing.getEndDate())){//如果已经考试
							if(examing.getChoice()!=null){//判断是否是选择题，如果null，无效，不管，否则构建缓存，更新状态为1
								ExamSession examSession = buildExamSession(examing);//构建考试会话
								ExamingCache.addExamSession(examSession);//放入缓存
								try {
									examService.updateExaming(examing.getId());//修改它的状态为1
								} catch (Exception e) {
									logger.error(e);
								}
							}
						}else if(date.after(examing.getEndDate())&&examing.getStatus()!=-1){//如果有没有在缓存，而且还没有过期处理的
							try {
								examService.updateExamed(examing.getId());//修改考试的状态为-1过期
							} catch (Exception e) {
								logger.error(e);
							}
						}else if(date.before(examing.getStartDate())&&examing.getStatus()!=0){//最后判断一下,除此时间段之外之外，未开始的考试，状态不是0的，全部置为0
							try {
								examService.updateWillExam(examing.getId());//修改考试的状态为0
							} catch (Exception e) {
								logger.error(e);
							}
						}
					}
				}
			}
		}
		
		private ExamSession buildExamSession(Exam examing){
			ExamSession examSession = new ExamSession();
			examSession.setId(examing.getId());
			examSession.setTitle(examing.getTitle());
			examSession.setChoice(examing.getChoice());
			examSession.setScore(examing.getScore());
			examSession.setCnProportion(examing.getCnProportion());
			examSession.setEnProportion(examing.getEnProportion());
			examSession.setPunProportion(examing.getPunProportion());
			examSession.setNumProportion(examing.getNumProportion());
			examSession.setStartDate(examing.getStartDate());
			examSession.setEndDate(examing.getEndDate());
			examSession.setLongTime(examing.getLongTime());
			if(examing.getChoice()){
				Collection<QuestionChoice> choices = questionChoiceService.getAllByQuestionId(examing.getQuestionId());
				if(choices!=null && !choices.isEmpty()){
					for(QuestionChoice choice : choices){
						examSession.addQuestionChoice(choice);
					}
				}
			}else{
				Question question = questionService.get(examing.getQuestionId());
				if(question.getAudioPath()!=null && !question.getAudioPath().trim().isEmpty()){
					examSession.setAudio(true);
					examSession.setAudioPath(question.getAudioPath());
				}else{
					examSession.setAudio(false);
				}
				examSession.setTextContent(question.getTextContent());
			}
			return examSession;
		}
		
	}
	
	/**
	 * 定时管理考试情况，每10秒执行一次,如某考生考试时长，超过时长自动交卷，交卷后将该考生从考试缓存删除，然后提交到成绩表（该线程属于补漏，如果前台脚本失效，可以有处理）
	 */
	private void manageExam(){
		Thread thread = new Thread(new ManageExamThread());
		thread.start();
	}
	
	private class ManageExamThread implements Runnable{
		
		public void run() {
			logger.debug("<考生状态监控线程>已经启动...");
			while(true){
				try{
					updateCache();
					logger.debug("<考生状态监控线程>已经执行完毕，10秒钟之后再次运行!");
					Thread.sleep(1000*10);
				}catch (Exception e) {
					logger.error(e);
				}
			}
		}
		
		private void updateCache(){
			Collection<ExamSession> examSessions = ExamingCache.getExamSessions();
			if(examSessions!=null && !examSessions.isEmpty()){
				for(ExamSession examSession:examSessions){
					Collection<ExamineeSession> examineeSessions = examSession.getExamineeSessions();//获取没有提交的用户
					if(examineeSessions!=null && !examineeSessions.isEmpty()){//如果让然有考生没有提交，视为自动提交
						for(ExamineeSession examineeSession : examineeSessions){
							int examineeLongtime = (int)(EncodeUtils.millisTime()/1000-examineeSession.getExamDate());//当前累计秒-考试开始秒=该生实际时长
							if(examSession.getLongTime()*60<examineeLongtime){//判断是否超过规定的考试时长
								if(!examSession.isChoice()){//如果是速录考试
									ScoreEvaluate scoreEvaluate = new ScoreEvaluate(
											examSession.getTextContent(), examSession.getScore(), 
											examSession.getCnProportion(), examSession.getEnProportion(),
											examSession.getPunProportion(), examSession.getNumProportion());
									Score score = new Score();
									score.setContext(examineeSession.getTempContent());
									score.setExamId(examSession.getId());
									score.setExamineeId(examineeSession.getId());
									score.setLongTime(examSession.getLongTime()*60);
									score.setStartDate(new Date(examineeSession.getExamDate()*1000));//进入考试的时间
									int finalScore = scoreEvaluate.getScore(examineeSession.getTempContent());//通过算法获得分数
									score.setScore(finalScore);
									try {
										scoreService.save(score);
										examSession.removeExamineeSession(examineeSession.getId());
									} catch (Exception e) {
										logger.error(e);
										logger.error("<考试异常暂存日志>考号:"+examineeSession.getCode()+" 耗时:"+score.getLongTime()+"秒 得分:"+score.getScore()+"内容:"+score.getContext());
										logger.error(e);
									}
								}else{//如果是选择题
									ChoiceScoreEvaluate choiceScoreEvaluate = new ChoiceScoreEvaluate(examSession.getQuestionChoices(), examSession.getScore());
									Score score = new Score();
									String context = ChoiceScoreEvaluate.buildChoiceContext(examineeSession.getChoices());//将选择题变成文本
									score.setContext(context);
									score.setExamId(examSession.getId());
									score.setExamineeId(examineeSession.getId());
									score.setLongTime(examSession.getLongTime()*60);
									score.setStartDate(new Date(examineeSession.getExamDate()*1000));//进入考试的时间
									int finalScore = choiceScoreEvaluate.getScore(examineeSession.getChoices());//通过算法获得分数
									score.setScore(finalScore);
									try {
										scoreService.save(score);
										examSession.removeExamineeSession(examineeSession.getId());
									} catch (Exception e) {
										logger.error(e);
										logger.error("<考试异常暂存日志>考号:"+examineeSession.getCode()+" 耗时:"+score.getLongTime()+"秒 得分:"+score.getScore()+"内容:"+score.getContext());
										logger.error(e);
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
}
