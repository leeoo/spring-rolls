package org.apache.jsp.WEB_002dINF.pages.system.console;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.exam.web.BaseController;
import com.exam.web.BaseController;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/pages/system/console/../../global/head.jsp");
    _jspx_dependants.add("/commons/global.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/console/../../global/title.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/console/../../global/menu.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
      out.write("<title>Paramecium开发平台演示——CONSOLE控制台</title>\r\n");
      out.write("\t<style>\r\n");
      out.write("\t\t#console{\r\n");
      out.write("\t\t\tbackground-color:#000;\r\n");
      out.write("\t\t\tfont-family: \"Courier New\", Courier, \"宋体\", monospace;\r\n");
      out.write("\t\t\tfont-size: 18px;\r\n");
      out.write("\t\t\tcolor: #CCCCCC;\r\n");
      out.write("\t\t\tpadding-left: 10px;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</style>\r\n");
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
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fset_005f6(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f7 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
      _jspx_th_c_005fset_005f7.setParent(null);
      _jspx_th_c_005fset_005f7.setVar("ext");
      _jspx_th_c_005fset_005f7.setValue(BaseController.EXT );
      int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
      if (_jspx_th_c_005fset_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f7);
        return;
      }
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f7);
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"easyui-layout\">\r\n");
      out.write("\t");
      if (_jspx_meth_c_005fset_005f8(_jspx_page_context))
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
      out.write("<div region=\"center\" title=\"控制台\">\r\n");
      out.write("\t<div id=\"console\" style=\"height: 100%;\">\r\n");
      out.write("\t\t<div style=\"width: 100%;background-color:#000;\">\r\n");
      out.write("\t\t\t");
      if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t");
      if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t<form id=\"cf\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/system/console/run");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ext}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" method=\"post\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"type\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${type}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("\t\t\t\t");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${type}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(">\r\n");
      out.write("\t\t\t\t<textarea id=\"cmd\" onkeypress=\"isSubmit();\" name=\"cmd\" style=\"font-family: Courier New, Courier, 黑体, monospace;width: 98%;background-color: #000;height: 200px;color: #FFF;font-size: 18px;border: 0px;overflow-x:hidden;overflow-y:hidden\"></textarea>\r\n");
      out.write("\t\t\t</form>\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<script>\r\n");
      out.write("\t\t\tdocument.getElementById('cmd').focus();\r\n");
      out.write("\t\t\tfunction isSubmit(){\r\n");
      out.write("\t\t\t\tif(event.keyCode==13) {\r\n");
      out.write("\t\t\t\t\tdocument.getElementById('cf').submit(); \r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
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
    _jspx_th_c_005fset_005f4.setVar("path");
    _jspx_th_c_005fset_005f4.setValue(new String(""));
    int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
    if (_jspx_th_c_005fset_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath!='/'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\r');
        out.write('\n');
        out.write('	');
        if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f5 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
    _jspx_th_c_005fset_005f5.setVar("path");
    _jspx_th_c_005fset_005f5.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
    if (_jspx_th_c_005fset_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f6 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f6.setParent(null);
    _jspx_th_c_005fset_005f6.setVar("base");
    _jspx_th_c_005fset_005f6.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort}${path}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
    if (_jspx_th_c_005fset_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f8 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f8.setParent(null);
    _jspx_th_c_005fset_005f8.setVar("baseHeight");
    _jspx_th_c_005fset_005f8.setValue(new String("73"));
    int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
    if (_jspx_th_c_005fset_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent(null);
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${result!=null&&result!=''}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${result}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("<br/><br/>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent(null);
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${result==null||result==''}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\tParamecium 开发版 [版本 1.1.0]<br/>\r\n");
        out.write("\t\t\t\t(C) 版权所有 1982-2011 CaoYang Corp.<br/><br/><br/>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }
}
