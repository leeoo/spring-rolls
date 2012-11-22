<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——搜索引擎演示</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="搜索新闻">
	<div style="padding: 20px;">
		<form id="search" action="${base}/showcase/search/list${ext}" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page==null||page.pageNo==0?0:page.pageNo}"/>
			<input id="text" name="text" class="easyui-searchbox" data-options="prompt:'请输入查询的内容...',menu:'#mm',searcher:doSearch2" style="width:500px" value="${text}"></input>
			<a class="easyui-linkbutton" href="${baes}/showcase/news/list${ext}" iconCls="icon-back">返回</a>
		</form>
		<div id="mm" style="width:120px">
			<div data-options="name:'text',iconCls:'icon-home'">所有分类</div>
		</div>
	</div>
	<div>
		<table style="border: 1px;border-style: dotted;width: 90%;">
			<tr style="background-color: #3c72b4;color: #FFF;height: 25px;"><th>标题</th><th>发布日期</th><th>作者</th></tr>
			<c:forEach var="news" items="${page.result}">
				<tr>
					<td style="width: 70%;padding-left: 10px;" nowrap="nowrap">${news.title}</td>
					<td style="width: 20%;padding-left: 10px;" nowrap="nowrap">${news.publishDate}</td>
					<td style="width: 10%;padding-left: 10px;" nowrap="nowrap">${news.auth}</td>
				</tr>
				<tr>
					<td colspan="3">
						<hr style="border-style: dotted;"/>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3" align="left">
					第${page.pageNo}页, 共${page.totalPages}页 
					<a href="#" onclick="doSearch(1);">首页</a>
					<c:if test="${page.pageNo-3>0}">
						...&nbsp;
					</c:if>
					<c:if test="${page.pageNo-2>0}">
						<a href="#" onclick="doSearch(${page.pageNo-2});">${page.pageNo-2}</a>&nbsp;
					</c:if>
					<c:if test="${page.pageNo-1>0}">
						<a href="#" onclick="doSearch(${page.pageNo-1});">${page.pageNo-1}</a>&nbsp;
					</c:if>
					<c:if test="${page.pageNo>0}">
						${page.pageNo}&nbsp;
					</c:if>
					<c:if test="${page.totalPages>=page.pageNo+1}">
						<a href="#" onclick="doSearch(${page.pageNo+1});">${page.pageNo+1}</a>&nbsp;
					</c:if>
					<c:if test="${page.totalPages>=page.pageNo+2}">
						<a href="#" onclick="doSearch(${page.pageNo+2});">${page.pageNo+2}</a>&nbsp;
					</c:if>
					<c:if test="${page.totalPages>=page.pageNo+3}">
						<a href="#" onclick="doSearch(${page.pageNo+3});">${page.pageNo+3}</a>&nbsp;
					</c:if>
					<c:if test="${page.totalPages>=page.pageNo+4}">
						<a href="#" onclick="doSearch(${page.pageNo+4});">${page.pageNo+4}</a>&nbsp;
					</c:if>
					<c:if test="${page.totalPages>=page.pageNo+5}">
						...&nbsp;
					</c:if>
					<a href="#" onclick="doSearch(${page.totalPages});">末页</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<script>
	function doSearch(page_no){
		if($.trim($('#text').val())!=''){
			$('#pageNo').val(page_no==null||page_no==0?0:page_no);
			$('#search').submit();
		}else{
			$.messager.alert('提示','请填写查询信息!','warning');
		}
	}
	function doSearch2(text){
		if($.trim(text)==''){
			$.messager.alert('提示','请填写查询信息!','warning');
		}else{
			$('#pageNo').val('${page.pageNo}');
			$('#text').val(text);
			$('#search').submit();
		}
	}
	
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>