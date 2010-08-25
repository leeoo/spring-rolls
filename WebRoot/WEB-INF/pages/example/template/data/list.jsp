<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>模板管理 - ${template.name}(${template.templatePath})填充数据 - 数据列表</title>
</head>
<body>
	<table>
		<tr><th colspan="5" align="center">${template.name}(${template.templatePath})模板内容</th></tr>
		<tr><td colspan="5">
			<textarea style="width: 100%;">${templateContext}</textarea>
		</td></tr>
		<tr><th>数据名</th><th>SQL</th><th>结果类型</th><th>数据描述</th><th>操作</th></tr>
		<c:forEach var="templateData" items="${templateDatas}">
			<tr>
				<td>${templateData.dataName}</td>
				<td>${templateData.sqlValue}</td>
				<td>
					<c:if test="${templateData.isUniqueResult==true}">唯一结果</c:if>
					<c:if test="${templateData.isUniqueResult==false}">集合结果</c:if>
				</td>
				<td>${templateData.description}</td>
				<td>
					<a href="${base}/example/template/${templateId}/data/input/${templateData.id}.htm">修改</a>
					<a href="${base}/example/template/${templateId}/data/delete/${templateData.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr><td colspan="5" align="right">
		<a href="${base}/example/template/list/1.htm">返回</a>
		<a href="${base}/example/template/${templateId}/data/input/.htm">添加数据</a>
		</td></tr>
	</table>
</body>
</html>