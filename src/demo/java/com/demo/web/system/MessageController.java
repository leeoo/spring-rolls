package com.demo.web.system;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.DateUtils;
import org.paramecium.commons.EncodeUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.commons.SecurityUitls;
import org.paramecium.commons.ZipUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
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
	private static final Log logger = LoggerFactory.getLogger();
	
	@SuppressWarnings("unchecked")
	private final static Cache<String,Message> messages = (Cache<String, Message>) CacheManager.getCacheByType("MESSAGE",128,100l);
	
	@ShowLabel("发送消息")
	@MappingMethod
	public void send(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		String content = mv.getValue("content",String.class);
		String title = mv.getValue("title",String.class);
		try {
			if(idstr!=null && content!=null){
				String[] ids = idstr.split(",");
				for(String sessionId : ids){
					Message message = new Message();
					message.setAuth(SecurityUitls.getLoginUser().getName());
					message.setContent(content);
					if(title!=null&&!title.trim().isEmpty()){
						String tempPath = PathUtils.getWebRootPath() + "//upload//temp//";
						String zipFileName = EncodeUtils.millisTime() +".zip";
						String zipFullFileName = tempPath + zipFileName;
						if(title.indexOf('|')>0){
							String[] fileNames = title.split("\\|");
							for(int i=0,length = fileNames.length;i<length;i++){
								fileNames[i] = tempPath + fileNames[i];
							}
							ZipUtils.zip(zipFullFileName, fileNames);
							for(int i=0,length = fileNames.length;i<length;i++){
								new File(fileNames[i]).delete();
							}
						}else{
							ZipUtils.zip(zipFullFileName, tempPath + title);
							new File(tempPath + title).delete();
						}
						message.setTitle(zipFileName);
					}
					message.setPublishDate(DateUtils.getCurrentDateTime());
					messages.put(sessionId, message);
				}
			}
		} catch (Exception e) {
			logger.error(e);
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
		messages.remove(sessionId);
		mv.printJSON("{\"content\":\""+message.getContent()+"\",\"auth\":\""+message.getAuth()+"\",\"title\":\""+message.getTitle()+"\",\"date\":\""+DateUtils.parse(new SimpleDateFormat("MM月dd日HH时mm分ss秒",java.util.Locale.CHINA),message.getPublishDate())+"\"}");
	}
	
	@ShowLabel("下载附件")
	@MappingMethod
	public ModelAndView download(ModelAndView mv){
		String fileName = mv.getValue("title",String.class);
		if(fileName!=null&&!fileName.trim().isEmpty()){
			String tempPath = PathUtils.getWebRootPath() + "//upload//temp//";
			File file = new File(tempPath+fileName);
			FileInputStream fis = null;
			ByteArrayOutputStream bos = null;
			byte[] buffer = null;
			try {
				fis = new FileInputStream(file);
				bos = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while ((n = fis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
				buffer = bos.toByteArray();
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.flush();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
			file.delete();//删除zip
			return mv.download(buffer, fileName);
		}
		return null;
	}

}
