<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>500——您访问的页面出现错误</title>
</head>
<body>
	<h1>您把我弄坏了!</h1>
		错误来源:<br>
		${base}${pageContext.errorData.requestURI}<br>
		<hr>
		${errormessage}
		错误信息:<br>
		<c:if test="${pageContext.exception!=null}">
			${pageContext.exception.cause}<br>
		</c:if>
</body>
</html>
