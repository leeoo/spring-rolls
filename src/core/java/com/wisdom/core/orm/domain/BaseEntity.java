package com.wisdom.core.orm.domain;

import java.util.Date;

import com.wisdom.core.dao.IDCreator;
import com.wisdom.core.security.resource.SecurityUtils;
import com.wisdom.core.utils.DateUtils;

/**
 * 功能描述(Description):<br>
 * <b> 实体基本信息抽象集成类 </b><br>
 * 作 者(Author): <i><b>曹阳(Cao.Yang)</b></i> <br>
 * 建立日期(Create Date): <b>2010-5-23下午06:23:40</b> <br>
 * 项目名称(Project Name): <b>wisdom.3.0RC2</b> <br>
 * 包及类名(Package Class): <b>com.wisdom.core.orm.domain.BaseEntity.java</b>
 */
public abstract class BaseEntity{

	private Date insertDate = DateUtils.getCurrentDateTime();
	private Date updateDate = DateUtils.getCurrentDateTime();
	private String operatorName = SecurityUtils.getCurrentUserName();
	private String operatorIp = SecurityUtils.getCurrentUserIp();
	private String businessCode = IDCreator.businessCode;

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

}
