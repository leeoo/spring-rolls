package com.demo.web.system;

import java.util.Collection;

import org.paramecium.commons.DateUtils;
import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.OnlineUserCache;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

/**
 * 功 能 描 述:<br>
 * 在线用户管理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-1下午02:16:00
 * <br>项 目 信 息:paramecium:com.demo.web.system.OnlineUserController.java
 */
@Security
@ShowLabel("在线用户")
@Controller("/system/online")
public class OnlineUserController extends BaseController{
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		return mv.forward(getPage("/online/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		Page page = new Page();
		page.setPageNo(1);
		Collection<?> users = OnlineUserCache.getAllOnlineUsers();
		int count = users.size();
		page.setPageSize(count);
		page.setTotalCount(count);
		String json = JsonUitls.getBeansJson(users,false,DateUtils.DATE_TIME_FORMAT);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@ShowLabel("踢出用户")
	@MappingMethod
	public void kill(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] sessionIds = idstr.split(",");
				for(String sessionId : sessionIds){
					OnlineUserCache.kill(sessionId);
				}
			}
		} catch (Exception e) {
		}
	}

	@ShowLabel("踢出所有用户")
	@MappingMethod
	public void killAll(ModelAndView mv){
		try {
			OnlineUserCache.killAll();
		} catch (Exception e) {
		}
	}

}
