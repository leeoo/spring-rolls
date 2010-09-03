<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>角色管理 - 角色信息</title>
</head>
<body>
<div align="left">
	<form name="role" action="${base}/example/role/save.htm" method="post">
		<input type="hidden" name="id" value="${role.id}"/>
		角色标示：<input name="name" value="${role.name}"/><br/>
		角色名称：<input name="cnname" value="${role.cnname}"/><br/>
		拥有权限：<c:forEach items="${resources}" var="resource">
				<c:if test="${role==null || role.resources==null}">
					<input type="checkbox" name="resourceIds" value="${resource.id}">${resource.cnname}&nbsp;
				</c:if>
				<c:set var="sign" value="0"></c:set>
				<c:if test="${role!=null && role.resources!=null}">
					<c:forEach items="${role.resources}" var="checkResource">
						<c:if test="${resource.id==checkResource.id}">
							<input type="checkbox" name="resourceIds" value="${resource.id}" checked="checked">${resource.cnname}&nbsp;
							<c:set var="sign" value="1"></c:set>
						</c:if>
			 		</c:forEach>
			 		<c:if test="${sign==0}">
			 			<input type="checkbox" name="resourceIds" value="${resource.id}">${resource.cnname}&nbsp;
			 		</c:if>
				</c:if>
			 </c:forEach><br>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>