<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="shortcut icon" href="${base}/commons/images/xeyes.png" type="image/x-icon" /> 
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/${theme}/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/locale/easyui-lang-zh_CN.js"></script>
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
<body style="background-image: url('${base}/commons/images/bodybg.png')">
	<div id="login" class="easyui-dialog" title="请在此登录" style="width:280px;height:160px;padding-top: 10px;"
			buttons="#dlg-buttons" resizable="false" iconCls="icon-key">
		<form id="firstForm" action="${base}/login${ext}" method="post">
		<center>
			<table>
				<tr>
					<td>账号:</td>
					<td><input type='text' name='login.username' style="width: 150px;" class="easyui-validatebox" data-options="required:true" validType="length[2,20]"/></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type='password' name="login.password" style="width: 150px;" class="easyui-validatebox" data-options="required:true" validType="length[2,20]"/></td>
				</tr>
				<tr>
					<td></td>
					<td align="right">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-security">登录</a>
					<script>
						function submitForm(){
							var waitNum = Math.random() * 5000;
							if(waitNum<1200){
								waitNum = 1200;
							}
							$.messager.progress({
				    			title:'稍等片刻',
				    			msg:'正在努力提交您的登录信息...'
				    		});
				    		setTimeout(function(){
				    			$.messager.progress('close');
				    			$('#firstForm').submit();
				    		},waitNum);
						}
					</script>
					</td>
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
	}else if(error==11){
		msg = '您的IP地址没有被系统授权,请联系管理员!';
	}else if(error==13){
		msg = '您被系统管理员强制退出,请联系管理员!';
	}
	if(msg!=''){
		$.messager.show({title:'提示',msg:msg,timeout:3000,showType:'slide'});
	}
</script>
</html>