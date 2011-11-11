<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——登录成功</title>
<%@ include file="../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../global/title.jsp"%>
	<%@ include file="../global/menu.jsp"%>
	<div region="center" title="登录成功">
		<div style="padding-left: 20px;padding-top: 10px;">
			<div><img src="${base}/commons/images/logo.gif" alt="产品LOGO" /></div>
			<div>
				<paramecium:loginAuthorize>我登录了<br></paramecium:loginAuthorize>
				<paramecium:loginAuthorize ifNotLogin="true">我没有登录<br></paramecium:loginAuthorize>
				<paramecium:resourceAuthorize ifAllGranted="/system/user#save,/system/user#list">
					我有/system/user#save,/system/user#list权限<br>
				</paramecium:resourceAuthorize>
				<paramecium:resourceAuthorize ifAnyGranted="/system/user#save,/system/user#list">
					我包含/system/user#save,/system/user#list其中的权限<br>
				</paramecium:resourceAuthorize>
				<paramecium:resourceAuthorize ifNotGranted="empty">
					我不包含empty权限<br>
				</paramecium:resourceAuthorize>
			</div>
		</div>
	</div>
	
	<script>
		var loginName = '<%=request.getAttribute("loginName")%>';
		if(loginName!=''&&loginName!='null'){
			$.messager.show({title:'提示',msg:'尊敬的用户: '+loginName+' 你好! \n 欢迎登录本系统',timeout:3000,showType:'slide'});
		}
	</script>
</body>
</html>