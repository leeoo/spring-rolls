<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>亚伟在线考试系统——数据源配置——监控器</title>
<meta http-equiv="refresh" content="5">
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript" src="${base}/commons/js/chart/chart.js"></script>
<script type="text/javascript" src="${base}/commons/js/chart/hlight.js"></script>
</head>
<body>
		<div id="canvas-wrapper1" style="float:left;width:390px;height:250px;"></div>
		<div id="canvas-wrapper2" style="float:left;width:390px;height:250px;"></div> 
		<div style="width: 1px;height: 1px;"></div>
		<div id="canvas-wrapper3" style="float:left;width:390px;height:250px;"></div>
		<div id="canvas-wrapper4" style="float:left;width:390px;height:250px;"></div> 
		<script>
			new Chart.Meter({
				'title':'连接池容量状态监控',
			    'degreeStart':180,//起始角度
			    'degreeMax':180,//最大角度
			    'valueMax':${dataSource.poolMax},
			    'valueMin':0,
			    'valueUnit':1,
			    'valueHalf':true,
			    'tip':'mousemove',
			    'showValue':true,
			    'area':[
			        {'value':<fmt:formatNumber type="number" value="${dataSource.poolMax/3.3}" maxFractionDigits="0"/>, 'color':'#31CD31'},     //绿色区域
			        {'value':<fmt:formatNumber type="number" value="${dataSource.poolMax/1.3}" maxFractionDigits="0"/>, 'color':'#F6D34B'},    //黄色区域
			        {'value':${dataSource.poolMax}, 'color':'#A10000'}    //红色区域
			    ],
			    'item':${dataSource.currentPoolMax}    //指针数值
			}).render('canvas-wrapper1');
			new Chart.Meter({
				'title':'连接池工作状态监控',
			    'degreeStart':180,//起始角度
			    'degreeMax':180,//最大角度
			    'valueMax':${dataSource.poolMax},
			    'valueMin':0,
			    'valueUnit':1,
			    'valueHalf':true,
			    'tip':'mousemove',
			    'showValue':true,
			    'area':[
			        {'value':<fmt:formatNumber type="number" value="${dataSource.poolMax/3.3}" maxFractionDigits="0"/>, 'color':'#31CD31'},     //绿色区域
			        {'value':<fmt:formatNumber type="number" value="${dataSource.poolMax/1.3}" maxFractionDigits="0"/>, 'color':'#F6D34B'},    //黄色区域
			        {'value':${dataSource.poolMax}, 'color':'#A10000'}    //红色区域
			    ],
			    'item':${dataSource.busyPoolMax}    //指针数值
			}).render('canvas-wrapper2');
			new Chart.Bar({
				'title':'待释放连接监控',
			    'item' : [
					{'text':'','value':[
					<c:forEach items="${dataSource.idleTimes}" var="conn">
						${conn},
					</c:forEach>
					]}
			    ],
			    'categories':[
					<c:forEach items="${dataSource.idleTimes}" var="conn">
					${conn},
					</c:forEach>
				],
			    'showValue':true,
			    'focusEvent':'mousemove',
			    'grid':'h',
			    'valueMax' : ${dataSource.connectLife},
			    'valueMin' : 0,
			    'valueUnit' : 200
			}).render('canvas-wrapper3');
			new Chart.Bar({
				'title':'待收回连接监控',
			    'item' : [
					{'text':'','value':[
					<c:forEach items="${dataSource.busyTimes}" var="conn">
						${conn},
					</c:forEach>
					]}
			    ],
			    'categories':[
					<c:forEach items="${dataSource.busyTimes}" var="conn">
					${conn},
					</c:forEach>
				],
			    'showValue':true,
			    'focusEvent':'mousemove',
			    'grid':'h',
			    'valueMax' : ${dataSource.busyConnectTimeOut},
			    'valueMin' : 0,
			    'valueUnit' : 200
			}).render('canvas-wrapper4');
		</script>
</body>
</html>