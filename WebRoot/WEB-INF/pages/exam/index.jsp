<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../global/head.jsp"%>
<title>${title}——考生 <%=request.getAttribute("loginName")%> 登录成功</title>
<link rel="stylesheet" type="text/css" href="${base}/commons/css/jplayer/blue.monday/jplayer.blue.monday.css">
<script type="text/javascript" src="${base}/commons/js/clock/jqueryRotate.js"></script>
<script type="text/javascript" src="${base}/commons/js/jplayer/jquery.jplayer.min.js"></script>
<script type="text/javascript">
function getDateTime(value){
	window.location.href='${base}/exam/examing${ext}?id='+value+'&dateTime='+new Date().getTime();
}
//<![CDATA[
$(document).ready(function(){
	$("#jquery_jplayer_1").jPlayer({
		ready: function () {
			$(this).jPlayer("setMedia", {
				mp3:'${base}/upload/audio/test.mp3'
			});
		},
		swfPath: "${base}/commons/js/jplayer/Jplayer.swf",
		supplied: "mp3",
		wmode: "window"
	});
});
//]]>
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
        });
    }
    setInterval(reload,60000);
    $(function(){
		$('#examing').datagrid({
			height:200,
			width:1200,
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
						{field:'id',title:'参加考试',width:100,
							formatter:function(value,rec){
								return '<a href="#" onClick="getDateTime('+value+')">进入</a>';
							}}
					]]
		});
		$('#score').datagrid({
			height:310,
			nowrap: true,
			striped: true,
			collapsible:true,
			rownumbers: true,
			remoteSort: false,
			pageList:[10],
			url:'${base}/exam/score-data.json',
			idField:'id',
			columns:[[
						{field:'startDate',title:'参考时间',width:130},
						{field:'longTime',title:'考试用时',width:100,
						formatter:function(value,rec){
							return value+'秒';
						}},
						{field:'score',title:'成绩',width:100,
							formatter:function(value,rec){
								return value+'分';
						}},
						{field:'examId',title:'详情',width:100,
							formatter:function(value,rec){
								return '<a href="#" onClick="dialogScore('+value+')">查看</a>';
						}},
					]],
			pagination:true
		});
	});
    function dialogScore(examId){
    	window.showModalDialog("${base}/exam/score${ext}?examId="+examId+"&temp="+new Date().getTime(),null,"dialogWidth=800px;dialogHeight=600px");
    }
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
	<div region="center" title="考生：${loginExaminee.username}您好！您的考号为：${loginExaminee.code}">
		<table style="width: 97%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" style="height: 200px;width:90%;">
					<div style="font-size: 15px;font-style: italic;"><b>正在进行的考试</b></div>
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
				<td colspan="2" valign="top">
					<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 465px;height: 100%;" valign="top">
							<div style="font-size: 15px;font-style: italic;"><b>我的考试成绩</b></div>
							<table id="score"></table>
						</td>
						<td style="valign="top">
							<div style="font-size: 15px;font-style: italic;"><b>设备调试</b></div>
							<div id="jquery_jplayer_1" class="jp-jplayer">
							</div>
							<div id="jp_container_1" class="jp-audio">
								<div class="jp-type-single">
									<div class="jp-gui jp-interface">
										<ul class="jp-controls">
											<li><a href="javascript:;" class="jp-play" tabindex="1" title="播放">播放</a></li>
											<li><a href="javascript:;" class="jp-pause" tabindex="1" title="暂停">暂停</a></li>
											<li><a href="javascript:;" class="jp-stop" tabindex="1" title="停止">停止</a></li>
											<li><a href="javascript:;" class="jp-mute" tabindex="1" title="小声">小声</a></li>
											<li><a href="javascript:;" class="jp-unmute" tabindex="1" title="静音">静音</a></li>
											<li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="大声">大声</a></li>
										</ul>
										<div class="jp-progress">
											<div class="jp-seek-bar">
												<div class="jp-play-bar"></div>
											</div>
										</div>
										<div class="jp-volume-bar">
											<div class="jp-volume-bar-value"></div>
										</div>
										<div class="jp-time-holder">
											<div class="jp-current-time"></div>
											<div class="jp-duration"></div>
										</div>
									</div>
								</div>
							</div>
							<textarea rows="10" cols="10" style="font-size: 20px;width:98%;height: 90%">点击播放按钮,调试耳麦.在这里输入,测试录入设备.</textarea>
						</td>
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
		var s_message = '<paramecium:successMessage/>';
		var e_message = '<paramecium:errorMessage/>';
		if(s_message!=''&&s_message!='null'){
			 $.messager.alert('成功提示',s_message,'info');
		}
		if(e_message!=''&&e_message!='null'){
			 $.messager.alert('失败提示',e_message,'error');
		}
	</script>
</body>
</html>