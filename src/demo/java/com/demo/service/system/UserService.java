package com.demo.service.system;

import java.sql.SQLException;
import java.util.Collection;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
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
	
	@ShowLabel(name="获取用户详情")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User get(int id){
		User user = ormDao.select(id);
		Collection<Role> roles = (Collection<Role>) ormDao.getGenericJdbcDao().queryByArray("SELECT * FROM t_security_role sr WHERE sr.rolename IN(SELECT ur.rolename FROM t_user_role ur WHERE ur.username IN(SELECT su.username FROM t_security_user su WHERE su.id=?))", Role.class, id);
		user.setRoles(roles);
		return user;
	}
	
	@ShowLabel(name="获取用户分页信息")
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao.select(page);
	}

}
