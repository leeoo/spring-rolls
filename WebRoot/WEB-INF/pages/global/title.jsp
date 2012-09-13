<c:set var="baseHeight" value="73"/>
<%@ page contentType="text/html;charset=UTF-8"%>
	<div region="north" border="false" style="height:45px;background-image: url('${base}/commons/images/head.gif');background-color: #EEF9FB;">
		<div align="left" style="float: left;">
			<div style="font-size: 25px;color: #FFF;padding-left: 10px;padding-top:5px;font-weight: bold;font-family: 微软雅黑">亚伟在线考试系统</div>
		</div>
		<div align="right" style="padding-right: 50px;padding-top: 5px;">
			<a href="javascript:void(0)" id="$security" class="easyui-splitbutton" style="color: #FFF" menu="#security" iconCls="icon-security">系统管理</a>
			<a href="javascript:void(0)" id="$logger" class="easyui-splitbutton" style="color: #FFF" menu="#logger" iconCls="icon-date">日志查看</a>
			<a href="${base}/system/console/index${ext}" class="easyui-linkbutton" style="color: #FFF" plain="true" iconCls="icon-talk">控制台</a>
			<a href="${base}/system/help/list${ext}" class="easyui-linkbutton" style="color: #FFF" plain="true" iconCls="icon-help">帮助</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" style="color: #FFF" onclick="return isExit();">退出</a>
		</div>
		<div id="security" style="width:150px;">
			<div iconCls="icon-db" onclick="gotoUrl('${base}/system/ds/list${ext}');">数据源配置</div>
			<div iconCls="icon-tools" onclick="gotoUrl('${base}/system/config/security/list${ext}');">系统安全配置</div>
			<div iconCls="icon-ding" onclick="gotoUrl('${base}/system/config/ip/list${ext}')">IP地址限制</div>
			<div class="menu-sep"></div>
			<div iconCls="icon-key" onclick="gotoUrl('${base}/system/role/list${ext}')">角色设定</div>
			<div iconCls="icon-user" onclick="gotoUrl('${base}/system/user/list${ext}')">登录账号管理</div>
			<div iconCls="icon-group" onclick="gotoUrl('${base}/system/online/list${ext}')">在线用户管理</div>
		</div>
		<div id="logger" style="width:150px;">
			<div iconCls="icon-doc" onclick="gotoUrl('${base}/system/config/log/list${ext}')">日志全局配置</div>
			<div class="menu-sep"></div>
			<div iconCls="icon-warn" onclick="gotoUrl('${base}/system/log/error/list${ext}')">系统错误日志</div>
			<div iconCls="icon-reload" onclick="gotoUrl('${base}/system/log/web/list${ext}')">WEB层请求日志</div>
			<div iconCls="icon-undo" onclick="gotoUrl('${base}/system/log/bean/list${ext}')">业务代码调用日志</div>
			<div iconCls="icon-db" onclick="gotoUrl('${base}/system/log/jdbc/list${ext}')">数据库SQL日志</div>
		</div>
	</div>
	