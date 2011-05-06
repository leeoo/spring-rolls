<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<style>
		body{TEXT-ALIGN: center;}
		#center{ MARGIN-RIGHT: auto;
		MARGIN-LEFT: auto; 
		height:200px;
		background:#F00;
		width:400px;
		vertical-align:middle;
		line-height:200px;
		}
	</style>
<title>登录首页</title>
</head>
<body style="background-image: url('${base}/commons/images/loginbg.gif')">
	<div id="login" class="easyui-dialog" title="请在此登录" style="width:270px;height:155px;padding-top: 10px;"
			buttons="#dlg-buttons" resizable="false" iconCls="icon-key">
		<form action="${base}/login.jhtml" method="post">
		<center>
			<table>
				<tr>
					<td>账号:</td>
					<td><input type='text' name='username' style="width: 150px;" class="easyui-validatebox" required="true" validType="length[2,20]"/></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type='password' name="password" style="width: 150px;" class="easyui-validatebox" required="true" validType="length[2,20]"/></td>
				</tr>
				<tr>
					<td></td>
					<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-security">登录</button></td>
				</tr>
			</table>
		</center>
	</form>
	</div>
</body>
<script>
	var error = <%=request.getParameter("error")%>;
	var msg = '';
	if(error==0){
		msg = '登录名或密码错误!';
	}else if(error==1){
		msg = '请您登录后访问!';
	}else if(error==3){
		msg = '该资源受保护,请用合法用户登录再试!';
	}else if(error==5){
		msg = '您的账户在其他地点登录,请重新登录!';
	}else if(error==7){
		msg = '您的账户被冻结,请联系管理员!';
	}else if(error==9){
		msg = '您在登录后长时间没有操作,请重新登录!';
	}
	if(msg!=''){
		$.messager.show({title:'提示',msg:msg,timeout:3000,showType:'slide'});
	}
</script>
</html>