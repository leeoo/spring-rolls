<%@ page contentType="text/html;charset=UTF-8"%>

	<div region="west" split="true" icon="icon-home" title="菜单" style="width:200px;padding1:1px;overflow:hidden;">
		<div style="height: 100%; padding-left: 10px;padding-top: 5px;">
			<div><a href="${base}/exam/config/index${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-cl">系统配置</a></div>
			<div><a href="${base}/exam/examinee/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-group">考生信息</a></div>
			<div><a href="${base}/exam/question_c/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-book">理论题库(选择题)</a></div>
			<div><a href="${base}/exam/question/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-print">速录题库(打字)</a></div>
			<div><a href="${base}/exam/exam/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-edit">考试管理</a></div>
			<div><a href="${base}/exam/invigilate/list${ext}" class="easyui-linkbutton" plain="true" iconCls="icon-search">在线监考</a></div>
			<div><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-report">考试结果统计</a></div>
		</div>
	</div>