package com.wisdom.core.logger.domain;

import javax.validation.constraints.NotNull;

/**
 * 功 能 描 述:<br>
 * 抽象地址匹配
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午05:21:59
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.domain.AbstractMatch.java
 */
public abstract class AbstractMatch {

	@NotNull(message="关键字不能为空")
	private String keyword;
	
	@NotNull(message="关键字含义不能为空")
	private String name;
	
	private boolean enabled;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
