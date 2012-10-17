<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——成绩查看</title>
</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" <paramecium:resourceAuthorize ifNotGranted="/system/themes#change"> oncontextmenu="return false;" onselectstart="return false;" </paramecium:resourceAuthorize> >
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF">
			<td height="5%" align="right" style="padding-right: 20px;">
				<span style="font-size: 20px;font-style: italic;color: RED"><b>${score.score}分</b></span>&nbsp;&nbsp;
				<span style="font-size: 13px;"><b>考试用时：${score.longTime}秒</b></span>
				<span style="border: solid 1px ; border-color :#139900;background-color:#bbeebb;width: 60px;height: 20px;padding-right: 10px;font-size: 15px;">回答正确</span>
				<span style="border: solid 1px ; border-color :#ff0000;background-color:#eed3d3;width: 60px;height: 20px;padding-right: 10px;font-size: 15px;">回答错误</span>
			</td>
		</tr>
		<c:forEach items="${questionChoices}" var="choice" varStatus="status">
			<tr>
				<td valign="top" align="left" style="width: 100%;">
					<c:if test="${choice.right}">
					<div style="border: solid 1px ; border-color :#139900;background-color:#bbeebb;width: 100%;">
					</c:if>
					<c:if test="${!choice.right}">
					<div style="border: solid 1px ; border-color :#ff0000;background-color:#eed3d3;width: 100%;">
					</c:if>
						<b>${status.index+1}.${choice.title}&nbsp;&nbsp;(权重：${choice.proportion})</b><b style="color: BLUE"><paramecium:resourceAuthorize ifAnyGranted="/system/themes#change">&nbsp;&nbsp;正确答案[ ${choice.answer} ]</paramecium:resourceAuthorize>&nbsp;&nbsp;考生答案[ ${choice.examineeAnswer} ]</b>
						<div>A：${choice.aOption}</div>
						<div>B：${choice.bOption}</div>
						<c:if test="${choice.cOption!=null&&choice.cOption!=''}">
							<div>C：${choice.cOption}</div>
						</c:if>
						<c:if test="${choice.dOption!=null&&choice.dOption!=''}">
							<div>D：${choice.dOption}</div>
						</c:if>
						<c:if test="${choice.eOption!=null&&choice.eOption!=''}">
							<div>E：${choice.eOption}</div>
						</c:if>
						<c:if test="${choice.fOption!=null&&choice.fOption!=''}">
							<div>F：${choice.fOption}</div>
						</c:if>
						<c:if test="${choice.gOption!=null&&choice.gOption!=''}">
							<div>G：${choice.gOption}</div>
						</c:if>
						<c:if test="${choice.hOption!=null&&choice.hOption!=''}">
							<div>H：${choice.hOption}</div>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>