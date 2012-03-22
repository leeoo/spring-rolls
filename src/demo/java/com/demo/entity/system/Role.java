package com.demo.entity.system;

import java.io.Serializable;
import java.util.Collection;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;

@Entity(tableName="t_security_role",orderBy="id DESC")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 8491420399084065819L;

	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(isDynamicWhere=true)
	private String rolename;
	
	@Column(isDynamicWhere=true)
	private String name;
	
	private Collection<String> auth;

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

	public Collection<String> getAuth() {
		return auth;
	}

	public void setAuth(Collection<String> auth) {
		this.auth = auth;
	}
	

}
