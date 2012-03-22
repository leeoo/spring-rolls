package com.demo.entity.system;

import java.io.Serializable;
import java.util.Collection;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;

@Entity(tableName="t_security_user",orderBy="id DESC")
public class User implements Serializable{
	
	private static final long serialVersionUID = 2383428612051199985L;

	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(isDynamicWhere=true)
	private String username;
	
	@Column(isDynamicWhere=true)
	private String name;
	
	@Column
	private String password;
	
	@Column(isDynamicWhere=true)
	private Boolean enabled;
	
	private Collection<Role> roles;

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	

}
