<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——Bean调用日志</title>
<%@ include file="../../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../../global/title.jsp"%>
	<%@ include file="../../../global/menu.jsp"%>
<div region="center" title="Bean调用日志">
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
			url:'${base}/system/log/bean/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'date',title:'时间',width:200},
						{field:'log',title:'日志内容',align:'center'}
					]],
					toolbar: [{
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
										   url: "${base}/system/log/bean/delete${ext}",
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
			            text: '查找',
			            iconCls: 'icon-search',
			            handler:function(){
							alert('查找');
						}
			        }],
			pagination:true
		});
	});
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>