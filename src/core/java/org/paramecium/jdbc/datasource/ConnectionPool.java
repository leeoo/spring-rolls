package org.paramecium.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import org.paramecium.commons.EncodeUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 连接池
 * @author caoyang
 */
public class ConnectionPool {
	private final static Log logger = LoggerFactory.getLogger();
	private final ConcurrentMap<DefineConnection,Long> connectionPool = new ConcurrentHashMap<DefineConnection,Long>();
	private final ConcurrentMap<Integer,DefineConnection> connectionPoolIndex = new ConcurrentHashMap<Integer,DefineConnection>();
	private int busyConnectTimeOut = 1800;//被占用连接多久超时(秒)
	private int poolMax = 5;//最大连接数
	private int connectLife = 120;//连接生命长度(秒)
	private int poolThreadTime = 60;//连接池线程轮训间隔(秒)
	private final ReentrantLock lock = new ReentrantLock();
	private volatile int busyCount = 0;
	private Map<String,Object> poolStatus = new HashMap<String, Object>();
	
	/**
	 * 构造方法，用于初始化
	 * @param poolMax
	 * @param connectLife
	 * @param poolThreadTime
	 */
	public ConnectionPool(int poolMax,int connectLife,int poolThreadTime,int busyConnectTimeOut){
		this.poolMax = poolMax;
		this.connectLife = connectLife;
		this.poolThreadTime = poolThreadTime;
		this.busyConnectTimeOut = busyConnectTimeOut;
		new Thread(new PoolHandlerThread()).start();
	}
	
	/**
	 * 统计连接池信息使用
	 * @return
	 */
	public Map<String,Object> getPoolStatus(){
		poolStatus.put("currentPoolMax", connectionPool.size());
		poolStatus.put("busyPoolMax", this.busyCount);
		long currentTime = EncodeUtils.millisTime();
		int[] idleTimes = new int[connectionPool.size()];
		int[] busyTimes = new int[this.busyCount];
		Iterator<DefineConnection> it = connectionPool.keySet().iterator();
		int j = 0;
		for(int i=0;i<idleTimes.length;i++){
			DefineConnection conn = it.next();
			idleTimes[i] = (int)(currentTime - connectionPool.get(conn))/1000;
			if(conn.isBusy()){
				busyTimes[j++] =  (int)(currentTime - conn.busyLong)/1000;
			}
		}
		poolStatus.put("idleTimes", idleTimes);
		poolStatus.put("busyTimes", busyTimes);
		return poolStatus;
	}
	
	/**
	 * 清理连接池状态
	 */
	public void cleanPoolStatus(){
		poolStatus.clear();
	}
	
	/**
	 * 判断是否满了
	 * @return
	 */
	public boolean isFull(){
		lock.lock();
		try{
			if(connectionPool.size()>=poolMax){
				return true;
			}
			return false;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 放入一个连接
	 * @param connection
	 */
	public void put(Connection connection){
		lock.lock();
		try{
			if(connection==null||connectionPool.size()>=poolMax){
				return;
			}
			DefineConnection defineConnection = new DefineConnection(connection);
			defineConnection.setIdle();
			defineConnection.reset();
			connectionPool.put(defineConnection, EncodeUtils.millisTime());
			connectionPoolIndex.put(connection.hashCode(), defineConnection);
			logger.debug("新的连接"+connection.hashCode()+"被放入连接池,当前连接数:"+connectionPool.size());
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 使用完后归还池
	 * @param connection
	 */
	public void replace(Connection connection){
		lock.lock();
		try{
			DefineConnection defineConnection = connectionPoolIndex.get(connection.hashCode());
			if(defineConnection!=null){
				defineConnection.setIdle();
				defineConnection.reset();
				busyCount--;
				connectionPool.put(defineConnection,EncodeUtils.millisTime());
				logger.debug("一个使用完毕的连接被归还:"+connection.hashCode());
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 获得一个连接，无需等待，如果都繁忙，返回空
	 * @return
	 */
	public Connection getConnection(){
		lock.lock();
		try{
			for(DefineConnection connection : connectionPool.keySet()){
				if(!connection.isBusy()){
					connection.setBusy();
					connection.reset();
					busyCount++;
					Connection conn = connection.getConnection();
					logger.debug("一个空闲的连接被获取:"+conn.hashCode());
					return conn;
				}
			}
			return null;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 清理池
	 */
	public void clear(){
		for(DefineConnection connection : connectionPool.keySet()){
			if(!connection.isBusy()){
				try {
					connection.getConnection().close();
					connectionPool.remove(connection);
					connectionPoolIndex.remove(connection.getConnection().hashCode());
					logger.debug("连接池的连接"+connection.getConnection().hashCode()+"被手动移除!");
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			//剩下的繁忙连接交给清理线程
		}
	}
	
	/**
	 * 连接池状态
	 * @author caoyang
	 *
	 */
	public enum CONNECTION_STATUS{
		BUSY,IDLE;
	}
	
	/**
	 * 自定义连接
	 * @author caoyang
	 *
	 */
	private class DefineConnection{
		private final ReentrantLock lock = new ReentrantLock();
		private Connection connection;
		private volatile CONNECTION_STATUS status = CONNECTION_STATUS.IDLE;
		private volatile long busyLong = 0L;
		
		public DefineConnection(Connection connection){
			this.connection = connection;
		}
		
		/**
		 * 设置为繁忙
		 */
		public void setBusy(){
			lock.lock();
			try{
				this.status = CONNECTION_STATUS.BUSY;
				this.busyLong = EncodeUtils.millisTime();
			} finally {
				lock.unlock();
			}
		}
		
		/**
		 * 设置为空闲
		 */
		public void setIdle(){
			lock.lock();
			try{
				this.status = CONNECTION_STATUS.IDLE;
				this.busyLong = 0L;
			} finally {
				lock.unlock();
			}
		}
		
		/**
		 * 判断是否繁忙
		 * @return
		 */
		public boolean isBusy(){
			lock.lock();
			try{
				if(this.status == CONNECTION_STATUS.BUSY){
					return true;
				}
				return false;
			} finally {
				lock.unlock();
			}
		}
		
		/**
		 * 获得连接获取时间
		 * @return
		 */
		public long getBusyLong(){
			lock.lock();
			try{
				return this.busyLong;
			} finally {
				lock.unlock();
			}
		}
		
		/**
		 * 获得连接
		 * @return
		 */
		public Connection getConnection(){
			lock.lock();
			try{
				return this.connection;
			} finally {
				lock.unlock();
			}
		}
		
		public void reset(){
			lock.lock();
			try{
				this.connection.setAutoCommit(false);
				this.connection.setReadOnly(false);
			} catch (SQLException e){
				logger.error(e);
			}finally {
				lock.unlock();
			}
		}
		
	}
	
	/**
	 * 数据源服务线程
	 */
	private class PoolHandlerThread implements Runnable {
		
		public PoolHandlerThread(){
			logger.debug(this.hashCode()+"数据库连接池连接释放线程监控已启动!");
		}
		
		public void run() {
			clearPool();
		}
		
		/**
		 * 清理连接池
		 * 一定时间之内没有人使用连接池，将会把所有连接关闭，并清空连接池
		 */
		private void clearPool(){
			while (true) {
				try {
					Thread.sleep(poolThreadTime * 1000);// 指定轮询间隔清理使用完毕的Connection
					if(connectionPool==null||connectionPool.isEmpty()){
						continue;
					}
					logger.debug("##连接池监控"+this.hashCode()+"## 默认数据源连接池当前数量为:"+connectionPool.size() +" 繁忙连接数量:"+busyCount);
					long currentTime = EncodeUtils.millisTime();
					for (DefineConnection connection : connectionPool.keySet()) {
						try {
							if(connection.isBusy()&&busyConnectTimeOut>0){//如果不大于0，不管占用多久都不释放
								int bl = (int)((currentTime-connection.getBusyLong())/1000);//获得多久没有结束的连接时长
								logger.debug("##连接池监控"+this.hashCode()+"## 一个繁忙的连接:"+connection.getConnection().hashCode()+"已经被占用了"+bl+"秒");
								if(bl<busyConnectTimeOut){
									continue;
								}else{
									connection.setIdle();//释放掉，然后下面进行回收.
									logger.debug("##连接池监控"+this.hashCode()+"## 一个繁忙的连接:"+connection.getConnection().hashCode()+"被连接池强制回收!由于该连接占用时间较长,自动转为闲置连接被清理，不被重复利用!");
									busyCount--;
								}
							}
							int bw = (int)((currentTime-connectionPool.get(connection))/1000);//最近一次获得连接时间距现在有多久
							logger.debug("##连接池监控"+this.hashCode()+"## 一个空闲的连接:"+connection.getConnection().hashCode()+"已经闲置了"+bw+"秒");
							if(bw<connectLife){//如果间隔时间小于指定时间,说明使用较为频繁,暂不做清理
								continue;
							}
							if (!connection.isBusy()) {
								connection.getConnection().close();
								logger.debug("##连接池监控"+this.hashCode()+"## EN:A long time not use connection is colse! CN:一个长时间未被使用的Connection被关闭!"+connection.getConnection().hashCode());
							}
						} catch (SQLException e) {
							logger.equals(e);
						}
					}
					for(DefineConnection connection : connectionPool.keySet()){
						if(connection.getConnection().isClosed()){
							connectionPoolIndex.remove(connection.getConnection().hashCode());
							connectionPool.remove(connection);
							logger.debug("##连接池监控"+this.hashCode()+"## EN:A closed connection is clean from pool! CN:一个被关闭的Connection从连接池中清除!"+connection.getConnection().hashCode());
						}
					}
				} catch (Exception ex) {
					logger.error(ex);
					try {
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	public int getPoolMax() {
		return poolMax;
	}

	public void setPoolMax(int poolMax) {
		this.poolMax = poolMax;
	}

	public int getConnectLife() {
		return connectLife;
	}

	public void setConnectLife(int connectLife) {
		this.connectLife = connectLife;
	}

	public int getPoolThreadTime() {
		return poolThreadTime;
	}

	public void setPoolThreadTime(int poolThreadTime) {
		this.poolThreadTime = poolThreadTime;
	}

	public int getBusyConnectTimeOut() {
		return busyConnectTimeOut;
	}

	public void setBusyConnectTimeOut(int busyConnectTimeOut) {
		this.busyConnectTimeOut = busyConnectTimeOut;
	}
	
}
