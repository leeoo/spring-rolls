<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.PrintWriter"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>500——您访问的页面出现错误</title>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.8.0.min.js"></script>
	<style type="text/css">
		<!-- 
			html,body{height:100%; margin:0px;} 
		--> 
		#errorDiv{
			text-align:left;
			background: #000000;
			border:1px solid #ffffff;
			color: #ffffff;
			overflow: auto;
			margin: 0 auto;
		}
		#error500title{
			font-weight: bold;
			font-size: 120%;
			color: #ff0000;
		}
	</style>
</head>
<body>
	<div style="text-align: center;width: 100%;height: 100%;background-image: url('${base}/commons/images/bodybg.png');">
		<div style="color: #FFF;font-size: 280px;font-weight: bolder;">500</div>
		<div style="color: #FFF;font-size: 50px;font-weight: bolder;">您访问的页面出现错误</div>
		<div id="errorDiv">
			<span id='errorMove'>详细信息</span>
			<pre id='errorRequest'>
				<%
					if(exception != null){
				    	PrintWriter pout = new PrintWriter(out);
				    	out.println("<pre id='error500'>");
				    	out.println("<span id='error500title'>Exception: " + exception+"</span>");
				    	exception.printStackTrace(pout);
				    	out.println("</pre>");
				    }
				%>
			</pre>
		</div>
	</div>
</body>
</html>
