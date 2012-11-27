<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="refresh" content="8">
<%@ include file="../../global/head.jsp"%>
<title>${title}——数据源[${dsName}]监控</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="数据源[${dsName}]监控">
	<div style="padding:10px;">
		<a class="easyui-linkbutton" href="${baes}/system/ds/list${ext}" iconCls="icon-back">返回</a>
	</div>
	<div style="padding: 30px;border: dotted 1px ; border-color :#afafaf;">
		<font style="font-size: 15px;color: #176fba;"><b>连接池容量</b></font>
		<div id="conn" class="easyui-progressbar" style="width:700px;" align="left"></div>
		<div style="width:700px;" align="center">池内连接共:${dataSource.currentPoolMax} / 连接池峰值:${dataSource.poolMax}</div>
		<script>
			$(document).ready(function(){
				$('#conn').progressbar('setValue', <fmt:formatNumber type="number" value="${dataSource.currentPoolMax/dataSource.poolMax*100}" maxFractionDigits="0"/>);  
			});
		</script>
	</div>
	
	<div style="padding: 30px;border: dotted 1px ; border-color :#afafaf;">
		<font style="font-size: 15px;color: #176fba;"><b>活动链接数量</b></font>
		<div id="active-conn" class="easyui-progressbar" style="width:700px;" align="left"></div>
		<div style="width:700px;" align="center">活动连接共:${dataSource.busyPoolMax} / 池内连接共:${dataSource.currentPoolMax}</div>
		<script>
			$(document).ready(function(){
				$('#active-conn').progressbar('setValue', <fmt:formatNumber type="number" value="${dataSource.busyPoolMax/dataSource.currentPoolMax*100}" maxFractionDigits="0"/>);  
			});
		</script>
	</div>
	
	<div style="padding: 30px;border: dotted 1px ; border-color :#afafaf;">
		<font style="font-size: 15px;color: #176fba;"><b>闲置连接释放监控</b></font>
		<c:forEach items="${dataSource.idleTimes}" var="idle" varStatus="status">
			<div id="conn-life${status.index}" class="easyui-progressbar" style="width:700px;" align="left"></div>
			<div style="width:700px;" align="center">累计闲置:${idle}秒 / 生命周期:${dataSource.connectLife}秒</div>
			<script>
				$(document).ready(function(){
					$('#conn-life${status.index}').progressbar('setValue', <fmt:formatNumber type="number" value="${idle/dataSource.connectLife*100}" maxFractionDigits="0"/>);  
				});
			</script>
		</c:forEach>
	</div>
	
	<div style="padding: 30px;border: dotted 1px ; border-color :#afafaf;">
		<font style="font-size: 15px;color: #176fba;"><b>活动连接超时回收监控</b></font>
		<c:forEach items="${dataSource.busyTimes}" var="busy" varStatus="status">
			<div id="conn-busy${status.index}" class="easyui-progressbar" style="width:700px;" align="left"></div>
			<div style="width:700px;" align="center">累计活动:${busy}秒 / 超时周期:${dataSource.busyConnectTimeOut}秒</div>
			<script>
				$(document).ready(function(){
					$('#conn-busy${status.index}').progressbar('setValue', <fmt:formatNumber type="number" value="${busy/dataSource.busyConnectTimeOut*100}" maxFractionDigits="0"/>);  
				});
			</script>
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