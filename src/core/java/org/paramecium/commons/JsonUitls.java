package org.paramecium.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * Json转换工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-5-2上午10:50:44</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.commons.JsonUitls.java</b>
 */
public abstract class JsonUitls {

	private final static Log logger = LoggerFactory.getLogger();
	
	public static String getBeansJson(Collection<?> beans,boolean startAndEndMaker){
		return getBeansJson(beans, startAndEndMaker,null);
	}
	
	/**
	 * 将html转译为文本内容
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		if(inputString==null||inputString.isEmpty()){
			return null;
		}
		return inputString.replaceAll("\r\n", "").replaceAll("<[.[^<]]*>","").replaceAll("	", ""); 
	}
	
	/**
	 * bean集合变为json字符串
	 * @param beans
	 * @return
	 */
	public static String getBeansJson(Collection<?> beans,boolean startAndEndMaker,DateFormat format){
		if(beans==null||beans.isEmpty()){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if(startAndEndMaker){
			sb.append('{');
		}
		for(Object bean : beans){
			sb.append('{');
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					field.setAccessible(true);
					try {
						sb.append("\"").append(field.getName()).append("\":");
						if(field.get(bean)==null){
							sb.append("\"\",");
						}else{
							if(format!=null&&field.getType()==Date.class){
								sb.append("\"").append(DateUtils.parse(format,(Date)field.get(bean))).append("\",");
							}else{
								sb.append("\"").append(field.get(bean)).append("\",");
							}
						}
					} catch (Exception e) {
						logger.warn(e);
					}
				}
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append('}').append(',');
		}
		sb.delete(sb.length()-1, sb.length());
		if(startAndEndMaker){
			sb.append('}');
		}
		return sb.toString();
	}
	
	public static String getMapsJson(Collection<Map<String,Object>> maps,boolean startAndEndMaker){
		return getMapsJson(maps,startAndEndMaker,null);
	}
	
	/**
	 * map集合转换为json字符串
	 * @param maps
	 * @return
	 */
	public static String getMapsJson(Collection<Map<String,Object>> maps,boolean startAndEndMaker,DateFormat format){
		if(maps==null||maps.isEmpty()){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if(startAndEndMaker){
			sb.append('{');
		}
		for(Map<String,Object> map : maps){
			sb.append('{');
			for(Entry<String, Object> entry : map.entrySet()){
				sb.append("\"").append(entry.getKey()).append("\":");
				if(entry.getValue()==null){
					sb.append("\"\",");
				}else{
					if(format!=null||entry.getValue().getClass()==Date.class){
						sb.append("\"").append(DateUtils.parse(format,(Date)entry.getValue())).append("\",");
					}else{
						sb.append("\"").append(entry.getValue()).append("\",");
					}
				}
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append('}').append(',');
		}
		sb.delete(sb.length()-1, sb.length());
		if(startAndEndMaker){
			sb.append('}');
		}
		return sb.toString();
	}
	
}
