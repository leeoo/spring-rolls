<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>404——您访问的页面无法找到</title>
	<style type="text/css">
	body{
		background-image:url("${base}/commons/images/loginbg.gif");
	}
	</style>
</head>
<body>
	<div style="text-align: center;">
		<img alt="IE的404必须要求大于512字节，否则会被IE换成自己的界面。因此我用了这个图片占用一下字节。" src="${base}/commons/images/logo.gif">
		<div style="color: #FFF;font-size: 280px;font-weight: bolder;">404</div>
		<div style="color: #FFF;font-size: 50px;font-weight: bolder;">您访问的页面无法找到</div>
	</div>
</body>
</html>
