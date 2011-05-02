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
<div id="pager" style="background:#efefef;border:1px solid #ccc;"></div>
<script>
	$(function(){
		$('#list').datagrid({
			nowrap: true,
			striped: true,
			collapsible:true,
			fitColumns: true,
			url:'${base}/logger/data.json',
			idField:'id',
			frozenColumns:[[
		                    {field:'id',checkbox:true}
		                ]],
			columns:[[
						{field:'info',title:'日志信息',width:200},
						{field:'date',title:'日志日期',width:200,align:'center'},
						{field:'opt',title:'操作',width:100,align:'center',
							formatter:function(value,rec){
								return '<span style="color:red">Edit Delete</span>';
							}
						}
					]]
			//pagination:true,
			//rownumbers:true,
		});
		$(function(){
			$('#pager').pagination({
				total:108,
				pageSize:20,
				pageList:[20],
				buttons:[{
					iconCls:'icon-add',
					handler:function(){
						alert('add');
					}
				},{
					iconCls:'icon-cut',
					handler:function(){
						alert('cut');
					}
				},{
					iconCls:'icon-cancel',
					handler:function(){
						alert('save');
					}
				}],
				onSelectPage:function(pageNumber){
					$(this).pagination('loading');
					$('#list').datagrid({
						url:'${base}/logger/data.json?page.pageNo='+pageNumber
					});
					$(this).pagination('loaded');
				}
			});
		});
	});
</script>
</body>
</html>