package com.wisdom.example.web.example.report;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能描述：报表
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-4</b>
 * <br>创建时间：<b>下午02:34:14</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.report/ReportController.java</b>
 */
@Controller
@RequestMapping("/example/report")
public class ReportController {
	
	@RequestMapping("/print/{name}")
	public void print(HttpServletResponse response,@PathVariable String name) throws Exception{
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader( "Content-Disposition", "attachment;filename="+name.concat(".xml"));
		OutputStream outStream = response.getOutputStream();
		String xml=getPieXml();
		outStream.write(xml.getBytes("GBK"));
		outStream.flush();
	    outStream.close();
	}
	
	@RequestMapping("/pie")
	public String pie(HttpServletRequest request) throws Exception{
		request.setAttribute("report", "pie");
		return "/example/report/index";
	}

	@RequestMapping("/series")
	public String series(HttpServletRequest request) throws Exception{
		request.setAttribute("report", "series");
		return "/example/report/index";
	}

	@RequestMapping("/line")
	public String line(HttpServletRequest request) throws Exception{
		request.setAttribute("report", "line");
		return "/example/report/index";
	}
	
	private static String getPieXml(){
		String xml="<chart baseFont='sans-serif' baseFontSize='14' palette='4' decimals='0' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70' >";
		xml=xml.concat("<set label='曹阳' value='8.5' isSliced='1'/>");
		xml=xml.concat("<set label='解蕾' value='10.5'/>");
		xml=xml.concat("<set label='赵磊' value='5'/>");
		xml=xml.concat("<set label='张迪' value='7'/>");
		xml=xml.concat("<set label='窦志' value='13.5'/>");
		xml=xml.concat("<set label='陈涛' value='1.5' isSliced='1'/>");
		xml=xml.concat("<set label='宋柏林' value='3.5'/>");
		xml=xml.concat("</chart>");
		return xml;
	}
	
}
