<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>远程调用 - 远程信息列表</title>
</head>
<body>
	<form name="news" action="${base}/example/remoting/save.htm" method="post">
		<input name="remotingValue"/>
		<button type="submit">添加</button>		
	</form>
	<table>
		<tr><th>远程服务传递信息</th><th>操作</th></tr>
		<c:forEach var="remotingValue" items="${list}">
			<tr>
				<td>${remotingValue}</td>
				<td>
					<a href="${base}/example/remoting/delete/${remotingValue}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
	</table>
</body>
</html>