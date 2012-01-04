package org.paramecium.cache;

import java.io.Serializable;
/**
 * 功 能 描 述:<br>
 * 缓存子元素
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午03:09:55
 * <br>项 目 信 息:paramecium:org.paramecium.cache.Element.java
 */
public class Element implements Serializable, Cloneable {

	private static final long serialVersionUID = -6829091505751452721L;
	private long accessTime;
	private final Object key;
	private final Object value;
	
	public Element(Object key,Object value){
		this.key = key;
		this.value = value;
		this.accessTime = System.currentTimeMillis();
	}
	
	public boolean equals(Object object) {
        if (object == null || !(object instanceof Element)) {
            return false;
        }
        Element element = (Element) object;
        if (key == null || element.getKey() == null) {
            return false;
        }
        return key.equals(element.getKey());
    }
    
    public int hashCode() {
        return key.hashCode();
    }

	public Object getKey() {
		this.accessTime = System.currentTimeMillis();
		return key;
	}

	public Object getValue() {
		this.accessTime = System.currentTimeMillis();
		return value;
	}

	public long getAccessTime() {
		return accessTime;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ key = ").append(key).append(", value=").append(value).append(", accessTime = ").append(accessTime).append(" ]");
        return sb.toString();
    }
	
}
