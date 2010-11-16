<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 日志模块匹配列表</title>
</head>
<body>
	<table>
		<tr><th>模块关键字</th><th nowrap="nowrap">模块含义</th><th nowrap="nowrap">是否有效</th><th nowrap="nowrap">操作</th></tr>
		<c:forEach var="somewhere" items="${somewheres}">
			<tr>
				<td>${somewhere.keyword}</td>
				<td>${somewhere.name}</td>
				<td>${somewhere.enabled}</td>
				<td>
					<a href="${base}/example/logger/inputSomewhere.htm?keyword=${somewhere.keyword}">修改</a>
					<a href="${base}/example/logger/deletesomewhere.htm?keyword=${somewhere.keyword}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr><td colspan="4" align="right"><a href="${base}/example/logger/inputSomewhere.htm">新增</a></td></tr>
	</table>
</body>
</html>