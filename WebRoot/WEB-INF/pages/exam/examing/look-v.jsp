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
        	if(msg!=null){
        		$.messager.alert('严重警告！',msg.message,'error');
        	}
        }
    })
}
setInterval(tempSave,10000);

function finish(){
	$.messager.confirm('提示','您确认交卷吗?',function(d){
        if(d){
			$('#examing').submit();
        }
    });
}

function getDateTime(value){
	window.location.href='${base}/exam/change-layout${ext}?id='+value+'&dateTime='+new Date().getTime();
}
</script>

</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" oncontextmenu="return false;" onselectstart="return false;">
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF"><td height="5%" align="right">
			<span id="time"></span>
			<a href="#" onclick="getDateTime(${examSession.id})" class="easyui-linkbutton">切换水平布局</a>
			<a href="#" class="easyui-linkbutton" onclick="isAutoScroll();">自动滚动原文</a>
			<select id='speed' onchange="changeSpeed();">
				<option value="3000">慢速</option>
				<option value="1500">中速</option>
				<option value="800" selected="selected">偏快速</option>
				<option value="400">快速</option>
				<option value="200">超快速</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="return finish();">交 卷</a>
		</td></tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 43%">
				<div id="sourceConent" onmousemove="moveScroll();" style="background-color: #EFEFBA;width: 100%;height: 100%;font-size: 20px;OVERFLOW-y:auto;">${examSession.textContent}</div>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 50%">
				<form id="examing" method="post" action="${base}/exam/save${ext}">
					<input type="hidden" id="examSessionId" name="examSessionId" value="${examSession.id}"></input>
					<textarea id="tempContent" name="tempContent" onpaste="return false;" ondragstart="return false;" rows="10" cols="10" style="font-size: 20px;width:98%;height: 98%">${examineeSession.tempContent}</textarea>
				</form>
			</td>
		</tr>
	</table>
</body>
<script>
var e=document.getElementById("sourceConent");
var speed=document.getElementById("speed").value;
var y = false;
var i = e.scrollTop;
function sc(){
	if(y){
		e.scrollTop=i++;
	}
}
var s;
function isAutoScroll(){
	i = e.scrollTop;
	y = !y;
	if(y){
		s=setInterval(sc,speed);
	}else{
		window.clearInterval(s);
	}
}
function moveScroll(){
	if(y){
		i = e.scrollTop;
	}
}
function changeSpeed(){
	if(y){
		window.clearInterval(s);
		speed=document.getElementById("speed").value;
		s=setInterval(sc,speed);
	}
}
</script>
</html>