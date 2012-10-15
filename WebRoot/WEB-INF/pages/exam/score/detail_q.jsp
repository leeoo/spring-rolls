<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——成绩查看</title>
<paramecium:resourceAuthorize ifAnyGranted="/system/themes#change">
<script type="text/javascript" src="${base}/commons/js/clipboard/jquery.clipboard.min.js"></script>
<script>
    jQuery(function($){  
        jQuery.clipboardReady(function() {  
            jQuery("#copyA").click(function () {
            	var content = $("#q_content").val();
            	if(content==null||content==''){
            		content = $("#q_content").text();
            	}
            	jQuery.clipboard(content);
                $.messager.alert('提示','原文复制成功！','info'); 
                return false;  
            });  
            jQuery("#copyB").click(function () {
            	var content = $("#s_content").val();
            	if(content==null||content==''){
            		content = $("#s_content").text();
            	}
            	jQuery.clipboard(content);
                $.messager.alert('提示','答案复制成功！','info');
                return false;  
            });
        }, { swfpath: "${base}/commons/js/clipboard/jquery.clipboard.swf", debug: true });  
    });
</script>
</paramecium:resourceAuthorize>
</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" <paramecium:resourceAuthorize ifNotGranted="/system/themes#change"> oncontextmenu="return false;" onselectstart="return false;" </paramecium:resourceAuthorize> >
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="1" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF">
			<td height="5%" align="right" style="padding-right: 20px;">
				<span style="font-size: 13px;"><b>评分权重：[汉字:${exam.cnProportion},字母:${exam.enProportion},标点:${exam.punProportion},数字:${exam.numProportion}]</b></span>&nbsp;&nbsp;
				<span style="font-size: 20px;font-style: italic;color: RED"><b>${score.score}分</b></span>&nbsp;&nbsp;
				<span style="font-size: 13px;"><b>考试用时：${score.longTime}秒</b></span>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 45%">
				<div><b style="font-size: 15px;font-style: italic;">原文</b><paramecium:resourceAuthorize ifAnyGranted="/system/themes#change"><span style="padding-left: 20px;"><a href="#" class="easyui-linkbutton" iconCls="icon-doc" id="copyA">复制</a></span></paramecium:resourceAuthorize></div>
				<div id="q_content" style="background-color: #EFEFBA;width: 100%;height: 94%;font-size: 20px;OVERFLOW-y:auto;">${question.textContent}</div>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="width: 100%;height: 45%">
				<div><b  style="font-size: 15px;font-style: italic;background-color: #FFF;">答案</b><paramecium:resourceAuthorize ifAnyGranted="/system/themes#change"><span style="padding-left: 20px;"><a href="#" class="easyui-linkbutton" iconCls="icon-doc" id="copyB">复制</a></span></paramecium:resourceAuthorize></div>
				<div id="s_content" style="background-color: #ABEFFF;width: 100%;height: 94%;font-size: 20px;OVERFLOW-y:auto;">${score.context}</div>
			</td>
		</tr>
	</table>
</body>
</html>