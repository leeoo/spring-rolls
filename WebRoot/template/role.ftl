<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>静态页演示</title>
</head>
<body>
	<h1>静态页演示</h1>
	<a href="/index.jsp">首页</a>
	<a href="/demo/index.html">用户信息</a>
	<a href="/demo/role.html">角色信息</a>
	<h3>角色信息列表</h3>
	<#list bean.roles as role>
		${role.cnname} | ${role.name} <br>
	</#list>
</body>
</html>