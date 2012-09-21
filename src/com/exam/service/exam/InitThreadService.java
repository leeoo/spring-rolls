package com.exam.service.exam;

import java.io.File;

import org.paramecium.commons.PathUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

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
	
	public void init(){
		logger.debug("Exam业务线程正在启动...");
		if(initCount==0){
			initCount++;
			clearUploadTempFile();
			clearExaminee();
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
		
	}
	
	/**
	 * 定时管理考试情况，每10秒执行一次,如某考生考试时长，超过时长自动交卷，交卷后将该考生从考试缓存删除，然后提交到成绩表（该线程属于补漏，如果前台脚本失效，可以有处理）
	 */
	private void manageExam(){
		
	}
	
	/**
	 * 定时针对在线考试的考生，每10秒执行一次，监考老师可以看到打字情况或选择的情况
	 */
	private void examMonitor(){
		
	}
	
	
}
