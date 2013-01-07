<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——角色信息维护</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="角色信息维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="firstForm" action="${base}/system/role/save${ext}" method="post">
			<c:if test="${role.id!=null}">
				<input type="hidden" name="role.id" value="${role.id}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">角色描述:</td>
						<td><input name="role.name" class="easyui-validatebox" data-options="required:true" validType="length[2,50]" style="width: 300px;" value="${role.name}"/></td>
					</tr>
					<tr>
						<td>角色标志:</td>
						<td><input name="role.rolename" class="easyui-validatebox" data-options="required:true" validType="length[2,50]" style="width: 300px;" value="${role.rolename}"/></td>
					</tr>
					<tr>
						<td>授权信息:</td>
						<td>
							<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
							<c:forEach items="${resources}" var="resourceKey" varStatus="status">
								<label><input type="checkbox" id="C_${status.index}"></input><b>${resourceKey.key.showLabel}</b></label>&nbsp;&nbsp;&nbsp;&nbsp;
								<div id="R_${status.index}" style="padding-left: 20px;border-bottom: 1px;border-bottom-style: dotted;">
									<c:forEach items="${resourceKey.value}" var="resource">
										<c:if test="${role==null || role.auth==null}">
											<label><input type="checkbox" name="auth" value="${resource}">${resource.showLabel}</label>&nbsp;
										</c:if>
										<c:set var="sign" value="0"></c:set>
										<c:if test="${role!=null && role.auth!=null}">
											<c:forEach items="${role.auth}" var="checkResource">
												<c:if test="${resource==checkResource}">
													<label><input type="checkbox" name="auth" value="${resource}" checked="checked">${resource.showLabel}</label>&nbsp;
													<c:set var="sign" value="1"></c:set>
												</c:if>
									 		</c:forEach>
									 		<c:if test="${sign==0}">
									 			<label><input type="checkbox" name="auth" value="${resource}">${resource.showLabel}</label>&nbsp;
									 		</c:if>
										</c:if>
									</c:forEach>
								</div>
								<script>
								$(document).ready(function(){
									$("#C_${status.index}").change(function () {
										if($("#C_${status.index}").attr('checked')=='checked'){
											$('#R_${status.index} :checkbox').attr("checked","checked");
										}else{
											$('#R_${status.index} :checkbox').removeAttr("checked");
										}
							       	});
								 });
								</script>
							</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right">
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save">提交</a>
							<script>
								function submitForm(){
									$('#firstForm').submit();
								}
							</script>
						</td>
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