package com.wisdom.core.security.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.dao.GenericDaoFactory;
import com.wisdom.core.dao.JdbcTemplate;
import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.Role;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityResourceCache;
import com.wisdom.core.utils.EncodeUtils;
import com.wisdom.core.utils.Page;

/**
 * 功能描述：
 * 用户安全业务类
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午07:16:10</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.service/UserService.java</b>
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserService{
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate=GenericDaoFactory.getJdbcTemplate(dataSource);
	}
	
	@Transactional(readOnly=true)
	public User getUser(Long id) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info WHERE id=?";
		return (User)jdbcTemplate.findUniqueBeanByArray(sql, User.class, id);
	}
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers() {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info";
		return jdbcTemplate.findListBean(sql, User.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllUsers(Page page) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info";
		return jdbcTemplate.findPageListBean(sql, User.class, page);
	}
	
	@Transactional(readOnly=true)
	public User getUserByLoginName(String loginName) {
		String sql="SELECT id, username, password, cnname, email,mobile, enabled, account_expired, account_locked, credentials_expired FROM t_system_user_info WHERE username=?";
		return (User)jdbcTemplate.findUniqueBeanByArray(sql, User.class, loginName);
	}

	public void saveUser(User user,List<Long> ids) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		long userId=jdbcTemplate.insertBeanGetGeneratedKey("t_system_user_info", "id", user);
		for(long roleId:ids){
			jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",userId,roleId);
		}
	}

	public void saveUser(User user) throws Exception {
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		long userId=jdbcTemplate.insertBeanGetGeneratedKey("t_system_user_info", "id", user);
		for(Role role:user.getRoles()){
			jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",userId,role.getId());
		}
	}
	
	public void updateUser(User user,List<Long> ids) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, email=:email,mobile=:mobile, enabled=:enabled WHERE id=:id";
		jdbcTemplate.executeBean(sql, user);
		jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		for(long roleId:ids){
			jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),roleId);
		}
	}

	public void updateUser(User user) throws Exception {
		String sql="UPDATE t_system_user_info SET cnname=:cnname, email=:email,mobile=:mobile, enabled=:enabled WHERE id=:id";
		jdbcTemplate.executeBean(sql, user);
		jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",user.getId());
		for(Role role:user.getRoles()){
			jdbcTemplate.executeArray("INSERT INTO t_system_user_role(user_id,role_id) VALUES(?,?)",user.getId(),role.getId());
		}
	}
	
	public void updateUserPassword(User user)throws Exception{
		String psw=EncodeUtils.getMd5PasswordEncoder(user.getPassword(), user.getUsername());
		user.setPassword(psw);
		String sql="UPDATE t_system_user_info SET password=:password WHERE id=:id";
		jdbcTemplate.executeBean(sql, user);
	}
	
	public void updateUserEnabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=1 WHERE id=?";
		jdbcTemplate.executeArray(sql, id);
	}

	public void updateUserDisabled(Long id)throws Exception{
		String sql="UPDATE t_system_user_info SET enabled=0 WHERE id=?";
		jdbcTemplate.executeArray(sql, id);
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
		jdbcTemplate.executeBean(sql, user);
	}

	public void deleteUser(Long id) throws Exception{
		jdbcTemplate.executeArray("DELETE FROM t_system_user_info WHERE id=?",id);
		jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE user_id=?",id);
	}

	@Transactional(readOnly=true)
	public List<Role> getAllRoles() {
		return jdbcTemplate.findListBean("SELECT id, name, cnname FROM t_system_role_info", Role.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllRoles(Page page) {
		String sql="SELECT id, name, cnname FROM t_system_role_info";
		return jdbcTemplate.findPageListBean(sql, Role.class, page);
	}
	
	@Transactional(readOnly=true)
	public List<Role> getRolesByUserId(long userId) {
		String sql="SELECT id, name, cnname FROM t_system_role_info WHERE id in(SELECT ur.role_id FROM t_system_user_role as ur WHERE ur.user_id=?)";
		return jdbcTemplate.findListBeanByArray(sql, Role.class,userId);
	}

	@Transactional(readOnly=true)
	public Role getRole(Long id) {
		String sql="SELECT id, name, cnname FROM t_system_role_info WHERE id=?";
		return (Role)jdbcTemplate.findUniqueBeanByArray(sql, Role.class, id);
	}

	public void saveRole(Role role,List<Long> ids) throws Exception {
		long roleId=jdbcTemplate.insertBeanGetGeneratedKey("t_system_role_info", "id", role);
		for(long resourceId:ids){
			jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",roleId,resourceId);
		}
	}
	
	public void saveRole(Role role)throws Exception{
		long roleId=jdbcTemplate.insertBeanGetGeneratedKey("t_system_role_info", "id", role);
		for(Resource resource:role.getResources()){
			jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",roleId,resource.getId());
		}
	}
	
	public void updateRole(Role role,List<Long> ids) throws Exception {
		jdbcTemplate.executeBean("UPDATE t_system_role_info SET name=:name, cnname=:cnname WHERE id=:id", role);
		jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", role.getId());
		for(long resourceId:ids){
			jdbcTemplate.executeArray("INSERT INTO t_system_role_resource(role_id,resource_id) VALUES(?,?)",role.getId(),resourceId);
		}
	}

	public void deleteRole(Long id) throws Exception {
		jdbcTemplate.executeArray("DELETE FROM t_system_role_info WHERE id=?", id);
		jdbcTemplate.executeArray("DELETE FROM t_system_user_role WHERE role_id=?", id);
		jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE role_id=?", id);
	}
	
	public void saveResource(Resource resource) throws Exception{
		String sql="INSERT INTO t_system_resource_info(name, path,cnname) VALUES(:name,:path,:cnname)";
		jdbcTemplate.executeBean(sql, resource);
		SecurityResourceCache.putCache(resource);
	}

	public void updateResource(Resource resource)throws Exception{
		String sql="UPDATE t_system_resource_info SET name=:name, path=:path,cnname=:cnname WHERE id=:id";
		jdbcTemplate.executeBean(sql, resource);
		SecurityResourceCache.removeCache(resource.getPath());
		SecurityResourceCache.putCache(resource);
	}

	public void deleteResource(Long id)throws Exception{
		String path=getResource(id).getPath();
		jdbcTemplate.executeArray("DELETE FROM t_system_resource_info WHERE id=?", id);
		jdbcTemplate.executeArray("DELETE FROM t_system_role_resource WHERE resource_id=?", id);
		SecurityResourceCache.removeCache(path);
	}

	@Transactional(readOnly=true)
	public List<Resource> getAllResources() {
		return jdbcTemplate.findListBean("SELECT id, name, path, cnname FROM t_system_resource_info", Resource.class);
	}
	
	@Transactional(readOnly=true)
	public Page getAllResources(Page page) {
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info";
		return jdbcTemplate.findPageListBean(sql, Resource.class, page);
	}

	@Transactional(readOnly=true)
	public Resource getResource(Long id) {
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id=?";
		return (Resource)jdbcTemplate.findUniqueBeanByArray(sql, Resource.class,id);
	}

	@Transactional(readOnly=true)
	public Resource getResource(String resPath) {
		return (Resource)jdbcTemplate.findUniqueBeanByArray("SELECT id, name, path, cnname FROM t_system_resource_info WHERE path=?", Resource.class, resPath);
	}
	
	@Transactional(readOnly=true)
	public List<Resource> getResourcesByRoleId(long roleId){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource as rr WHERE rr.role_id =?)";
		return jdbcTemplate.findListBeanByArray(sql, Resource.class,roleId);
	}

	@Transactional(readOnly=true)
	public List<Resource> getResourcesByUserName(String loginName){
		String sql="SELECT id, name, path, cnname FROM t_system_resource_info WHERE id in(SElECT rr.resource_id FROM t_system_role_resource AS rr WHERE rr.role_id in(SELECT ur.role_id FROM t_system_user_role AS ur WHERE ur.user_id in(SELECT u.id FROM t_system_user_info AS u WHERE u.username=?)))";
		return jdbcTemplate.findListBeanByArray(sql, Resource.class,loginName);
	}
	
/*	public GrantedAuthority[] getAuthoritys(String resPath){
		Resource resource=getResource(resPath);
		GrantedAuthority[] gas=new GrantedAuthority[0];
		gas[0]=new GrantedAuthorityImpl(resource.getName());
		return gas;
	}
*/

}
