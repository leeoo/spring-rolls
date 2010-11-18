package com.wisdom.core.logger.domain;

import java.io.Serializable;

import com.wisdom.core.annotation.SimpleEntity;
/**
 * 功 能 描 述:<br>
 * 日志信息中用于匹配用户行为
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午05:24:41
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.domain.LoggerSomething.java
 */
@SimpleEntity(tableName="t_logger_something",pkPropertyName="keyword",orderBy="keyword DESC")
public class LoggerSomething extends AbstractMatch implements Serializable{

	private static final long serialVersionUID = 4215901793876515630L;
	
}
