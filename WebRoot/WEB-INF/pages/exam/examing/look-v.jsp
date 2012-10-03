<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——开始考试</title>
</head>
<body class="easyui-layout">
	垂直布局的看打<a href="${base}/exam/change-layout${ext}?id=${examSession.id}">切换水平布局</a>
</body>
</html>