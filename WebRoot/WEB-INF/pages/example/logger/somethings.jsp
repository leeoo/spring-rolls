<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 日志行为匹配列表</title>
</head>
<body>
	<table>
		<tr><th>行为关键字</th><th nowrap="nowrap">行为含义</th><th nowrap="nowrap">是否有效</th><th nowrap="nowrap">操作</th></tr>
		<c:forEach var="something" items="${somethings}">
			<tr>
				<td>${something.keyword}</td>
				<td>${something.name}</td>
				<td>${something.enabled}</td>
				<td>
					<a href="${base}/example/logger/inputSomething.htm?keyword=${something.keyword}">修改</a>
					<a href="${base}/example/logger/deleteSomething.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr><td colspan="4" align="right"><a href="${base}/example/logger/inputSomething.htm">新增</a></td></tr>
	</table>
</body>
</html>