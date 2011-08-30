<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>Paramecium开发平台演示——<decorator:title></decorator:title></title>
	<link rel="shortcut icon" href="${base}/commons/images/xeyes.png" type="image/x-icon" /> 
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<script>
		function isExit(){
			$.messager.confirm('提示','是否确认退出本系统?',function(d){
	            if(d){
	            	location.href ='${base}/logout${ext}';
	            }
	        });
		}
	</script>
	<decorator:head></decorator:head>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:80px;background-image: url('/commons/images/head.gif');background-color: #EEF9FB;background-repeat: no-repeat;">
		<div align="right" style="padding-right: 10px;padding-top: 5px;">
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-group">在线用户</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-msg">短消息</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">帮助</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="return isExit();">退出</a>
		</div>
	</div>
	<div region="west" split="true" icon="icon-cl" title="功能菜单" style="width:200px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="系统管理" icon="icon-tools" selected="true" style="overflow:auto;padding-left: 20px;padding-top: 10px;">
				<p><a href="${base}/system/role/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-key">角色设定</a></p>
				<p><a href="${base}/system/user/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-user">登录账户</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-security">菜单权限</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-home">机构管理</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-doc">数据字典</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-ding">系统开关</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-date">日志管理</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-time">任务调度</a></p>
			</div>
			<div title="服务管理" icon="icon-db" style="padding-left: 10px;padding-top: 20px;">
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
			</div>
			<div title="上行短信统计" icon="icon-mobile" style="padding-left: 10px;padding-top: 20px;">
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
			</div>
			<div title="历史报表" icon="icon-report" style="padding-left: 10px;padding-top: 20px;">
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
			</div>
		</div>
	</div>
	<decorator:body></decorator:body>
</body>
</html>