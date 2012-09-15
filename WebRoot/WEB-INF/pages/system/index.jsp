<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>亚伟在线考试系统——登录成功</title>
<%@ include file="../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../global/title.jsp"%>
	<%@ include file="../global/menu.jsp"%>
	<div region="center" title="欢迎登录亚伟在线考试系统">
		<!-- <div style="background:url('${base}/commons/images/exam.gif') no-repeat;width: 100%;height: 100%;">
			&nbsp;
		</div> -->
	</div>
	
	<script>
		var loginName = '<%=request.getAttribute("loginName")%>';
		if(loginName!=''&&loginName!='null'){
			$.messager.show({title:'提示',msg:'尊敬的用户: '+loginName+' 你好! \n 欢迎登录本系统',timeout:3000,showType:'slide'});
		}
	</script>
</body>
</html>