<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>账户管理 - 账户信息</title>
</head>
<body>
<div align="left">
	<form name="user" action="${base}/example/user/save.htm" method="post">
		<input type="hidden" name="id" value="${user.id}"/>
		账号：<input name="username" value="${user.username}"/>
			<c:if test="${user!=null && user.id!=null}">
				<a href="${base}/example/user/${user.id}/change_password.htm">修改密码</a><br/>
				<input name="pyname" value="${user.pyname}"/><br/>
				<input type="hidden" name="password" value="********"/>
			</c:if>
		<br/>
		<c:if test="${user==null || user.id==null}">
			密码：<input type="password" name="password" value="${user.password}"/><br/>
		</c:if>
		姓名：<input name="cnname" value="${user.cnname}"/><br/>
		邮箱：<input name="email" value="${user.email}"/><br/>
		角色：<c:forEach items="${roles}" var="role">
				<c:if test="${user==null || user.roles==null}">
					<input type="checkbox" name="roleIds" value="${role.id}">${role.cnname}&nbsp;
				</c:if>
				<c:set var="sign" value="0"></c:set>
				<c:if test="${user!=null && user.roles!=null}">
					<c:forEach items="${user.roles}" var="checkRole">
						<c:if test="${role.id==checkRole.id}">
							<input type="checkbox" name="roleIds" value="${role.id}" checked="checked">${role.cnname}&nbsp;
							<c:set var="sign" value="1"></c:set>
						</c:if>
			 		</c:forEach>
			 		<c:if test="${sign==0}">
			 			<input type="checkbox" name="roleIds" value="${role.id}">${role.cnname}&nbsp;
			 		</c:if>
				</c:if>
			 </c:forEach><br>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>