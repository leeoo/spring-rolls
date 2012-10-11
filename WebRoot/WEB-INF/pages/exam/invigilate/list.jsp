<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——在线监考</title>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="正在进行的考试列表">
	<table id="list"></table>
</div>
<script>
	$(function(){
		$('#list').datagrid({
			nowrap: true,
			striped: true,
			url:'${base}/exam/examing-data.json',
			columns:[[
						{field:'title',title:'考试描述',width:400},
						{field:'choice',title:'类型',width:70,
							formatter:function(value,rec){
								if(value=='0'||value=='false'||value=='FALSE'){
									return '速录';
								}else{
									return '理论';
								}
						}},
						{field:'audio',title:'方式',width:70,
							formatter:function(value,rec){
								if(value=='0'||value=='false'||value=='FALSE'){
									return '看打';
								}else if(value=='1'||value=='true'||value=='TRUE'){
									return '听打';
								}else{
									return '理论';
								}
						}},
						{field:'startDate',title:'开始时间',width:150},
						{field:'endDate',title:'结束时间',width:150},
						{field:'score',title:'满分',width:70,
							formatter:function(value,rec){
								return value+'分';
						}},
						{field:'longTime',title:'考试时长',width:100,
						formatter:function(value,rec){
							return value+'分钟';
						}},
						{field:'id',title:'监考',width:100,
							formatter:function(value,rec){
								return '<a href="#" onClick="dialogScore('+value+')">开始</a>';
						}}
					]]
		});
	});
	function dialogScore(examId){
    	window.showModalDialog("${base}/exam/invigilate/report${ext}?id="+examId+"&temp="+new Date().getTime(),null,"dialogWidth=1000px;dialogHeight=520px");
    }
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>