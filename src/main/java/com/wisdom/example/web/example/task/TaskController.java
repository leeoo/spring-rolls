package com.wisdom.example.web.example.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisdom.core.task.TaskRunnable;
import com.wisdom.core.task.TaskScheduledManualExecutor;
import com.wisdom.core.task.domain.RunHistory;
import com.wisdom.core.task.domain.Task;
import com.wisdom.core.task.service.TaskScheduledCache;
import com.wisdom.core.task.service.TaskService;
import com.wisdom.core.utils.FormatConstants;
import com.wisdom.core.utils.Page;
import com.wisdom.core.utils.ScheduledThreadUtils;
import com.wisdom.example.commons.ExcelUtils;
import com.wisdom.example.commons.ZipUtils;
/**
 * 功能描述：任务调度web控制器
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午05:02:55</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.task/TaskController.java</b>
 */
@Controller
@RequestMapping("/example/task")
public class TaskController {
	@Resource
	private TaskService taskService;
	@Resource
	private TaskScheduledManualExecutor taskScheduledManualExecutor;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request){
		List<Task> tasks=taskService.getAllTask();
		request.setAttribute("tasks", tasks);
		return "/example/task/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request){
		if(id!=null){
			Task task=taskService.getTaskById(id);
			request.setAttribute("task", task);
		}
		Map<Integer, String> units=new LinkedHashMap<Integer, String>();
		units.put(TaskRunnable.RUNUNIT_HOUR, "每小时运行");
		units.put(TaskRunnable.RUNUNIT_DAY, "每天运行");
		units.put(TaskRunnable.RUNUNIT_WEEK, "每周运行");
		units.put(TaskRunnable.RUNUNIT_MONTH, "每月运行");
		units.put(TaskRunnable.RUNUNIT_YEAR, "每年运行");
		request.setAttribute("units", units);
		return "/example/task/input";
	}
	
	@RequestMapping("/save")
	public String save(Task task,HttpServletRequest request)throws Exception{
		if(task.getId()!=null){
			taskService.updateTask(task);
		}else{
			task.setEnabled(TaskRunnable.STATE_ENABLED);
			taskService.saveTask(task);
		}
		return "redirect:/example/task/list.htm";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder)throws ServletException {
		DateFormat dateFormat = FormatConstants.DATE_TIME_FORMAT;
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id)throws Exception{
		taskService.deleteTask(id);
		return "redirect:/example/task/list.htm";
	}
	
	@RequestMapping("/enabled/{id}")
	public String enabled(@PathVariable Long id)throws Exception{
		Task task=new Task();
		task.setId(id);
		task.setEnabled(TaskRunnable.STATE_ENABLED);
		taskService.updateTaskEnabled(task);
		return "redirect:/example/task/list.htm";
	}

	@RequestMapping("/disabled/{id}")
	public String disabled(@PathVariable Long id)throws Exception{
		Task task=new Task();
		task.setId(id);
		task.setEnabled(TaskRunnable.STATE_DISABLED);
		taskService.updateTaskEnabled(task);
		return "redirect:/example/task/list.htm";
	}
	
	@RequestMapping("/run/{id}")
	public String run(@PathVariable Long id)throws Exception{
		Task task=TaskScheduledCache.getCache(id);
		taskScheduledManualExecutor.setTask(task);
		ScheduledThreadUtils.start(taskScheduledManualExecutor);
		return "redirect:/example/task/list.htm";
	}
	
	@RequestMapping("{taskId}/run_history/list/{no}")
	public String runHistoryList(@PathVariable Long taskId,@PathVariable int no,HttpServletRequest request,Page page)throws Exception{
		if(taskId!=null){
			page.setPageSize(5);
			page.setPageNo(no);
			page=taskService.getAllRunHistoryByTaskId(page, taskId);
			Task task=TaskScheduledCache.getCache(taskId);
			request.setAttribute("task", task);
			request.setAttribute("page", page);
		}
		return "/example/task/history/list";
	}
	
	@RequestMapping("{taskId}/run_history/download")
	public void runHistoryDownload(@PathVariable Long taskId,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(taskId!=null){
			List<RunHistory> historys=taskService.getAllRunHistoryByTaskId(taskId);
			String savePath=request.getSession().getServletContext().getRealPath("").concat("//demo");
			Map<String,String> titles=new LinkedHashMap<String, String>();
			titles.put("runStateName", "状态");
			titles.put("runTypeName", "方式");
			titles.put("runTime", "运行时间");
			titles.put("endTime", "结束时间");
			titles.put("errorInfo", "信息");
			String[] mergeFields={"runStateName","runTypeName"};
			String[] fileNames=ExcelUtils.writeExcel(titles, historys, savePath, true, 2000, mergeFields);
			String fileName=ZipUtils.createZip(fileNames, savePath, "report", true);
			File file=new File(fileName);
			OutputStream outStream = response.getOutputStream();
			InputStream is=null;
			try{
				is=new BufferedInputStream(new FileInputStream(file));   
				byte[] buffer = new byte[1024];   
		        int len = 0;   
		        while ((len = is.read(buffer)) > 0){   
		        	outStream.write(buffer , 0 , len);  
		        }
		    }catch (Exception e) {}
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", "attachment;filename=report.zip");
			is.close();
			outStream.flush();
		    outStream.close();
		    file.delete();
		}
	}
	
	@RequestMapping("{taskId}/run_history/delete/{no}-{id}")
	public String delete(@PathVariable Long taskId,@PathVariable int no,@PathVariable Long id)throws Exception{
		taskService.deleteRunHistory(id);
		return "redirect:/example/task/"+taskId+"/run_history/list/"+no+".htm";
	}
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public TaskScheduledManualExecutor getTaskScheduledManualExecutor() {
		return taskScheduledManualExecutor;
	}

	public void setTaskScheduledManualExecutor(
			TaskScheduledManualExecutor taskScheduledManualExecutor) {
		this.taskScheduledManualExecutor = taskScheduledManualExecutor;
	}
	
}
