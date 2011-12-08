package com.demo.web.system;

import org.paramecium.commons.CommandUtils;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.service.system.SqlRunner;
import com.demo.web.BaseController;

@Security
@ShowLabel("控制台")
@Controller("/system/console")
public class ConsoleController extends BaseController{

	@AutoInject
	private SqlRunner sqlRunner;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView index(ModelAndView mv){
		mv.addValue("type", "system");
		return mv.forward(getPage("/console/index.jsp"));
	}
	
	@ShowLabel("执行命令")
	@MappingMethod
	public ModelAndView run(ModelAndView mv){
		String cmd = mv.getValue("cmd", String.class);
		String type = mv.getValue("type", String.class);
		if(cmd==null||cmd.isEmpty()){
			mv.addValue("type", type);
			return mv.forward(getPage("/console/index.jsp"));
		}
		if(cmd!=null&&cmd.equalsIgnoreCase("sql")){
			type = "sql";
			mv.addValue("type", type);
			return mv.forward(getPage("/console/index.jsp"));
		}else if(cmd!=null&&cmd.equalsIgnoreCase("system")){
			type = "system";
			mv.addValue("type", type);
			return mv.forward(getPage("/console/index.jsp"));
		}
		if(type.equalsIgnoreCase("system")){
			String result = CommandUtils.getRunResult(cmd,true);
			mv.addValue("type", type);
			mv.addValue("cmd", cmd);
			mv.addValue("result", result);
		}else{
			String result = null;
			if(cmd!=null&&cmd.toLowerCase().indexOf("select")>-1&&cmd.toLowerCase().indexOf("select")<7){
				result = sqlRunner.select(cmd);
			}else if(cmd!=null&&(cmd.toLowerCase().indexOf("insert")>-1||cmd.toLowerCase().indexOf("update")>-1||cmd.toLowerCase().indexOf("delete")>-1)){
				result = sqlRunner.executeDML(cmd);
			}else{
				result = sqlRunner.executeDDL(cmd);
			}
			mv.addValue("type", type);
			mv.addValue("cmd", cmd);
			mv.addValue("result", result);
		}
		
		return mv.forward(getPage("/console/index.jsp"));
	}
	
}
