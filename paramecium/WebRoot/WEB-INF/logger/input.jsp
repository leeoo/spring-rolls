<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>日志信息维护</title>
</head>
<body style="background-color:#AABBBB;height: 100%">
	<div style="background-color: #AABBBB;width: 100%;">
		<img alt="logo" src="${base}/commons/images/logo.gif" height="70" width="110"/>
		<hr width="100%"/>
	</div>
	<div style="background-color: #FFFFFF;width: 100%;height: 100%;">
		<form id="testForm" action="${base}logger/save.jhtml" method="post">
		<c:if test="${logger.id!=null}">
			<input type="hidden" name="logger.id" value="${logger.id}"/>
		</c:if>
		<label>信息</label><input name="logger.info" value="${logger.info}"/><br>
		<label>日期</label><input name="logger.date" value="<fmt:formatDate value="${logger.date}" pattern="yyyy-MM-dd"/>"/><br>
		<label><button type="submit">提交</button></label>
	</form>
	</div>
</body>
</html>