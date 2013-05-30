<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——线程监控</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="线程监控">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<div style="background-color: #EFEFEF;height:20px;width: 200px;font-size: 14px;"><b>活动区</b></div>
		<table>
			<tr>
				<td>实例码</td>
				<td>线程实例</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${startedThreads}" var="thread">
				<tr>
					<td>${thread.hashCode}</td>
					<td>${thread.class.name}</td>
					<td>${thread.threadStatus}</td>
					<td><a class="easyui-linkbutton" href="${base}/system/thread/shutdown${ext}?id=${thread.hashCode}" iconCls="icon-stop">shutdown</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<div style="background-color: #EFEFEF;height:20px;width: 200px;font-size: 14px;"><b>回收区</b></div>
		<table>
			<tr>
				<td>实例码</td>
				<td>线程实例</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${shutdownThreads}" var="thread">
				<tr>
					<td>${thread.hashCode}</td>
					<td>${thread.class.name}</td>
					<td>${thread.threadStatus}</td>
					<td><a class="easyui-linkbutton" href="${base}/system/thread/restart${ext}?id=${thread.hashCode}" iconCls="icon-play">restart</a></td>
				</tr>
			</c:forEach>
		</table>
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