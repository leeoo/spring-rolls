package com.exam.web.exam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.paramecium.commons.EncodeUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
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
	public void score(ModelAndView mv) {
		Integer id = mv.getValue("id", Integer.class);
		if (id != null) {
			mv.addValue("scores", scoreService.getMapScoreByExamId(id));
			mv.addValue("id", id);
		}
		mv.forward(getExamPage("/report/score.jsp"));
	}

	@ShowLabel("导出报表")
	@MappingMethod
	public void export(ModelAndView mv) {
		Integer id = mv.getValue("id", Integer.class);
		if (id != null) {
			List<Map<String, Object>> values = (List<Map<String, Object>>) scoreService.getMapScoreByExamId(id);
			Map<String, String> titles = new HashMap<String, String>();
			titles.put("score", "成绩");
			titles.put("long_time", "耗时(秒)");
			titles.put("code", "考号");
			titles.put("username", "姓名");
			String fileName = ExcelUtils.writeExcelMap(titles, values,PathUtils.getWebRootPath() + "//upload//temp", true);
			File file = new File(fileName);
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
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
			}
			try {
				if (bos != null) {
					bos.flush();
				}
			} catch (Exception e) {
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
			}
			file.delete();
			mv.download(buffer, "top-"+EncodeUtils.millisTime()+".xls");
		}
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
