<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="refresh" content="11">
<%@ include file="../../global/head.jsp"%>
<script type="text/javascript" src="${base}/commons/js/jscharts/jscharts.js"></script>
<title>${title}——在线监控</title>
</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" oncontextmenu="return false;" onselectstart="return false;">
	<div id="graph">目前没有考生参加考试</div>
	<div>提示：</div>
	<div style="padding-left: 20px;">1.如果：长时间没有变化的考生，可能已经掉线，且没有手动提交考卷，系统在考试规定时长自动提交。</div>
	<div style="padding-left: 20px;">2.如果：某考生在短时间内大幅度增加录入速度，可能才有作弊手段，请留意。</div>
	<div style="padding-left: 20px;">3.如果：某考生突然消失在报表中，说明该考生考试完毕，已经交卷。</div>
<script type="text/javascript">
	var myData = new Array(${data});
	var myChart = new JSChart('graph', 'bar');
	myChart.setDataArray(myData);
	myChart.setTitle('速录考试监控');
	myChart.setAxisNameX('考生姓名');
	myChart.setAxisNameY('每分钟完成字数');
	myChart.setAxisWidth(2);
	myChart.setAxisPaddingTop(60);
	myChart.setAxisPaddingLeft(60);
	myChart.setAxisPaddingBottom(60);
	myChart.setTextPaddingBottom(20);
	myChart.setTextPaddingLeft(10);
	myChart.setTitleFontSize(12);
	myChart.setBarSpacingRatio(60);
	myChart.setSize(${width}, 350);
	myChart.draw();
</script>
</body>
</html>