<%@ page contentType="text/html;charset=UTF-8"%>
	<div region="north" border="false" style="height:45px;background-image: url('${base}/commons/images/head-${theme}.gif');overflow-y:hidden;">
		<div align="left" style="float: left;">
			<div style="font-size: 25px;color: #FFF;padding-left: 10px;padding-top:5px;font-weight: bold;font-family: 微软雅黑">${title}</div>
		</div>
		<paramecium:resourceAuthorize ifAnyGranted="/system/themes#change">
			<div align="right" style="padding-right: 40px;padding-top: 10px;float: right ;">
				<form id="themeId" action="${base}/system/themes/change${ext}" method="post">
					<select name="themeName" onchange="javascript:document.forms[0].submit();">
						<option value="default" <c:if test="${theme!=null && theme=='default'}">selected='selected'</c:if>>浅蓝</option>
						<option value="gray" <c:if test="${theme!=null && theme=='gray'}">selected='selected'</c:if>>银灰</option>
						<option value="cupertino" <c:if test="${theme!=null && theme=='cupertino'}">selected='selected'</c:if>>柔兰</option>
						<option value="dark-hive" <c:if test="${theme!=null && theme=='dark-hive'}">selected='selected'</c:if>>暗室</option>
						<option value="metro" <c:if test="${theme!=null && theme=='metro'}">selected='selected'</c:if>>都市</option>
						<option value="pepper-grinder" <c:if test="${theme!=null && theme=='pepper-grinder'}">selected='selected'</c:if>>理石</option>
						<option value="sunny" <c:if test="${theme!=null && theme=='sunny'}">selected='selected'</c:if>>晴朗</option>
						<option value="black" <c:if test="${theme!=null && theme=='black'}">selected='selected'</c:if>>黑暗</option>
						<option value="bootstrap" <c:if test="${theme!=null && theme=='bootstrap'}">selected='selected'</c:if>>流行风</option>
					</select><span style="color: #FFF;">切换主题</span>
				</form>
			</div>
		</paramecium:resourceAuthorize>
		<div align="right" style="padding-right: 50px;padding-top: 10px;float: right ;">
			<paramecium:resourceAuthorize ifAnyGranted="/system/themes#change">
				<a href="javascript:void(0)" id="$security" class="easyui-splitbutton" style="color: #FFF" menu="#security" iconCls="icon-security">系统管理</a>
			</paramecium:resourceAuthorize>
			<paramecium:resourceAuthorize ifAnyGranted="/system/console#run">
				<a href="javascript:void(0)" id="$logger" class="easyui-splitbutton" style="color: #FFF" menu="#logger" iconCls="icon-date">日志查看</a>
			</paramecium:resourceAuthorize>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" style="color: #FFF" onclick="return isExit();">退出</a>
		</div>
		<paramecium:resourceAuthorize ifAnyGranted="/system/themes#change">
			<div id="security" style="width:150px;">
				<paramecium:resourceAuthorize ifAnyGranted="/system/console#run">
					<div iconCls="icon-cl" onclick="gotoUrl('${base}/system/hardware/list${ext}');">硬件情况</div>
					<div iconCls="icon-db" onclick="gotoUrl('${base}/system/ds/list${ext}');">数据源配置</div>
					<div iconCls="icon-tools" onclick="gotoUrl('${base}/system/config/security/list${ext}');">系统安全配置</div>
					<div iconCls="icon-reload" onclick="gotoUrl('${base}/system/cache/list${ext}');">缓存监控</div>
					<div iconCls="icon-talk" onclick="gotoUrl('${base}/system/console/index${ext}');">控制台</div>
					<div iconCls="icon-help" onclick="gotoUrl('${base}/system/help/list${ext}');">开发帮助</div>
				</paramecium:resourceAuthorize>
				<div iconCls="icon-ding" onclick="gotoUrl('${base}/system/config/ip/list${ext}')">IP地址限制</div>
				<div class="menu-sep"></div>
				<paramecium:resourceAuthorize ifAnyGranted="/system/console#run">
					<div iconCls="icon-key" onclick="gotoUrl('${base}/system/role/list${ext}')">角色设定</div>
				</paramecium:resourceAuthorize>
				<div iconCls="icon-user" onclick="gotoUrl('${base}/system/user/list${ext}')">登录账号管理</div>
				<div iconCls="icon-group" onclick="gotoUrl('${base}/system/online/list${ext}')">在线用户管理</div>
			</div>
		</paramecium:resourceAuthorize>
		<paramecium:resourceAuthorize ifAnyGranted="/system/console#run">
			<div id="logger" style="width:150px;">
				<div iconCls="icon-doc" onclick="gotoUrl('${base}/system/config/log/list${ext}')">日志全局配置</div>
				<div class="menu-sep"></div>
				<div iconCls="icon-warn" onclick="gotoUrl('${base}/system/log/error/list${ext}')">系统错误日志</div>
				<div iconCls="icon-reload" onclick="gotoUrl('${base}/system/log/web/list${ext}')">WEB层请求日志</div>
				<div iconCls="icon-undo" onclick="gotoUrl('${base}/system/log/bean/list${ext}')">业务代码调用日志</div>
				<div iconCls="icon-db" onclick="gotoUrl('${base}/system/log/jdbc/list${ext}')">数据库SQL日志</div>
			</div>
		</paramecium:resourceAuthorize>
	</div>
	