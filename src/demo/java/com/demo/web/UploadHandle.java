package com.demo.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dily.commons.EncodeUtils;
import org.dily.commons.PathUtils;
import org.dily.ioc.annotation.ShowLabel;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;
import org.dily.mvc.ModelAndView;
import org.dily.mvc.annotation.Controller;
import org.dily.mvc.annotation.MappingMethod;

@ShowLabel("文件上传")
@Controller("/file")
public class UploadHandle {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	@ShowLabel("上传")
	@MappingMethod
	public void upload(ModelAndView mv) {
		try {
			// 调用Decode方法，返回一个哈希表
			Map<String, Object> map = decode(mv.getRequest());
			// 以字节数据的方式获得文件的内容
			byte[] filecontent = (byte[]) map.get("file");
			String filename = (String) map.get("filename");
			if(filename==null||filename.isEmpty()){
				return;
			}
			String dName = PathUtils.getWebRootPath() + "//upload//temp";
			String fName = EncodeUtils.millisTime() + filename.substring(filename.lastIndexOf('.'));

			File dir = new File(dName);
			File file = new File(dir, fName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!file.exists()) {
				dir.createNewFile();
			}
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(filecontent);
			} catch (IOException e) {
				logger.error(e);
			} finally {
				if(fileOutputStream!=null){
					try {
						fileOutputStream.flush();
					} catch (IOException e) {
						logger.error(e);
					}
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						logger.error(e);
					}
				}
				mv.printJSON(fName);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static Map<String, Object> decode(HttpServletRequest req) throws java.io.IOException {
		byte[] body = null;
		int bodyLen = 0;
		byte[] bound = null;
		int boundLen = 0;
		int index = 0;
		bodyLen = req.getContentLength();
		body = new byte[bodyLen];
		BufferedInputStream dataIn = new BufferedInputStream(req.getInputStream());
		int readed = 0;
		int cur_read = 0;
		while (readed < bodyLen) {
			cur_read = dataIn.read(body, readed, bodyLen - readed);
			if (cur_read < 0) {
				break;
			}
			readed = readed + cur_read;
		}
		int i = 0;
		while (i <= bodyLen) {
			if (body[i] == 13 && body[i + 1] == 10){
				break;
			}else{
				i++;
			}
		}
		if (i > bodyLen){
			return null;
		}
		boundLen = i;
		bound = new byte[boundLen];
		for (int j = 0; j < boundLen; j++) {
			bound[j] = body[j + index]; // decode bound
		}
		i = i + 2; // plus 2 to skip the following bytes "0D 0A"
		index = i; // point to the beginning of first parameter
		Map<String, Object> hashtable = new HashMap<String, Object>();
		while (i < bodyLen) {
			if (!compareByteArray(copybyte(body, i, boundLen), bound)) {
				i++;
			} else {
				int j = index;
				while ((j < i)&& (body[j] != 13 || body[j + 1] != 10|| body[j + 2] != 13 || body[j + 3] != 10)) {
					j++;
				}
				if (j >= i){
					break;
				}
				String paramHeader = new String(body, index, j - index + 2);
				index = j;
				int m = paramHeader.indexOf("name=\"");
				if (m < 0){
					break;
				}
				m = m + 6; // point to name value
				int n = paramHeader.indexOf("\"", m);
				if (n <= m){
					break;
				}
				String name = paramHeader.substring(m, n); // get name
				boolean isFile = false;
				String filename = "";
				String filetype = "";
				m = paramHeader.indexOf("filename=\"", n + 1);
				if (m > n) {
					isFile = true;
					m = m + 10; // skip (filename=")
					n = paramHeader.indexOf("\"", m);
					if (n > m){
						filename = paramHeader.substring(m, n);
					}
					m = paramHeader.indexOf("Content-Type: ", n + 1);
					if (m > n) {
						m = m + 14;
						n = m;
						while ((n < paramHeader.length())&& (paramHeader.charAt(n) != 13 || paramHeader.charAt(n + 1) != 10)) {
							n++;
						}
						if (n <= paramHeader.length()){
							filetype = paramHeader.substring(m, n);
						}
					}
				}
				j = j + 4;
				byte[] value = copybyte(body, j, i - j - 2);
				if (!isFile) {
					String tmpstr = new String(value);
					hashtable.put(name, tmpstr);
				} else {
					hashtable.put(name, value);
					hashtable.put("filename", filename);
					hashtable.put("filetype", filetype);
					break;
				}
				i = i + boundLen + 2;
				index = i;
			} // end else
		} // end while
		dataIn.close();
		return hashtable;
	}

	private static boolean compareByteArray(byte[] a, byte[] b) {
		if (a.length != b.length){
			return false;
		}
		for (int i = 0; i < a.length; i++){
			if (a[i] != b[i]){
				return false;
			}
		}
		return true;
	}

	private static byte[] copybyte(byte[] a, int from, int len) {
		int copylen = len;
		if ((a.length - from) < copylen){
			copylen = a.length - from;
		}
		byte[] b = new byte[copylen];
		for (int i = 0; i < copylen; i++){
			b[i] = a[from + i];
		}
		return b;
	}

}
