package org.paramecium.security.taglib;

import java.util.Collection;

import javax.servlet.jsp.JspException;

import org.paramecium.security.Resource;
import org.paramecium.security.UserDetails;
/**
 * 功 能 描 述:<br>
 * 身份验证标签
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-9下午04:20:29
 * <br>项 目 信 息:paramecium:org.paramecium.security.taglib.ResourceAuthorizeTag.java
 */
public class ResourceAuthorizeTag extends BaseSecurityTag{

	private static final long serialVersionUID = -2087116999369663254L;
	private String ifNotGranted;//不包含
	private String ifAllGranted;//全部包含
	private String ifAnyGranted;//任意包含
	
	public int doStartTag() throws JspException {
		int count = 0;
		if(ifNotGranted!=null){
			++count;
		}
		if(ifAllGranted!=null){
			++count;
		}
		if(ifAnyGranted!=null){
			++count;
		}
		if(count!=1){//只能使用一个标签属性
			return SKIP_BODY;
		}
		UserDetails<?> user = getUserDetails();
		if(user!=null){//判断是否登录
			Collection<Resource> userRes = user.getResources();
			if(ifNotGranted!=null){
				if(ifNotGranted.indexOf(',')>0){//判断是否多个
					String[] res = ifNotGranted.split(",");
					for(String re : res){
						for(Resource resource : userRes){
							if(re.equalsIgnoreCase(resource.toString())){
								return SKIP_BODY;
							}
						}
					}
				}else{//单个资源单独处理
					for(Resource resource : userRes){
						if(ifNotGranted.equalsIgnoreCase(resource.toString())){
							return SKIP_BODY;
						}
					}
				}
				return EVAL_BODY_INCLUDE;
			}else if(ifAllGranted!=null){
				if(ifAllGranted.indexOf(',')>0){//判断是否多个
					String[] res = ifAllGranted.split(",");
					for(String re : res){
						boolean sign = false;
						b:for(Resource resource : userRes){
							if(re.equalsIgnoreCase(resource.toString())){
								sign = true;
								break b;
							}
						}
						if(!sign){
							return SKIP_BODY;
						}
					}
				}else{//单个资源单独处理
					boolean sign = false;
					for(Resource resource : userRes){
						if(ifAllGranted.equalsIgnoreCase(resource.toString())){
							sign = true;
							break;
						}
					}
					if(!sign){
						return SKIP_BODY;
					}
				}
				return EVAL_BODY_INCLUDE;
			}else if(ifAnyGranted!=null){
				if(ifAnyGranted.indexOf(',')>0){//判断是否多个
					String[] res = ifAnyGranted.split(",");
					for(String re : res){
						for(Resource resource : userRes){
							if(re.equalsIgnoreCase(resource.toString())){
								return EVAL_BODY_INCLUDE;
							}
						}
					}
				}else{//单个资源单独处理
					for(Resource resource : userRes){
						if(ifAnyGranted.equalsIgnoreCase(resource.toString())){
							return EVAL_BODY_INCLUDE;
						}
					}
				}
				return SKIP_BODY;
			}else{
				return SKIP_BODY;
			}
		}else{
			if(ifNotGranted!=null){
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	public void setIfNotGranted(String ifNotGranted) {
		this.ifNotGranted = ifNotGranted;
	}

	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public void setIfAllGranted(String ifAllGranted) {
		this.ifAllGranted = ifAllGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) {
		this.ifAnyGranted = ifAnyGranted;
	}

}
