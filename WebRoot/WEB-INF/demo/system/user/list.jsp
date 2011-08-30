<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>用户列表</title>
</head>
<body>
<div region="center" title="用户列表">
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#list').datagrid({
			nowrap: true,
			striped: true,
			collapsible:true,
			fitColumns: true,
			rownumbers: true,
			pageList:[20],
			url:'${base}/system/user/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'username',title:'登录账号',width:200},
						{field:'name',title:'姓名',width:200,align:'center'},
						{field:'enabled',title:'状态',width:200,align:'center'}
					]],
					toolbar: [{
			            text: '新增',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/system/user/input${ext}';
						}
			        }, '-', {
			            text: '修改',
			            iconCls: 'icon-edit',
			            handler:function(){
				        	var ids = [];
							var rows = $('#list').datagrid('getSelections');
							if(rows.length!=1){
								$.messager.alert('提示','必须选择一行!','warning');
								$('#list').datagrid('clearSelections');
								return false;
							}
							location.href ='${base}/system/user/input${ext}?id='+rows[0].id;
						}
			        }, '-', {
			            text: '删除',
			            iconCls: 'icon-cancel',
			            handler:function(){
				        	var ids = [];
							var rows = $('#list').datagrid('getSelections');
							if(rows.length<1){
								$.messager.alert('提示','至少选择一行!','warning');
								return false;
							}
							for(var i=0;i<rows.length;i++){
								ids.push(rows[i].id);
							}
							$.messager.confirm('提示','确认删除吗?',function(d){
					            if(d){
					            	$.ajax({
										   type: "get",
										   url: "${base}/system/user/delete${ext}",
										   data: "ids="+ids.join(','),
										   success: function(msg){
											   $.messager.show({
													title:'提示',
													msg:'删除成功！',
													timeout:3000,
													showType:'slide'
												});
										   }
									});
									$('#list').datagrid('reload');
									$('#list').datagrid('clearSelections');
					            }
					        });
						}
			        }, '-', {
			            text: '冻结',
			            iconCls: 'icon-stop',
			            handler:function(){
				        	var ids = [];
							var rows = $('#list').datagrid('getSelections');
							if(rows.length<1){
								$.messager.alert('提示','至少选择一行!','warning');
								return false;
							}
							for(var i=0;i<rows.length;i++){
								ids.push(rows[i].id);
							}
							$.messager.confirm('提示','确认冻结吗?',function(d){
					            if(d){
					            	$.ajax({
										   type: "get",
										   url: "${base}/system/user/disabled${ext}",
										   data: "ids="+ids.join(','),
										   success: function(msg){
											   $.messager.show({
													title:'提示',
													msg:'冻结成功！',
													timeout:3000,
													showType:'slide'
												});
										   }
									});
									$('#list').datagrid('reload');
									$('#list').datagrid('clearSelections');
					            }
					        });
						}
			        }, '-', {
			            text: '解冻',
			            iconCls: 'icon-play',
			            handler:function(){
				        	var ids = [];
							var rows = $('#list').datagrid('getSelections');
							if(rows.length<1){
								$.messager.alert('提示','至少选择一行!','warning');
								return false;
							}
							for(var i=0;i<rows.length;i++){
								ids.push(rows[i].id);
							}
							$.messager.confirm('提示','确认解冻吗?',function(d){
					            if(d){
					            	$.ajax({
										   type: "get",
										   url: "${base}/system/user/enabled${ext}",
										   data: "ids="+ids.join(','),
										   success: function(msg){
											   $.messager.show({
													title:'提示',
													msg:'解冻成功！',
													timeout:3000,
													showType:'slide'
												});
										   }
									});
									$('#list').datagrid('reload');
									$('#list').datagrid('clearSelections');
					            }
					        });
						}
			        }, '-', {
			            text: '查找',
			            iconCls: 'icon-search',
			            handler:function(){
							alert('查找');
						}
			        }],
			pagination:true
		});
	});
</script>
</body>
</html>