<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——数据源配置</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="数据源配置">
	<c:forEach items="${dataSources}" var="dataSource">
		<form id="${dataSource.key}Form" name="${dataSource.key}Form" action="${base}/system/ds/save${ext}" method="post">
			<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
				<input type="hidden" name="dsName" value="${dataSource.key}"/>
				<table>
					<tr>
						<td nowrap="nowrap">数据源名称:</td>
						<td>${dataSource.key}</td>
					</tr>
					<tr>
						<td nowrap="nowrap">连接地址:</td>
						<td><input name="${dataSource.key}.url" value="${dataSource.value.url}" style="width: 250px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">用户名:</td>
						<td><input name="${dataSource.key}.username" value="${dataSource.value.username}" style="width: 150px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">密码:</td>
						<td><input name="${dataSource.key}.password" value="${dataSource.value.password}" style="width: 200px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">连接池最大值:</td>
						<td><input name="${dataSource.key}.poolMax" value="${dataSource.value.poolMax}" style="width: 100px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">连接池基数:</td>
						<td><input name="${dataSource.key}.poolBase" value="${dataSource.value.poolBase}" style="width: 100px;"/>(视实际性能而定)</td>
					</tr>
					<tr>
						<td nowrap="nowrap">空闲连接生命周期:</td>
						<td><input name="${dataSource.key}.connectLife" value="${dataSource.value.connectLife}" style="width: 100px;"/>(秒)</td>
					</tr>
					<tr>
						<td nowrap="nowrap">线程监控执行周期:</td>
						<td><input name="${dataSource.key}.poolThreadTime" value="${dataSource.value.poolThreadTime}" style="width: 100px;"/>(秒)</td>
					</tr>
					<tr>
						<td nowrap="nowrap">连接超时:</td>
						<td><input name="${dataSource.key}.loginTimeout" value="${dataSource.value.loginTimeout}" style="width: 100px;"/>(秒)</td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
					</tr>
				</table>
			</div>
		</form>
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