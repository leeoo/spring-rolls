<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——速录考试题库维护</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="速录考试题库维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="questionForm" action="${base}/exam/question/save${ext}" method="post">
			<input type="hidden" name="question.choice" value="false"/>
			<c:if test="${question!=null}">
				<input type="hidden" name="question.id" value="${question.id}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">题库描述:</td>
						<td><input name="question.title" class="easyui-validatebox" required="true" validType="length[3,500]" style="width: 500px;" value="${question.title}"/></td>
					</tr>
					<tr>
						<td>音频上传:</td>
						<td><input name="question.audioPath" class="easyui-validatebox" style="width: 300px;" value="${question.audioPath}"/>(如果看打无需上传音频)</td>
					</tr>
					<tr>
						<td>原文:</td>
						<td>
							<textarea rows="25" cols="90" name="question.textContent" class="easyui-validatebox" required="true" validType="length[10,9999999]">${question.textContent}</textarea>
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