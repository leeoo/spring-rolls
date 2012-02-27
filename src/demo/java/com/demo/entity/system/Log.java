package com.demo.entity.system;

import java.io.Serializable;
import java.util.Date;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;
/**
 * 功 能 描 述:<br>
 * 日志信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-9上午09:04:00
 * <br>项 目 信 息:paramecium:com.demo.entity.system.Log.java
 */
@Entity(tableName="t_log",orderBy="id DESC")
public class Log implements Serializable{

	private static final long serialVersionUID = 6406892373238739485L;
	
	@PrimaryKey
	@Column
	private Integer id;
	
	@Column
	private String log;
	
	@Column
	private Date date;
	
	@Column(isDynamicWhere=true)
	private Integer type=0;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLog() {
		if(this.log!=null&&this.log.length()>130){
			return this.log.substring(0, 130).concat("...");
		}
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
