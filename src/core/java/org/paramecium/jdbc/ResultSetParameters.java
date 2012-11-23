package org.paramecium.jdbc;

import java.sql.ResultSet;
/**
 *<b>参数 int resultSetType</b> <br>
 * 	ResultSet.TYPE_FORWARD_ONLY 结果集的游标只能向下滚动。 只能使ResultSet 接口的next()方法而不能使用previous()方法否则会产生错误.<br>
 * 	ResultSet.TYPE_SCROLL_INSENSITIVE 结果集的游标可以上下移动，当数据库变化时，当前结果集不变。 其他用户更新了数据库中的数据，但是当前用户所获取的记录集中的数据不会受到任何影响。<br>
 * 	ResultSet.TYPE_SCROLL_SENSITIVE 返回可滚动的结果集，当数据库变化时，当前结果集同步改变。 建议慎重使用该常数<br>
 *<b>参数 int resultSetConcurrency</b> <br>
 * 	ResultSet.CONCUR_READ_ONLY 不能用结果集更新数据库中的表。 <br>
 * 	ResultSet.CONCUR_UPDATABLE 能用结果集更新数据库中的表，用户就可以使用updateXXX()等方法更新记<br>
 *<b>参数int resultSetHoldability</b> <br>
 *  ResultSet.HOLD_CURSORS_OVER_COMMIT 修改提交时ResultSet不关闭。<br>
 *  ResultSet.CLOSE_CURSORS_AT_COMMIT 修改提交时ResultSet关闭。<br>
 * @author caoyang
 *
 */
public class ResultSetParameters {
	
	private int resultSetType = ResultSet.TYPE_FORWARD_ONLY;
	private int resultSetConcurrency = ResultSet.CONCUR_UPDATABLE;
	private int resultSetHoldability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
	
	public ResultSetParameters(){
		this(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	}
	
	public ResultSetParameters(int resultSetType,int resultSetConcurrency){
		this(resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}
	
	public ResultSetParameters(int resultSetType,int resultSetConcurrency,int resultSetHoldability){
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;
		this.resultSetHoldability = resultSetHoldability;
	}

	public int getResultSetType() {
		return this.resultSetType;
	}

	public int getResultSetConcurrency() {
		return this.resultSetConcurrency;
	}

	public int getResultSetHoldability() {
		return this.resultSetHoldability;
	}
	
}
