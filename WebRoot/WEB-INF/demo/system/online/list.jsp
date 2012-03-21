<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——在线用户列表</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="在线用户列表">
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#list').datagrid({
			height: document.body.clientHeight-${baseHeight},
			nowrap: true,
			url:'${base}/system/online/data.json',
			idField:'sessionId',
			frozenColumns:[[
		                    {field:'sessionId',checkbox:true}
		                ]],
			columns:[[
						{field:'username',title:'登录账号',width:200},
						{field:'name',title:'姓名',width:200,align:'center'},
						{field:'enable',title:'状态',width:100,align:'center'},
						{field:'address',title:'IP地址',width:300,align:'center'},
						{field:'loginDate',title:'登录时间',width:200,align:'center'}
					]],
					toolbar: [{
			            text: '踢出',
			            iconCls: 'icon-cancel',
			            handler:function(){
				        	var ids = [];
							var rows = $('#list').datagrid('getSelections');
							if(rows.length<1){
								$.messager.alert('提示','至少选择一行!','warning');
								return false;
							}
							for(var i=0;i<rows.length;i++){
								ids.push(rows[i].sessionId);
							}
							$.messager.confirm('提示','确认要踢出此用户吗?',function(d){
					            if(d){
					            	$.ajax({
										   type: "get",
										   url: "${base}/system/online/kill${ext}",
										   data: "ids="+ids.join(','),
										   success: function(msg){
											   $.messager.show({
													title:'提示',
													msg:'成功踢出！',
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
			        },'-',{
				        text: '全部踢出',
			            iconCls: 'icon-cancel',
			            handler:function(){
							$.messager.confirm('提示','确认要踢出所有用户吗?',function(d){
								if(d){
									$.ajax({
										   type: "get",
										   url: "${base}/system/online/killAll${ext}",
										   success: function(msg){
											   $.messager.show({
													title:'提示',
													msg:'成功踢出所有用户！',
													timeout:3000,
													showType:'slide'
												});
										   }
									});
								}
								$('#list').datagrid('reload');
								$('#list').datagrid('clearSelections');
					        });
						}
			        }]
		});
	});
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>