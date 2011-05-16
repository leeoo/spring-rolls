package com.demo.entity.system;

import java.io.Serializable;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;

@Entity(tableName="t_security_role",orderBy="id DESC")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 8491420399084065819L;

	@PrimaryKey
	@Column
	private Integer id;
	
	@Column
	private String rolename;
	
	@Column
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
