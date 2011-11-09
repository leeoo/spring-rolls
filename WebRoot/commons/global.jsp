<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.paramecium.org/tags" prefix="paramecium"%>
<c:set var="path" value=""/>
<c:if test="${pageContext.request.contextPath!='/'}">
	<c:set var="path" value="${pageContext.request.contextPath}"/>
</c:if>
<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort}${path}"/>
<%@page import="com.demo.web.BaseController"%>
<c:set var="ext" value="<%=BaseController.EXT %>"/>