package org.paramecium.security;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 功能描述(Description):<br><b>
 * 资源授权菜单，提供系统中受保护的所有权限信息
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-24下午07:32:33</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.AuthorizationMenu.java</b>
 */
public class AuthorizationMenu{

	private final static Map<Resource, Collection<Resource>> authorizationMenu = new LinkedHashMap<Resource, Collection<Resource>>();
	
	public static void put(Resource resource, Collection<Resource> resources){
		authorizationMenu.put(resource,resources);
	}
	
	/**
	 * 获得所有授权菜单资源
	 * @return
	 */
	public static Collection<Resource> getAllAuthorizationMenu(){
		Collection<Resource> allMenu = new LinkedList<Resource>();
		for(Collection<Resource> resources : authorizationMenu.values()){
			allMenu.addAll(resources);
		}
		return allMenu;
	}
	
	public static Map<Resource, Collection<Resource>> getAuthorMenu(){
		return authorizationMenu;
	}
	
}
