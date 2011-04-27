package org.paramecium.security;

import java.io.Serializable;
/**
 * 功 能 描 述:<br>
 * 受保护资源
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:45:18
 * <br>项 目 信 息:paramecium:org.paramecium.security.Resource.java
 */
public class Resource implements Serializable{

	private static final long serialVersionUID = 5410454114477519189L;
	
	private String firstResource;

	private String lastResource;
	
	private String showLabel;
	
	public boolean equals(Object obj){
		if(obj instanceof Resource){
			Resource resource2 = (Resource)obj;
			if(this.firstResource.equals(resource2.getFirstResource())){
				if(this.lastResource.equals(resource2.getLastResource())){
					return false;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return firstResource+"#"+lastResource;
	}

	public String getShowLabel() {
		if(showLabel!=null){
			return showLabel;
		}else if(firstResource!=null&&lastResource==null){
			return firstResource;
		}else if(firstResource==null&&lastResource!=null){
			return lastResource;
		}
		return toString();
	}

	public void setShowLabel(String showLabel) {
		this.showLabel = showLabel;
	}

	public String getFirstResource() {
		return firstResource;
	}

	public void setFirstResource(String firstResource) {
		this.firstResource = firstResource;
	}

	public String getLastResource() {
		return lastResource;
	}

	public void setLastResource(String lastResource) {
		this.lastResource = lastResource;
	}

}
