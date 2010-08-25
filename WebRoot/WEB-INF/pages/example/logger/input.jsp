<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 配置日志级别</title>
</head>
<body>
<div align="left">
	<h3>请设置日志的级别</h3>(系统初始化时日志状态为关闭)
	<form name="news" action="${base}/example/logger/save.htm" method="post">
		<c:forEach items="${loggerLevers}" var="lever">
			<input type="checkbox" name="levers" value="${lever.value}">${lever.key}&nbsp;
		</c:forEach>
		<button type="submit">提交</button>
	</form>
</div>
</body>
</html>