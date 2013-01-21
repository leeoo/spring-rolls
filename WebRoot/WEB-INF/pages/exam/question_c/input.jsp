<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>${title}——理论考试题库维护</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="理论考试题库维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<div>
			<table>
				<tr>
					<td nowrap="nowrap">题库描述:</td>
					<td>
						<form action="${base}/exam/question_c/save${ext}" method="post" id="questionForm">
							<input type="hidden" name="question.choice" value="true"/>
							<input id="questionTitle" name="question.title" class="easyui-validatebox" required="true" validType="length[3,500]" style="width: 500px;" value="${question.title}"/>
							<c:if test="${question!=null}">
								<input type="hidden" id="question.id" name="question.id" value="${question.id}"/>
							</c:if>
							<a class="easyui-linkbutton" href="${base}/exam/question_c/list${ext}" iconCls="icon-back">返 回</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save">提 交</a>
							<script>
								function submitForm(){
									$('#questionForm').submit();
								}
							</script>
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
						<form action="${base}/exam/question_c/choice_save${ext}" method="post" id="choiceForm" onsubmit="return onSub();">
							<table>
								<tr>
									<td width="100">问题内容:</td><td><textarea id="choiceTitle" class="easyui-validatebox" required="true" rows="3" cols="60" name="choice.title"></textarea></td>
									<td>权重:<input id="proportion" name="choice.proportion" required="true" class="easyui-numberbox" style="width: 30px;" required="true" value="1"/></td>
								</tr>
								<tr><td width="100">选项A:</td><td><input id="aOption" name="choice.aOption" class="easyui-validatebox" required="true" style="width: 500px;"/></td><td>正确答案:<input type="checkbox" id="answer_a" value="A"/></td></tr>
								<tr><td width="100">选项B:</td><td><input id="bOption" name="choice.bOption" class="easyui-validatebox" required="true" style="width: 500px;"/></td><td>正确答案:<input type="checkbox" id="answer_b" value="B"/></td></tr>
								<tr><td width="100">选项C:</td><td><input id="cOption" name="choice.cOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_c" value="C"/></td></tr>
								<tr><td width="100">选项D:</td><td><input id="dOption" name="choice.dOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_d" value="D"/></td></tr>
								<tr><td width="100">选项E:</td><td><input id="eOption" name="choice.eOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_e" value="E"/></td></tr>
								<tr><td width="100">选项F:</td><td><input id="fOption" name="choice.fOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_f" value="F"/></td></tr>
								<tr><td width="100">选项G:</td><td><input id="gOption" name="choice.gOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_g" value="G"/></td></tr>
								<tr><td width="100">选项H:</td><td><input id="hOption" name="choice.hOption" style="width: 500px;"/></td><td>正确答案:<input disabled="disabled" type="checkbox" id="answer_h" value="H"/></td></tr>
								<tr><td colspan="3" align="right">
								<input type="hidden" id="answer" name="choice.answer"/>
								<c:if test="${question!=null}">
									<input type="hidden" id="question.id" name="question.id" value="${question.id}"/>
								</c:if>
								<input type="hidden" id='questionTitleHidden' name="question.title" value="${question.title}"/>
								<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm2()" iconCls="icon-save">添 加</a>
								<script>
									function submitForm2(){
										$('#choiceForm').submit();
									}
								</script>
								</td></tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table>
							<c:forEach items="${choices}" var="choice" varStatus="status">
								<tr>
									<td>
										<b>${status.index+1}.${choice.title}&nbsp;&nbsp;(权重：${choice.proportion})</b><b style="color: RED">&nbsp;&nbsp;正确答案[ ${choice.answer} ]</b><span style="padding-left: 20px;"><a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="return removeChoice(${choice.id});">删除该题</a></span>
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
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
	
	$(document).ready(function(){
		$("#questionTitle").change(function () {
			$('#questionTitleHidden').val($("#questionTitle").val());
       	});
		$("#bOption").change(function () {
			if($('#aOption').val()==''){
				$('#bOption').val('');
				$.messager.alert('严重警告！','填写B选项之前，A选项必须填写！','error');
				$('#aOption').focus();
				return false;
			}
       	});
		$("#cOption").change(function () {
			if($('#bOption').val()==''){
				$('#cOption').val('');
				$.messager.alert('严重警告！','填写C选项之前，B选项必须填写！','error');
				$('#bOption').focus();
				return false;
			}
			if($('#cOption').val()!=''){
				$('#answer_c').removeAttr("disabled");
			}else{
				$('#answer_c').attr("disabled","disabled");
				$('#answer_c').removeAttr("checked");
			}
       	});
		$("#dOption").change(function () {
			if($('#cOption').val()==''){
				$('#dOption').val('');
				$.messager.alert('严重警告！','填写D选项之前，C选项必须填写！','error');
				$('#cOption').focus();
				return false;
			}
			if($('#dOption').val()!=''){
				$('#answer_d').removeAttr("disabled");
			}else{
				$('#answer_c').attr("disabled","disabled");
				$('#answer_c').removeAttr("checked");
			}
       	});
		$("#eOption").change(function () {
			if($('#dOption').val()==''){
				$('#eOption').val('');
				$.messager.alert('严重警告！','填写E选项之前，D选项必须填写！','error');
				$('#dOption').focus();
				return false;
			}
			if($('#eOption').val()!=''){
				$('#answer_e').removeAttr("disabled");
			}else{
				$('#answer_e').attr("disabled","disabled");
				$('#answer_e').removeAttr("checked");
			}
       	});
		$("#fOption").change(function () {
			if($('#eOption').val()==''){
				$('#fOption').val('');
				$.messager.alert('严重警告！','填写F选项之前，E选项必须填写！','error');
				$('#eOption').focus();
				return false;
			}
			if($('#fOption').val()!=''){
				$('#answer_f').removeAttr("disabled");
			}else{
				$('#answer_f').attr("disabled","disabled");
				$('#answer_f').removeAttr("checked");
			}
       	});
		$("#gOption").change(function () {
			if($('#fOption').val()==''){
				$('#gOption').val('');
				$.messager.alert('严重警告！','填写G选项之前，F选项必须填写！','error');
				$('#fOption').focus();
				return false;
			}
			if($('#gOption').val()!=''){
				$('#answer_g').removeAttr("disabled");
			}else{
				$('#answer_g').attr("disabled","disabled");
				$('#answer_g').removeAttr("checked");
			}
       	});
		$("#hOption").change(function () {
			if($('#gOption').val()==''){
				$('#hOption').val('');
				$.messager.alert('严重警告！','填写H选项之前，G选项必须填写！','error');
				$('#gOption').focus();
				return false;
			}
			if($('#hOption').val()!=''){
				$('#answer_h').removeAttr("disabled");
			}else{
				$('#answer_h').attr("disabled","disabled");
				$('#answer_h').removeAttr("checked");
			}
       	});
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
	
	function onSub(){
		if($('#proportion').val()==''){
			$.messager.alert('严重警告！','必须设置权重！系统为您填写默认值为1.','error');
			$('#proportion').val('1');
			return false;
		}
		if($('#choiceTitle').text()==''&&$('#choiceTitle').val()==''){
			$.messager.alert('严重警告！','请填写问题内容！','error');
			return false;
		}
		if($('#aOption').val()==''||$('#bOption').val()==''){
			$.messager.alert('严重警告！','至少设置两个选项，请填写A、B选项！','error');
			return false;
		}
		if($('#answer').val()==''){
			$.messager.alert('严重警告！','您没有设置正确答案！','error');
			return false;
		}
	}
	
	function removeChoice(id){
		$.messager.confirm('提示','确认删除该题吗?',function(d){
            if(d){
            	var choiceForm = document.getElementById('choiceForm');
            	choiceForm.method = "POST";
            	choiceForm.action = '${base}/exam/question_c/choice_delete${ext}?id='+id;
            	choiceForm.submit();
            }
        });
	}
	
</script>
</body>
</html>