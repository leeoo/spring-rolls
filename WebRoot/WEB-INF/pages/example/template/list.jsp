<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>模板管理 - 模板列表</title>
</head>
<body>
	<table>
		<tr><th>模板信息</th><th>编辑时间</th><th>模板描述</th><th>操作</th></tr>
		<c:forEach var="template" items="${page.result}">
			<tr>
				<td>${template.name}(<a href="${base}/example/template/download/${page.pageNo}-${template.id}.htm">下载${template.templatePath}</a>)</td>
				<td><fmt:formatDate value="${template.lastDate}" pattern="yyyy-MM-dd"/></td>
				<td>${template.description}</td>
				<td>
					<a href="${base}/example/template/${template.id}/data/list.htm">填充数据</a>
					<a href="${base}/example/template/input/${template.id}.htm">修改</a>
					<a href="${base}/example/template/delete/${page.pageNo}-${template.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="3" align="left">
			第${page.pageNo}页, 共${page.totalPages}页 
			<c:set var="actionName" value="${base}/example/template/list"></c:set>
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
		<td align="center">
		<a href="${base}/example/template/create.htm">生成页面</a>
		<a href="${base}/example/template/input/.htm">添加模板</a>
		</td></tr>
	</table>
</body>
</html>