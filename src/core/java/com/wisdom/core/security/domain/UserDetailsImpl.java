package com.wisdom.core.security.domain;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * 功能描述(Description):<br><b>
 * 实现UserDetails
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-10-31下午04:25:01</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.security.domain.UserDetailsImpl.java</b>
 */
public class UserDetailsImpl extends User implements UserDetails,CredentialsContainer{
	
	private static final long serialVersionUID = 9068370464939309858L;

	/**
	 * 获取当前登录用户详细信息必须重写次方法
	 */
	public int hashCode() {
		return getUsername().hashCode();
	}
	
	/**
	 * 获取当前登录用户详细信息必须重写次方法
	 */
	public boolean equals(Object obj) {
		if(obj instanceof UserDetails){
			UserDetails ud = (UserDetails)obj;
			if(ud.getUsername().equals(this.getUsername())){
				return true;
			}
		}
		return false;
	}
	
	public UserDetailsImpl(User user,Collection<GrantedAuthority> authorities){
		setId(user.getId());
		setUsername(user.getUsername());
		setCnname(user.getCnname());
		setPassword(user.getPassword());
		setMobile(user.getMobile());
		setEmail(user.getEmail());
		setOrganCode(user.getOrganCode());
		setOrganName(user.getOrganName());
		setLastLoginDate(user.getLastLoginDate());
		setPyname(user.getPyname());
		setBusinessCode(user.getBusinessCode());
		setInsertDate(user.getInsertDate());
		setUpdateDate(user.getUpdateDate());
		setOperatorIp(user.getOperatorIp());
		setOperatorName(user.getOperatorName());
		setAccountExpired(user.isAccountExpired());
		setAccountLocked(user.isAccountLocked());
		setCredentialsExpired(user.isCredentialsExpired());
		setEnabled(user.isEnabled());
		this.authorities = authorities;
	}
	
	private Collection<GrantedAuthority> authorities;

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return isAccountExpired();
	}

	public boolean isAccountNonLocked() {
		return isAccountLocked();
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsExpired();
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 用于清除敏感数据
	 */
	public void eraseCredentials() {
		setPassword(null);
	}

}
