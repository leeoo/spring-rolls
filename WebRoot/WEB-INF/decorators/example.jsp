<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" type="image/x-icon"  href="${base}/common/images/favicon.ico"/>
		<title>Wisdom3 Examples - <decorator:title></decorator:title></title>
		<link rel="stylesheet" href="${base}/common/css/main.css" type="text/css" />
		<decorator:head></decorator:head>
	</head>
	<body>
				<!-- wrap starts here -->
		<div id="wrap">
				
			<!--header -->
			<div id="header">			
				<h1 id="logo-text"><a href="#">Wisdom3 Examples</a></h1>		
				<p id="slogan">完全基于wisdom3.0RC2框架的应用演示...</p>		
					
				<div id="header-links">
					<p>
					当前用户:<security:authentication property="name"/> | 
					<a href="${base}/example/index.htm">版本</a> | <a href="#">修改密码</a> | 
					<a href="${base}/j_spring_security_logout">退出</a>			
					</p>
					<p>
						<c:if test="${maxMem!=null}">
							<form action="${base}/example/index.htm" method="post">
							额定${maxMem} 实际${currentMem} 剩余${residualMem}
							<input type="submit" value="关闭监控"/></form>
						</c:if>
						<c:if test="${maxMem==null}">
							<form action="${base}/example/index.htm" method="post">
							JVM内存监控处于关闭中
							<input type="submit" value="开启监控"/></form>
						</c:if>
					</p>		
				</div>		
			</div>
				
			<!-- navigation -->	
			<div  id="menu">
				<ul>
					<!--<li id="current"><a href="index.html">Home</a></li>-->
					<li><a href="${base}/example/user/list/1.htm">账户管理</a></li>
					<li><a href="${base}/example/role/list/1.htm">角色管理</a></li>
					<li><a href="${base}/example/resource/list/1.htm">权限管理</a></li>
					<li><a href="${base}/example/template/list/1.htm">模板管理</a></li>
					<li><a href="${base}/example/task/list.htm">任务调度</a></li>
					<li><a href="${base}/example/search/list/1.htm">搜索引擎</a></li>
					<li><a href="${base}/example/logger/list/1.htm">日志管理</a></li>
					<li><a href="${base}/example/report/pie.htm">报表统计</a></li>
				</ul>
			</div>					
					
			<!-- content-wrap starts here -->
			<div id="content-wrap">
				
				<div id="main">				
					<decorator:body/>
				</div>
				
					
				<div id="sidebar">
					
					<h2>快速搜索</h2>	
					<form action="${base}/example/search.htm" class="searchform" method="post">
						<p>
						<input name="text" class="textbox" type="text" value="${text}"/>
						<input class="button" value="搜索" type="submit" />
						</p>			
					</form>	
							
					<h2>便捷菜单</h2>
					<ul class="sidemenu">				
						<li><a href="${base}/example/user/list/1.htm">账户管理</a>&nbsp;|&nbsp;<a href="${base}/example/role/list/1.htm">角色管理</a>&nbsp;|&nbsp;<a href="${base}/example/resource/list/1.htm">权限管理</a></li>
						<li><a href="${base}/example/template/list/1.htm">模板管理</a>&nbsp;|&nbsp;<a href="${base}/example/task/list.htm">任务调度</a>&nbsp;|&nbsp;<a href="${base}/example/search/list/1.htm">搜索引擎</a></li>
						<li><a href="${base}/example/logger/list/1.htm">日志管理</a>&nbsp;|&nbsp;<a href="${base}/example/report/pie.htm">报表统计</a>&nbsp;|&nbsp;<a href="${base}/example/remoting/list.htm">远程调用</a></li>
					</ul>	
						
					<h2>成功案例</h2>
					<ul class="sidemenu">
						<li><a href="http://www.jhlc.cn/">嘉华理财网</a></li>
						<li><a href="http://www.newchinalife.com">新华保险积分系统</a></li>
						<li><a href="http://www.newchinalife.com">新华保险巡检系统</a></li>
						<li><a href="http://www.newchinalife.com">新华保险积分商城</a></li>
						<li><a href="http://www.neubj.org:8080">东北大学校友会</a></li>
					</ul>
					
				</div>
						
			<!-- content-wrap ends here -->	
			</div>
							
			<!--footer starts here-->
			<div id="footer">
				<p>
					&copy; 2009 <strong>caoyang</strong> | 
					Design by: <a href="http://www.styleshout.com/">styleshout</a> | 
					Valid <a href="http://validator.w3.org/check?uri=referer">XHTML</a> | 
					<a href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a>   		
			   		</p>
			</div>	
		
		<!-- wrap ends here -->
		</div>
	</body>
</html>
