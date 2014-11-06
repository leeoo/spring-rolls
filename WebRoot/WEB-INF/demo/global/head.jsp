<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="shortcut icon" href="${base}/commons/images/xeyes.png" type="image/x-icon" /> 
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/${theme}/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/color.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/locale/easyui-lang-zh_CN.js"></script>
	<script>
		function getMessage() {  
	        $.ajax({  
	            url: "${base}/system/message/receive.json",
	            cache: false,
	            dataType: "json",
	            type: "post",
	            timeout: 2000,  
	            success: function (msg) {
	            	if(msg!=null){
	            		var title = '';
	            		if(msg.title!=null&&msg.title!=''&&msg.title!='null'){
	            			title='<br><a href="${base}/system/message/download${ext}?title='+msg.title+'">下载附件</a>';
	            		}
	            		$.messager.alert(msg.date+'<br>来自于&nbsp;'+msg.auth,msg.content+title,'info');
	            	}
	            },
	            error: function (msg) {
	            	alert('严重警告:您与服务器断开连接,请您暂停所有操作,以免造成不必要的损失!');
	            }
	        })
	    }
		window.onload=function(){
			window.setInterval(getMessage,32000);
		};
		function isExit(){
			$.messager.confirm('提示','是否确认退出本系统?',function(d){
	            if(d){
	            	location.href ='${base}/logout${ext}';
	            }
	        });
		}
		function gotoUrl(url){
			location.href = url;
		}
	</script>