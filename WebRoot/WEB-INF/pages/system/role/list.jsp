<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——角色列表</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="角色列表">
	<div title="按条件查询" id="search" icon="icon-search" close="true" style="padding:5px;width:350px;height:200px;">
			<p>角色描述：<input type="text" name="name" id="name"/></p>
			<p>角色标志：<input type="text" name="rolename" id="rolename"/></p>
	</div>
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#search').dialog({
			buttons:[{
				text:'查询',
				iconCls:'icon-search',
				handler:function(){
				   $('#list').datagrid('options').queryParams = {
			    	   'name': $.trim($("#name").val()),
			    	   'rolename': $.trim($("#rolename").val())
			    	};
			       $('#list').datagrid('options').pageNumber = 1;
			       var p = $('#list').datagrid('getPager');
			       if (p){
			           $(p).pagination({
			              pageNumber:1
			           });
			       	}
					$('#list').datagrid('reload');
					$('#search').dialog('close');
				}
			}]
		});
		$('#search').dialog('close');
		$('#list').datagrid({
			height: document.body.clientHeight-${baseHeight},
			nowrap: true,
			striped: true,
			collapsible:true,
			rownumbers: true,
			remoteSort: false,
			pageList:[20],
			url:'${base}/system/role/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'name',title:'角色描述',width:200},
						{field:'rolename',title:'角色标志',width:200,align:'center'}
					]],
					toolbar: [{
			            text: '新增',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/system/role/input${ext}';
							return false;
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
							location.href ='${base}/system/role/input${ext}?id='+rows[0].id;
							return false;
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
										   url: "${base}/system/role/delete${ext}",
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
			            text: '查询',
			            iconCls: 'icon-search',
			            handler:function(){
			        		$('#search').dialog('open');
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