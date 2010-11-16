package com.wisdom.core.logger;

import java.util.Collection;

import com.wisdom.core.logger.domain.LoggerSomething;
import com.wisdom.core.logger.domain.LoggerSomewhere;

public interface MatchService {
	public Collection<LoggerSomething> getAllLoggerSomething();
	public Collection<LoggerSomewhere> getAllLoggerSomewhere();
}
