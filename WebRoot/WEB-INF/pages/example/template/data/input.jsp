<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>模板管理 - 填充数据 - 数据信息</title>
</head>
<body>
<div align="left">
	<form name="templateData" action="${base}/example/template/${templateId}/data/save.htm" method="post">
		<input type="hidden" name="id" value="${templateData.id}"/>
		数据名称：<input name="dataName" value="${templateData.dataName}"/><br/>
		SQL语句：<textarea name="sqlValue">${templateData.sqlValue}</textarea><br/>
		返回类型：<select name="isUniqueResult">
				<c:if test="${templateData!=null&&templateData.isUniqueResult==true}">
					<option value="1" selected="selected">唯一结果</option>
					<option value="0">集合结果</option>
				</c:if>
				<c:if test="${templateData!=null&&templateData.isUniqueResult==false}">
					<option value="1">唯一结果</option>
					<option value="0" selected="selected">集合结果</option>
				</c:if>
				<c:if test="${templateData==null}">
					<option value="1" selected="selected">唯一结果</option>
					<option value="0">集合结果</option>
				</c:if>
				</select><br/>
		数据描述：<textarea name="description" >${templateData.description}</textarea><br/>
		<button type="submit">提交</button>
	</form>
</div>
</body>
</html>