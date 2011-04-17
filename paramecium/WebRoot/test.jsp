<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>测试</title>
</head>
<body>
<div align="center" id="center" >
	<div>
		<form id="testForm" action="${base}/test.jhtml" method="put">
			<input name="test1" value="hello world"/>
			<label><button type="submit">提交</button></label>
		</form>
	</div>
</div>
</body>
</html>