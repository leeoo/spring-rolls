package com.demo.web.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.paramecium.commons.CommandUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("硬件信息")
@Controller("/system/hardware")
public class HardwareController extends BaseController{

	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		if(CommandUtils.getOSName().toLowerCase().indexOf("win")>-1){
			mv.addValue("cpu", getWinOSCPUInfo());
			mv.addValue("memory", getWinOSMemoryInfo());
			mv.addValue("disks", getWinOSDiskInfo());
		}
		return mv.forward(getPage("/hardware/list.jsp"));
	}
	
	private Map<String,Object> getWinOSCPUInfo(){
		Map<String,Object> info = new HashMap<String, Object>();
		String cpu = CommandUtils.getRunResult("wmic cpu get loadpercentage,numberOfCores,numberOflogicalprocessors,name");
		int s1 = cpu.indexOf('\n');
		cpu = cpu.substring(s1).trim();
		s1 = cpu.indexOf(' ');
		String loadStr = cpu.substring(0,s1).trim();
		int load = 0;
		if(loadStr!=null){
			load = Integer.parseInt(loadStr);
		}
		cpu = cpu.substring(s1).trim();
		s1 = cpu.indexOf("  ");
		String name = cpu.substring(0,s1).trim();
		cpu = cpu.substring(s1).trim();
		s1 = cpu.indexOf(" ");
		name += cpu.substring(0,s1).trim()+"核 ";
		name += cpu.substring(s1).trim()+"线程";
		info.put("name", name);
		info.put("load", load);
		return info;
	}
	
	private Map<String,float[]> getWinOSDiskInfo(){
		Map<String,float[]> info = new LinkedHashMap<String, float[]>();
		String disk = CommandUtils.getRunResult("wmic logicaldisk  get deviceID,size,FreeSpace");
		int s1 = disk.indexOf('\n');
		disk = disk.substring(s1).trim();
		for(String d : disk.split("\n")){
			if(!d.trim().isEmpty()){
				s1 = d.indexOf(' ');
				String name = d.substring(0,s1).trim();
				d = d.substring(s1).trim();
				s1 = d.indexOf(' ');
				String freeDisk = d.substring(0,s1).trim();
				String totalDisk = d.substring(s1).trim();
				float total =  Float.parseFloat(totalDisk)/(1024*1024*1024);
				float free =  Float.parseFloat(freeDisk)/(1024*1024*1024);
				float use = total - free;
				total = new BigDecimal(total).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
				use = new BigDecimal(use).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
				free = new BigDecimal(free).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
				float[] values = {total,use,free};
				info.put(name, values);
			}
		}
		return info;
	}
	
	private Map<String,Float> getWinOSMemoryInfo(){
		Map<String,Float> info = new HashMap<String, Float>();
		String mem = CommandUtils.getRunResult("wmic OS get FreePhysicalMemory,TotalVisibleMemorySize");
		int s1 = mem.indexOf('\n');
		mem = mem.substring(s1).trim();
		s1 = mem.indexOf(' ');
		String freeMem = mem.substring(0,s1).trim();
		String totalMem = mem.substring(s1).trim();
		float total =  Float.parseFloat(totalMem)/(1024*1024);
		float free =  Float.parseFloat(freeMem)/(1024*1024);
		float use = total - free;
		total = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		use = new BigDecimal(use).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		free = new BigDecimal(free).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		info.put("total", total);
		info.put("use", use);
		info.put("free", free);
		return info;
	}

}
