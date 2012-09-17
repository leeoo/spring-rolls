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
import org.paramecium.validation.annotation.IDCard;
import org.paramecium.validation.annotation.Mobile;
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
	private int id;

	@Column
	@NotNull
	@Length(min=3,max=15)
	@ShowLabel("学号")
	private String code;
	
	@Column
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
	@IDCard
	@ShowLabel("密码")
	private String card;
	
	@Column
	@Mobile
	@ShowLabel("手机号码")
	private String tel;
	
	@Column
	@NotUpdate
	private Date regDate = DateUtils.getCurrentDateTime();
	
	@Column
	private int canDays = 0;//账户有效期多少天 0为永远有效

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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
	
}
