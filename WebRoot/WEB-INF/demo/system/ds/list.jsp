<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Paramecium开发平台演示——数据源配置</title>
<%@ include file="../../global/head.jsp"%>
<script type="text/javascript" src="${base}/commons/js/chart/chart.js"></script>
<script type="text/javascript" src="${base}/commons/js/chart/hlight.js"></script>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="数据源配置">
	<c:forEach items="${dataSources}" var="dataSource">
			<div style=" padding: 8px;">
				<div style="border: solid 1px ; border-color :#afafaf;width: 450px;float: left;">
					<form id="${dataSource.key}Form" name="${dataSource.key}Form" action="${base}/system/ds/save${ext}" method="post">
						<input type="hidden" name="dsName" value="${dataSource.key}"/>
						<table>
							<tr>
								<td nowrap="nowrap">数据源名称:</td>
								<td>${dataSource.key}</td>
							</tr>
							<tr>
								<td nowrap="nowrap">连接地址:</td>
								<td><input name="${dataSource.key}.url" value="${dataSource.value.url}" style="width: 250px;"/></td>
							</tr>
							<tr>
								<td nowrap="nowrap">用户名:</td>
								<td><input name="${dataSource.key}.username" value="${dataSource.value.username}" style="width: 150px;"/></td>
							</tr>
							<tr>
								<td nowrap="nowrap">密码:</td>
								<td><input name="${dataSource.key}.password" value="${dataSource.value.password}" style="width: 200px;"/></td>
							</tr>
							<tr>
								<td nowrap="nowrap">连接池最大值:</td>
								<td style="height: 60px;">
								<input name="${dataSource.key}.poolMax" value="${dataSource.value.poolMax}" class="easyui-slider" showTip="true" max="200" rule="[1,'|',50,'|',100,'|',150,'|',200]" style="width:300px">
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap">繁忙连接生命周期:</td>
								<td><input name="${dataSource.key}.busyConnectTimeOut" value="${dataSource.value.busyConnectTimeOut}" class="easyui-numberspinner" style="width: 100px;"/>(秒)</td>
							</tr>
							<tr>
								<td nowrap="nowrap">空闲连接生命周期:</td>
								<td><input name="${dataSource.key}.connectLife" value="${dataSource.value.connectLife}" class="easyui-numberspinner" style="width: 100px;"/>(秒)</td>
							</tr>
							<tr>
								<td nowrap="nowrap">线程监控执行周期:</td>
								<td><input name="${dataSource.key}.poolThreadTime" value="${dataSource.value.poolThreadTime}" class="easyui-numberspinner" style="width: 100px;"/>(秒)</td>
							</tr>
							<tr>
								<td nowrap="nowrap">连接超时:</td>
								<td><input name="${dataSource.key}.loginTimeout" value="${dataSource.value.loginTimeout}" class="easyui-numberspinner" style="width: 100px;"/>(秒)</td>
							</tr>
							<tr>
								<td></td>
								<td align="right"><button type="submit" class="easyui-linkbutton" iconCls="icon-save">提交</button></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="float: left;">
					<script>
						var meter = new Chart.Meter({
							'title':'连接池容量状态',
						    'degreeStart':180,//起始角度
						    'degreeMax':180,//最大角度
						    'valueMax':${dataSource.value.poolMax},
						    'valueMin':0,
						    'valueUnit':1,
						    'valueHalf':true,
						    'tip':'mousemove',
						    'showValue':true,
						    'area':[
						        {'value':<fmt:formatNumber type="number" value="${dataSource.value.poolMax/3.3}" maxFractionDigits="0"/>, 'color':'#31CD31'},     //绿色区域
						        {'value':<fmt:formatNumber type="number" value="${dataSource.value.poolMax/1.3}" maxFractionDigits="0"/>, 'color':'#F6D34B'},    //黄色区域
						        {'value':${dataSource.value.poolMax}, 'color':'#A10000'}    //红色区域
						    ],
						    'item':${dataSource.value.currentPoolMax}    //指针数值
						}).render('canvas-wrapper1');
						var meter2 = new Chart.Meter({
							'title':'连接池工作状态',
						    'degreeStart':180,//起始角度
						    'degreeMax':180,//最大角度
						    'valueMax':${dataSource.value.poolMax},
						    'valueMin':0,
						    'valueUnit':1,
						    'valueHalf':true,
						    'tip':'mousemove',
						    'showValue':true,
						    'area':[
						        {'value':<fmt:formatNumber type="number" value="${dataSource.value.poolMax/3.3}" maxFractionDigits="0"/>, 'color':'#31CD31'},     //绿色区域
						        {'value':<fmt:formatNumber type="number" value="${dataSource.value.poolMax/1.3}" maxFractionDigits="0"/>, 'color':'#F6D34B'},    //黄色区域
						        {'value':${dataSource.value.poolMax}, 'color':'#A10000'}    //红色区域
						    ],
						    'item':${dataSource.value.busyPoolMax}    //指针数值
						}).render('canvas-wrapper2');
					</script>
					<div id="canvas-wrapper1" style="float: left;width: 300px;height: 200px;"></div>
					<div id="canvas-wrapper2" style="float: left;width: 300px;height: 200px;"></div> 
				</div>
			</div>
	</c:forEach>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>