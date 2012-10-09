<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——成绩查看</title>
</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" oncontextmenu="return false;" onselectstart="return false;">
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF">
			<td height="5%" align="right" style="padding-right: 20px;">
				<span style="font-size: 15px;"><b>评分权重：[汉字:${exam.cnProportion},字母:${exam.enProportion},标点:${exam.punProportion},数字:${exam.numProportion}]</b></span>&nbsp;&nbsp;
				<span style="font-size: 30px;font-style: italic;color: RED"><b>${score.score}分</b></span>&nbsp;&nbsp;
				<span style="font-size: 15px;"><b>考试用时：${score.longTime}秒</b></span>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 47%">
				<div style="font-size: 15px;font-style: italic;"><b>原文</b></div>
				<div style="background-color: #EFEFBA;width: 100%;height: 100%;font-size: 20px;OVERFLOW-y:auto;">${question.textContent}</div>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 47%">
				<div style="font-size: 15px;font-style: italic;"><b>答案</b></div>
				<div style="background-color: #ABEFFF;width: 100%;height: 100%;font-size: 20px;OVERFLOW-y:auto;">${score.context}</div>
			</td>
		</tr>
	</table>
</body>
</html>