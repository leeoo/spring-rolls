package com.wisdom.example.service.security;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.orm.SimpleOrmGenericDao;
import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.Role;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityResourceCache;
import com.wisdom.core.security.service.UserService;
import com.wisdom.core.utils.EncodeUtils;
import com.wisdom.core.utils.Page;
import com.wisdom.core.utils.PinYinUtils;

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
public class SecurityService implements UserService{
	
	private SimpleOrmGenericDao<User, Long> userDao;
	private SimpleOrmGenericDao<Role, Long> roleDao;
	private SimpleOrmGenericDao<Resource, Long> resourceDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		userDao = new SimpleOrmGenericDao<User, Long>(dataSource,User.class);
		roleDao = new SimpleOrmGenericDao<Role, Long>(dataSource,Role.class);
		resourceDao = new SimpleOrmGenericDao<Resource, Long>(dataSource,Resource.class);
	}
	
	@Transactional(readOnly=true)
	public User getUser(Long id) {
		return userDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Collection<User> getAllUsers() {
		return userDao.getAll();
	}
	
	@Transactional(readOnly=true)
	public Page getAllUsers(Page page) {
		return userDao.getAll(page);
	}
	
	@Transactional(readOnly=true)
	public User getUserByLoginName(String loginName) {
		try{
			return userDao.getAllByProperty("username", loginName).iterator().next();
		}catch (Exception e) {
		}
		return null;
	}

	public void saveUser(User user,Collection<Long> ids) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		Collection<String> pinyins = PinYinUtils.getPinyin(user.getCnname());
		if(pinyins!=null&&!pinyins.isEmpty()){
			user.setPyname(pinyins.iterator().next());
		}
		userDao._save(user);
		for(long roleId:ids){
			userDao.jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),roleId);
		}
	}

	public void saveUser(User user) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		Collection<String> pinyins = PinYinUtils.getPinyin(user.getCnname());
		if(pinyins!=null&&!pinyins.isEmpty()){
			user.setPyname(pinyins.iterator().next());
		}
		userDao._save(user);
		if(user.getRoles()!=null){
			for(Role role:user.getRoles()){
				userDao.jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),role.getId());
			}
		}
	}
	
	public void updateUser(User user,Collection<Long> ids) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, pyname=:pyname, email=:email,mobile=:mobile WHERE id=:id";
		userDao.jdbcTemplate.executeBean(sql, user);
		userDao.jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		if(ids!=null){
			for(long roleId:ids){
				userDao.jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),roleId);
			}
		}
	}

	public void updateUser(User user) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, pyname=:pyname, email=:email,mobile=:mobile WHERE id=:id";
		userDao.jdbcTemplate.executeBean(sql, user);
		userDao.jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		if(user.getRoles()!=null){
			for(Role role:user.getRoles()){
				userDao.jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),role.getId());
			}
		}
	}
	
	public void updateUserPassword(User user)throws Exception{
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		String sql="UPDATE t_system_user_info SET password=:password WHERE id=:id";
		userDao.jdbcTemplate.executeBean(sql, user);
	}
	
	public void updateUserEnabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=1 WHERE id=?";
		userDao.jdbcTemplate.executeArray(sql, id);
	}

	public void updateUserDisabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=0 WHERE id=?";
		userDao.jdbcTemplate.executeArray(sql, id);
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
		userDao.jdbcTemplate.executeBean(sql, user);
	}

	public void deleteUser(Long id) throws Exception{
		userDao.delete(id);
		userDao.jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",id);
	}

	@Transactional(readOnly=true)
	public Collection<Role> getAllRoles() {
		return roleDao.getAll();
	}
	
	@Transactional(readOnly=true)
	public Page getAllRoles(Page page) {
		return roleDao.getAll(page);
	}
	
	@Transactional(readOnly=true)
	public Collection<Role> getRolesByUserId(Long userId) {
		String sql="SELECT id, name, cnname FROM t_system_role_info WHERE id in(SELECT ur.role_id FROM t_system_user_role ur WHERE ur.user_id=?)";
		return roleDao.jdbcTemplate.findListBeanByArray(sql, Role.class,userId);
	}

	@Transactional(readOnly=true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	public void saveRole(Role role,Collection<Long> ids) throws Exception {
		roleDao._save(role);
		if(ids!=null){
			for(long resourceId:ids){
				roleDao.jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",role.getId(),resourceId);
			}
		}
	}
	
	public void saveRole(Role role)throws Exception{
		roleDao._save(role);
		if(role.getResources()!=null){
			for(Resource resource:role.getResources()){
				roleDao.jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",role.getId(),resource.getId());
			}
		}
	}
	
	public void updateRole(Role role,Collection<Long> ids) throws Exception {
		roleDao.update(role);
		roleDao.jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", role.getId());
		if(ids!=null){
			for(long resourceId:ids){
				roleDao.jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",role.getId(),resourceId);
			}
		}
	}

	public void deleteRole(Long id) throws Exception {
		roleDao.delete(id);
		roleDao.jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE role_id=?", id);
		roleDao.jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", id);
	}
	
	public void saveResource(Resource resource) throws Exception{
		resourceDao._save(resource);
		SecurityResourceCache.putCache(resource);
	}

	public void updateResource(Resource resource)throws Exception{
		resourceDao.update(resource);
		SecurityResourceCache.removeCache(resource.getPath());
		SecurityResourceCache.putCache(resource);
	}

	public void deleteResource(Long id)throws Exception{
		String path=getResource(id).getPath();
		resourceDao.delete(id);
		roleDao.jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE resource_id=?", id);
		SecurityResourceCache.removeCache(path);
	}

	@Transactional(readOnly=true)
	public Collection<Resource> getAllResources() {
		return resourceDao.getAll();
	}
	
	@Transactional(readOnly=true)
	public Page getAllResources(Page page) {
		return resourceDao.getAll(page);
	}

	@Transactional(readOnly=true)
	public Resource getResource(Long id) {
		return resourceDao.get(id);
	}

	@Transactional(readOnly=true)
	public Resource getResource(String resPath) {
		try{
			return resourceDao.getAllByProperty("path", resPath).iterator().next();
		}catch (Exception e) {
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public Collection<Resource> getResourcesByRoleId(long roleId){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource rr WHERE rr.role_id =?)";
		return resourceDao.jdbcTemplate.findListBeanByArray(sql, Resource.class,roleId);
	}

	@Transactional(readOnly=true)
	public Collection<Resource> getResourcesByUserName(String loginName){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource rr WHERE rr.role_id in(SELECT ur.role_id FROM t_system_user_role ur WHERE ur.user_id in(SELECT u.id FROM t_system_user_info u WHERE u.username=?)))";
		return resourceDao.jdbcTemplate.findListBeanByArray(sql, Resource.class,loginName);
	}
	
}
