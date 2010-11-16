<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 维护日志行为匹配信息</title>
</head>
<body>
<div align="left">
	<form name="something" action="${base}/example/logger/saveSomething.htm" method="post">
		行为关键字：<input name="keyword" value="${something.keyword}" <c:if test="${something!=null}">readonly="readonly"</c:if>/><br/>
		行为含义：<input name="name" value="${something.name}"/><br/>
		是否有效：<c:if test="${something!=null}">
					<c:if test="${something.enabled==true}">
						<input type="checkbox" name="enabled" value="1" checked="checked">参与日志
					</c:if>
					<c:if test="${something.enabled==false}">
						<input type="checkbox" name="enabled" value="1">参与日志
					</c:if>
				 </c:if>
				 <c:if test="${something==null}">
					<input type="checkbox" name="enabled" value="1">参与日志
				  </c:if>
				<br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>