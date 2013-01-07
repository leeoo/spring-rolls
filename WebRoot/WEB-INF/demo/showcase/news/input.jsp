<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——搜索引擎演示——新闻录入</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="新闻信息维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="firstForm" action="${base}/showcase/news/save${ext}" method="post">
			<c:if test="${news!=null}">
				<input type="hidden" name="news.id" value="${news.id}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">标题:</td>
						<td><input name="news.title" class="easyui-validatebox" data-options="required:true" validType="length[2,50]" style="width: 700px;" value="${news.title}"/></td>
					</tr>
					<tr>
						<td>作者:</td>
						<td><input name="news.auth" class="easyui-validatebox" data-options="required:true" validType="length[2,10]" style="width: 300px;" value="${news.auth}"/></td>
					</tr>
					<tr>
						<td>正文:</td>
						<td>
							<textarea rows="25" cols="90" name="news.content" class="easyui-validatebox" data-options="required:true" validType="length[2,999999]">${news.content}</textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right">
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save">提交</a>
						<script>
							function submitForm(){
								$('#firstForm').submit();
							}
						</script>
						</td>
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