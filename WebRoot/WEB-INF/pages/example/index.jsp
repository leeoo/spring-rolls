<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>登录成功</title>
</head>
<body>
<div>
<h3>Wisdom3版本信息</h3>
<h3>
		<div id="onlineUser" class="ui-corner-bottom ui-helper-clearfix ui-widget-content content">
			<c:forEach items="${onlineUsers}" var="user" varStatus="status">
				<div style="border: 1px solid #777;">
					<center><b>${user.cnname}</b></center>
					<center><b><fmt:formatDate value="${user.lastLoginDate}" pattern="MM月dd日HH:mm"/>登录</b></center>
					<center><b>${user.operatorIp}</b></center>
					<center><b>累计在线${user.operatorName}</b></center>
					<center><b><c:if test="${user.lastLoginDate!=null}"><fmt:formatDate value="${user.lastLoginDate}" pattern="MM月dd日HH:mm"/>最后一次登录</b></c:if></center>
				</div>
			</c:forEach>
		</div>
</h3>

</div>
</body>
</html>