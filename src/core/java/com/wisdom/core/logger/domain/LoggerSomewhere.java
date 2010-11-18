package com.wisdom.core.logger.domain;

import java.io.Serializable;

import com.wisdom.core.annotation.SimpleEntity;
/**
 * 功 能 描 述:<br>
 * 用户匹配用户到过那些地方
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午05:25:31
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.domain.LoggerSomewhere.java
 */
@SimpleEntity(tableName="t_logger_somewhere",pkPropertyName="keyword",orderBy="keyword DESC")
public class LoggerSomewhere extends AbstractMatch implements Serializable{

	private static final long serialVersionUID = -2175855248687642789L;
	
}
