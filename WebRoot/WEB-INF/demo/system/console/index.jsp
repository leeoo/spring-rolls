<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——CONSOLE控制台</title>
	<style>
		#console{
			background-color:#000000;
			font-family: "Courier New", Courier, "宋体", monospace;
			font-size: 18px;
			color: #CCCCCC;
			padding-left: 10px;
		}
	</style>
<%@ include file="../../global/head.jsp"%>
<%@ include file="/commons/global.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="控制台" style="background-color:#000000;font-size: 18px;color: #CCCCCC;">
	<div id="console">
		<div>
			<c:if test="${result!=null&&result!=''}">
				${result}<br/><br/>
			</c:if>
			<c:if test="${result==null||result==''}">
				Paramecium 开发版 [版本 1.1.0]<br/>
				(C) 版权所有 1982-2011 CaoYang Corp.<br/><br/><br/>
			</c:if>
			<form id="cf" action="${base}/system/console/run${ext}" method="post">
				<input type="hidden" name="type" value="${type}"/>
				${type}>
				<textarea id="cmd" onkeypress="isSubmit();" name="cmd" style="font-family: Courier New, Courier, 黑体, monospace;width: 98%;background-color: #000;height: 200px;color: #FFF;font-size: 18px;border: 0px;overflow-x:hidden;overflow-y:hidden"></textarea>
			</form>		
		</div>
		<script>
			document.getElementById('cmd').focus();
			function isSubmit(){
				if(event.keyCode==13) {
					document.getElementById('cf').submit(); 
				}
			}
		</script>
	</div>
</div>
</body>
</html>