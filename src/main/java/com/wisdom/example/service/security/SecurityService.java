package com.wisdom.example.service.security;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.Role;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityResourceCache;
import com.wisdom.core.security.service.UserService;
import com.wisdom.core.utils.EncodeUtils;
import com.wisdom.core.utils.Page;
import com.wisdom.example.dao.JdbcGenericSupportDao;

/**
 * 功 能 描 述:<br>
 * 用户安全业务类
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-8-27上午09:46:02
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.example.service.security.SecurityService.java
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class SecurityService extends JdbcGenericSupportDao implements UserService{
	
	@Transactional(readOnly=true)
	public User getUser(Long id) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info WHERE id=?";
		return (User)jdbcDao.findUniqueBeanByArray(sql, User.class, id);
	}
	
	@Transactional(readOnly=true)
	public Collection<User> getAllUsers() {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info";
		return jdbcDao.findListBean(sql, User.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllUsers(Page page) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info";
		return jdbcDao.findPageListBean(sql, User.class, page);
	}
	
	@Transactional(readOnly=true)
	public User getUserByLoginName(String loginName) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info WHERE username=?";
		return (User)jdbcDao.findUniqueBeanByArray(sql, User.class, loginName);
	}

	public void saveUser(User user,Collection<Long> ids) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		long userId=jdbcDao.insertBeanGetGeneratedKey("t_system_user_info", "id", user);
		for(long roleId:ids){
			jdbcDao.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",userId,roleId);
		}
	}

	public void saveUser(User user) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		long userId=jdbcDao.insertBeanGetGeneratedKey("t_system_user_info", "id", user);
		for(Role role:user.getRoles()){
			jdbcDao.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",userId,role.getId());
		}
	}
	
	public void updateUser(User user,Collection<Long> ids) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, email=:email,mobile=:mobile, enabled=:enabled WHERE id=:id";
		jdbcDao.executeBean(sql, user);
		jdbcDao.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		for(long roleId:ids){
			jdbcDao.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),roleId);
		}
	}

	public void updateUser(User user) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, email=:email,mobile=:mobile, enabled=:enabled WHERE id=:id";
		jdbcDao.executeBean(sql, user);
		jdbcDao.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		for(Role role:user.getRoles()){
			jdbcDao.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),role.getId());
		}
	}
	
	public void updateUserPassword(User user)throws Exception{
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		String sql="UPDATE t_system_user_info SET password=:password WHERE id=:id";
		jdbcDao.executeBean(sql, user);
	}
	
	public void updateUserEnabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=1 WHERE id=?";
		jdbcDao.executeArray(sql, id);
	}

	public void updateUserDisabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=0 WHERE id=?";
		jdbcDao.executeArray(sql, id);
	}

	public void updateUserPassword(User user,String oldPwd)throws Exception{
		String validatorInputPwd=EncodeUtils.getMd5PasswordEncoder(oldPwd, user.getUsername());
		String validatorOldPwd=getUser(user.getId()).getPassword();
		if(!validatorInputPwd.equals(validatorOldPwd)){
			throw new Exception("旧密码输入错误!");
		}
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		String sql="UPDATE t_system_user_info SET password=:password WHERE id=:id";
		jdbcDao.executeBean(sql, user);
	}

	public void deleteUser(Long id) throws Exception{
		jdbcDao.executeArray("DELETE FROM t_system_user_info WHERE id=?",id);
		jdbcDao.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",id);
	}

	@Transactional(readOnly=true)
	public Collection<Role> getAllRoles() {
		return jdbcDao.findListBean("SELECT id, name, cnname FROM t_system_role_info", Role.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllRoles(Page page) {
		String sql="SELECT id, name, cnname FROM t_system_role_info";
		return jdbcDao.findPageListBean(sql, Role.class, page);
	}
	
	@Transactional(readOnly=true)
	public Collection<Role> getRolesByUserId(long userId) {
		String sql="SELECT id, name, cnname FROM t_system_role_info WHERE id in(SELECT ur.role_id FROM t_system_user_role as ur WHERE ur.user_id=?)";
		return jdbcDao.findListBeanByArray(sql, Role.class,userId);
	}

	@Transactional(readOnly=true)
	public Role getRole(Long id) {
		String sql="SELECT id, name, cnname FROM t_system_role_info WHERE id=?";
		return (Role)jdbcDao.findUniqueBeanByArray(sql, Role.class, id);
	}

	public void saveRole(Role role,Collection<Long> ids) throws Exception {
		long roleId=jdbcDao.insertBeanGetGeneratedKey("t_system_role_info", "id", role);
		for(long resourceId:ids){
			jdbcDao.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",roleId,resourceId);
		}
	}
	
	public void saveRole(Role role)throws Exception{
		long roleId=jdbcDao.insertBeanGetGeneratedKey("t_system_role_info", "id", role);
		for(Resource resource:role.getResources()){
			jdbcDao.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",roleId,resource.getId());
		}
	}
	
	public void updateRole(Role role,Collection<Long> ids) throws Exception {
		jdbcDao.executeBean("UPDATE t_system_role_info SET name=:name, cnname=:cnname WHERE id=:id", role);
		jdbcDao.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", role.getId());
		for(long resourceId:ids){
			jdbcDao.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",role.getId(),resourceId);
		}
	}

	public void deleteRole(Long id) throws Exception {
		jdbcDao.executeArray("DELETE FROM t_system_role_info WHERE id=?", id);
		jdbcDao.executeArray("DELETE FROM t_system_user_role WHERE role_id=?", id);
		jdbcDao.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", id);
	}
	
	public void saveResource(Resource resource) throws Exception{
		String sql="INSERT INTO t_system_resource_info(name, path,cnname) VALUES(:name,:path,:cnname)";
		jdbcDao.executeBean(sql, resource);
		SecurityResourceCache.putCache(resource);
	}

	public void updateResource(Resource resource)throws Exception{
		String sql="UPDATE t_system_resource_info SET name=:name, path=:path,cnname=:cnname WHERE id=:id";
		jdbcDao.executeBean(sql, resource);
		SecurityResourceCache.removeCache(resource.getPath());
		SecurityResourceCache.putCache(resource);
	}

	public void deleteResource(Long id)throws Exception{
		String path=getResource(id).getPath();
		jdbcDao.executeArray("DELETE FROM t_system_resource_info WHERE id=?", id);
		jdbcDao.executeArray("DELETE FROM t_system_role_resource WHERE resource_id=?", id);
		SecurityResourceCache.removeCache(path);
	}

	@Transactional(readOnly=true)
	public Collection<Resource> getAllResources() {
		return jdbcDao.findListBean("SELECT id, name, path, cnname FROM t_system_resource_info", Resource.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllResources(Page page) {
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info";
		return jdbcDao.findPageListBean(sql, Resource.class, page);
	}

	@Transactional(readOnly=true)
	public Resource getResource(Long id) {
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id=?";
		return (Resource)jdbcDao.findUniqueBeanByArray(sql, Resource.class,id);
	}

	@Transactional(readOnly=true)
	public Resource getResource(String resPath) {
		return (Resource)jdbcDao.findUniqueBeanByArray("SELECT id, name, path, cnname FROM t_system_resource_info WHERE path=?", Resource.class, resPath);
	}
	
	@Transactional(readOnly=true)
	public Collection<Resource> getResourcesByRoleId(long roleId){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource as rr WHERE rr.role_id =?)";
		return jdbcDao.findListBeanByArray(sql, Resource.class,roleId);
	}

	@Transactional(readOnly=true)
	public Collection<Resource> getResourcesByUserName(String loginName){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource AS rr WHERE rr.role_id in(SELECT ur.role_id FROM t_system_user_role AS ur WHERE ur.user_id in(SELECT u.id FROM t_system_user_info AS u WHERE u.username=?)))";
		return jdbcDao.findListBeanByArray(sql, Resource.class,loginName);
	}
	
}
