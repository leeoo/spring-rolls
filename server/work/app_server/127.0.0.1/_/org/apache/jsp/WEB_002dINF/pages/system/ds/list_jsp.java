package org.apache.jsp.WEB_002dINF.pages.system.ds;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.exam.web.BaseController;

public final class list_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/pages/system/ds/../../global/head.jsp");
    _jspx_dependants.add("/commons/global.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/ds/../../global/title.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/ds/../../global/menu.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody.release();
    _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<title>Paramecium开发平台演示——数据源配置</title>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fset_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f3 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
      _jspx_th_c_005fset_005f3.setParent(null);
      _jspx_th_c_005fset_005f3.setVar("ext");
      _jspx_th_c_005fset_005f3.setValue(BaseController.EXT );
      int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
      if (_jspx_th_c_005fset_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
        return;
      }
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
      out.write("\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></meta>\r\n");
      out.write("\t<link rel=\"shortcut icon\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/images/xeyes.png\" type=\"image/x-icon\" /> \r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/css/jquery/gray/easyui.css\">\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/css/jquery/icon.css\">\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/js/jquery/jquery-1.7.2.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/js/jquery/jquery.easyui.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/js/jquery/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\tfunction getMessage() {  \r\n");
      out.write("\t        $.ajax({  \r\n");
      out.write("\t            url: \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/message/receive.json\",\r\n");
      out.write("\t            cache: false,\r\n");
      out.write("\t            dataType: \"json\",\r\n");
      out.write("\t            type: \"post\",\r\n");
      out.write("\t            timeout: 2000,  \r\n");
      out.write("\t            success: function (msg) {\r\n");
      out.write("\t            \tif(msg!=null){\r\n");
      out.write("\t            \t\t$.messager.alert('来自于&nbsp;'+msg.auth+'&nbsp;的站内消息',msg.content,'info');\r\n");
      out.write("\t            \t}\r\n");
      out.write("\t            }\r\n");
      out.write("\t        })\r\n");
      out.write("\t    }\r\n");
      out.write("\t\twindow.onload=function(){\r\n");
      out.write("\t\t\twindow.setInterval(getMessage,10000);\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\tfunction isExit(){\r\n");
      out.write("\t\t\t$.messager.confirm('提示','是否确认退出本系统?',function(d){\r\n");
      out.write("\t            if(d){\r\n");
      out.write("\t            \tlocation.href ='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/logout");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\t            }\r\n");
      out.write("\t        });\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction gotoUrl(url){\r\n");
      out.write("\t\t\tlocation.href = url;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"easyui-layout\">\r\n");
      out.write("\t");
      if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<div region=\"north\" border=\"false\" style=\"height:45px;background-image: url('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/images/head.gif');background-color: #EEF9FB;\">\r\n");
      out.write("\t\t<div align=\"left\" style=\"float: left;\">\r\n");
      out.write("\t\t\t<div style=\"font-size: 38px;color: #FFF;padding-left: 10px;font-weight: bold;\">Paramecium</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div align=\"right\" style=\"padding-right: 50px;padding-top: 5px;\">\r\n");
      out.write("\t\t\t<a href=\"javascript:void(0)\" id=\"$security\" class=\"easyui-splitbutton\" style=\"color: #FFF\" menu=\"#security\" iconCls=\"icon-security\">系统管理</a>\r\n");
      out.write("\t\t\t<a href=\"javascript:void(0)\" id=\"$logger\" class=\"easyui-splitbutton\" style=\"color: #FFF\" menu=\"#logger\" iconCls=\"icon-date\">日志查看</a>\r\n");
      out.write("\t\t\t<a href=\"javascript:void(0)\" id=\"$demo\" class=\"easyui-splitbutton\" style=\"color: #FFF\" menu=\"#demo\" iconCls=\"icon-good\">功能演示</a>\r\n");
      out.write("\t\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/console/index");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" style=\"color: #FFF\" plain=\"true\" iconCls=\"icon-talk\">控制台</a>\r\n");
      out.write("\t\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/help/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" style=\"color: #FFF\" plain=\"true\" iconCls=\"icon-help\">帮助</a>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-undo\" style=\"color: #FFF\" onclick=\"return isExit();\">退出</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"security\" style=\"width:150px;\">\r\n");
      out.write("\t\t\t<div iconCls=\"icon-db\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/ds/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\">数据源配置</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-tools\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/security/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\">系统安全配置</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-ding\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/ip/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">IP地址限制</div>\r\n");
      out.write("\t\t\t<div class=\"menu-sep\"></div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-key\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/role/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">角色设定</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-user\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/user/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">登录账号管理</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-group\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/online/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">在线用户管理</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"logger\" style=\"width:150px;\">\r\n");
      out.write("\t\t\t<div iconCls=\"icon-doc\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/log/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">日志全局配置</div>\r\n");
      out.write("\t\t\t<div class=\"menu-sep\"></div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-warn\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/error/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">系统错误日志</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-reload\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/web/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">WEB层请求日志</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-undo\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/bean/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">业务代码调用日志</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-db\" onclick=\"gotoUrl('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/jdbc/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\">数据库SQL日志</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"demo\" style=\"width:150px;\">\r\n");
      out.write("\t\t\t<div iconCls=\"icon-cl\" onclick=\"javascript:void(0)\">便捷查询(Search)</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-ok\" onclick=\"javascript:void(0)\">验证标签(Validation)</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-time\" onclick=\"javascript:void(0)\">任务调度(Thread)</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-search\" onclick=\"javascript:void(0)\">搜索引擎(Lucene)</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-doc\" onclick=\"javascript:void(0)\">NoSQL封装(MongoDB)</div>\r\n");
      out.write("\t\t\t<div iconCls=\"icon-report\" onclick=\"javascript:void(0)\">酷炫报表(FusionCharts)</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<div region=\"west\" split=\"true\" icon=\"icon-home\" title=\"功能菜单\" style=\"width:200px;padding1:1px;overflow:hidden;\">\r\n");
      out.write("\t\t<div class=\"easyui-accordion\" fit=\"true\" border=\"false\" style=\"background-color: #EFEFEF;\">\r\n");
      out.write("\t\t\t<div title=\"权限安全\" icon=\"icon-security\" selected=\"true\" style=\"overflow:auto;padding-left: 20px;\">\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/ds/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-db\">数据源配置</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/security/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-tools\">安全配置</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/role/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-key\">角色设定</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/user/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-user\">登录账户</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/ip/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-ding\">IP限制</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/online/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-group\">在线用户</a></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div title=\"日志系统\" icon=\"icon-date\" style=\"padding-left: 20px;\">\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/config/log/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-doc\">日志配置</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/error/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-warn\">错误日志</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/web/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-reload\">请求日志</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/bean/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-undo\">调用日志</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/log/jdbc/list");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-db\">数据日志</a></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div title=\"功能演示\" icon=\"icon-good\" style=\"padding-left: 20px;\">\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-cl\">便利查询(Search)</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-ok\">验证标签(Validation)</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-time\">任务调度(Thread)</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-search\">搜索引擎(Lucene)</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-doc\">NoSQL控(MongoDB)</a></div>\r\n");
      out.write("\t\t\t\t<div><a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-report\">酷炫报表(F)</a></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>");
      out.write("\r\n");
      out.write("<div region=\"center\" title=\"数据源配置\">\r\n");
      out.write("\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("<script>\r\n");
      out.write("\tvar message = '");
      if (_jspx_meth_paramecium_005fsuccessMessage_005f0(_jspx_page_context))
        return;
      if (_jspx_meth_paramecium_005ferrorMessage_005f0(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\tif(message!=''&&message!='null'){\r\n");
      out.write("\t\t$.messager.show({title:'提示',msg:message,timeout:3000,showType:'slide'});\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    _jspx_th_c_005fset_005f0.setVar("path");
    _jspx_th_c_005fset_005f0.setValue(new String(""));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath!='/'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\r');
        out.write('\n');
        out.write('	');
        if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    _jspx_th_c_005fset_005f1.setVar("path");
    _jspx_th_c_005fset_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
    if (_jspx_th_c_005fset_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f2 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f2.setParent(null);
    _jspx_th_c_005fset_005f2.setVar("base");
    _jspx_th_c_005fset_005f2.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort}${path}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
    if (_jspx_th_c_005fset_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f4 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f4.setParent(null);
    _jspx_th_c_005fset_005f4.setVar("baseHeight");
    _jspx_th_c_005fset_005f4.setValue(new String("73"));
    int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
    if (_jspx_th_c_005fset_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSources}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f0.setVar("dataSource");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t<div style=\" padding: 8px;\">\r\n");
          out.write("\t\t\t\t<div style=\"border: solid 1px ; border-color :#afafaf;width: 450px;float: left;\">\r\n");
          out.write("\t\t\t\t\t<form id=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("Form\" name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("Form\" action=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("/system/ds/save");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" method=\"post\">\r\n");
          out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"dsName\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"/>\r\n");
          out.write("\t\t\t\t\t\t<table>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">数据源名称:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">连接地址:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".url\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.url}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" style=\"width: 250px;\"/></td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">用户名:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".username\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.username}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" style=\"width: 150px;\"/></td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">密码:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".password\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.password}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" style=\"width: 200px;\"/></td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">连接池最大值:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td style=\"height: 60px;\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".poolMax\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.poolMax}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"easyui-slider\" showTip=\"true\" max=\"200\" rule=\"[1,'|',50,'|',100,'|',150,'|',200]\" style=\"width:300px\">\r\n");
          out.write("\t\t\t\t\t\t\t\t</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">繁忙连接生命周期:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".busyConnectTimeOut\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.busyConnectTimeOut}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"easyui-numberspinner\" style=\"width: 100px;\"/>(秒)</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">空闲连接生命周期:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".connectLife\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.connectLife}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"easyui-numberspinner\" style=\"width: 100px;\"/>(秒)</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">线程监控执行周期:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".poolThreadTime\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.poolThreadTime}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"easyui-numberspinner\" style=\"width: 100px;\"/>(秒)</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td nowrap=\"nowrap\">连接超时:</td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td><input name=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(".loginTimeout\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.value.loginTimeout}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"easyui-numberspinner\" style=\"width: 100px;\"/>(秒)</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t\t<tr>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
          out.write("\t\t\t\t\t\t\t\t<td align=\"right\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">提交</button>\r\n");
          out.write("\t\t\t\t\t\t\t\t<a class=\"easyui-linkbutton\" href=\"#\" onclick=\"window.open('");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baes}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("/system/ds/monitor");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("?dsName=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dataSource.key}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("','监控','width=830,height=520,directories');\">监控</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t</td>\r\n");
          out.write("\t\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t\t</table>\r\n");
          out.write("\t\t\t\t\t</form>\r\n");
          out.write("\t\t\t\t</div>\r\n");
          out.write("\t\t\t</div>\r\n");
          out.write("\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_paramecium_005fsuccessMessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:successMessage
    org.paramecium.mvc.taglib.SuccessMessageTag _jspx_th_paramecium_005fsuccessMessage_005f0 = (org.paramecium.mvc.taglib.SuccessMessageTag) _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody.get(org.paramecium.mvc.taglib.SuccessMessageTag.class);
    _jspx_th_paramecium_005fsuccessMessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005fsuccessMessage_005f0.setParent(null);
    int _jspx_eval_paramecium_005fsuccessMessage_005f0 = _jspx_th_paramecium_005fsuccessMessage_005f0.doStartTag();
    if (_jspx_th_paramecium_005fsuccessMessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody.reuse(_jspx_th_paramecium_005fsuccessMessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005fsuccessMessage_005fnobody.reuse(_jspx_th_paramecium_005fsuccessMessage_005f0);
    return false;
  }

  private boolean _jspx_meth_paramecium_005ferrorMessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:errorMessage
    org.paramecium.mvc.taglib.ErrorMessageTag _jspx_th_paramecium_005ferrorMessage_005f0 = (org.paramecium.mvc.taglib.ErrorMessageTag) _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody.get(org.paramecium.mvc.taglib.ErrorMessageTag.class);
    _jspx_th_paramecium_005ferrorMessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005ferrorMessage_005f0.setParent(null);
    int _jspx_eval_paramecium_005ferrorMessage_005f0 = _jspx_th_paramecium_005ferrorMessage_005f0.doStartTag();
    if (_jspx_th_paramecium_005ferrorMessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody.reuse(_jspx_th_paramecium_005ferrorMessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005ferrorMessage_005fnobody.reuse(_jspx_th_paramecium_005ferrorMessage_005f0);
    return false;
  }
}
