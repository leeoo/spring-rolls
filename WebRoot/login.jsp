<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>请登录</title>
<style>
#center{
	 position:absolute;
	 top:0;
	 bottom:0;
	 left:0;
	 right:0;
	 margin:auto;
	 height:100%;
	 width:100%;
	 padding-top: 10%;
}
#panel{
	width: 667px;
	height:365px;
	background: url('/common/images/login-bg.jpg');
}
#panel form{
	position: relative;
	top:150px;
	left:180px;
}
</style>
</head>
<body style="background: #CCC">
<div align="center" id="center" >
	<div id="panel">
		<form id="security" action="${base}/static/j_spring_security_check" method="post">
			账号：<input type='text' name='j_username' style="width: 120px;"/><br/>
			密码：<input type='password' name='j_password' style="width: 120px;"/><br/>
			<label><button type="submit">登 录</button></label>
		</form>
	</div>
</div>
</body>
</html>