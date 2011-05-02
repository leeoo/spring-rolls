<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>Paramecium开发平台演示——<decorator:title></decorator:title></title>
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<decorator:head></decorator:head>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:80px;background-image: url('/commons/images/head.gif');background-color: #EEF9FB;background-repeat: no-repeat;">
		<div align="right" style="padding-right: 10px;padding-top: 5px;">
			<a href="${base}/logout.jhtml" class="easyui-linkbutton" plain="false" iconCls="icon-undo">退出</a>
		</div>
	</div>
	<div region="west" split="true" icon="icon-search" title="功能菜单" style="width:200px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="系统管理" icon="icon-save" selected="true" style="overflow:auto;padding-left: 20px;padding-top: 10px;">
				<p>角色设定</p>
				<p>登录账户</p>
				<p>菜单权限</p>
				<p>机构管理</p>
				<p>数据字典</p>
				<p>系统开关</p>
				<p>日志管理</p>
				<p>任务调度</p>
			</div>
			<div title="服务管理" icon="icon-print" style="padding-left: 10px;padding-top: 20px;">
				<p>服务设定1</p>
				<p>服务设定2</p>
				<p>服务设定3</p>
				<p>服务设定4</p>
				<p>服务设定5</p>
			</div>
			<div title="上行短信统计" icon="icon-cut" style="padding-left: 10px;padding-top: 20px;">
				<p>服务设定1</p>
				<p>服务设定2</p>
				<p>服务设定3</p>
				<p>服务设定4</p>
				<p>服务设定5</p>
			</div>
			<div title="历史报表" icon="icon-sum" style="padding-left: 10px;padding-top: 20px;">
				<p>服务设定1</p>
				<p>服务设定2</p>
				<p>服务设定3</p>
				<p>服务设定4</p>
				<p>服务设定5</p>
			</div>
		</div>
	</div>
	<div region="center" title="<decorator:title></decorator:title>">
		<decorator:body></decorator:body>
	</div>
</body>
</html>