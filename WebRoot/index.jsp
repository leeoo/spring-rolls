<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<link rel="stylesheet" type="text/css" href="${base}/commons/css/jquery/gray/easyui.css">
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
<title>亚伟在线考试系统</title>
</head>
<body>
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
	        <td width="49%" align="right">
	        	<div style="background:url('/commons/images/exam-mini.gif') no-repeat;width: 500px;height: 317px;"></div>
	        </td>
	        <td width="1%" >&nbsp;</td>
	        <td width="50%" valign="bottom"><table width="100%" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td width="4%">&nbsp;</td>
	              <td width="96%" height="38"><span class="login_txt_bt" style="font-size: 18px;font-family: 微软雅黑;">亚伟在线考试系统</span></td>
	            </tr>
	            <tr>
	              <td>&nbsp;</td>
	              <td height="21"><table cellSpacing="0" cellPadding="0" width="100%" border="0" id="table211" height="328">
	                  <tr>
	                    <td height="164" colspan="2" align="middle">
		                    <form action="${base}/login${ext}" method="post" method="post">
		                        <table cellSpacing="0" cellPadding="0" width="100%" border="0" height="143" id="table212">
		                          <tr>
		                            <td width="10%" height="38" class="top_hui_text"><span class="login_txt">登录名：&nbsp;&nbsp; </span></td>
		                            <td height="38" colspan="2" class="top_hui_text" align="left"><input name="login.username" class="editbox4" size="20"></td>
		                          </tr>
		                          <tr>
		                            <td width="10%" height="35" class="top_hui_text"><span class="login_txt"> 密 码： &nbsp;&nbsp; </span></td>
		                            <td height="35" colspan="2" class="top_hui_text" align="left"><input class="editbox4" type="password" size="20" name="login.password">
		                              <img src="${base}/commons/css/login/lock.gif" width="19" height="18"> </td>
		                          </tr>
		                          <tr>
		                            <td height="35" >&nbsp;</td>
		                            <td width="10%" height="35" ><button type="submit" class="easyui-linkbutton" iconCls="icon-key">登录</button></td>
		                            <td width="67%" class="top_hui_text" align="left"><button type="reset" class="easyui-linkbutton" iconCls="icon-undo">取消</button></td>
		                          </tr>
		                        </table>
		                        <br>
		                    </form>
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
	        <td align="center"><span class="login-buttom-txt">Copyright &copy; 2011-2012 沈阳亚伟科技有限公司 </span></td>
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
		$.messager.show({title:'提示',msg:msg,timeout:3000,showType:'slide'});
	}
</script>
</html>