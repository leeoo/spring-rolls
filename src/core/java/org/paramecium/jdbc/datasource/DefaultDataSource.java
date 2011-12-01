package org.paramecium.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.paramecium.commons.EncodeUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * 默认数据源实现(如果出现事务错误，请将poolBase基数调大一些)
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-1下午08:11:11</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.jdbc.datasource.DefaultDataSource.java</b>
 */
public class DefaultDataSource implements DataSource{
	private final static Log logger = LoggerFactory.getLogger();
	private boolean isInit = false;//判断是否实例化后被使用过
	private final static ConcurrentMap<String,ConcurrentMap<Connection,Long>> connectionPool = new ConcurrentHashMap<String,ConcurrentMap<Connection,Long>>();
	private PrintWriter printWriter;
	private int poolMax = 2;//最大连接数
	private int poolBase = 3;//控制并发基数
	private int connectLife = 120;//连接生命长度(秒)
	private int reconnectionTime = 10;//失效后多久重连(秒)
	private int poolThreadTime = 60;//连接池线程轮训间隔(秒)
	private String ds;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int loginTimeout = 5;
	
	public DefaultDataSource(){
		logger.debug("默认数据源被载入!");
		new Thread(new PoolHandlerThread()).start();
		logger.debug("默认数据库连接池连接释放线程监控已启动!");
	}
	
	/**
	 * 构建新连接到连接池中
	 */
	private synchronized void buildConnectionToPool(){
		if(connectionPool.get(ds).size()>=poolMax){
			return;
		}
		Connection connection = null;
		if(url!=null&&username!=null){
			try {
				if(!isInit){
					try {
						Class.forName(driverClassName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						logger.error(e);
					}
					isInit= true;
				}
				connection = DriverManager.getConnection(url, username, password);
				DriverManager.setLoginTimeout(getLoginTimeout());
				connection.setAutoCommit(true);
				connection.setReadOnly(false);
			} catch (SQLException e) {
				logger.error(e);
				connection = null;
				try {//如果连接失败，等待n秒钟再次连接
					Thread.sleep(reconnectionTime*1000);
				} catch (InterruptedException e1) {
					logger.error(e1);
				}
				return;
			}
			if(connection!=null){
				connectionPool.get(ds).put(connection, EncodeUtils.millisTime());
			}
		}
		logger.debug("新的连接被放入连接池,当前连接数："+connectionPool.get(ds).size());
	}
	
	/**
	 * 获得连接
	 */
	public synchronized Connection getConnection(){
		if(connectionPool.get(ds)==null){//由于是运行时构建数据源实例，很多属性需要之后填充,之所以不把此处放在构造方法中，是因为当时ds还没有被赋值.
			ConcurrentMap<Connection, Long> connectionMap = new ConcurrentHashMap<Connection, Long>();
			connectionPool.put(ds, connectionMap);
		}
		Connection connection = getConnectionFromPool();
		connectionPool.get(ds).put(connection, EncodeUtils.millisTime());
		return connection;
	}
	
	/**
	 * 从连接池中获得连接
	 * @return
	 */
	private synchronized Connection getConnectionFromPool(){
		for(Connection connection : connectionPool.get(ds).keySet()){
			try {
				if (!connection.getAutoCommit()) {//查看是否正在使用，如果多线程的话可能会出现失误，不过没关系，下面仍有处理
					continue;
				}
				long currentTime = EncodeUtils.millisTime();
				long lastUseTime = connectionPool.get(ds).get(connection);
				if((currentTime-lastUseTime)>poolBase){//只要有间隔，可错开多线程
					return connection;
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		try {
			Thread.sleep(1);//如果上面没有找到可用连接，稍等片刻，重现递归调用
			buildConnectionToPool();//休息片刻初始化一下连接池，看看还有没有可用的新连接可以创建
		} catch (Exception e) {
			logger.error(e);
		}
		return getConnectionFromPool();//
	}

	/**
	 * 数据源服务线程
	 */
	private class PoolHandlerThread implements Runnable {
		
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
					if(connectionPool.get(ds)==null||connectionPool.get(ds).isEmpty()){
						continue;
					}
					logger.debug("默认数据源连接池当前数量为："+connectionPool.get(ds).size());
					long currentTime = EncodeUtils.millisTime();
					for (Connection connection : connectionPool.get(ds).keySet()) {
						try {
							if(!connection.getAutoCommit()){
								continue;
							}
							int bw = (int)((currentTime-connectionPool.get(ds).get(connection))/1000);//最近一次获得连接时间距现在有多久
							if(bw<connectLife){//如果间隔时间小于指定时间,说明使用较为频繁,暂不做清理
								continue;
							}
							if (connection.getAutoCommit()) {
								connection.close();
								logger.debug("EN:A long time not use connection is colse! CN:一个长时间未被使用的Connection被关闭!");
							}
						} catch (SQLException e) {
							logger.equals(e);
						}
					}
					for(Connection connection : connectionPool.get(ds).keySet()){
						if(connection.isClosed()){
							connectionPool.get(ds).remove(connection);
							logger.debug("EN:A closed connection is clean from pool! CN:一个被关闭的Connection从连接池中清除!");
						}
					}
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
		
	}
	
	public Connection getConnection(String arg0, String arg1)throws SQLException {
		throw new UnsupportedOperationException("EN:The method can't using!;CN:不能使用该方法!;");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException("EN:The method can't using!;CN:不能使用该方法!;");
	}

	public PrintWriter getLogWriter() throws SQLException {
		return printWriter;
	}

	public void setLogWriter(PrintWriter _printWriter) throws SQLException {
		this.printWriter = _printWriter;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

}
