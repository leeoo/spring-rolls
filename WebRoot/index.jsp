<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/${theme}/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/icon.css">
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/login/skin.css">
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/commons/js/jquery/locale/easyui-lang-zh_CN.js"></script>
	<style type="text/css">
	<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-color: #1D3647;
		}
	-->
	</style>
<title>${title}</title>
</head>
<body>
	<div title="考生注册" id="reg" icon="icon-user" close="true" style="padding:5px;width:350px;height:300px;">
			<p>考号：<input id="code" name="examinee.code" class="easyui-validatebox" required="true" validType="length[5,20]" style="width: 300px;"/>（可填写手机、证件号等唯一编码）</p>
			<p>密码：<input id="password" name="examinee.password" type="password" class="easyui-validatebox" required="true" validType="length[3,15]" style="width: 300px;"/></p>
			<p>姓名：<input id="username" name="examinee.username" class="easyui-validatebox" required="true" validType="length[2,10]" style="width: 300px;"/>（请填写真实姓名）</p>
	</div>
	<script>
	function regOpen(){
		$('#reg').dialog('open');
	}
	$(function(){
		$('#reg').dialog({
			buttons:[{
				text:'注册',
				iconCls:'icon-user',
				handler:function(){
					$.ajax({
						   type: "post",
						   url: "${base}/reg${ext}",
						   data: {
					    	   'code': $.trim($("#code").val()),
					    	   'password': $.trim($("#password").val()),
					    	   'username': $.trim($("#username").val())
					    	   },
						   success: function(msg){
							   if(msg=='yes'){
								   $.messager.alert('注册成功','请使用考号"'+$("#code").val()+'"登录！','info');
							   }else if(msg=='no1'){
								   $.messager.alert('注册失败','请将注册信息填写全后再注册！','error');
							   }else if(msg=='no2'){
								   $.messager.alert('注册失败','考号"'+$("#code").val()+'"已经被注册过，请更换考号再注册！','error');
							   }else{
								   $.messager.alert('注册失败',msg,'error');
							   }
						   },
						   error: function(msg){
							   $.messager.alert('注册失败','与服务器断开连接，请检查网络状况！','error');
						   }
					});
					$('#reg').dialog('close');
				}
			
			}]
		});
		$('#reg').dialog('close');
	});
	</script>
	<table width="100%" height="166" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td height="42" valign="top">
		    <table width="100%" height="42" border="0" cellpadding="0" cellspacing="0" class="login_top_bg">
		      <tr>
		        <td width="1%" height="21">&nbsp;</td>
		        <td height="42">&nbsp;</td>
		        <td width="17%">&nbsp;</td>
		      </tr>
		    </table>
	    </td>
	  </tr>
	  <tr>
	    <td valign="top">
	    <table width="100%" height="532" border="0" cellpadding="0" cellspacing="0" class="login_bg">
	      <tr>
	        <td width="49%" align="right" valign="bottom" style="padding-bottom: 60px;">
		        <div style="width: 600px;height: 350px;background-image: url('${base}/commons/images/exam-mini.gif');background-repeat: no-repeat;" align="right">
		        	<div style="width: 330px;height: 250px;padding-top: 20px;" align="left">
		        		<div><img src="${base}/commons/images/jian.gif" alt="推荐" /><a href="#" style="color:#030Fd3; font-family: 微软雅黑;font-size: 13px;">下载安装谷歌浏览器，使用起来更流畅、更稳定！</a></div>
		        		<div><img src="${base}/commons/css/jquery/icons/user.png" alt="注册" /><a href="#" onclick="regOpen();" style="color:#030Fd3;font-family: 微软雅黑;font-size: 13px;">温馨提示：如果您是第一次登录，请先注册考号。</a></div>
		        		<div><img src="${base}/commons/css/jquery/icons/idea.png" alt="小窍门" /><a href="#" style="color:#030Fd3;font-family: 微软雅黑;font-size: 13px;">小 窍 门：当您第一次使用本系统，可以使用向导。</a></div>
		        	</div>
		        </div>
	        </td>
	        <td width="1%" >&nbsp;</td>
	        <td width="50%" valign="bottom"><table width="100%" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td width="4%">&nbsp;</td>
	              <td width="96%" height="38"><span class="login_txt_bt" style="padding-left:30px;font-size: 30px;font-family: 微软雅黑;">${title}</span></td>
	            </tr>
	            <tr>
	              <td>&nbsp;</td>
	              <td height="21"><table cellSpacing="0" cellPadding="0" width="100%" border="0" id="table211" height="328">
	                  <tr>
	                    <td height="164" colspan="2" align="middle">
	                    	<div style="padding-left: 40px;">
			                    <form action="${base}/login${ext}" method="post" method="post">
			                        <table cellSpacing="0" cellPadding="0" width="100%" border="0" height="143" id="table212">
			                          <tr>
			                            <td width="5%" nowrap="nowrap" height="38" class="top_hui_text"><span class="login_txt_bt" style="font-size: 13px;font-weight: bold">登录名：</span></td>
			                            <td height="38" colspan="2" class="top_hui_text" align="left"><input name="login.username" class="editbox4" style="width: 150px;"></td>
			                          </tr>
			                          <tr>
			                            <td width="5%" nowrap="nowrap" height="35" class="top_hui_text"><span class="login_txt_bt" style="font-size: 13px;font-weight: bold">密&nbsp;&nbsp;码：</span></td>
			                            <td height="35" colspan="2" class="top_hui_text" align="left"><input class="editbox4" type="password" style="width: 150px;" name="login.password">
			                              <img src="${base}/commons/css/login/lock.gif" width="19" height="18"> </td>
			                          </tr>
			                          <tr>
			                            <td height="35" >&nbsp;</td>
			                            <td width="15%" height="35" ><button type="submit" class="easyui-linkbutton" iconCls="icon-key">登 录</button></td>
			                            <td width="67%" class="top_hui_text" align="left"><button type="button" class="easyui-linkbutton" iconCls="icon-user" onclick="regOpen();">注 册</button></td>
			                          </tr>
			                        </table>
			                        <br>
			                    </form>
		                    </div>
		                 </td>
	                  </tr>
	                  <tr>
	                    <td width="433" height="164" align="right" valign="bottom"><img src="${base}/commons/css/login/login-wel.gif" width="242" height="138"></td>
	                    <td width="57" align="right" valign="bottom">&nbsp;</td>
	                  </tr>
	              </table></td>
	            </tr>
	          </table>
	          </td>
	      </tr>
	    </table></td>
	  </tr>
	  <tr>
	    <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="login-buttom-bg">
	      <tr>
	        <td align="center"><span class="login-buttom-txt">Copyright &copy; 2011-2012 ${title} </span></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</body>


<script>
	var error = <%=request.getParameter("error")%>;
	var msg = '';
	if(error==0){
		msg = '登录名或密码错误!';
	}else if(error==1){
		msg = '请您登录后访问!';
	}else if(error==3){
		msg = '该资源受保护,请用合法用户登录再试!';
	}else if(error==5){
		msg = '您的账户在其他地点登录,请重新登录!';
	}else if(error==7){
		msg = '您的账户被冻结,请联系管理员!';
	}else if(error==9){
		msg = '您在登录后长时间没有操作,请重新登录!';
	}else if(error==11){
		msg = '您的IP地址没有被系统授权,请联系管理员!';
	}else if(error==13){
		msg = '您被系统管理员强制退出,请联系管理员!';
	}
	if(msg!=''){
		 $.messager.alert('系统安全提示',msg,'warning');
	}
</script>
</html>