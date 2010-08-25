<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>任务调度 - 任务信息</title>
<script language="javascript" type="text/javascript" src="${base}/common/my97/WdatePicker.js"></script>
</head>
<body>
<div align="left">
	<form name="task" action="${base}/example/task/save.htm" method="post">
		<input type="hidden" name="id" value="${task.id}"/>
		任务名称：<input name="taskName" value="${task.taskName}"/><br/>
		运行时间：<input name="nextRunTime" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',firstDayOfWeek:1});" value="<fmt:formatDate value="${task.nextRunTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/><br/>
		运行周期：<select name="runUnit">
					<c:forEach var="unit" items="${units}">
						<c:if test="${task!=null&&task.runUnit==unit.key}">
							<option value="${unit.key}" selected="selected">${unit.value}</option>
						</c:if>
						<c:if test="${task==null||task.runUnit!=unit.key}">
							<option value="${unit.key}">${unit.value}</option>
						</c:if>
					</c:forEach>
				</select><br/>
		程序实例：<input name="instanceName" value="${task.instanceName}"/><br/>
		任务描述：<textarea name="description">${task.description}</textarea><br/>
		<button type="submit">提交</button>
	</form>
</div>
</body>
</html>