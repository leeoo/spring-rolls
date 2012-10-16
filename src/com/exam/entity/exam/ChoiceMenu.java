package com.exam.entity.exam;

import java.io.Serializable;
/**
 * 选择题菜单
 * @author caoyang
 *
 */
public class ChoiceMenu implements Serializable{

	private static final long serialVersionUID = 3326267224264035874L;
	
	private Integer id;
	
	private int status = 0;//0没答过，1打完，-1不确定，
	
	private Integer previousId;
	
	private Integer nextId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPreviousId() {
		return previousId;
	}

	public void setPreviousId(Integer previousId) {
		this.previousId = previousId;
	}

	public Integer getNextId() {
		return nextId;
	}

	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
