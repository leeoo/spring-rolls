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
			url:'${base}/logger/data.json',
			columns:[[
						{field:'id',title:'编号',width:80},
						{field:'info',title:'日志信息',width:200},
						{field:'date',title:'日志日期',width:200,align:'center'},
						{field:'opt',title:'操作',width:100,align:'center',
							formatter:function(value,rec){
								return '<span style="color:red">Edit Delete</span>';
							}
						}
					]],
			pagination:true,
			rownumbers:true
		});
	});
</script>
</body>
</html>