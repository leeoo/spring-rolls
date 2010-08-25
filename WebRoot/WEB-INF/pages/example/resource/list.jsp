<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>权限管理 - 权限列表</title>
</head>
<body>
	<table>
		<tr><th>权限标示</th><th>权限名称</th><th colspan="2">授权资源</th><th>操作</th></tr>
		<c:forEach var="resource" items="${page.result}">
			<tr>
				<td>${resource.name}</td>
				<td>${resource.cnname}</td>
				<td colspan="2">${resource.path}</td>
				<td>
					<a href="${base}/example/resource/input/${resource.id}.htm">修改</a>
					<a href="${base}/example/resource/delete/${page.pageNo}-${resource.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="3" align="left">
			第${page.pageNo}页, 共${page.totalPages}页 
			<c:set var="actionName" value="${base}/example/resource/list"></c:set>
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
			<a href="${actionName}/${page.totalPages}.htm">末页</a>
		</td>
		<td colspan="2" align="center"><a href="${base}/example/resource/${page.pageNo}/system/reload.htm">缓存重载</a>&nbsp;<a href="${base}/example/resource/input/.htm">添加权限</a></td></tr>
	</table>
</body>
</html>