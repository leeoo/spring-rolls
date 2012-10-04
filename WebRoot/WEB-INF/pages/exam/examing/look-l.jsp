<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——开始考试</title>
<script type="text/javascript" src="${base}/commons/js/jcountdown/jquery-1.6.1.js"></script>
<script type="text/javascript" src="${base}/commons/js/jcountdown/jquery.jcountdown1.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#time").countdown({
		date: '${examingEndTime}',
		htmlTemplate: "距考试结束还有 <span style='font-size: 20px;color: RED;'>%{h}</span> 小时 <span style='font-size: 20px;color: RED;'>%{m}</span> 分 <span style='font-size: 20px;color: RED;'>%{s}</span> 秒",
		onChange: function( event, timer ){
		},
		onComplete: function( event ){
			alert('考试结束！');
		},
		leadingZero: true,
		direction: "down"
	});
});
</script>

</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;">
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF"><td colspan="2" height="5%" align="right">
			<span id="time"></span>
			<a href="${base}/exam/change-layout${ext}?id=${examSession.id}" class="">切换垂直布局</a>
		</td></tr>
		<tr>
			<td valign="top" align="left" style="width: 50%;height: 95%">
				<div id="sourceConent" style="background-color: #EFEFBA;width: 100%;height: 100%;font-size: 24px;">${examSession.textContent}</div>
			</td>
			<td valign="top" align="left" valign="top" align="left">
				<form method="post" action="${base}/exam/save${ext}">
					<textarea rows="20" cols="10" style="font-size: 24px;width:98%;height: 98%">${examineeSession.tempContent}</textarea>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>