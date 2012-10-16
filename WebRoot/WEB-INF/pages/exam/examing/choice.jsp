<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——开始考试</title>
<script type="text/javascript" src="${base}/commons/js/jcountdown/jquery.jcountdown1.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#time").countdown({
		date: '${examingEndTime}',
		htmlTemplate: "距考试结束还有 <span style='font-size: 20px;color: RED;'>%{h}</span> 小时 <span style='font-size: 20px;color: RED;'>%{m}</span> 分 <span style='font-size: 20px;color: RED;'>%{s}</span> 秒",
		onChange: function( event, timer ){
		},
		onComplete: function( event ){
			$.messager.alert('考试结束','系统已经为您自动交卷！','info');
			$('#examing').submit();
		},
		leadingZero: true,
		direction: "down"
	});
});
function tempSave() {  
    $.ajax({  
        url: "${base}/exam/temp-save.json",
        cache: false,
        dataType: "json",
        type: "post",
        data: {
	    	   'examSessionId': $.trim($("#examSessionId").val()),
	    	   'tempContent': $.trim($("#tempContent").val())
	    	   },
        timeout: 2000,  
        success: function (msg) {
        	 if(msg!=null&&msg!=''){
        		$.messager.alert('严重警告！',msg,'error');
        	}
        }
    })
}
function getDateTime(value){
	window.location.href=value+new Date().getTime();
}
function finish(){
	$.messager.confirm('提示','您确认交卷吗?',function(d){
        if(d){
			$('#examing').submit();
        }
    });
}
</script>
</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" oncontextmenu="return false;" onselectstart="return false;">
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF">
			<td height="5%" align="right" style="padding-right: 30px;" colspan="2">
				<span id="time"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="return finish();">交 卷</a>
			</td>
		</tr>
		<tr>
			<td style="background-color: #EFEFEF;width: 300px;height: 100%;" align="left" valign="top" nowrap="nowrap">
				答题目录：
				<div style="padding:30px;">
					<c:forEach items="${choiceMenus}" var="choiceMenu" varStatus="status">
						<c:if test="${choiceMenu.value.status==1}">
							<span style="border: solid <c:if test="${currentChoiceMenu.id==choiceMenu.value.id}">3</c:if><c:if test="${currentChoiceMenu.id!=choiceMenu.value.id}">1</c:if>px ; border-color :#139900;background-color:#bbeebb;width: 40px;height: 40px;padding-right: 10px;font-size: 20px;">&nbsp;<a style="color: #000;" href="#" onclick="getDateTime('${base}/exam/choice${ext}?choiceId=${choiceMenu.key}&examSessionId=${examSession.id}&dateTime=');">${status.index+1}</a></span>
						</c:if>
						<c:if test="${choiceMenu.value.status==-1}">
							<span style="border: solid <c:if test="${currentChoiceMenu.id==choiceMenu.value.id}">3</c:if><c:if test="${currentChoiceMenu.id!=choiceMenu.value.id}">1</c:if>px ; border-color :#0027ff;background-color:#d3eeee;width: 40px;height: 40px;padding-right: 10px;font-size: 20px;">&nbsp;<a style="color: #000;" href="#" onclick="getDateTime('${base}/exam/choice${ext}?choiceId=${choiceMenu.key}&examSessionId=${examSession.id}&dateTime=');">${status.index+1}</a></span>
						</c:if>
						<c:if test="${choiceMenu.value.status==0}">
							<span style="border: solid <c:if test="${currentChoiceMenu.id==choiceMenu.value.id}">3</c:if><c:if test="${currentChoiceMenu.id!=choiceMenu.value.id}">1</c:if>px ; border-color :#ff0000;background-color:#eed3d3;width: 40px;height: 40px;padding-right: 10px;font-size: 20px;">&nbsp;<a style="color: #000;" href="#" onclick="getDateTime('${base}/exam/choice${ext}?choiceId=${choiceMenu.key}&examSessionId=${examSession.id}&dateTime=');">${status.index+1}</a></span>
						</c:if>
						<c:if test="${(status.index+1) mod 5 == 0}">
							<div style="font-size: 6px;">&nbsp;</div>
						</c:if>
					</c:forEach>
				</div>
				
				<div style="padding: 30px;">
					<div style="border: solid 1px ; border-color :#139900;background-color:#bbeebb;width: 60px;height: 20px;padding-right: 10px;font-size: 15px;">确认答案</div>
					<div style="font-size: 6px;">&nbsp;</div>
					<div style="border: solid 1px ; border-color :#0027ff;background-color:#d3eeee;width: 60px;height: 20px;padding-right: 10px;font-size: 15px;">待定答案</div>
					<div style="font-size: 6px;">&nbsp;</div>
					<div style="border: solid 1px ; border-color :#ff0000;background-color:#eed3d3;width: 60px;height: 20px;padding-right: 10px;font-size: 15px;">未解答案</div>
				</div>
			</td>
			<td style="width:90%;height: 100%;" valign="top">
				<form id="examing" method="post" action="${base}/exam/save${ext}">
					<input type="hidden" id="examSessionId" name="examSessionId" value="${examSession.id}"/>
					<input type="hidden" id="status" name="status" value="0"/>
					<input type="hidden" id="answer" name="answer"/>
				</form>
				<div style="padding: 40px;">
					<table>
						<tr>
							<td>
								<b>${choice.title}</b>
							</td>
						</tr>
						<tr>
							<td>
								A：${choice.aOption}
							</td>
						</tr>
						<tr>
							<td>
								B：${choice.bOption}
							</td>
						</tr>
						<c:if test="${choice.cOption!=null&&choice.cOption!=''}">
							<tr>
								<td>
									C：${choice.cOption}
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.dOption!=null&&choice.dOption!=''}">
							<tr>
								<td>
									D：${choice.dOption}
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.eOption!=null&&choice.eOption!=''}">
							<tr>
								<td>
									E：${choice.eOption}
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.fOption!=null&&choice.fOption!=''}">
							<tr>
								<td>
									F：${choice.fOption}
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.gOption!=null&&choice.gOption!=''}">
							<tr>
								<td>
									G：${choice.gOption}
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.hOption!=null&&choice.hOption!=''}">
							<tr>
								<td>
									H：${choice.hOption}
								</td>
							</tr>
						</c:if>
					</table>
				</div>
				<div style="padding: 40px;">
				
				</div>
			</td>
		</tr>
	</table>
</body>
</html>