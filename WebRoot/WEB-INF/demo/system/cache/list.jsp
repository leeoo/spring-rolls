<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="refresh" content="8">
<title>Paramecium开发平台演示——缓存使用情况</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="缓存使用情况">
	<c:forEach items="${caches}" var="cache" varStatus="status">
		<div style="padding-left: 50px;padding-top: 30px;">
				<font style="font-size: 15px;color: #176fba;"><b>${cache.key}</b>
				&nbsp;${cache.value[1]}已用&nbsp;/&nbsp;共${cache.value[0]}</font>
				<div id="p${status.index}" class="easyui-progressbar" style="width:500px;"></div>
				<script>
					$(document).ready(function(){
						$('#p${status.index}').progressbar('setValue', <fmt:formatNumber type="number" value="${cache.value[1]/cache.value[0]*100}" maxFractionDigits="1"/>);  
					});
				</script>
		</div>
	</c:forEach>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>