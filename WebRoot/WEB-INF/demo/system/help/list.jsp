<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——帮助信息</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="帮助信息">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<div><h2>Controller信息</h2></div>
		<div style="padding-left: 20px;">
			<c:forEach items="${controllers}" var="app">
				<div>命名空间:${app.namespace}&nbsp;&nbsp;&nbsp;&nbsp;对应类:${app.clazz}</div>
			</c:forEach>
		</div>
		<div><h2>Service信息</h2></div>
		<div style="padding-left: 20px;">
			<c:forEach items="${services}" var="app">
				<div>实例名:${app.uniqueName}&nbsp;&nbsp;&nbsp;&nbsp;事务代理:${app.transactional}&nbsp;&nbsp;&nbsp;&nbsp;对应类:${app.clazz}</div>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>