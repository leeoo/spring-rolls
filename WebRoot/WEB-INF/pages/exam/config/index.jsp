<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——系统配置</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="系统配置">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="configForm" action="${base}/exam/config/save${ext}" method="post">
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">系统标题名称:</td>
						<td>
						<input name="title" class="easyui-validatebox" required="true"  validType="length[3,100]" style="width: 500px;" value="${title}"/>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">系统主题风格:</td>
						<td>
						<select name="themeName">
							<option value="default" <c:if test="${theme!=null && theme=='default'}">selected='selected'</c:if>>浅蓝</option>
							<option value="gray" <c:if test="${theme!=null && theme=='gray'}">selected='selected'</c:if>>银灰</option>
							<option value="cupertino" <c:if test="${theme!=null && theme=='cupertino'}">selected='selected'</c:if>>柔兰</option>
							<option value="dark-hive" <c:if test="${theme!=null && theme=='dark-hive'}">selected='selected'</c:if>>暗室</option>
							<option value="metro" <c:if test="${theme!=null && theme=='metro'}">selected='selected'</c:if>>都市</option>
							<option value="pepper-grinder" <c:if test="${theme!=null && theme=='pepper-grinder'}">selected='selected'</c:if>>理石</option>
							<option value="sunny" <c:if test="${theme!=null && theme=='sunny'}">selected='selected'</c:if>>晴朗</option>
						</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">注册考生存储周期:</td>
						<td>
							${config.key}
							<input name="examineeDays" class="easyui-numberbox" required="true" value="<%=ConfigInfo.examineeDays%>"/>天 (0为永久有效)
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