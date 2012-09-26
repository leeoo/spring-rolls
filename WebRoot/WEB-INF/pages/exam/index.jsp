<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../global/head.jsp"%>
<title>${title}——考生 <%=request.getAttribute("loginName")%> 登录成功</title>
<script type="text/javascript" src="${base}/commons/js/clock/jqueryRotate.js"></script>
<script type="text/javascript">
    var angleSec = 0;
    var angleMin = 0;
    var angleHour = 0;

    $(document).ready(function () {
        $("#sec").rotate(angleSec);
        $("#min").rotate(angleMin);
        $("#hour").rotate(angleHour);
    });

    setInterval(function () {
        var d = new Date();

        angleSec = (d.getSeconds() * 6);
        $("#sec").rotate(angleSec);

        angleMin = (d.getMinutes() * 6);
        $("#min").rotate(angleMin);

        angleHour = ((d.getHours() * 5 + d.getMinutes() / 12) * 6);
        $("#hour").rotate(angleHour);

    }, 1000);
</script>
<style type="text/css">
        #clockHolder
        {
            width:200px;
            position:relative;
            top:0px;
            left:0px;
        }
        
        #sec
        {
            display:block;
            position:absolute;
        }
        #min
        {
            display:block;
            position:absolute;
        }
        #hour
        {
            display:block;
            position:absolute;
        }
        
        .rotatingWrapper
        {
            position:absolute;
            width:200px;
            height:200px;
            top:0px;
            left:0px;
        }
    </style>
</head>
<body class="easyui-layout">
	<%@ include file="../global/title.jsp"%>
	<div region="center" title="欢迎登录到${title}">
		<table style="width: 97%;height: 100%;" border="1">
			<tr>
				<td rowspan="3" style="width: 450px;">考场列表</td>
				<td>系统介绍</td>
				<td style="width: 200px;height: 200px;">
					<div id="clockHolder">
					    <div class="rotatingWrapper"><img id="hour" src="${base}/commons/images/clock/hour.gif" width="200" height="200"/></div>
				        <div class="rotatingWrapper"><img id="min" src="${base}/commons/images/clock/minute.gif" width="200" height="200"/></div>
					    <div class="rotatingWrapper"><img id="sec" src="${base}/commons/images/clock/second.gif" width="200" height="200"/></div>
					    <img id="clock" src="${base}/commons/images/clock/clock.gif" width="200" height="200"/>
				    </div>
				</td>
			</tr>
			<tr>
				<td colspan="2">成绩查询</td>
			</tr>
			<tr>
				<td colspan="2">演武场</td>
			</tr>
		</table>
	</div>
	<script>
		var loginName = '<%=request.getAttribute("loginName")%>';
		if(loginName!=''&&loginName!='null'){
			$.messager.show({title:'提示',msg:'尊敬的考生: '+loginName+' 你好! \n 欢迎登录本系统',timeout:3000,showType:'slide'});
		}
	</script>
</body>
</html>