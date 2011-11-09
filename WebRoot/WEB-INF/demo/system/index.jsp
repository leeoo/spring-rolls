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
				<prmm:loginAuthorize>我登录了</prmm:loginAuthorize>
			<prmm:loginAuthorize isNotLogin="true">我没有登录</prmm:loginAuthorize>
				<h3>本DEMO用于演示Paramecium敏捷开发架构的常规开发演示；<br>
				以<font color="red">保信通2011</font>为例，将原有<font color="red">保信通2008(bxt3.6)</font>功能以Paramecium开发架构重新实现，后期可以通过评测工具进行比较。<br>
				希望用户提出宝贵意见！<br>
				<font color="red">QQ:563509737</font><br>
				<font color="red">EMAIL:caoyang@sinowel.com</font><br>
				</h3>
			</div>
		</div>
	</div>
	
	<script>
		var loginName = '<%=request.getAttribute("loginName")%>';
		if(name!=null&&name!='null'){
			$.messager.show({title:'提示',msg:'尊敬的用户: '+loginName+' 你好! \n 欢迎登录本系统',timeout:3000,showType:'slide'});
		}
</script>
</body>
</html>