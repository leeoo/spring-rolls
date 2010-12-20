package com.wisdom.core.utils;

import java.util.List;

/**
 * 封装分页和排序查询的结果,并继承QueryParameter的所有查询请求参数.
 * 
 */
public class Page extends QueryParameter {

	protected int pageNo = 1;
	protected int pageSize = 20;
	protected boolean autoCount = true;
	@SuppressWarnings("unchecked")
	private List result = null;

	private int totalCount = -1;

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
		if(getTotalPages()!=-1&&getTotalPages()<=pageNo){
			return getTotalPages();
		}
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
	
	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page(int pageSize, boolean autoCount) {
		this.pageSize = pageSize;
		this.autoCount = autoCount;
	}

	public Page(int pageSize, boolean autoCount,String orderBy,String order) {
		this.pageSize = pageSize;
		this.autoCount = autoCount;
	}

	/**
	 * 页内的数据列表.
	 */
	@SuppressWarnings("unchecked")
	public List getResult() {
		if(result != null && !result.isEmpty()){
			return result;
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * 总记录数.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 计算总页数.
	 */
	public int getTotalPages() {
		if (totalCount == -1)
			return -1;

		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页. 
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
}
