<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="shortcut icon" href="${base}/commons/images/xeyes.png" type="image/x-icon" /> 
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
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
	            		$.messager.alert('来自于&nbsp;'+msg.auth+'&nbsp;的站内消息',msg.content,'info');
	            	}
	            }
	        })
	    }
		window.onload=function(){
			window.setInterval(getMessage,10000);
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