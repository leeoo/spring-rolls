<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>报表统计</title>
<script language="JavaScript" src="${base}/common/js/FusionCharts.js"></script>
</head>
<body>
	<div align="center">
		<h3>报表演示：东方版若沈阳分公司2009年度假期分配报表</h3>
		<%@ include file="include.jsp"%>
		<div id="chartdiv" align="center"></div>
		<script type="text/javascript">
			var chart = new FusionCharts("${base}/common/report/${report}.swf", "ChartId", "500", "300", "0", "0");
			chart.setDataURL("${base}/example/report/print/${report}.htm");		   
		   	chart.render("chartdiv");
		</script>
	</div>
</body>
</html>