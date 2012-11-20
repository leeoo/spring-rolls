package com.demo.web.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.paramecium.commons.CommandUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("硬件信息")
@Controller("/system/hardware")
public class HardwareController extends BaseController{
	private static final Log logger = LoggerFactory.getLogger();

	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		if(CommandUtils.getOSName().toLowerCase().indexOf("win")>-1){
			mv.addValue("cpu", getWinOSCPUInfo());
			mv.addValue("memory", getWinOSMemoryInfo());
			mv.addValue("disks", getWinOSDiskInfo());
		}else{
			mv.addValue("cpu", getLinuxOSCPUInfo());
			mv.addValue("memory", getLinuxOSMemoryInfo());
			mv.addValue("disks", getLinuxOSDiskInfo());
		}
		return mv.forward(getPage("/hardware/list.jsp"));
	}
	
	private Map<String,float[]> getLinuxOSDiskInfo(){
		Map<String,float[]> info = new LinkedHashMap<String, float[]>();
		try{
			String disk = CommandUtils.getRunResult("df -lh");
			int s1 = disk.indexOf('\n');
			disk = disk.substring(s1).trim();
			int as = 1;
			for(String d : disk.split("\n")){
				if(!d.trim().isEmpty()){
					s1 = d.indexOf(' ');
					String name = d.substring(0,s1).trim();
					float total = 0l;
					float free = 0l;
					float use = 0l;
					try{
						d = d.substring(s1).trim();
						s1 = d.indexOf(' ');
						String totalDisk = d.substring(0,s1).trim();
						
						d = d.substring(s1).trim();
						s1 = d.indexOf(' ');
						String useDisk = d.substring(0,s1).trim();
						
						d = d.substring(s1).trim();
						s1 = d.indexOf(' ');
						String freeDisk = d.substring(0,s1).trim();
						String t = totalDisk.substring(totalDisk.length()-1, totalDisk.length()).toUpperCase();
						String f = freeDisk.substring(freeDisk.length()-1, freeDisk.length()).toUpperCase();
						String u = useDisk.substring(useDisk.length()-1, useDisk.length()).toUpperCase();
						if(t.equals("G")||t.equals("M")||t.equals("K")){
							totalDisk = totalDisk.substring(0,totalDisk.length()-1);
						}
						if(f.equals("G")||f.equals("M")||f.equals("K")){
							freeDisk = freeDisk.substring(0,freeDisk.length()-1);
						}
						if(u.equals("G")||u.equals("M")||u.equals("K")){
							useDisk = useDisk.substring(0,useDisk.length()-1);
						}
						total =  Float.parseFloat(totalDisk)/getNum(t);
						free =  Float.parseFloat(freeDisk)/getNum(f);
						use = Float.parseFloat(useDisk)/getNum(u);
					}catch (StringIndexOutOfBoundsException e) {
						//如果有些读卡器空载入或光驱，只有盘符，没有容量，忽略不计
						name += "(空设备)";
					}
					total = new BigDecimal(total).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					use = new BigDecimal(use).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					free = new BigDecimal(free).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					float[] values = {total,use,free};
					if(info.get(name)!=null){
						name += ++as;
					}
					info.put(name, values);
				}
			}
		}catch (Exception e) {
			logger.error(e);
		}
		return info;
	}
	
	private int getNum(String value){
		int base = 1;
		if(value.equals("G")){
			base = 1;
		}else if(value.equals("M")){
			base = 1024;
		}else if(value.equals("K")){
			base = 1024*1024;
		}else{
			base = 1024*1024*1024;
		}
		return base;
	}
	
	private Map<String,Object> getLinuxOSCPUInfo(){
		Map<String,Object> info = new HashMap<String, Object>();
		int load = 0;
		String name = "未知CPU(或虚拟机CPU)";
		try{
			String cpu = CommandUtils.getRunResult("top -bn1");
			cpu = cpu.substring(cpu.indexOf("Cpu(s):"),cpu.indexOf("Swap:"));
			int s1 = cpu.indexOf("Cpu(s):");
			int s2 = cpu.indexOf("%us,");
			int s3 = cpu.indexOf("%sy,");
			String us = cpu.substring(s1+7,s2).trim();
			String sy = cpu.substring(s2+4,s3).trim();
			if(us!=null&&sy!=null){
				load = new BigDecimal(Float.parseFloat(us) + Float.parseFloat(sy)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			}
			cpu = CommandUtils.getRunResult("cat /proc/cpuinfo");
			s1 = cpu.indexOf("model name");
			s2 = cpu.indexOf("stepping");
			name = cpu.substring(s1+10,s2).replace(':',' ').trim();
		}catch (Exception e) {
			logger.error(e);
		}
		info.put("name", name);
		info.put("load", load);
		return info;
	}
	
	private Map<String,Float> getLinuxOSMemoryInfo(){
		Map<String,Float> info = new HashMap<String, Float>();
		float total = 0;
		float free = 0;
		float use = 0;
		try{
			String mem = CommandUtils.getRunResult("top -bn1");
			mem = mem.substring(mem.indexOf("Mem:"),mem.indexOf("Swap:"));
			int s1 = mem.indexOf("Mem:");
			int s2 = mem.indexOf("k total,");
			int s3 = mem.indexOf("k used,");
			int s4 = mem.indexOf("k free,");
			String totalMem = mem.substring(s1+4,s2).trim();
			String useMem = mem.substring(s2+8,s3).trim();
			String freeMem = mem.substring(s3+7,s4).trim();
			total = Float.parseFloat(totalMem)/(1024*1024);
			free = Float.parseFloat(freeMem)/(1024*1024);
			use = Float.parseFloat(useMem)/(1024*1024);
			total = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			use = new BigDecimal(use).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			free = new BigDecimal(free).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		}catch (Exception e) {
			logger.error(e);
		}
		info.put("total", total);
		info.put("use", use);
		info.put("free", free);
		return info;
	}
	
	private Map<String,Object> getWinOSCPUInfo(){
		Map<String,Object> info = new HashMap<String, Object>();
		int load = 0;
		String name = "未知CPU(或虚拟机CPU)";
		try{
			String cpu = CommandUtils.getRunResult("wmic cpu get loadpercentage,numberOfCores,numberOflogicalprocessors,name");
			int s1 = cpu.indexOf('\n');
			try{
				cpu = cpu.substring(s1).trim();
				s1 = cpu.indexOf(' ');
				String loadStr = cpu.substring(0,s1).trim();
				if(loadStr!=null){
					load = Integer.parseInt(loadStr);
				}
				cpu = cpu.substring(s1).trim();
				s1 = cpu.indexOf("  ");
			}catch (StringIndexOutOfBoundsException e) {
				//有时候没有负载，或显示为空
			}
			name = cpu.substring(0,s1).trim().concat(" ");
			cpu = cpu.substring(s1).trim();
			s1 = cpu.indexOf(" ");
			name += cpu.substring(0,s1).trim()+"核 ";
			name += cpu.substring(s1).trim()+"线程";
		}catch (Exception e) {
			logger.error(e);
		}
		info.put("name", name);
		info.put("load", load);
		return info;
	}
	
	private Map<String,float[]> getWinOSDiskInfo(){
		Map<String,float[]> info = new LinkedHashMap<String, float[]>();
		try{
			String disk = CommandUtils.getRunResult("wmic logicaldisk  get deviceID,size,FreeSpace");
			int s1 = disk.indexOf('\n');
			disk = disk.substring(s1).trim();
			int as = 1;
			for(String d : disk.split("\n")){
				if(!d.trim().isEmpty()){
					s1 = d.indexOf(' ');
					String name = d.substring(0,s1).trim();
					d = d.substring(s1).trim();
					s1 = d.indexOf(' ');
					float total = 0l;
					float free = 0l;
					try{
						String freeDisk = d.substring(0,s1).trim();
						String totalDisk = d.substring(s1).trim();
						total =  Float.parseFloat(totalDisk)/(1024*1024*1024);
						free =  Float.parseFloat(freeDisk)/(1024*1024*1024);
					}catch (StringIndexOutOfBoundsException e) {
						//如果有些读卡器空载入或光驱，只有盘符，没有容量，忽略不计
						name += "(空设备)";
					}
					float use = total - free;
					total = new BigDecimal(total).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					use = new BigDecimal(use).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					free = new BigDecimal(free).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
					float[] values = {total,use,free};
					if(info.get(name)!=null){
						name += ++as;
					}
					info.put(name, values);
				}
			}
		}catch (Exception e) {
			logger.error(e);
		}
		return info;
	}
	
	private Map<String,Float> getWinOSMemoryInfo(){
		Map<String,Float> info = new HashMap<String, Float>();
		float total = 0;
		float free = 0;
		float use = 0;
		try{
			String mem = CommandUtils.getRunResult("wmic OS get FreePhysicalMemory,TotalVisibleMemorySize");
			int s1 = mem.indexOf('\n');
			mem = mem.substring(s1).trim();
			s1 = mem.indexOf(' ');
			String freeMem = mem.substring(0,s1).trim();
			String totalMem = mem.substring(s1).trim();
			total = Float.parseFloat(totalMem)/(1024*1024);
			free = Float.parseFloat(freeMem)/(1024*1024);
			use = total - free;
			total = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			use = new BigDecimal(use).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			free = new BigDecimal(free).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		}catch (Exception e) {
			logger.error(e);
		}
		info.put("total", total);
		info.put("use", use);
		info.put("free", free);
		return info;
	}

}
