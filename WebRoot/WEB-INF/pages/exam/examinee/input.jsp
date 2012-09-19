<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——考生信息维护</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="考生信息维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="examineeForm" action="${base}/exam/examinee/save${ext}" method="post">
			<c:if test="${examinee!=null}">
				<input type="hidden" name="examinee.id" value="${examinee.id}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">学号:</td>
						<td><input name="examinee.code" class="easyui-validatebox" required="true" validType="length[3,15]" style="width: 300px;" value="${examinee.code}"/></td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td><input name="examinee.username" class="easyui-validatebox" required="true" validType="length[2,10]" style="width: 300px;" value="${examinee.username}"/></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input name="examinee.password" type="password" class="easyui-validatebox" required="true" validType="length[3,15]" style="width: 300px;" value="${examinee.password}"/></td>
					</tr>
					<tr>
						<td>有效天数:</td>
						<td>
							<input name="examinee.canDays" class="easyui-numberbox" value="${examinee.canDays==null?0:examinee.canDays}"/>(0为永久有效)
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