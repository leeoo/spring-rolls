<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>搜索引擎 - 新闻信息</title>
<script language="javascript" type="text/javascript" src="${base}/common/my97/WdatePicker.js"></script>
</head>
<body>
<div align="left">
	<form name="news" action="${base}/example/search/save.htm" method="post">
		<input type="hidden" name="id" value="${news.id}"/>
		<c:if test="${news.enabled!=null}">
			<input type="hidden" name="enabled" value="${news.enabled}"/>
		</c:if>
		标题：<input name="title" value="${news.title}"/><br/>
		作者：<input name="auth" value="${news.auth}"/><br/>
		内容：<textarea name="context">${news.context}</textarea><br/>
		发布日期：<input name="publishDate" 
				onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',firstDayOfWeek:1});" 
				value="<fmt:formatDate value="${news.publishDate}" pattern="yyyy-MM-dd"/>"/><br/>
		过期日期：<input name="durability" 
				onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',firstDayOfWeek:1});" 
				value="<fmt:formatDate value="${news.durability}" pattern="yyyy-MM-dd"/>"/><br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>