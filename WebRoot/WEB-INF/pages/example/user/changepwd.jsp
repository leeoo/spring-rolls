<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>账户管理 - 修改${user.username}(${user.cnname})账户密码</title>
</head>
<body>
<div align="left">
	<h3>您正在修改 ${user.username}(${user.cnname}) 账户密码</h3>
	<form name="user" action="${base}/example/user/change_password.htm" method="post">
		<input type="hidden" name="id" value="${user.id}"/>
		<input type="hidden" name="username" value="${user.username}"/>
		新密码：<input type="password" name="password"/><br>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>