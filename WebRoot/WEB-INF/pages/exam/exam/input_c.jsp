<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——理论考试信息维护</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<script>
		$(function(){
			$('#selectABC').combogrid({
				panelWidth:450,
				value:'${exam.questionId}',
				idField:'id',
				textField:'miniTitle',
				url:'${base}/exam/exam/qcdata.json',
				pagination : true,//是否分页  
                rownumbers:true,//序号 
                collapsible:false,//是否可折叠的  
                pageSize: 5,//每页显示的记录条数，默认为5  
                pageList: [5],//可以设置每页记录条数的列表
                method:'post',
                fit: true,//自动大小 
				columns:[[
					{field:'miniTitle',title:'题库标题',width:400}
				]]
			});
		});
</script>
<div region="center" title="理论考试信息维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="firstForm" action="${base}/exam/exam/save${ext}" method="post">
			<c:if test="${exam!=null}">
				<input type="hidden" name="exam.id" value="${exam.id}"/>
			</c:if>
				<input type="hidden" name="exam.status" value="${exam.status}"/>
				<input type="hidden" name="exam.choice" value="true"/>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">考试描述:</td>
						<td><input name="exam.title" class="easyui-validatebox" required="true"  validType="length[3,500]" style="width: 500px;" value="${exam.title}"/></td>
					</tr>
					<tr>
						<td>总分数:</td>
						<td>
							<input name="exam.score" class="easyui-numberbox" required="true" value="${exam.score==null?100:exam.score}"/>分
						</td>
					</tr>
					<tr>
						<td>考试时长:</td>
						<td>
							<input name="exam.longTime" class="easyui-numberbox" required="true" value="${exam.longTime==null?60:exam.longTime}"/>分钟
						</td>
					</tr>
					<tr>
						<td>开始时间:</td>
						<td><input name="exam.startDate" class="easyui-datetimebox" value="${exam.startDate}"/></td>
					</tr>
					<tr>
						<td>结束时间:</td>
						<td><input name="exam.endDate" class="easyui-datetimebox" value="${exam.endDate}"/></td>
					</tr>
					<tr>
						<td>选择题库:</td>
						<td><select id="selectABC" name="exam.questionId" style="width:250px;"></select></td>
					</tr>
					<tr>
						<td></td>
						<td align="right">
						<a class="easyui-linkbutton" href="${base}/exam/exam/list${ext}" iconCls="icon-back">返 回</a>
						<c:if test="${exam==null||exam.status==0}">
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save">提 交</a>
							<script>
								function submitForm(){
									$('#firstForm').submit();
								}
							</script>
						</c:if></td>
					</tr>
				</table>
			</div>
		</form>
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