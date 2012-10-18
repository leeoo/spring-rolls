<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——考生成绩排名统计</title>
</head>
<body class="easyui-layout">
<%@ include file="../../global/title.jsp"%>
<%@ include file="../../global/menu.jsp"%>
<div region="center" title="考生成绩排名统计">
	<div style="padding-left: 50px;padding-top: 10px;">
		<a href="${base}/exam/report/list${ext}" class="easyui-linkbutton" iconCls="icon-redo">返回</a>
	</div>
	<div style="padding-left: 30px;padding-top: 10px;">
		<table style="font-size: 20px;border-color: #0b335b;" align="left" width="80%" border="1" cellpadding="0" cellspacing="0">
			<tr style="background-color: #0b335b;color: #FFF;">
				<th style="border-color: #FFF;" width="10%">名次</th>
				<th style="border-color: #FFF;" width="20%">考号</th>
				<th style="border-color: #FFF;" width="20%">姓名</th>
				<th style="border-color: #FFF;" width="10%">分数</th>
				<th style="border-color: #FFF;" width="20%">耗时</th>
			</tr>
			<c:forEach items="${scores}" var="score" varStatus="status">
				<tr>
					<td style="border-color: #0b335b;" align="center" nowrap="nowrap">${status.index+1}</td>
					<td style="border-color: #0b335b;" align="center" nowrap="nowrap">${score.code}</td>
					<td style="border-color: #0b335b;" align="center" nowrap="nowrap">${score.username}</td>
					<td style="border-color: #0b335b;" align="center" nowrap="nowrap">${score.score}</td>
					<td style="border-color: #0b335b;" align="center" nowrap="nowrap">${score.long_time}(秒)</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>