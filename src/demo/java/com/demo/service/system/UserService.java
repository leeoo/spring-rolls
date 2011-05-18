package com.demo.service.system;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.security.Resource;
import org.paramecium.security.annotation.Security;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.system.Role;
import com.demo.entity.system.User;

@Service
@Transactional
@Security
@ShowLabel(name="用户管理")
public class UserService {
	
	private GenericOrmDao<User, Integer> ormDao = new GenericOrmDao<User, Integer>("ds1", User.class);
	
	@ShowLabel(name="保存用户")
	public int save(User user) throws SQLException{
		if(user.getRoles()==null||user.getRoles().isEmpty()){
			new SQLException("创建用户必须选择角色!");
		}
		int id = ormDao.insert(user).intValue();
		for(Role role:user.getRoles()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_user_role(username,rolename) VALUES(?,?)",user.getUsername(),role.getRolename());
		}
		return id;
	}
	
	@ShowLabel(name="修改用户")
	public void update(User user) throws SQLException{
		if(user.getRoles()==null||user.getRoles().isEmpty()){
			new SQLException("修改用户必须选择角色!");
		}
		ormDao.update(user);
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_user_role WHERE username=?",user.getUsername());
		for(Role role:user.getRoles()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_user_role(username,rolename) VALUES(?,?)",user.getUsername(),role.getRolename());
		}
	}

	@ShowLabel(name="删除用户")
	public void delete(String[] ids) throws SQLException{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_user_role WHERE username IN(SELECT su.username FROM t_security_user su WHERE su.id=?)",Integer.parseInt(id));
			ormDao.delete(Integer.parseInt(id));
		}
	}

	@ShowLabel(name="冻结用户")
	public void disabled(String[] ids) throws SQLException{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_security_user SET enabled=0 WHERE id=?",Integer.parseInt(id));
		}
	}

	@ShowLabel(name="解冻用户")
	public void enabled(String[] ids) throws SQLException{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_security_user SET enabled=1 WHERE id=?",Integer.parseInt(id));
		}
	}
	
	@ShowLabel(name="获取用户详情")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User get(int id){
		User user = ormDao.select(id);
		Collection<Role> roles = (Collection<Role>) ormDao.getGenericJdbcDao().queryByArray("SELECT * FROM t_security_role sr WHERE sr.rolename IN(SELECT ur.rolename FROM t_user_role ur WHERE ur.username IN(SELECT su.username FROM t_security_user su WHERE su.id=?))", Role.class, id);
		user.setRoles(roles);
		return user;
	}
	
	@Transactional(readOnly=true)
	@Security(protecting=false)
	public User getUser(String username){
		return (User)ormDao.getGenericJdbcDao().queryUniqueByArray("SELECT * FROM t_security_user WHERE username=?", User.class,username);
	}

	@Transactional(readOnly=true)
	@Security(protecting=false)
	public Collection<Resource> getUserAuth(String username){
		Collection<Map<String, Object>> maps = ormDao.getGenericJdbcDao().queryByArray("SELECT auth FROM t_role_auth ra WHERE ra.rolename IN(SELECT ur.rolename FROM t_user_role ur WHERE ur.username)", username);
		Collection<Resource> resources = new HashSet<Resource>();
		for(Map<String,Object> map : maps){
			Resource resource = new Resource((String)map.get("auth"));
			resources.add(resource);
		}
		return resources;
	}
	
	@ShowLabel(name="获取用户分页信息")
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao.select(page);
	}

}
