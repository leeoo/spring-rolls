<%@ page contentType="text/html;charset=UTF-8"%>
	<div region="north" border="false" style="height:80px;background-image: url('${base}/commons/images/head.gif');background-color: #EEF9FB;background-repeat: no-repeat;">
		<div align="right" style="padding-right: 10px;padding-top: 5px;">
			<a href="${base}/system/console/index${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-talk">控制台</a>
			<a href="${base}/system/help/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-help">帮助</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="return isExit();">退出</a>
		</div>
	</div>
