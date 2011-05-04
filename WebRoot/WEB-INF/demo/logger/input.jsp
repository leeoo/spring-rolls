<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>日志信息维护</title>
</head>
<body>
	<form id="loggerForm" action="${base}logger/save.jhtml" method="post">
		<c:if test="${logger.id!=null}">
			<input type="hidden" name="logger.id" value="${logger.id}"/>
		</c:if>
		<div>
			<table>
				<tr>
					<td>信息:</td>
					<td><textarea name="logger.info" class="easyui-validatebox" required="true" validType="length[2,50]" style="height:100px;width: 300px;">${logger.info}</textarea></td>
				</tr>
				<tr>
					<td>日期:</td>
					<td><input name="logger.date" class="easyui-datebox" value="<fmt:formatDate value='${logger.date}' pattern='yyyy-MM-dd'/>"></td>
				</tr>
				<tr>
					<td></td>
					<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>