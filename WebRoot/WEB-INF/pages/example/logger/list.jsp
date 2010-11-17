<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 日志列表</title>
</head>
<body>
	<table>
		<tr><th>日志时间</th><th>用户名</th><th>所属机构</th><th>IP</th><th>访问模块</th><th>行为</th><th nowrap="nowrap">操作</th></tr>
		<c:forEach var="logger" items="${page.result}">
			<tr>
				<td><fmt:formatDate value="${logger.logDate}" pattern="yyyy年MM月dd日HH时mm分ss秒"/></td>
				<td>${logger.cnname}(${logger.username})</td>
				<td>${logger.organName}</td>
				<td>${logger.ip}</td>
				<td>${logger.somewhere}</td>
				<td>${logger.something}</td>
				<td>
					<a href="${base}/example/logger/delete/${page.pageNo}-${logger.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="7" align="left">
			第${page.pageNo}页, 共${page.totalPages}页 
			<c:set var="actionName" value="${base}/example/logger/list"></c:set>
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
			<a href="${base}/example/logger/somewheres.htm">模块配置</a>
			<a href="${base}/example/logger/somethings.htm">行为配置</a>
			<a href="${base}/example/logger/load.htm">手动装载</a>
			<a href="${base}/example/logger/sqllist.htm">SQL日志</a>
		</td>
		</tr>
	</table>
</body>
</html>