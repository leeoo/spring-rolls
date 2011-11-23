package com.demo.test;

import java.util.Date;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

@Entity(tableName="performance_test")
public class Performance {
	
	@PrimaryKey
	private Long id;
	
	@NotNull
	@Length(min=2,max=250)
	@Column
	private String name;
	
	@NotNull
	@Column
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
