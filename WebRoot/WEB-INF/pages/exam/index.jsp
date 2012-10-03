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
    
    function reload() {  
        $.ajax({  
        	url:'${base}/exam/examing-data.json',
            cache: false,
            dataType: "json",
            type: "post",
            timeout: 2000,  
            success: function (msg) {
            	$('#examing').datagrid('reload');
            }
        })
    }
	window.onload=function(){
		window.setInterval(reload,25000);
	};
    
    $(function(){
		$('#examing').datagrid({
			height:200,
			nowrap: true,
			striped: true,
			url:'${base}/exam/examing-data.json',
			columns:[[
						{field:'title',title:'考试描述',width:400},
						{field:'choice',title:'类型',width:50,
							formatter:function(value,rec){
								if(value=='0'||value=='false'||value=='FALSE'){
									return '速录';
								}else{
									return '理论';
								}
						}},
						{field:'audio',title:'方式',width:50,
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
						{field:'longTime',title:'考试时长',width:100,
						formatter:function(value,rec){
							return value+'分钟';
						}},
						{field:'id',title:'参加考试',width:100,
							formatter:function(value,rec){
								return '<a href="#" class="easyui-linkbutton" iconCls="icon-cl">进入</a>';
							}}
					]]
		});
	});
    
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
				<td valign="top">
					<table id="examing"></table>
				</td>
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
				<td colspan="2">
					<table style="width: 100%;height: 100%;" border="1">
					<tr>
						<td style="width: 50%;">sd</td><td style="width: 50%;">sd</td>
					</tr>
					</table>
				</td>
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