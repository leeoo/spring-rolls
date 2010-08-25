<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>搜索引擎 - 新闻列表</title>
<script language="javascript" type="text/javascript" src="${base}/common/my97/WdatePicker.js"></script>
</head>
<body>
	<form name="news" action="${base}/example/search/list/${page.pageNo}.htm" method="post">
	发布日期：<input name="publishDateBegin" 
				onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',firstDayOfWeek:1});" 
				value="<fmt:formatDate value="${news.publishDateBegin}" pattern="yyyy-MM-dd"/>"/>
			~
			<input name="publishDateEnd" 
				onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',firstDayOfWeek:1});" 
				value="<fmt:formatDate value="${news.publishDateEnd}" pattern="yyyy-MM-dd"/>"/>	
		<button type="submit">查询</button>		
	</form>
	<table>
		<tr><th>标题</th><th>作者</th><th>内容简介</th><th>发布日期</th><th>状态</th><th>操作</th></tr>
		<c:forEach var="news" items="${page.result}">
			<tr>
				<td>${news.title}</td>
				<td>${news.username}</td>
				<td>${fn:substring(news.context,0,40)}...</td>
				<td><fmt:formatDate value="${news.publishDate}" pattern="yyyy年MM月dd日"/></td>
				<td>${news.state}</td>
				<td>
					<a href="${base}/example/search/disabled/${page.pageNo}-${news.id}.htm">撤销</a>
					<a href="${base}/example/search/input/${news.id}.htm">修改</a>
					<a href="${base}/example/search/delete/${page.pageNo}-${news.id}.htm">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td colspan="5" align="left">
			第${page.pageNo}页, 共${page.totalPages}页 
			<c:set var="actionName" value="${base}/example/search/list"></c:set>
			<a href="${actionName}/1.htm">首页</a>
			<c:if test="${page.pageNo-3>0}">
				...&nbsp;
			</c:if>
			<c:if test="${page.pageNo-2>0}">
				<a href="${actionName}/${page.pageNo-2}.htm">${page.pageNo-2}</a>&nbsp;
			</c:if>
			<c:if test="${page.pageNo-1>0}">
				<a href="${actionName}/${page.pageNo-1}.htm">${page.pageNo-1}</a>&nbsp;
			</c:if>
			<c:if test="${page.pageNo>0}">
				${page.pageNo}&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+1}">
				<a href="${actionName}/${page.pageNo+1}.htm">${page.pageNo+1}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+2}">
				<a href="${actionName}/${page.pageNo+2}.htm">${page.pageNo+2}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+3}">
				<a href="${actionName}/${page.pageNo+3}.htm">${page.pageNo+3}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+4}">
				<a href="${actionName}/${page.pageNo+4}.htm">${page.pageNo+4}</a>&nbsp;
			</c:if>
			<c:if test="${page.totalPages>=page.pageNo+5}">
				...&nbsp;
			</c:if>
			<a href="${actionName}/${page.totalPages}.htm">末页</a>
		</td>
		<td><a href="${base}/example/search/input/.htm">添加新闻</a></td></tr>
	</table>
</body>
</html>