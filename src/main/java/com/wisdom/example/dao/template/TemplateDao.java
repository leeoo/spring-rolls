package com.wisdom.example.dao.template;
/**
 * 功能描述：模板信息数据操作接口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午01:47:22</b>
 * <br>文件结构：<b>spring:com.wisdom.example.dao.template/TemplateDao.java</b>
 */
public interface TemplateDao {
	public final String SQL_INSERT_TEMPLATE = "INSERT INTO t_template_info(name,template_path,html_path,last_date,description)"
			+ "VALUES(:name,:templatePath,:htmlPath,:lastDate,:description)";
	public final String SQL_UPDATE_TEMPLATE = "UPDATE t_template_info SET name=:name,template_path=:templatePath,"
			+ "html_path=:htmlPath,last_date=:lastDate,description=:description WHERE id=:id";
	public final String SQL_DELETE_TEMPLATE = "DELETE FROM t_template_info WHERE id=?";
	public final String SQL_SELECT_ALL_TEMPLATE = "SELECT id,name,template_path,html_path,last_date,description FROM t_template_info ORDER BY last_date DESC";
	public final String SQL_SELECT_WHERE_TEMPLATE = "SELECT id,name,template_path,html_path,last_date,description FROM t_template_info WHERE id=?";
	public final String SQL_DELETE_DATA_WHERE_TEMPLATEID = "DELETE FROM t_template_data WHERE template_id=?";

}
