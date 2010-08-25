<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>账户管理 - 账户列表</title>
</head>
<body>
	<table>
		<tr><th>登录账号</th><th>姓名</th><th>邮箱地址</th><th>状态</th><th>操作</th></tr>
		<c:forEach var="user" items="${page.result}">
			<tr>
				<td>${user.username}</td>
				<td>${user.cnname}</td>
				<td>${user.email}</td>
				<c:if test="${user.enabled==true}">
					<td><img src="${base}/common/images/yes.jpg" alt="有效"/></td>
				</c:if>
				<c:if test="${user.enabled==false}">
					<td><img src="${base}/common/images/no.jpg" alt="无效"/></td>
				</c:if>
				<td>
					<a href="${base}/example/user/input/${user.id}.htm">修改</a>
					<c:if test="${user.enabled==true}">
						<a href="${base}/example/user/disabled/${page.pageNo}-${user.id}.htm">冻结</a>
					</c:if>
					<c:if test="${user.enabled==false}">
						<a href="${base}/example/user/enabled/${page.pageNo}-${user.id}.htm">激活</a>
					</c:if>
					<a href="${base}/example/user/delete/${page.pageNo}-${user.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" align="left">
				第${page.pageNo}页, 共${page.totalPages}页 
				<c:set var="actionName" value="${base}/example/user/list"></c:set>
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
			<td align="center"><a href="${base}/example/user/input/.htm">添加账户</a></td>
		</tr>
	</table>
</body>
</html>