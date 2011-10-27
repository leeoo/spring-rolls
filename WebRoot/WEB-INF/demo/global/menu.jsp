<%@ page contentType="text/html;charset=UTF-8"%>
	<div region="west" split="true" icon="icon-cl" title="功能菜单" style="width:200px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="系统管理" icon="icon-tools" selected="true" style="overflow:auto;padding-left: 20px;padding-top: 10px;">
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-home">机构管理</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-security">菜单权限</a></p>
				<p><a href="${base}/system/role/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-key">角色设定</a></p>
				<p><a href="${base}/system/user/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-user">登录账户</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-ding">参数设定</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-date">日志系统</a></p>
				<p style="padding-left: 10px;"><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-date">错误日志</a></p>
				<p style="padding-left: 10px;"><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-date">请求日志</a></p>
				<p style="padding-left: 10px;"><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-time">调用日志</a></p>
				<p style="padding-left: 10px;"><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-date">数据日志</a></p>
			</div>
			<div title="功能演示" icon="icon-db" style="padding-left: 10px;padding-top: 20px;">
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">任务调度(Thread)</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">搜索引擎(Lucene)</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">NoSQL控(MongoDB)</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">酷炫报表(F)</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
				<p><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-help">待定功能入口</a></p>
			</div>
		</div>
	</div>