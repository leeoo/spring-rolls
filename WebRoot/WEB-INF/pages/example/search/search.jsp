<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>搜索引擎 - 新闻查询</title>
</head>
<body>
	<h3>系统为您找到<font color="red">${fn:length(newses)}</font>条相关信息</h3>
	<table>
		<c:forEach var="news" items="${newses}">
			<tr>
				<th>《${news.title}》  作者——${news.auth} 发布日期：<fmt:formatDate value="${news.publishDate}" pattern="yyyy年MM月dd日"/></th>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;${news.context}</td>
			</tr>
		</c:forEach>
	</table>
	<script>
	var nWord='${text}';
	nWord = nWord.replace(/\　/g,'|');
	nWord = nWord.replace(/\,/g,'|');
	nWord = nWord.replace(/\，/,'|');
	nWord = nWord.replace(/\ /g,'|');
	var Arr = nWord.split('|');
	for(var i=0;i<Arr.length;i++){
		var orange = document.body.createTextRange();
		while(orange.findText(Arr[i])){
			orange.pasteHTML("<span style='color:black;background-color:#abcabc;'>" + orange.text + "</span>");
			orange.moveStart('character',1);
		}
	}
    </script>
</body>
</html>