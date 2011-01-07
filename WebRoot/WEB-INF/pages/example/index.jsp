<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>登录成功</title>
</head>
<body>
<h2>欢迎<b>${username}</b>登录本系统，率先领略Wisdom3的精彩应用！</h2>
<br><hr align="center" width="500px;"/><br>
<div>
<h3>Wisdom3版本信息</h3>
<h3>
<div id="onlineUser" class="ui-corner-bottom ui-helper-clearfix ui-widget-content content">
	<c:forEach items="${onlineUsers}" var="user">
		<span style="width: 120px;height: 200px;">
			<div style="border: 1px solid #777;">
			<center><b>${user.username}</b></center>
			<center><b>${user.operatorIp}</b></center>
			<center><b><fmt:formatDate value="${user.lastLoginDate}" pattern="MM月dd日HH:mm"/>登录</b></center>
			<center><b><c:if test="${user.lastLoginDate!=null}"><fmt:formatDate value="${user.lastLoginDate}" pattern="MM月dd日HH:mm"/>最后一次登录</b></c:if></center>
			</div>
		</span>
	</c:forEach>
</div>
</h3>

</div>
</body>
</html>