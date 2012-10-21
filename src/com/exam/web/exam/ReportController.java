package com.exam.web.exam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.paramecium.commons.EncodeUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.commons.ZipUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.commons.ExcelUtils;
import com.exam.service.exam.ExamService;
import com.exam.service.exam.ScoreService;
import com.exam.web.BaseController;

@Security
@ShowLabel("成绩排名统计")
@Controller("/exam/report")
public class ReportController extends BaseController {
	private final static Log logger = LoggerFactory.getLogger();

	@AutoInject
	private ExamService examService;

	@AutoInject
	private ScoreService scoreService;

	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv) {
		mv.forward(getExamPage("/report/list.jsp"));
	}

	@ShowLabel("成绩排名")
	@MappingMethod
	public ModelAndView score(ModelAndView mv) {
		Integer id = mv.getValue("id", Integer.class);
		if (id != null) {
			mv.addValue("scores", scoreService.getMapScoreByExamId(id));
			mv.addValue("id", id);
		}
		return mv.forward(getExamPage("/report/score.jsp"));
	}

	@ShowLabel("导出报表")
	@MappingMethod
	public ModelAndView export(ModelAndView mv) {
		Integer id = mv.getValue("id", Integer.class);
		if (id != null) {
			List<Map<String, Object>> values = (List<Map<String, Object>>) scoreService.getMapScoreByExamId(id);
			if(values==null||values.isEmpty()){
				mv.setErrorMessage("该考试没有成绩信息！");
				return score(mv);
			}
			Map<String, String> titles = new HashMap<String, String>();
			titles.put("score", "成绩");
			titles.put("long_time", "耗时(秒)");
			titles.put("code", "考号");
			titles.put("username", "姓名");
			String tempPath = PathUtils.getWebRootPath() + "//upload//temp";
			Collection<String> zipFileNames = new ArrayList<String>();//准备要压缩的文件名列表
			String xlsFileName = ExcelUtils.writeExcelMap(titles, values,tempPath, true);//生成excel
			zipFileNames.add(xlsFileName);//先放入xls文件
			for(Map<String, Object> map : values){
				String fileName = tempPath+"//"+(String)map.get("code")+".txt";//生成以code为文件名的考试详情
				String context = (String)map.get("context");
				File file = new File(fileName);
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.error(e);
				}
				FileWriter fw = null;
				try{
					fw = new FileWriter(file);
					fw.write(context);//写入文本
				}catch (Exception e) {
					logger.error(e);
				}
				try{
					if(fw!=null){
						fw.flush();
					}
				}catch (Exception e) {
					logger.error(e);
				}
				try{
					if(fw!=null){
						fw.close();
					}
				}catch (Exception e) {
					logger.error(e);
				}
				zipFileNames.add(fileName);//再放入压缩列表
			}
			String zipFileName = tempPath + "//" +EncodeUtils.millisTime() +".zip";
			ZipUtils.zip(zipFileName, zipFileNames.toArray(new String[zipFileNames.size()]));
			File file = new File(zipFileName);
			FileInputStream fis = null;
			ByteArrayOutputStream bos = null;
			byte[] buffer = null;
			try {
				fis = new FileInputStream(file);
				bos = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while ((n = fis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
				buffer = bos.toByteArray();
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.flush();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			file.delete();//先删除zip
			for(String fileName : zipFileNames){
				file = new File(fileName);
				file.delete();//再删除压缩之前的文件列表
			}
			return mv.download(buffer, "top-"+EncodeUtils.millisTime()+".zip");
		}
		mv.setErrorMessage("该考试没有成绩信息！");
		return score(mv);
	}

	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv) {
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = examService.getAllExamedByStatus(page, -1);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}

}
