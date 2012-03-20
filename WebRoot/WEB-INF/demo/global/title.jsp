<c:set var="baseHeight" value="98"/>
<%@ page contentType="text/html;charset=UTF-8"%>
	<div region="north" border="false" style="height:70px;background-image: url('${base}/commons/images/head.gif');background-color: #EEF9FB;background-repeat: no-repeat;">
		<div align="right" style="padding-right: 20px;padding-top: 5px;">
			<a href="javascript:void(0)" id="$security" class="easyui-splitbutton" menu="#security" iconCls="icon-security">系统管理</a>
			<a href="javascript:void(0)" id="$logger" class="easyui-splitbutton" menu="#logger" iconCls="icon-date">日志查看</a>
			<a href="javascript:void(0)" id="$demo" class="easyui-splitbutton" menu="#demo" iconCls="icon-good">功能演示</a>
			<a href="${base}/system/console/index${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-talk">控制台</a>
			<a href="${base}/system/help/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-help">帮助</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="return isExit();">退出</a>
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
		<div id="demo" style="width:150px;">
			<div iconCls="icon-cl" onclick="javascript:void(0)">便捷查询(Search)</div>
			<div iconCls="icon-ok" onclick="javascript:void(0)">验证标签(Validation)</div>
			<div iconCls="icon-time" onclick="javascript:void(0)">任务调度(Thread)</div>
			<div iconCls="icon-search" onclick="javascript:void(0)">搜索引擎(Lucene)</div>
			<div iconCls="icon-doc" onclick="javascript:void(0)">NoSQL封装(MongoDB)</div>
			<div iconCls="icon-report" onclick="javascript:void(0)">酷炫报表(FusionCharts)</div>
		</div>
	</div>
	