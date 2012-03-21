<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——系统日志配置</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="系统日志配置">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="userForm" action="${base}/system/config/log/save${ext}" method="post">
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">是否输出到控制台:</td>
						<td>
						<select name="isConsole">
							<option value="true" <c:if test="${isConsole}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!isConsole}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否输出到文件:</td>
						<td>
						<select name="isFile">
							<option value="true" <c:if test="${isFile}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!isFile}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否输出到数据库:</td>
						<td>
						<select name="isDb">
							<option value="true" <c:if test="${isDb}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!isDb}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">控制台日志级别:</td>
						<td>
						<select name="consoleLoggerLevel">
							<c:forEach items="${levels}" var="level">
								<option value="${level.value}" <c:if test="${consoleLoggerLevel==level.value}">selected="selected"</c:if> >${level.key}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">文件日志级别:</td>
						<td>
						<select name="fileLoggerLevel">
							<c:forEach items="${levels}" var="level">
								<option value="${level.value}" <c:if test="${fileLoggerLevel==level.value}">selected="selected"</c:if> >${level.key}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">数据库日志级别:</td>
						<td>
						<select name="dbLoggerLevel">
							<c:forEach items="${levels}" var="level">
								<option value="${level.value}" <c:if test="${dbLoggerLevel==level.value}">selected="selected"</c:if> >${level.key}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">日志输出文件名:</td>
						<td><input name="loggerFileName" value="${loggerFileName}" style="width: 300px;"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap">日志文件大小:</td>
						<td>
						<select name="loggerFileMax">
							<option value="1" <c:if test="${loggerFileMax==1}">selected="selected"</c:if> >1MB</option>
							<option value="2" <c:if test="${loggerFileMax==2}">selected="selected"</c:if> >2MB</option>
							<option value="5" <c:if test="${loggerFileMax==5}">selected="selected"</c:if> >5MB</option>
							<option value="10" <c:if test="${loggerFileMax==10}">selected="selected"</c:if> >10MB</option>
							<option value="20" <c:if test="${loggerFileMax==20}">selected="selected"</c:if> >20MB</option>
							<option value="50" <c:if test="${loggerFileMax==50}">selected="selected"</c:if> >50MB</option>
							<option value="100" <c:if test="${loggerFileMax==100}">selected="selected"</c:if> >100MB</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">SQL是否格式化输出:</td>
						<td>
						<select name="sqlIsFormat">
							<option value="true" <c:if test="${sqlIsFormat}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!sqlIsFormat}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否记录JDBC执行日志:</td>
						<td>
						<select name="jdbcLogCollector">
							<option value="true" <c:if test="${jdbcLogCollector}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!jdbcLogCollector}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否记录容器调用日志:</td>
						<td>
						<select name="beanLogCollector">
							<option value="true" <c:if test="${beanLogCollector}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!beanLogCollector}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">是否记录WEB请求日志:</td>
						<td>
						<select name="webLogCollector">
							<option value="true" <c:if test="${webLogCollector}">selected="selected"</c:if> >启用</option>
							<option value="false" <c:if test="${!webLogCollector}">selected="selected"</c:if> >禁用</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">日志持久化长度约束:</td>
						<td><input name="logLength" value="${logLength}" style="width: 100px;"/></td>
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