package org.paramecium.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

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
	private PrintWriter printWriter;
	private int poolMax = 2;//最大连接数
	private int connectLife = 120;//连接生命长度(秒)
	private int poolThreadTime = 60;//连接池线程轮训间隔(秒)
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int busyConnectTimeOut = 1800;//被占用连接多久释放
	private int loginTimeout = 5;
	private boolean isInit = false;//判断是否实例化后被使用过
	private ConnectionPool connectionPool;
	
	private int busyPoolMax;
	private int currentPoolMax;
	private int[] idleTimes;
	private int[] busyTimes;
	
	public DefaultDataSource(){
		logger.debug("默认数据源被载入!");
	}
	
	/**
	 * 刷新连接池的状态,供连接池状使用
	 */
	public synchronized void refreshPoolStatus(){
		init();
		Map<String,Object> stauts = connectionPool.getPoolStatus();
		busyPoolMax = (Integer)stauts.get("busyPoolMax");
		currentPoolMax = (Integer)stauts.get("currentPoolMax");
		idleTimes = (int[])stauts.get("idleTimes");
		busyTimes = (int[])stauts.get("busyTimes");
		connectionPool.cleanPoolStatus();
	}
	
	/**
	 * 初始化
	 */
	public synchronized void init(){
		if(connectionPool == null){//由于是运行时构建数据源实例，很多属性需要之后填充,之所以不把此处放在构造方法中，是因为当时ds还没有被赋值.
			connectionPool = new ConnectionPool(poolMax, connectLife, poolThreadTime,busyConnectTimeOut);
		}
	}
	
	/**
	 * 使用完后放回
	 * @param connection
	 */
	public synchronized void replace(Connection connection){
		init();
		connectionPool.replace(connection);
	}
	
	/**
	 * 获得连接
	 */
	public synchronized Connection getConnection(){
		init();
		return getConnectionFromPool();
	}
	
	/**
	 * 构建新连接到连接池中
	 */
	private synchronized DataSourceMode buildConnectionToPool(){
		if(connectionPool.isFull()){
			return DataSourceMode.FULL;
		}
		Connection connection = null;
		if(url!=null&&username!=null){
			try {
				if(!isInit){
					try {
						Class.forName(driverClassName);
					} catch (ClassNotFoundException e) {
						logger.error(e);
					}
					isInit= true;
				}
				connection = DriverManager.getConnection(url, username, password);//Class.forName在拼装连接属性时已经调用
				DriverManager.setLoginTimeout(getLoginTimeout());
				connection.setAutoCommit(true);
				connection.setReadOnly(false);
			} catch (SQLException e) {
				logger.error(e);
				if(connection != null){
					try {
						connection.close();
					} catch (SQLException e1) {
						logger.error(e1);
					}
					connection = null;
				}
				return DataSourceMode.ERROR;
			}
			if(connection!=null){
				connectionPool.put(connection);
			}
		}
		return DataSourceMode.SUCCESS;
	}
	
	
	/**
	 * 从连接池中获得连接
	 * @return
	 */
	private synchronized Connection getConnectionFromPool(){
		Connection connection = connectionPool.getConnection();
		if(connection == null){//如果没有连接，可能连接不够用了
			if(!connectionPool.isFull()){//判断是否连接池中的被释放的连接过多，不够使用
				DataSourceMode mode = buildConnectionToPool();//重新构建几个连接并放入池中
				if(mode.equals(DataSourceMode.ERROR)){//如果构建出错直接返回空
					return null;
				}
				return getConnectionFromPool();//递归
			}
			throw new ConnectionPoolException("连接池中已无可用连接分配，目前为高并发的情况，请重新调整连接池数量!");
		}
		return connection;
	}
	
	/**
	 * 清理某数据源连接池
	 * @param dataSourceName
	 */
	public void clearPool(){
		init();
		connectionPool.clear();
	}
	
	public int getBusyPoolMax() {
		return busyPoolMax;
	}

	public void setBusyPoolMax(int busyPoolMax) {
		this.busyPoolMax = busyPoolMax;
	}

	public int getCurrentPoolMax() {
		return currentPoolMax;
	}

	public void setCurrentPoolMax(int currentPoolMax) {
		this.currentPoolMax = currentPoolMax;
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

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public int getPoolMax() {
		return poolMax;
	}

	public void setPoolMax(int poolMax) {
		this.poolMax = poolMax;
		init();
		connectionPool.setPoolMax(poolMax);
	}

	public int getPoolThreadTime() {
		return poolThreadTime;
	}

	public void setPoolThreadTime(int poolThreadTime) {
		this.poolThreadTime = poolThreadTime;
		init();
		connectionPool.setPoolThreadTime(poolThreadTime);
	}

	public int getConnectLife() {
		return connectLife;
	}
	
	public void setConnectLife(int connectLife) {
		this.connectLife = connectLife;
		init();
		connectionPool.setConnectLife(connectLife);
	}


	public int getBusyConnectTimeOut() {
		return busyConnectTimeOut;
	}


	public void setBusyConnectTimeOut(int busyConnectTimeOut) {
		this.busyConnectTimeOut = busyConnectTimeOut;
		init();
		connectionPool.setBusyConnectTimeOut(busyConnectTimeOut);
	}

	public int[] getIdleTimes() {
		return idleTimes;
	}

	public void setIdleTimes(int[] idleTimes) {
		this.idleTimes = idleTimes;
	}

	public int[] getBusyTimes() {
		return busyTimes;
	}

	public void setBusyTimes(int[] busyTimes) {
		this.busyTimes = busyTimes;
	}
	
}
