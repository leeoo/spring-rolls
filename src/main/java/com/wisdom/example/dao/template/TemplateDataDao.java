package com.wisdom.example.dao.template;
/**
 * 功能描述：'填充模板用数据'信息数据操作接口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午01:45:33</b>
 * <br>文件结构：<b>spring:com.wisdom.example.dao.template/TemplateDataDao.java</b>
 */
public interface TemplateDataDao {
	public final String SQL_INSERT_DATA="INSERT INTO t_template_data(data_name,sql_value,is_unique_result,description,template_id) VALUES(:dataName,:sqlValue,:isUniqueResult,:description,:templateId)";
	public final String SQL_UPDATE_DATA="UPDATE t_template_data SET data_name=:dataName,sql_value=:sqlValue,is_unique_result=:isUniqueResult,description=:description WHERE id=:id";
	public final String SQL_DELETE_DATA="DELETE FROM t_template_data WHERE id=?";
	public final String SQL_SELECT_DATA_WHERE_TEMPLATEID="SELECT id,data_name,sql_value,is_unique_result,description,template_id FROM t_template_data WHERE template_id=?";
	public final String SQL_SELECT_DATA_WHERE_ID="SELECT id,data_name,sql_value,is_unique_result,description,template_id FROM t_template_data WHERE id=?";
	
}
