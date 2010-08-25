package com.wisdom.core.dao;
/**
 * 
 * <b>业务说明</b>:<br>
 * bean属性映射数据库字段策略
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-3-13<br>
 * <b>建立时间</b>: 上午03:38:36<br>
 * <b>项目名称</b>: easyapp<br>
 * <b>包及类名</b>: com.wisdom.core.dao.jdbcBean2MapStrategy.java<br>
 */
public interface Bean2MapStrategy {
	
	/**
	 * bean属性不变，直接映射数据库字段名称，一般为sqlserver等数据库。如bean:userName mapping table:userName
	 */
	public final static int PROPERTY_Unchanged=-1;
	
	/**
	 * bean属性转为小写，一般为mysql数据库。如bean:userName mapping table:username
	 */
	public final static int PROPERTY_TO_LOWERCASE=1;
	
	/**
	 * bean属性转为小写，遇到大写字母加入下划线。如bean:userName mapping table:user_name
	 */
	public final static int PROPERTY_TO_LOWERCASE_Underline=3;
	
	/**
	 * bean属性转为大写，一般为oracle、db2等数据库。如bean:userName mapping table:USERNAME
	 */
	public final static int PROPERTY_TO_UPPERCASE=2;
	
	/**
	 *  bean属性转为大写,遇到大写字母加入下划线。如bean:userName mapping table:USER_NAME
	 */
	public final static int PROPERTY_TO_UPPERCASE_Underline=4;
	
	
	
	
}
