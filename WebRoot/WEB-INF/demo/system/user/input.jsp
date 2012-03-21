<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——用户信息维护</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="用户信息维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="userForm" action="${base}/system/user/save${ext}" method="post">
			<c:if test="${user.id!=null}">
				<input type="hidden" name="user.id" value="${user.id}"/>
				<input type="hidden" name="user.enabled" value="${user.enabled}"/>
			</c:if>
			<input type="hidden" name="user.enabled" value="true"/>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">用户姓名:</td>
						<td><input name="user.name" class="easyui-validatebox" required="true" validType="length[2,50]" style="width: 300px;" value="${user.name}"/></td>
					</tr>
					<tr>
						<td>登录账户:</td>
						<td><input name="user.username" class="easyui-validatebox" required="true" validType="length[2,50]" style="width: 300px;" value="${user.username}"/></td>
					</tr>
					<tr>
						<td>登录密码:</td>
						<td><input name="user.password" type="password" class="easyui-validatebox" required="true" validType="length[2,50]" style="width: 300px;" value="${user.password}"/></td>
					</tr>
					<tr>
						<td>角色分配:</td>
						<td>
							<c:forEach items="${roles}" var="role">
								<c:if test="${user==null || user.roles==null}">
									<label><input type="checkbox" name="roles" value="${role.rolename}">${role.name}</label>&nbsp;
								</c:if>
								<c:set var="sign" value="0"></c:set>
								<c:if test="${user!=null && user.roles!=null}">
									<c:forEach items="${user.roles}" var="checkRole">
										<c:if test="${role.rolename==checkRole.rolename}">
											<label><input type="checkbox" name="roles" value="${role.rolename}" checked="checked">${role.name}</label>&nbsp;
											<c:set var="sign" value="1"></c:set>
										</c:if>
							 		</c:forEach>
							 		<c:if test="${sign==0}">
							 			<label><input type="checkbox" name="roles" value="${role.rolename}">${role.name}</label>&nbsp;
							 		</c:if>
								</c:if>
							 </c:forEach>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>