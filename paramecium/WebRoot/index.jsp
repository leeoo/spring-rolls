<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>首页</title>
</head>
<body style="background-color:#AABBBB;height: 100%">
	<div style="background-color: #AABBBB;width: 100%;">
		<img alt="logo" src="${base}/commons/images/logo.gif" height="70" width="110"/>
		<hr width="100%"/>
	</div>
	<div style="background-color: #FFFFFF;width: 100%;height: 100%;">
		<form action="${base}/login.jhtml" method="post">
			账号<input type='text' name='username'/><br/>
			密码<input type='password' name="password"/><br/>
			<button type="submit">登录</button>
		</form>
	</div>
</body>
<script>
	var error = <%=request.getParameter("error")%>;
	if(error==0){
		alert('登录名或密码错误!');
	}else if(error==1){
		alert('请您登录后访问!');
	}else if(error==3){
		alert('该资源受保护,请用合法用户登录再试!');
	}else if(error==5){
		alert('您的账户在其他地点登录,请重新登录!');
	}else if(error==7){
		alert('您的账户被冻结,请联系管理员!');
	}else if(error==9){
		alert('您在登录后长时间没有操作,请重新登录!');
	}
</script>
</html>