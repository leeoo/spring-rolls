<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.PrintWriter"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>500——您访问的页面出现错误</title>
	<style type="text/css">
	body{
		background-image:url("/commons/images/exception.gif");
	}
	</style>
</head>
<body>
	<h1>您把我的网页弄坏了!</h1>
		错误来源:<br>
		${base}${pageContext.errorData.requestURI}<br>
		<hr>
		错误信息:<br>
		<%
			if(exception != null){
		    	PrintWriter pout = new PrintWriter(out);
		    	exception.printStackTrace(pout);
		    }else{
		    	Throwable throwable = (Throwable)request.getAttribute("paramecium_error_message");
				if(throwable!=null){
					PrintWriter pout = new PrintWriter(out);
					throwable.printStackTrace(pout);
				}
		    }
		%>
</body>
</html>
