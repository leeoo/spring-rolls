<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>模板管理 - 模板信息</title>
</head>
<body>
<div align="left">
	<form name="template" action="${base}/example/template/save.htm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${template.id}"/>
		模板名称：<input name="name" value="${template.name}"/><br/>
		上传模板：<input type="file" name="file"/><br/>
		模板路径：<input name="templatePath" value="${template.templatePath}"/><br/>
		网页路径：<input name="htmlPath" value="${template.htmlPath}"/><br/>
		模板描述：<textarea name="description">${template.description}</textarea><br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>