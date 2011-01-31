package com.wisdom.core.orm.sharding;

import java.util.concurrent.ConcurrentMap;

import com.wisdom.core.annotation.Sharding.MODE;

public class ShardingMode {
	
	public static ConcurrentMap<String, Integer> getShardings(MODE mode,String originalTableName){
		return null;
	}
	
	public static String getShardingTableName(MODE mode,String originalTableName){
		return null;
	}
	
}
