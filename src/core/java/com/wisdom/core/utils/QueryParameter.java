package com.wisdom.core.utils;


/**
 * 封装分页和排序查询请求参数.
 * 
 * @author caoyang
 */
public class QueryParameter {

	protected int pageNo = 1;
	protected int pageSize = 20;
	protected boolean autoCount = true;

	/**
	 * 获得每页的记录数量,无默认值.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 是否已设置每页的记录数量.
	 */
	public boolean isPageSizeSetted() {
		return pageSize > -1;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize);
	}

	/**
	 * 是否已设置第一条记录记录在总结果集中的位置.
	 */
	public boolean isFirstSetted() {
		return (pageNo > 0 && pageSize > 0);
	}

	/**
	 * 是否自动获取总页数,默认为false.
	 * 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
}