package com.exam.commons;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.DateUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 功能描述：<br>
 * 基于JXL的Excel操作，同时兼容Windows之外的操作系统下的OpenOffice组件，性能优于PIO。
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-8-25</b>
 * <br>创建时间：<b>下午09:55:41</b>
 * <br>文件结构：<b>point-system:com.wisdom.psystem.commons/ExcelUtils.java</b>
 */
public class ExcelUtils{
	
	protected static final Log logger = LoggerFactory.getLogger();
	protected static Map<String,int[]> coordinate=null;//合并单元格坐标
	
	/**
	 * 功能描述：
	 * 将数据写入Excel文件，如果生成数据大于给定限制，那么会生成新excel文件。
	 * <br>@param titles：作显示用的标题，应用Map做载体。其中其中value记载标题显示内容；Key记载POJO的属性名，以匹配values集合中POJO的属性值显示顺序。
	 * <br>@param values：数据集合，采用bean作载体，属性名要与标题Map的Key匹配。
	 * <br>@param savePath：保存的路径，可以是src类路径或web层服务器路径。避免使用中文、空格以及全角字符等。
	 * <br>@param isSequence：是否启用序号列，方便统计。
	 * <br>@param maxResults：最大数据结果集，超过这个数将形成新Excel文件。
	 * <br>@param mergeFields：需要合并单元格的列明数组，不需要合并可以设为NULL。
	 * <br>@return excel文件路径及文件名，当数据过大会形成新Excel文件。
	 */
	public static String[] writeExcel(Map<String,String> titles,List<Object> values,String savePath,boolean isSequence,int maxResults,String[] mergeFields){
		int count=values.size();//集合总数
		int multiples=count/maxResults;//拆分倍数，默认加入余数部分
		if(multiples*maxResults!=count){//拆分倍数是否没有余数
			 multiples=count/maxResults+1;//去掉倍数中的余数部分
		}
		String[] fileNames=new String[multiples];
		String fileName=DateUtils.getCurrentDateTimeStr().replace(" ",",").replace(":","'");
		for(int i=0;i<multiples;i++){
			fileNames[i]=getFileName(savePath,fileName,(i+1));
			int startNum=i*maxResults;//list的拆分起始数
			int endNum=(i+1)*maxResults;//list的拆分结束数
			if(i==(multiples-1)){//判断是否是最后一次
				endNum=count;
			}
			creatExcel(titles, values.subList(startNum,endNum), fileNames[i], isSequence,mergeFields);
		}
		return fileNames;
	}
	
	/**
	 * 功能描述：获得
	 * <br>@param savePath
	 * <br>@param i文件序号
	 * <br>@return
	 */
	private static String getFileName(String savePath,String fileName,int i){
		File file=new File(savePath);
		file.mkdir();//建立文件夹
		return savePath+"//"+fileName+"("+i+").xls";
	}
	
	/**
	 * 功能描述：单次创建excel文件
	 * <br>@param titles
	 * <br>@param values
	 * <br>@param fileName
	 * <br>@param isSequence
	 */
	private synchronized static void creatExcel(Map<String,String> titles,List<Object> values ,String fileName,boolean isSequence,String[] mergeFields){
		WritableWorkbook wbook =null;
        WritableSheet wsheet = null;
		try{
			File file=new File(fileName);
			wbook=Workbook.createWorkbook(file); // 建立excel文件
			wsheet=wbook.createSheet("报表", 0); // sheet名称
	        int i=0;
	        addTitles(wsheet,titles.values(),isSequence);//设置标题行
	        Object object=null;
	        if(mergeFields!=null&&mergeFields.length>0){
	        	coordinate=new LinkedHashMap<String,int[]>();
	        }
	        for(Iterator<Object> it=values.iterator();it.hasNext();){
	        	object=it.next();
	        	addContext(wsheet, titles.keySet(), object, ++i,isSequence,mergeFields);//设置内容
	        }
	        if(coordinate!=null&&!coordinate.isEmpty()){
	        	for(int[] cdi:coordinate.values()){
	        		wsheet.mergeCells(cdi[0], cdi[1], cdi[2], cdi[3]);
	        	}
	        }
	        logger.debug("Create Excel SUCCESS:"+fileName);
		}catch (Exception e) {
			logger.error("Create Excel ERROR:"+e.getMessage());
		}
		try{
			coordinate=null;//清空
			wbook.write(); // 写入文件
		}catch (Exception e) {
			logger.error("Write Excel ERROR:"+e.getMessage());
		}
		try{
			wbook.close(); // 关闭文件
		}catch (Exception e) {
			logger.error("Close Excel ERROR:"+e.getMessage());
		}
	}
	
	/**
	 * 功能描述：填充excel内容
	 * <br>@param wsheet
	 * <br>@param keys
	 * <br>@param object
	 * <br>@param count
	 * <br>@param isSequence
	 * <br>@throws Exception
	 */
	private synchronized static void addContext(WritableSheet wsheet,Collection<String> keys,Object object,int count,boolean isSequence,String[] mergeFields)throws Exception{
		int i=0;
		Label label=null;
		if(isSequence){//如果为true，设置第一列为序号
			label=new Label(i++, count, ""+count);
			wsheet.addCell(label);
		}
		Object obj=null;
		for(String fieldName:keys){//循环创建标题
			obj=BeanUtils.getFieldValue(object, object.getClass(), fieldName);
			String value="";
			if(obj!=null){
				value=obj.toString();
			}
			int temp=i++;
			if(mergeFields!=null&&contains(fieldName,mergeFields)){//判断是否需要合并单元格，并判断当前单元格是否在给定的范围中。
				String sign=fieldName+value;//设置字段+值的唯一标识
				int[] ci=new int[4];
				ci[2]=temp;
				ci[3]=count;
				if(coordinate.get(sign)==null){//判断该字段的重复值是否第一次使用
					ci[0]=temp;
					ci[1]=count;
					coordinate.put(sign,ci);
				}else{
					int[] tempCi=coordinate.get(sign);
					ci[0]=tempCi[0];
					ci[1]=tempCi[1];
					coordinate.put(sign,ci);
				}
			}
			label=new Label(temp, count, value);
        	wsheet.addCell(label);
        }
	}
	
	private static boolean contains(String fieldName,String[] mergeFields){
		for(String temp:mergeFields){
			if(fieldName.equals(temp)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 功能描述：设置标题行
	 * <br>@param wsheet
	 * <br>@param values
	 * <br>@throws Exception
	 */
	private synchronized static void addTitles(WritableSheet wsheet,Collection<String> values,boolean isSequence)throws Exception{
		int i=0;
		Label label=null;
		if(isSequence){//如果为true，设置第一列为序号标题
			label=new Label(i++, 0, "序号");
			wsheet.addCell(label);
		}
		for(String titleNames:values){//循环创建标题
			label=new Label(i++, 0, titleNames);
        	wsheet.addCell(label);
        }
	}
	
	/**
	 * 功能描述：
	 * 将数据写入Excel文件。如果生成数据大于给定限制，那么会生成新excel文件。
	 * <br>@param titles：作显示用的标题，应用Map做载体。其中其中value记载标题显示内容；Key记载POJO的属性名，以匹配values集合中POJO的属性值显示顺序。
	 * <br>@param values：数据集合，采用bean作载体，属性名要与标题Map的Key匹配。
	 * <br>@param savePath：保存的路径，可以是src类路径或web层服务器路径。避免使用中文、空格以及全角字符等。
	 * <br>@param maxResults：最大数据结果集，超过这个数将形成新Excel文件。
	 * <br>@return excel文件路径及文件名，当数据过大会形成新Excel文件。
	 */
	public static String[] writeExcel(Map<String,String> titles,List<Object> values,String savePath,int maxResults){
		return writeExcel(titles,values,savePath,false,maxResults,null);
	}
	
	/**
	 * 功能描述：
	 * 将数据写入Excel文件。
	 * <br>@param titles：作显示用的标题，应用Map做载体。其中其中value记载标题显示内容；Key记载POJO的属性名，以匹配values集合中POJO的属性值显示顺序。
	 * <br>@param values：数据集合，采用bean作载体，属性名要与标题Map的Key匹配。
	 * <br>@param savePath：保存的路径，可以是src类路径或web层服务器路径。避免使用中文、空格以及全角字符等。
	 * <br>@param isSequence：是否启用序号列，方便统计。
	 * <br>@return excel文件路径及文件名。
	 */
	public static String writeExcel(Map<String,String> titles,List<Object> values,String savePath,boolean isSequence){
		return writeExcel(titles,values,savePath,isSequence,6000,null)[0];
	}
	
	/**
	 * 功能描述：
	 * 将数据写入Excel文件。
	 * <br>@param titles：作显示用的标题，应用Map做载体。其中其中value记载标题显示内容；Key记载POJO的属性名，以匹配values集合中POJO的属性值显示顺序。
	 * <br>@param values：数据集合，采用bean作载体，属性名要与标题Map的Key匹配。
	 * <br>@param savePath：保存的路径，可以是src类路径或web层服务器路径。避免使用中文、空格以及全角字符等。
	 * <br>@return excel文件路径及文件名。
	 */
	public static String writeExcel(Map<String,String> titles,List<Object> values,String savePath){
		return writeExcel(titles,values,savePath,false,6000,null)[0];
	}
}
