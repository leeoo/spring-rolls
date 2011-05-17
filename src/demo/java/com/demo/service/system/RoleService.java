package com.demo.service.system;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.security.annotation.Security;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.system.Role;

@Service
@Transactional
@Security
@ShowLabel(name="角色管理")
public class RoleService {

	private GenericOrmDao<Role, Integer> ormDao = new GenericOrmDao<Role, Integer>("ds1", Role.class);
	
	@ShowLabel(name="保存角色")
	public int save(Role role) throws SQLException{
		if(role.getAuth()==null||role.getAuth().isEmpty()){
			new SQLException("创建角色必须选择授权信息!");
		}
		int id = ormDao.insert(role).intValue();
		for(String auth:role.getAuth()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_role_auth(rolename,auth) VALUES(?,?)",role.getRolename(),auth);
		}
		return id;
	}
	
	@ShowLabel(name="修改角色")
	public void update(Role role) throws SQLException{
		if(role.getAuth()==null||role.getAuth().isEmpty()){
			new SQLException("修改角色必须选择授权信息!");
		}
		ormDao.update(role);
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_role_auth WHERE rolename=?",role.getRolename());
		for(String auth:role.getAuth()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_role_auth(rolename,auth) VALUES(?,?)",role.getRolename(),auth);
		}
	}

	@ShowLabel(name="删除角色")
	public void delete(String[] ids) throws SQLException{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
			ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_role_auth ra WHERE ra.rolename IN(SELECT sr.rolename FROM t_security_role sr WHERE sr.id=?)",id);
		}
	}
	
	@ShowLabel(name="获取角色详情")
	@Transactional(readOnly=true)
	public Role get(int id){
		Role role = ormDao.select(id);
		Collection<Map<String, Object>> maps = ormDao.getGenericJdbcDao().queryByArray("SELECT ra.auth auth FROM t_role_auth ra WHERE ra.rolename IN(SELECT sr.rolename FROM t_security_role sr WHERE sr.id=?)", id);
		Collection<String> auth = new HashSet<String>();
		for(Map<String,Object> map : maps){
			auth.add((String)map.get("auth"));
		}
		role.setAuth(auth);
		return role;
	}
	
	@ShowLabel(name="获取角色分页信息")
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao.select(page);
	}
	
	@ShowLabel(name="获取角色列表信息")
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Collection<Role> getAll(){
		return (Collection<Role>) ormDao.getGenericJdbcDao().query("SELECT * FROM t_security_role", Role.class);
	}
	
}
