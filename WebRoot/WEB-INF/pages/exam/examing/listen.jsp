<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——开始考试</title>
<link rel="stylesheet" type="text/css" href="${base}/commons/css/jplayer/blue.monday/jplayer.blue.monday.css">
<script type="text/javascript" src="${base}/commons/js/jplayer/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="${base}/commons/js/jcountdown/jquery.jcountdown1.3.min.js"></script>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	$("#jquery_jplayer_1").jPlayer({
		ready: function () {
			var audioPath = '${examSession.audioPath}';
			$(this).jPlayer("setMedia", {
				mp3:'${base}/upload/audio/'+audioPath
			});
		},
		swfPath: "${base}/commons/js/jplayer/Jplayer.swf",
		supplied: "mp3",
		wmode: "window"
	});
});
//]]>
$(document).ready(function() {
	$("#time").countdown({
		date: '${examingEndTime}',
		htmlTemplate: "距考试结束还有 <span style='font-size: 20px;color: RED;'>%{h}</span> 小时 <span style='font-size: 20px;color: RED;'>%{m}</span> 分 <span style='font-size: 20px;color: RED;'>%{s}</span> 秒",
		onChange: function( event, timer ){
		},
		onComplete: function( event ){
			alert('考试结束！');
		},
		leadingZero: true,
		direction: "down"
	});
});
</script>

</head>
<body style="margin:0;padding:0;z-index:0;width: 100%;height: 100%;position:absolute;" oncontextmenu="return false;" onselectstart="return false;">
	<table style="width: 100%;height: 100%;border-color: #EFEFEF;" border="0" cellpadding="0" cellspacing="0">
		<tr style="background-color: #EFEFEF">
			<td align="left" width="20%">
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
			</td>
			<td align="left" style="color: blue;">
			戴好耳麦，点击播放按钮。
			</td>
			<td height="5%" align="right" style="padding-right: 30px;">
				<span id="time"></span>
			</td>
		</tr>
		<tr>
			<td colspan="3" valign="top" align="left" style="width: 100%;height: 90%">
				<form method="post" action="${base}/exam/save${ext}">
					<textarea onpaste="return false;" ondragstart="return false;" rows="20" cols="10" style="font-size: 20px;width:98%;height: 90%">${examineeSession.tempContent}</textarea>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>