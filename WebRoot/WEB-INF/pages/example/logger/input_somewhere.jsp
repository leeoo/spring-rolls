<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>日志管理 - 维护日志模块匹配信息</title>
</head>
<body>
<div align="left">
	<form name="somewhere" action="${base}/example/logger/saveSomewhere.htm" method="post">
		模块关键字：<input name="keyword" value="${somewhere.keyword}" <c:if test="${somewhere!=null}">readonly="readonly"</c:if>/><br/>
		模块含义：<input name="name" value="${somewhere.name}"/><br/>
		是否有效：<c:if test="${somewhere!=null}">
					<c:if test="${somewhere.enabled==true}">
						<input type="checkbox" name="enabled" value="1" checked="checked">参与日志
					</c:if>
					<c:if test="${somewhere.enabled==false}">
						<input type="checkbox" name="enabled" value="1">参与日志
					</c:if>
				 </c:if>
				 <c:if test="${somewhere==null}">
					<input type="checkbox" name="enabled" value="1">参与日志
				  </c:if>
				<br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>