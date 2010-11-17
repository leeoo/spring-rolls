<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - SQL日志列表</title>
</head>
<body>
	<table>
		<tr><th>日志时间</th><th>SQL语句</th><th>SQL类别</th><th>SQL调用信息</th><th nowrap="nowrap">操作</th></tr>
		<c:forEach var="logger" items="${page.result}">
			<tr>
				<td><fmt:formatDate value="${logger.logDate}" pattern="yyyy年MM月dd日HH时mm分ss秒"/></td>
				<td>${logger.sqlValue}</td>
				<td>${logger.sqlType}</td>
				<td>${logger.callDetails}</td>
				<td>
					<a href="${base}/example/logger/sqldelete/${page.pageNo}-${logger.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="5" align="left">
			第${page.pageNo}页, 共${page.totalPages}页 
			<c:set var="actionName" value="${base}/example/logger/sqllist"></c:set>
			<a href="${actionName}/1.htm">首页</a>
			<c:if test="${page.pageNo-3>0}">
				...&nbsp;
			</c:if>
			<c:if test="${page.pageNo-2>0}">
				<a href="${actionName}/${page.pageNo-2}.htm">${page.pageNo-2}</a>&nbsp;
			</c:if>
			<c:if test="${page.pageNo-1>0}">
				<a href="${actionName}/${page.pageNo-1}.htm">${page.pageNo-1}</a>&nbsp;
			</c:if>
			<c:if test="${page.pageNo>0}">
				${page.pageNo}&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+1}">
				<a href="${actionName}/${page.pageNo+1}.htm">${page.pageNo+1}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+2}">
				<a href="${actionName}/${page.pageNo+2}.htm">${page.pageNo+2}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+3}">
				<a href="${actionName}/${page.pageNo+3}.htm">${page.pageNo+3}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+4}">
				<a href="${actionName}/${page.pageNo+4}.htm">${page.pageNo+4}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+5}">
				...&nbsp;
			</c:if>
			<a href="${actionName}/${page.totalPages}.htm">末页</a>&nbsp;|&nbsp;
		</td>
		</tr>
	</table>
</body>
</html>