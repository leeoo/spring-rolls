package com.wisdom.core.logger;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.core.logger.domain.LoggerSomething;
import com.wisdom.core.logger.domain.LoggerSomewhere;

/**
 * 功 能 描 述:<br>
 * 日志匹配缓存初始化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-16下午03:41:05
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.MatchCacheInit.java
 */
public class MatchCacheInit {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private MatchService matchService;
	
	public void init(){
		logger.info("正在初始化日志匹配缓存");
		Collection<LoggerSomething> somethings = matchService.getAllLoggerSomething();
		Collection<LoggerSomewhere> somewheres = matchService.getAllLoggerSomewhere();
		if(somethings!=null&&!somethings.isEmpty()){
			for(LoggerSomething match:somethings){
				MatchCache.put(match);
			}
		}
		if(somewheres!=null&&!somewheres.isEmpty()){
			for(LoggerSomewhere match:somewheres){
				MatchCache.put(match);
			}
		}
		logger.info("日志匹配缓存初始化结束");
	}

	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}
	
	
}
