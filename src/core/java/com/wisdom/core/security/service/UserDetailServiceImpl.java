package com.wisdom.core.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.User;

/**
 * 实现SpringSecurity的UserDetailsService接口,获取用户Detail信息.
 * 
 * @author wisdom
 */
public class UserDetailServiceImpl implements UserDetailsService {

	private UserService userService;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		User user = userService.getUserByLoginName(userName);
		if (user == null){
			throw new UsernameNotFoundException(userName + " 用户名不存在，登录失败！");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority ag=null;
		Collection<Resource> ress=userService.getResourcesByUserName(userName);
		for(Resource res:ress){
			ag=new GrantedAuthorityImpl(res.getName());
			authorities.add(ag);
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),user.isAccountExpired(),user.isCredentialsExpired(),user.isAccountLocked(),authorities);
		return userDetails;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
