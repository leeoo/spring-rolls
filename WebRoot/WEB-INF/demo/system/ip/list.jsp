<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——IP地址过滤配置</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="IP地址过滤配置">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="userForm" action="${base}/system/config/ip/save${ext}" method="post">
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">是否启用IP过滤:</td>
						<td>
						<select name="enabled">
							<option value="true" <c:if test="${enabled}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!enabled}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">IP过滤策略:</td>
						<td>
						<select name="include">
							<option value="true" <c:if test="${include}">selected="selected"</c:if> >白名单机制</option>
							<option value="false" <c:if test="${!include}">selected="selected"</c:if> >黑名单机制</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">IP列表:</td>
						<td>
							<table style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
								<tr>
									<th align="left">IP地址</th><th align="right">操作</th>
								</tr>
								<tr>
									<td><input id="ip" name="ip"/></td><td><a href="#" onclick="return add();">添加</a></td>
									<script>
										function add(){
											var ip = document.getElementById("ip").value;
											if(ip==''){
												$.messager.alert('提示','请填写IP地址再添加!','warning');
												return false;
											}
											window.location.href = '${base}/system/config/ip/add${ext}?ip='+ip;
											return true;
										}
									</script>
								</tr>
								<c:forEach items="${ips}" var="ip">
									<tr>
										<td>${ip}</td><td><a href="${base}/system/config/ip/remove${ext}?ip=${ip}">移除</a></td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
					</tr>
				</table>
			</div>
		</form>
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