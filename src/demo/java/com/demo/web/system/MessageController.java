package com.demo.web.system;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.entity.system.Message;
import com.demo.web.BaseController;

@Security
@ShowLabel("站内消息")
@Controller("/system/message")
public class MessageController extends BaseController{
	
	@SuppressWarnings("unchecked")
	private final static Cache<String,Message> messages = (Cache<String, Message>) CacheManager.getCacheByType("MESSAGE",1000);
	
	@ShowLabel("发送消息")
	@MappingMethod
	public void send(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		String content = mv.getValue("content",String.class);
		try {
			if(idstr!=null && content!=null){
				String[] ids = idstr.split(",");
				for(String sessionId : ids){
					Message message = new Message();
					message.setAuth(SecurityUitls.getLoginUser().getName());
					message.setContent(content);
					//message.setPublishDate(DateUtils.getCurrentDateTime());
					messages.put(sessionId, message);
				}
			}
		} catch (Exception e) {
		}
	}
	
	@ShowLabel("接收消息")
	@MappingMethod
	public void receive(ModelAndView mv){
		String sessionId = SecurityUitls.getLoginUser().getSessionId();
		if(sessionId==null){
			return;
		}
		Message message = messages.get(sessionId);
		if(message==null){
			return;
		}
		mv.printJSON(message.getContent()+"/n/r"+message.getAuth());
	}

}
