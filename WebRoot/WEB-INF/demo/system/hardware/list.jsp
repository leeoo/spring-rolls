<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——服务器硬件情况</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="服务器硬件情况">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<img src="${base}/commons/images/cpu.gif" alt="" />
		${cpu.name}
		${cpu.load}
	</div>
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<img src="${base}/commons/images/memory.gif" alt="" />
		${memory.total}
		${memory.use}
		${memory.free}
	</div>
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<c:forEach items="${disks}" var="disk">
		<span>
			<img src="${base}/commons/images/disk.gif" alt="" />
			${disk.key}
			${disk.value[0]}
			${disk.value[1]}
			${disk.value[2]}
		</span>
		</c:forEach>
	</div>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>