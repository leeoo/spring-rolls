<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>权限管理 - 权限信息</title>
</head>
<body>
<div align="left">
	<form name="resource" action="${base}/example/resource/save.htm" method="post">
		<input type="hidden" name="id" value="${resource.id}"/>
		权限标示：<input name="name" value="${resource.name}"/><br/>
		权限名称：<input name="cnname" value="${resource.cnname}"/><br/>
		授权资源：<input name="path" value="${resource.path}"/><br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>