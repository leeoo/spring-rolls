<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>日志列表</title>
</head>
<body>
<table id="list"></table>
<script>
	$(function(){
		$('#list').datagrid({
			nowrap: true,
			striped: true,
			collapsible:true,
			fitColumns: true,
			rownumbers: true,
			pageList:[20],
			url:'${base}/logger/data.json',
			idField:'id',
			loadMsg:'数据正在载入...',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'info',title:'日志信息',width:200},
						{field:'date',title:'日志日期',width:200,align:'center'}
					]],
					toolbar: [{
			            text: '新增',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/logger/input.jhtml';
						}
			        }, '-', {
			            text: '修改',
			            iconCls: 'icon-edit',
			            handler:function(){
							$('#btnsave').linkbutton('enable');
							alert('add');
						}
			        }, '-', {
			            text: '删除',
			            iconCls: 'icon-cancel',
			            handler:function(){
							$('#btnsave').linkbutton('enable');
							alert('add');
						}
			        }, '-', {
			            text: '查找',
			            iconCls: 'icon-search',
			            handler:function(){
							$('#btnsave').linkbutton('enable');
							alert('add');
						}
			        }],
			pagination:true
		});
		 	$('#list').datagrid('getPager').pagination({
			    displayMsg:'本页从{from}到{to}条/共{total}条记录'
			});
	});
</script>
</body>
</html>