package com.demo.service.system;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.system.Role;

/**
 * 功 能 描 述:<br>
 * 角色管理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:40:09
 * <br>项 目 信 息:paramecium:com.demo.service.system.RoleService.java
 */
@Service
@Transactional
public class RoleService {

	private GenericOrmDao<Role, Integer> ormDao = new GenericOrmDao<Role, Integer>(Role.class);
	
	public int save(Role role) throws Exception{
		if(role.getAuth()==null||role.getAuth().isEmpty()){
			throw new RuntimeException("创建角色必须选择授权信息!");
		}
		int id = ormDao.insert(role).intValue();
		for(String auth:role.getAuth()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_role_auth(rolename,auth) VALUES(?,?)",role.getRolename(),auth);
		}
		return id;
	}
	
	public void update(Role role) throws Exception{
		if(role.getAuth()==null||role.getAuth().isEmpty()){
			throw new RuntimeException("修改角色必须选择授权信息!");
		}
		ormDao.update(role);
		ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_role_auth WHERE rolename=?",role.getRolename());
		for(String auth:role.getAuth()){
			ormDao.getGenericJdbcDao().executeDMLByArray("INSERT INTO t_role_auth(rolename,auth) VALUES(?,?)",role.getRolename(),auth);
		}
	}

	public void delete(String[] ids) throws Exception{
		for(String id : ids){
			ormDao.getGenericJdbcDao().executeDMLByArray("DELETE FROM t_role_auth WHERE rolename IN(SELECT sr.rolename FROM t_security_role sr WHERE sr.id=?)",Integer.parseInt(id));
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
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
	
	@Transactional(readOnly=true)
	public Page getAll(Page page,Role role){
		return ormDao.select(page,role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Collection<Role> getAll(){
		return (Collection<Role>) ormDao.getGenericJdbcDao().query("SELECT * FROM t_security_role", Role.class);
	}
	
}
