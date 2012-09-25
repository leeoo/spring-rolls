package com.exam.entity.exam;

import java.io.Serializable;
import java.util.Date;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.validation.annotation.Chinese;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;

/**
 * 考生信息
 * @author caoyang
 */
@Entity(tableName = "t_examinee", orderBy = "id DESC")
public class Examinee implements Serializable {
	
	private static final long serialVersionUID = 1654792353508687941L;

	@PrimaryKey
	@Column
	private Integer id;

	@Column(isDynamicWhere=true)
	@NotNull
	@Length(min=5,max=20)
	@ShowLabel("考号")
	private String code;
	
	@Column(isDynamicWhere=true)
	@NotNull
	@Length(min=2,max=10)
	@Chinese
	@ShowLabel("姓名")
	private String username;
	
	@Column
	@NotNull
	@Length(min=3,max=15)
	@ShowLabel("密码")
	private String password;
	
	@Column
	@NotUpdate
	private Date regDate = DateUtils.getCurrentDateTime();
	
	@Column
	private int canDays = ConfigInfo.examineeDays;//账户有效期多少天 0为永远有效

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getCanDays() {
		return canDays;
	}

	public void setCanDays(int canDays) {
		this.canDays = canDays;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
