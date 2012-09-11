<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——系统安全配置</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="系统安全配置">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="userForm" action="${base}/system/config/security/save${ext}" method="post">
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">是否启用安全验证:</td>
						<td>
						<select name="iocSecurity">
							<option value="true" <c:if test="${iocSecurity}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!iocSecurity}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否启用会话控制:</td>
						<td>
						<select name="sessionControl">
							<option value="true" <c:if test="${sessionControl}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!sessionControl}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">登录失败对应页面:</td>
						<td><input name="loginExceptionPage" value="${loginExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">匿名访问对应页面:</td>
						<td><input name="anonymousExceptionPage" value="${anonymousExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">授权失败对应页面:</td>
						<td><input name="authorizationExceptionPage" value="${authorizationExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">重复登录对应页面:</td>
						<td><input name="userKickExceptionPage" value="${userKickExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">账户失效对应页面:</td>
						<td><input name="userDisabledExceptionPage" value="${userDisabledExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">会话超时对应页面:</td>
						<td><input name="sessionExpiredExceptionPage" value="${sessionExpiredExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">IP限制对应页面:</td>
						<td><input name="ipAddressExceptionPage" value="${ipAddressExceptionPage}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">强制踢出对应页面:</td>
						<td><input name="userKillExceptionPage" value="${userKillExceptionPage}" style="width: 300px;"/></td>
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