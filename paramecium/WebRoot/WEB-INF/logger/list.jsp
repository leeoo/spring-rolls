<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>日志列表</title>
</head>
<body style="background-color:#AABBBB;height: 100%">
	<div style="background-color: #AABBBB;width: 100%;">
		<img alt="logo" src="${base}/commons/images/logo.gif" height="70" width="110"/>
		<hr width="100%"/>
	</div>
	<div style="background-color: #FFFFFF;width: 100%;height: 100%;">
		<table border="1">
			<tr><th>日志时间</th><th>日志内容</th><th nowrap="nowrap">操作</th></tr>
			<c:forEach var="logger" items="${page.result}">
				<tr>
					<td><fmt:formatDate value="${logger.date}" pattern="yyyy年MM月dd日"/></td>
					<td>${logger.info}</td>
					<td>
						<a href="${base}logger/delete.jhtml?page.pageNo=${page.pageNo}&id=${logger.id}">删除</a>
						<a href="${base}logger/input.jhtml?id=${logger.id}">修改</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
			<td colspan="7" align="left">
				第${page.pageNo}页, 共${page.totalPages}页 
				<c:set var="actionName" value="${base}logger/list.jhtml?page.pageNo="></c:set>
				<a href="${actionName}1">首页</a>
				<c:if test="${page.pageNo-3>0}">
					...&nbsp;
				</c:if>
				<c:if test="${page.pageNo-2>0}">
					<a href="${actionName}${page.pageNo-2}">${page.pageNo-2}</a>&nbsp;
				</c:if>
				<c:if test="${page.pageNo-1>0}">
					<a href="${actionName}${page.pageNo-1}">${page.pageNo-1}</a>&nbsp;
				</c:if>
				<c:if test="${page.pageNo>0}">
					${page.pageNo}&nbsp;
				</c:if>
				<c:if test="${page.totalPages>=page.pageNo+1}">
					<a href="${actionName}${page.pageNo+1}">${page.pageNo+1}</a>&nbsp;
				</c:if>
				<c:if test="${page.totalPages>=page.pageNo+2}">
					<a href="${actionName}${page.pageNo+2}">${page.pageNo+2}</a>&nbsp;
				</c:if>
				<c:if test="${page.totalPages>=page.pageNo+3}">
					<a href="${actionName}${page.pageNo+3}">${page.pageNo+3}</a>&nbsp;
				</c:if>
				<c:if test="${page.totalPages>=page.pageNo+4}">
					<a href="${actionName}${page.pageNo+4}">${page.pageNo+4}</a>&nbsp;
				</c:if>
				<c:if test="${page.totalPages>=page.pageNo+5}">
					...&nbsp;
				</c:if>
				<a href="${actionName}${page.totalPages}">末页</a>&nbsp;|&nbsp;
				<a href="${base}logger/input.jhtml">添加</a>
			</td>
			</tr>
		</table>
	</div>
</body>
</html>