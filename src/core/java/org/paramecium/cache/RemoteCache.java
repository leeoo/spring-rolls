package org.paramecium.cache;

import java.util.LinkedHashMap;

public class RemoteCache <KEY extends Object,VALUE extends Object> extends BaseCache<KEY, VALUE>{
	
	private static final long serialVersionUID = 2195981385896026876L;

	public RemoteCache(String name,int initSize){
		this.maxSize = initSize;
		this.name = name;
		map = new LinkedHashMap<KEY,Element<KEY,VALUE>>();
	}
	
	public synchronized void put(KEY key, VALUE value) {
		if(this.maxSize < size()){
			remove(map.keySet().iterator().next());
		}
		super.put(key, value);
	}
	
	public synchronized void clear() {
		super.clear();
	}
	
	public synchronized void remove(Object key) {
		super.remove(key);
	}
	
	

}
