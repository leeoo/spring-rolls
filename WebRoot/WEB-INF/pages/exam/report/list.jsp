<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——已经结束的考试列表</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="已经结束的考试列表">
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#list').datagrid({
			height: document.body.clientHeight-${baseHeight},
			nowrap: true,
			striped: true,
			collapsible:true,
			rownumbers: true,
			remoteSort: false,
			pageList:[20],
			url:'${base}/exam/report/data.json',
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
						{field:'startDate',title:'开始时间',width:150},
						{field:'endDate',title:'结束时间',width:150},
						{field:'longTime',title:'考试时长',width:100,
						formatter:function(value,rec){
							return value+'分钟';
						}},
						{field:'null',title:'成绩排名',width:70,
							formatter:function(value,rec){
								if(rec.status!='0'){
									return '<a href="${base}/exam/report/score${ext}?id='+rec.id+'">查看</a>';
								}
							}}
					]],
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