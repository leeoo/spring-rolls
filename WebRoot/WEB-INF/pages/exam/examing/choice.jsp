<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——开始考试</title>
<script type="text/javascript" src="${base}/commons/js/jcountdown/jquery.jcountdown1.3.min.js"></script>
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
			<td style="width:90%;height: 100%;" valign="top" align="left">
				<form id="examing" method="post" action="${base}/exam/save${ext}">
					<input type="hidden" id="examSessionId" name="examSessionId" value="${examSession.id}"/>
					<input type="hidden" id="choiceId" name="choiceId" value="${choice.id}"/>
					<input type="hidden" id="status" name="status" value="${currentChoiceMenu.status}"/>
					<input type="hidden" id="answer" name="answer" value="${anwserChoice}"/>
				</form>
				<c:if test="${currentChoiceMenu.status==1}">
					<div id="statusColor" style="border: solid 1px;padding: 40px;border-color :#139900;background-color:#bbeebb;">
				</c:if>
				<c:if test="${currentChoiceMenu.status==-1}">
					<div id="statusColor" style="border: solid 1px;padding: 40px;border-color :#0027ff;background-color:#d3eeee;">
				</c:if>
				<c:if test="${currentChoiceMenu.status==0}">
					<div id="statusColor" style="border: solid 1px;padding: 40px;border-color :#ff0000;background-color:#eed3d3;">
				</c:if>
					<table>
						<tr>
							<td>
								<b>${choice.title}&nbsp;(<c:if test="${!choice.multi}">单选题</c:if><c:if test="${choice.multi}">多选题</c:if>)</b>
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'A')>-1}">checked="checked"</c:if> id='answer_a' value='A'/>
									A：${choice.aOption}
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'B')>-1}">checked="checked"</c:if> id='answer_b' value='B'/>
									B：${choice.bOption}
								</label>
							</td>
						</tr>
						<c:if test="${choice.cOption!=null&&choice.cOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'C')>-1}">checked="checked"</c:if> id='answer_c' value='C'/>
										C：${choice.cOption}
									</label>
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.dOption!=null&&choice.dOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'D')>-1}">checked="checked"</c:if> id='answer_d' value='D'/>
										D：${choice.dOption}
									</label>
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.eOption!=null&&choice.eOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'E')>-1}">checked="checked"</c:if> id='answer_e' value='E'/>
										E：${choice.eOption}
									</label>
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.fOption!=null&&choice.fOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'F')>-1}">checked="checked"</c:if> id='answer_f' value='F'/>
										F：${choice.fOption}
									</label>
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.gOption!=null&&choice.gOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'G')>-1}">checked="checked"</c:if> id='answer_g' value='G'/>
										G：${choice.gOption}
									</label>
								</td>
							</tr>
						</c:if>
						<c:if test="${choice.hOption!=null&&choice.hOption!=''}">
							<tr>
								<td>
									<label>
										<input name='answer' type='<c:if test="${choice.multi}">checkbox</c:if><c:if test="${!choice.multi}">radio</c:if>' <c:if test="${fn:indexOf(anwserChoice,'H')>-1}">checked="checked"</c:if> id='answer_h' value='H'/>
										H：${choice.hOption}
									</label>
								</td>
							</tr>
						</c:if>
					</table>
					<div align="left">
						<a href="#" onclick="statusA();" class="easyui-linkbutton" iconCls="icon-ok">确认答案</a>&nbsp;&nbsp;<a href="#" onclick="statusB();" class="easyui-linkbutton" iconCls="icon-ding">待定答案</a>
					</div>
				</div>
				<div style="padding: 40px;" align="left">
					<span style="padding-left: 100px;"><c:if test='${currentChoiceMenu.previousId!=null}'><a href="#" onclick="getDateTime('${base}/exam/choice${ext}?choiceId=${currentChoiceMenu.previousId}&examSessionId=${examSession.id}&dateTime=');" class="easyui-linkbutton" iconCls="icon-back">上一题</a></c:if></span>
					<span style="padding-left: 200px;"><c:if test='${currentChoiceMenu.nextId!=null}'><a href="#" onclick="getDateTime('${base}/exam/choice${ext}?choiceId=${currentChoiceMenu.nextId}&examSessionId=${examSession.id}&dateTime=');" class="easyui-linkbutton" iconCls="icon-next">下一题</a></c:if></span>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
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
	    	   'status': $.trim($("#status").val()),
	    	   'answer': $.trim($("#answer").val()),
	    	   'choiceId': $.trim($("#choiceId").val())
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
	if($.trim($("#answer").val())!=''&&$.trim($("#status").val())==0){
		$.messager.confirm('提示','本题已经解答！是否确认答案?否则待定答案.',function(d){
            if(d){
            	$('#status').val(1);
        		tempSave();
            }else{
            	$('#status').val(-1);
        		tempSave();
            }
            var win = $.messager.progress({
    			title:'请稍等',
    			msg:'正在保存本题...'
    		});
    		setTimeout(function(){
    			$.messager.progress('close');
    			window.location.href=value+new Date().getTime();
    		},1000);
        });
	}else if($.trim($("#answer").val())==''){
		$.messager.confirm('提示','本题您还没有解答!是否待定此问题?否则继续解答本题.',function(d){
            if(d){
            	$('#status').val(-1);
        		tempSave();
        		var win = $.messager.progress({
        			title:'请稍等',
        			msg:'正在保存本题...'
        		});
        		setTimeout(function(){
        			$.messager.progress('close');
        			window.location.href=value+new Date().getTime();
        		},1000);
            }
        });
	}else{
		window.location.href=value+new Date().getTime();
	}
}

function finish(){
	$.messager.confirm('提示','您确认交卷吗?',function(d){
        if(d){
			$('#examing').submit();
        }
    });
}
$(document).ready(function(){
	$("#answer_a").click(function () {  
		answer();
		});
	$("#answer_b").change(function () {  
		answer();
		});
	$("#answer_c").change(function () {  
		answer();
		});
	$("#answer_d").change(function () {  
		answer();
		});
	$("#answer_e").change(function () {  
		answer();
		});
	$("#answer_f").change(function () {  
		answer();
		});
	$("#answer_g").change(function () {  
		answer();
		});
	$("#answer_h").change(function () {  
		answer();
		});
});
function answer(){
	var  answer = '';
	if($('#answer_a').attr("checked")==true||$('#answer_a').attr("checked")=='checked'){
		answer += ',A';
	}
	if($('#answer_b').attr("checked")==true||$('#answer_b').attr("checked")=='checked'){
		answer += ',B';
	}
	if($('#answer_c').attr("checked")==true||$('#answer_c').attr("checked")=='checked'){
		answer += ',C';
	}
	if($('#answer_d').attr("checked")==true||$('#answer_d').attr("checked")=='checked'){
		answer += ',D';
	}
	if($('#answer_e').attr("checked")==true||$('#answer_e').attr("checked")=='checked'){
		answer += ',E';
	}
	if($('#answer_f').attr("checked")==true||$('#answer_f').attr("checked")=='checked'){
		answer += ',F';
	}
	if($('#answer_g').attr("checked")==true||$('#answer_g').attr("checked")=='checked'){
		answer += ',G';
	}
	if($('#answer_h').attr("checked")==true||$('#answer_h').attr("checked")=='checked'){
		answer += ',H';
	}
	if(answer.length>0){
		answer = answer.substring(1, answer.length);
	}
	$('#answer').val(answer);
}
function statusA(){
	if($.trim($("#answer").val())!=''){
		$('#status').val(1);
		tempSave();
		$('#statusColor').css({'border-color' :'#139900','background-color':'#bbeebb'});
	}else{
		$.messager.alert('警告！','没有选项被选中，不能确认答案！','error');
	}
	
}
function statusB(){
	$('#status').val(-1);
	tempSave();
	$('#statusColor').css({'border-color' :'#0027ff','background-color':'#d3eeee'});
}
</script>