<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>任务调度 - 任务列表</title>
</head>
<body>
	<table>
		<tr><th>名称</th><th>运行时间</th><th>状态</th><th>操作</th></tr>
		<c:forEach var="task" items="${tasks}">
			<tr>
				<td>${task.taskName}</td>
				<td><fmt:formatDate value="${task.nextRunTime}" pattern="yyyy年MM月dd日HH时mm分ss秒"/></td>
				<td>
					<c:if test="${task.enabled==true}">
						<img src="${base}/common/images/yes.jpg" alt="启用"/>
					</c:if>
					<c:if test="${task.enabled==false}">
						<img src="${base}/common/images/no.jpg" alt="停用"/>
					</c:if>
				</td>
				<td>
					<a href="${base}/example/task/${task.id}/run_history/list/1.htm">历史</a>
					<a href="${base}/example/task/run/${task.id}.htm">运行</a>
					<c:if test="${task.enabled==true}">
						<a href="${base}/example/task/disabled/${task.id}.htm">停用</a>
					</c:if>
					<c:if test="${task.enabled==false}">
						<a href="${base}/example/task/enabled/${task.id}.htm">启用</a>
					</c:if>
					<a href="${base}/example/task/input/${task.id}.htm">修改</a>
					<a href="${base}/example/task/delete/${task.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="4" align="right">
			<a href="${base}/example/task/input/.htm">添加任务</a>
		</td></tr>
	</table>
</body>
</html>