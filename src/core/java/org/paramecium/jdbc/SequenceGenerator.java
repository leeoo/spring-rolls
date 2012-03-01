package org.paramecium.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.jdbc.datasource.MultiDataSourceFactory;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;


/**
 * 功 能 描 述:<br>
 * 系统级序列生成器,有些系统需要模拟序列，目前暂时没有分布式集群环境的生成方案。<br>
 * 请自行创建t_sequence表<br>
 *		主键为table_name，字符类型，长度在50左右长，要考虑某些长表名。<br>
 * 		seq_value，数值整形，长度自行控制，符合系统要求，如果数据量比较当，应该使用该库的长整型。<br>
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-1-6下午02:06:08
 * <br>项 目 信 息:paramecium:org.paramecium.jdbc.SequenceGenerator.java
 */
public class SequenceGenerator {
	
	private final static Log logger = LoggerFactory.getLogger();
	private static ConcurrentMap<String,Long> seqMap = new ConcurrentHashMap<String,Long>();
	private static ConcurrentMap<String,Long> maxValueMap = new ConcurrentHashMap<String,Long>();
	public static int sequenceCacheMax = 255;
	
	/**
	 * 获取新主键ID值
	 * @param idName
	 * @return 新主键值
	 */
	public static long getNextId(String dsName,String tableName){
		validationidName(tableName);
		Long currentSeq = seqMap.get(tableName);
		Long maxSeq = maxValueMap.get(tableName);
		if(currentSeq==null||maxSeq==null||currentSeq.longValue()>=maxSeq.longValue()){
			logger.debug(tableName+"表名称的自增序列值达到饱和或刚刚构建，正在重新取新值.");
			new SequenceGenerator().init(dsName,tableName);
			currentSeq = seqMap.get(tableName);
		}
		currentSeq = Long.valueOf(currentSeq.longValue() + 1);
		logger.debug(tableName+"表名称产生一个自增序列值"+currentSeq);
		seqMap.put(dsName.concat(".").concat(tableName), Long.valueOf(currentSeq));
		return currentSeq.longValue();
	}
	
	/**
	 * 获取当前主键值
	 * @param idName
	 * @return 当前主键值
	 */
	public static long getCurrentId(String dsName,String tableName){
		validationidName(tableName);
		Long currentSeq = seqMap.get(dsName.concat(".").concat(tableName));
		if(currentSeq==null){
			logger.error(tableName+"表名称没有加入序列生成器");
			throw new RuntimeException(tableName+"表名称没有加入序列生成器");
		}else{
			logger.debug(tableName+"表名称当前自增Sequence值为"+currentSeq);
		}
		return currentSeq.longValue();
	}
	
	private static void validationidName(String tableName){
		if(tableName==null||tableName.isEmpty()){
			throw new RuntimeException("表名称不能为空");
		}
	}

	
	private void init(String dsName,String tableName){
		Connection connection = null;
		try {
			connection = MultiDataSourceFactory.getDataSource(dsName).getConnection();
		} catch (SQLException e1) {
			logger.error(e1);
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			logger.error(e1);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT COUNT(0) ct FROM t_sequence WHERE table_name='"+dsName+"'");
		} catch (SQLException e1) {
			logger.error(e1);
		}
		int count = 0;
		try {
			if(rs.next()) {    
				count = rs.getInt("ct");
				rs.close();
			}
		} catch (SQLException e1) {
			logger.error(e1);
		}
		if(count<1){//一个新表刚刚使用该方法
			try {
				statement.execute("INSERT INTO t_sequence(table_name,seq_value) VALUES('"+tableName+"',"+sequenceCacheMax+")");
				connection.commit();
			} catch (Exception e) {
				logger.error(e);
			}
			seqMap.put(dsName.concat(".").concat(tableName), Long.valueOf(0));
			maxValueMap.put(dsName.concat(".").concat(tableName), Long.valueOf(sequenceCacheMax));
		}else{
			try {
				ResultSet rs2 = statement.executeQuery("SELECT seq_value sv FROM t_sequence WHERE table_name='"+tableName+"'");
				long  currentCount = 0;
				try {
					if(rs2.next()) {    
						currentCount = rs2.getInt("sv");
						rs2.close();
					}
				} catch (SQLException e1) {
					logger.error(e1);
				}
				long maxID = currentCount+sequenceCacheMax;
				statement.execute("UPDATE t_sequence SET seq_value="+maxID+" WHERE table_name='"+tableName+"'");
				connection.commit();
				maxValueMap.put(dsName.concat(".").concat(tableName), Long.valueOf(maxID));
				seqMap.put(dsName.concat(".").concat(tableName), Long.valueOf(currentCount));
			} catch (Exception e) {
				logger.error(e);
			}
		}
		try {
			connection.commit();
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			logger.error(e1);
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

}
