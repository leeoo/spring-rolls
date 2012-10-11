<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——考生成绩列表</title>
</head>
<body class="easyui-layout">
<div region="center" title="考生成绩列表">
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#list').datagrid({
			nowrap: true,
			striped: true,
			collapsible:true,
			rownumbers: true,
			remoteSort: false,
			pageList:[10],
			url:'${base}/exam/exam/score_data.json?id=${id}',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'code',title:'考号',width:70},
						{field:'username',title:'姓名',width:70},
						{field:'start_date',title:'参考时间',width:130},
						{field:'long_time',title:'考试用时',width:100,
							formatter:function(value,rec){
								return value+'秒';
						}},
						{field:'score',title:'成绩',width:70,
							formatter:function(value,rec){
								return value+'分';
						}},
						{field:'null',title:'详情',width:70,
							formatter:function(value,rec){
								return '<a href="#" onClick="dialogScore('+rec.id+')">查看</a>';
							}}
					]],
			pagination:true
		});
	});
	function dialogScore(scoreId){
    	window.showModalDialog("${base}/exam/exam/score_detail${ext}?id="+scoreId+"&temp="+new Date().getTime(),null,"dialogWidth=800px;dialogHeight=600px");
    }
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>