<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——考试信息列表</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="考试信息列表">
	<div title="按条件查询" id="search" icon="icon-search" close="true" style="padding:5px;width:350px;height:210px;">
			<p>考试描述：<input type="text" name=title id="title"/></p>
			<p>考试方式：<select name=choice id="choice"><option value="">请选择</option><option value="false">速录(打字)</option><option value="true">理论(选择题)</option></select></p>
			<p>考试状态：<select name="status" id="status"><option value="">请选择</option><option value="0">未开始</option><option value="1">正在进行</option><option value="-1">已结束</option></select></p>
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
			    	   'title': $.trim($("#title").val()),
			    	   'choice': $.trim($("#choice").val()),
			    	   'status': $.trim($("#status").val())
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
			url:'${base}/exam/exam/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'miniTitle',title:'考试描述',width:300},
						{field:'score',title:'总分数',width:50},
						{field:'choice',title:'考试方式',width:100,
							formatter:function(value,rec){
								if(value=='0'||value=='false'||value=='FALSE'){
									return '速录';
								}else{
									return '理论';
								}
						}},
						{field:'status',title:'状态',width:100,
						formatter:function(value,rec){
							if(value=='0'){
								return '未开始';
							}else if(value=='1'){
								return '正在进行';
							}else if(value=='-1'){
								return '已结束';
							}
						}},
						{field:'startDate',title:'开始时间',width:130},
						{field:'endDate',title:'结束时间',width:130},
						{field:'longTime',title:'考试时长',width:100,
						formatter:function(value,rec){
							return value+'分钟';
						}}
					]],
					toolbar: [{
			            text: '新增速录考试',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/exam/exam/input${ext}?choice=false';
							return false;
						}
					}, '-', {
			            text: '新增理论考试',
			            iconCls: 'icon-add',
			            handler:function(){
							location.href ='${base}/exam/exam/input${ext}?choice=true';
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
							if(rows[0].status==1){
								$.messager.alert('提示','该考试正在进行中，不能修改该信息!','warning');
								$('#list').datagrid('clearSelections');
								return false;
							}
							location.href ='${base}/exam/exam/input${ext}?id='+rows[0].id;
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
								if(rows[i].status==1){
									$.messager.alert('提示','该考试正在进行中，不能修删除信息!','warning');
									$('#list').datagrid('clearSelections');
									return false;
								}
								ids.push(rows[i].id);
							}
							$.messager.confirm('提示','确认删除吗?',function(d){
					            if(d){
					            	$.ajax({
										   type: "get",
										   url: "${base}/exam/exam/delete${ext}",
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