<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>任务调度 - ${task.taskName} - 执行历史</title>
</head>
<body><h3>${task.taskName} 执行历史</h3>
	<table>
		<tr><th>执行时间</th><th>结束时间</th><th>方式</th><th>状态</th><th>信息</th><th>操作</th></tr>
		<c:forEach var="history" items="${page.result}">
			<tr>
				<td><fmt:formatDate value="${history.runTime}" pattern="yyyy年MM月dd日HH时mm分ss秒"/></td>
				<td><fmt:formatDate value="${history.endTime}" pattern="yyyy年MM月dd日HH时mm分ss秒"/></td>
				<td>
					${history.runTypeName}
				</td>
				<td>
					<c:if test="${history.runState==1}">
						<img src="${base}/common/images/yes.jpg" alt="成功"/>
					</c:if>
					<c:if test="${history.runState==0}">
						<img src="${base}/common/images/no.jpg" alt="失败"/>
					</c:if>
					${history.runStateName}
				</td>
				<td>${history.errorInfo}</td>
				<td>
					<a href="${base}/example/task/${task.id}/run_history/delete/${page.pageNo}-${history.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="5" align="left">
				第${page.pageNo}页, 共${page.totalPages}页 
				<c:set var="actionName" value="${base}/example/task/${task.id}/run_history/list"></c:set>
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
		<td colspan="1" align="right">
			<a href="${base}/example/task/list.htm">返回</a>
			<a href="${base}/example/task/${task.id}/run_history/download.htm">下载</a>
		</td>
		</tr>
	</table>
</body>
</html>