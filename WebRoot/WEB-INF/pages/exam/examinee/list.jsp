<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——考生列表</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="考生列表">
	<div title="按条件查询" id="search" icon="icon-search" close="true" style="padding:5px;width:350px;height:210px;">
			<p>考号：<input type="text" name=code id="code"/></p>
			<p>姓名：<input type="text" name="username" id="username"/></p>
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
			    	   'code': $.trim($("#code").val()),
			    	   'username': $.trim($("#username").val())
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
			url:'${base}/exam/examinee/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'code',title:'考号',width:200},
						{field:'username',title:'姓名',width:200,align:'center'},
						{field:'regDate',title:'注册时间',width:300},
						{field:'canDays',title:'有效天数',width:100,
						formatter:function(value,rec){
							if(value=='0'){
								return '永久';
							}else{
								return value+'天';
							}
						}},
						{field:'null',title:'成绩',width:70,
							formatter:function(value,rec){
								return '<a href="#" onClick="dialogScore('+rec.id+')">查看</a>';
						}}
					]],
					toolbar: [{
			            text: '新增',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/exam/examinee/input${ext}';
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
							location.href ='${base}/exam/examinee/input${ext}?id='+rows[0].id;
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
										   url: "${base}/exam/examinee/delete${ext}",
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
	function dialogScore(examId){
    	window.showModalDialog("${base}/exam/examinee/score${ext}?id="+examId+"&temp="+new Date().getTime(),null,"dialogWidth=900px;dialogHeight=600px");
    }
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>