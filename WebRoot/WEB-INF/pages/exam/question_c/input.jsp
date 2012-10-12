<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——理论考试题库维护</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="理论考试题库维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="questionForm" action="${base}/exam/question_c/save${ext}" method="post">
			<input type="hidden" name="question.choice" value="true"/>
			<c:if test="${question!=null}">
				<input type="hidden" name="question.id" value="${question.id}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">题库描述:</td>
						<td><input name="question.title" class="easyui-validatebox" required="true" validType="length[3,500]" style="width: 500px;" value="${question.title}"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center" style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
							<table>
								<tr><td width="100">问题内容:</td><td><textarea class="easyui-validatebox" required="true" rows="3" cols="60" name="choice.title"></textarea></td><td>权重:<input name="choice.proportion" required="true" class="easyui-numberbox" style="width: 30px;" required="true" value="1"/></td></tr>
								<tr><td width="100">选项A:</td><td><input name="choice.aOption" class="easyui-validatebox" required="true" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_a" value="A"/></td></tr>
								<tr><td width="100">选项B:</td><td><input name="choice.bOption" class="easyui-validatebox" required="true" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_b" value="B"/></td></tr>
								<tr><td width="100">选项C:</td><td><input name="choice.cOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_c" value="C"/></td></tr>
								<tr><td width="100">选项D:</td><td><input name="choice.dOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_d" value="D"/></td></tr>
								<tr><td width="100">选项E:</td><td><input name="choice.eOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_e" value="E"/></td></tr>
								<tr><td width="100">选项F:</td><td><input name="choice.fOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_f" value="F"/></td></tr>
								<tr><td width="100">选项G:</td><td><input name="choice.gOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_g" value="G"/></td></tr>
								<tr><td width="100">选项H:</td><td><input name="choice.hOption" style="width: 500px;"></input></td><td>正确答案:<input type="checkbox" id="answer_h" value="H"/></td></tr>
								<tr><td colspan="3" align="right"><a href="#" class="easyui-linkbutton"  iconCls="icon-add">添加</a></td></tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
								<tr>
									<td nowrap="nowrap">-------------待开发-------------</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
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