package com.demo.service.system;

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
@ShowLabel("用户管理")
public class UserService {
	
	private GenericOrmDao<User, Integer> ormDao = new GenericOrmDao<User, Integer>(User.class);
	
	@ShowLabel("保存用户")
	public int save(User user) throws Exception{
		if(user.getRoles()==null||user.getRoles().isEmpty()){
			new Exception("创建用户必须选择角色!");
		}
		int id = ormDao.insert(user).intValue();
		for(Role role:user.getRoles()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_user_role(username,rolename) VALUES(?,?)",user.getUsername(),role.getRolename());
		}
		return id;
	}
	
	@ShowLabel("修改用户")
	public void update(User user) throws Exception{
		if(user.getRoles()==null||user.getRoles().isEmpty()){
			new Exception("修改用户必须选择角色!");
		}
		ormDao.update(user);
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_user_role WHERE user?",user.getUsername());
		for(Role role:user.getRoles()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_user_role(username,rolename) VALUES(?,?)",user.getUsername(),role.getRolename());
		}
	}

	@ShowLabel("删除用户")
	public void delete(String[] ids) throws Exception{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_user_role WHERE username IN(SELECT su.username FROM t_security_user su WHERE su.id=?)",Integer.parseInt(id));
			ormDao.delete(Integer.parseInt(id));
		}
	}

	@ShowLabel("冻结用户")
	public void disabled(String[] ids) throws Exception{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_security_user SET enabled=0 WHERE id=?",Integer.parseInt(id));
		}
	}

	@ShowLabel("解冻用户")
	public void enabled(String[] ids) throws Exception{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("UPDATE t_security_user SET enabled=1 WHERE id=?",Integer.parseInt(id));
		}
	}
	
	@ShowLabel("获取用户详情")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User get(int id){
		User user = ormDao.select(id);
		Collection<Role> roles = (Collection<Role>) ormDao.getGenericJdbcDao().queryByArray("SELECT * FROM t_security_role sr WHERE sr.rolename IN(SELECT ur.rolename FROM t_user_role ur WHERE ur.username IN(SELECT su.username FROM t_security_user su WHERE su.id=?))", Role.class, id);
		user.setRoles(roles);
		return user;
	}
	
	@Transactional(readOnly=true)
	@Security(false)
	public User getUser(String username){
		return (User)ormDao.getGenericJdbcDao().queryUniqueByArray("SELECT * FROM t_security_user WHERE user?", User.class,username);
	}

	@Transactional(readOnly=true)
	@Security(false)
	public Collection<Resource> getUserAuth(String username){
		Collection<Map<String, Object>> maps = ormDao.getGenericJdbcDao().queryByArray("SELECT ra.auth auth FROM t_role_auth ra WHERE ra.rolename IN(SELECT ur.rolename FROM t_user_role ur WHERE ur.user?)", username);
		Collection<Resource> resources = new HashSet<Resource>();
		for(Map<String,Object> map : maps){
			Resource resource = new Resource((String)map.get("auth"));
			resources.add(resource);
		}
		return resources;
	}
	
	@ShowLabel("获取用户分页信息")
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao.select(page);
	}

}
