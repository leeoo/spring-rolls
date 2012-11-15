<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="refresh" content="8">
<title>Paramecium开发平台演示——服务器硬件情况</title>
<%@ include file="../../global/head.jsp"%>
</head>
<body class="easyui-layout">
	<%@ include file="../../global/title.jsp"%>
	<%@ include file="../../global/menu.jsp"%>
<div region="center" title="服务器硬件情况">
	<div style="padding: 20px;">
		<div style="padding-left: 50px;">
			<div style="width:140px;height: 72px;background-image: url('${base}/commons/images/cpu.gif');">
			</div>
			<div>
				<div id="cpu" class="easyui-progressbar" style="width:140px;" align="left"></div>
				<div style="width:144px;">${cpu.name}</div>
				<script>
					$(document).ready(function(){
						$('#cpu').progressbar('setValue', <fmt:formatNumber type="number" value="${cpu.load/100*100}" maxFractionDigits="0"/>);  
					});
				</script>
			</div>
		</div>
	</div>
	<div style="padding: 20px;">
		<div style="padding-left: 50px;">
			<div style="width:188px;height: 48px;background-image: url('${base}/commons/images/memory.gif');">
					<div align="center" style="padding-top: 3px;width:188px;height: 48px;font-size: 20px;"><b>${memory.total}GB</b></div>
			</div>
			<div>
				<div id="memory" class="easyui-progressbar" style="width:190px;" align="left"></div>
				${memory.use}GB已占用&nbsp;/&nbsp;${memory.free}GB可用
				<script>
					$(document).ready(function(){
						$('#memory').progressbar('setValue', <fmt:formatNumber type="number" value="${memory.use/memory.total*100}" maxFractionDigits="1"/>);  
					});
				</script>
			</div>
		</div>
	</div>
	<div style="padding: 20px;">
		<c:forEach items="${disks}" var="disk" varStatus="status">
		<div style="float: left;padding-left: 50px;" align="center">
			<div style="width:136px;height: 80px;background-image: url('${base}/commons/images/disk.gif');">
				<div align="center" style="padding-top: 4px;width:136px;height: 80px;font-size: 25px;"><b>${disk.key}</b></div>
			</div>
			<div>
				<div id="p${status.index}" class="easyui-progressbar" style="width:140px;" align="left"></div>
				${disk.value[2]}GB可用&nbsp;/&nbsp;共${disk.value[0]}GB
				<script>
					$(document).ready(function(){
						var re = /^\d*\.{0,1}\d{0,1}$/;
						var n = '<fmt:formatNumber type="number" value="${disk.value[1]/disk.value[0]*100}" maxFractionDigits="1"/>';
						if(!re.test(n)){
							n = 0;
						}
						$('#p${status.index}').progressbar('setValue',n);  
					});
				</script>
			</div>
		</div>
		</c:forEach>
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