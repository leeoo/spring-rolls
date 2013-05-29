package org.paramecium.thread;

import java.util.Observable;
import java.util.Observer;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 观察者线程监听器
 * @author caoyang
 *
 */
public class ThreadListener implements Observer{

	private final static Log logger = LoggerFactory.getLogger();
	
	public void update(Observable observable, Object object) {
		RunnableMonitor monitor = (RunnableMonitor)observable;
		logger.warn(monitor.getClass()+"|"+monitor.hashCode()+"|"+"该线程出现停止运行！");
	}

}
