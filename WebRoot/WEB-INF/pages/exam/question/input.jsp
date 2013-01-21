<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../global/head.jsp"%>
<title>${title}——速录考试题库维护</title>
<link rel="stylesheet" type="text/css" href="${base}/commons/css/uploadify/uploadify.css">
<link rel="stylesheet" type="text/css" href="${base}/commons/css/jplayer/blue.monday/jplayer.blue.monday.css">
<script type="text/javascript" src="${base}/commons/js/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${base}/commons/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${base}/commons/js/jplayer/jquery.jplayer.min.js"></script>
<script>
	$(function() {
		$("#file_upload").uploadify({
			buttonText:'上传音频文件',
			fileTypeExts:'*.mp3',
			fileObjName:'file',
			fileTypeDesc:'请选择mp3格式的音频文件',
			swf:'${base}/commons/js/uploadify/uploadify.swf',
			uploader:'${base}/file/upload${ext}',
			height:20,
			width:100,
			onUploadSuccess:function(file, data, response) {
				$('#audioPath').val(data);
				$('#jquery_jplayer_1').jPlayer("setMedia", {//该方法肯定为新上传到temp
					mp3: '${base}/upload/temp/'+data
				});
				first_track = false;
				$('#jquery_jplayer_1').blur();
				return false;

	        }
		});
	});
	
	//<![CDATA[
	$(document).ready(function(){
		$("#jquery_jplayer_1").jPlayer({
			ready: function () {
				var audioPath = $('#audioPath').val();
				$(this).jPlayer("setMedia", {
					mp3:'${base}/upload/<c:if test="${question!=null}">audio</c:if><c:if test="${question==null}">temp</c:if>/'+audioPath
				});
			},
			swfPath: "${base}/commons/js/jplayer/Jplayer.swf",
			supplied: "mp3",
			wmode: "window"
		});
	});
	//]]>

</script>

</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="速录考试题库维护">
	<div style="border: solid 1px ; border-color :#afafaf; padding: 8px;">
		<form id="firstForm" action="${base}/exam/question/save${ext}" method="post">
			<input type="hidden" name="question.choice" value="false"/>
			<c:if test="${question!=null}">
				<input type="hidden" name="question.id" value="${question.id}"/>
				<input type="hidden" name="oldAudioPath" value="${question.audioPath}"/>
			</c:if>
			<div>
				<table>
					<tr>
						<td nowrap="nowrap">题库描述:</td>
						<td colspan="2"><input name="question.title" class="easyui-validatebox" required="true" validType="length[3,500]" style="width: 500px;" value="${question.title}"/></td>
					</tr>
					<tr>
						<td>音频文件:</td>
						<td nowrap="nowrap" width="50%"><input id="audioPath" readonly="readonly" name="question.audioPath" class="easyui-validatebox" style="width: 300px;" value="${question.audioPath}"/>(如果看打无需上传音频)</td>
						<td nowrap="nowrap" align="left" valign="middle"><input type="file" name="file" id="file_upload" /></td>
					</tr>
					<tr>
						<td>音频预览:</td>
						<td colspan="2">
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
					</tr>
					<tr>
						<td>原文:</td>
						<td colspan="2">
							<textarea rows="25" cols="90" name="question.textContent" class="easyui-validatebox" required="true" validType="length[10,9999999]">${question.textContent}</textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right" colspan="2">
							<a class="easyui-linkbutton" href="${base}/exam/question/list${ext}" iconCls="icon-back">返 回</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save">提 交</a>
							<script>
								function submitForm(){
									$('#firstForm').submit();
								}
							</script>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<script>
	var message = '<paramecium:successMessage/><paramecium:errorMessage/>';
	if(message!=''&&message!='null'){
		$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});
	}
</script>
</body>
</html>