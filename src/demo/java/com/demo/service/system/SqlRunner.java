package com.demo.service.system;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.GenericJdbcDao;
import org.paramecium.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * sql语句运行，由控制台管理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-8上午11:21:26
 * <br>项 目 信 息:paramecium:com.demo.service.system.SqlRunner.java
 */
@Service
@Transactional
public class SqlRunner {
	
	private GenericJdbcDao genericJdbcDao = new GenericJdbcDao();
	
	public String select(String sql){
		Collection<Map<String,Object>> rs = genericJdbcDao.query(sql);
		if(rs==null||rs.isEmpty()){
			return sql.concat("<br>0条返回结果!");
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("<table>");
			Map<String,Object> r = rs.iterator().next();
			sb.append("<tr>");
			for(String key : r.keySet()){
				sb.append("<th>").append(key).append("</th>");
			}
			sb.append("</tr>");
			for(Map<String,Object> r2:rs){
				sb.append("<tr>");
				for(Object value : r2.values()){
					sb.append("<td>").append(value).append("</td>");
				}
				sb.append("</tr>");
			}
			sb.append("</table><br>");
			return sb.append(sql.concat("<br>"+rs.size()+"条返回结果!")).toString();
		}
	}
	
	public String executeDML(String sql){
		int i = 0 ;
		try {
			i = genericJdbcDao.executeDML(sql);
		} catch (SQLException e) {
			i = 0;
		}
		return sql.concat("<br>"+i+"条受影响!");
	}

	public String executeDDL(String sql){
		boolean y = false;
		try {
			y = genericJdbcDao.executeDDL(sql);
		} catch (SQLException e) {
			y = false;
		}
		String r = y?"成功!":"失败!";
		return sql.concat("<br>执行"+r);
	}

}
