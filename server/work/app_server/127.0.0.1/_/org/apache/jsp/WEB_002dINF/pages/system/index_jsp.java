package org.apache.jsp.WEB_002dINF.pages.system;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.exam.web.BaseController;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/pages/system/../global/head.jsp");
    _jspx_dependants.add("/commons/global.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/../global/title.jsp");
    _jspx_dependants.add("/WEB-INF/pages/system/../global/menu.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005floginAuthorize;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize.release();
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin.release();
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted.release();
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted.release();
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted.release();
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
      out.write("<title>Paramecium开发平台演示——登录成功</title>\r\n");
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
      out.write("\t<div region=\"center\" title=\"登录成功\">\r\n");
      out.write("\t\t<div style=\"padding-left: 20px;padding-top: 10px;\">\r\n");
      out.write("\t\t\t<div><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/commons/images/logo.gif\" alt=\"产品LOGO\" /></div>\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_paramecium_005floginAuthorize_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_paramecium_005floginAuthorize_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_paramecium_005fresourceAuthorize_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_paramecium_005fresourceAuthorize_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_paramecium_005fresourceAuthorize_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\tvar loginName = '");
      out.print(request.getAttribute("loginName"));
      out.write("';\r\n");
      out.write("\t\tif(loginName!=''&&loginName!='null'){\r\n");
      out.write("\t\t\t$.messager.show({title:'提示',msg:'尊敬的用户: '+loginName+' 你好! \\n 欢迎登录本系统',timeout:3000,showType:'slide'});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>\r\n");
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

  private boolean _jspx_meth_paramecium_005floginAuthorize_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:loginAuthorize
    org.paramecium.security.taglib.LoginAuthorizeTag _jspx_th_paramecium_005floginAuthorize_005f0 = (org.paramecium.security.taglib.LoginAuthorizeTag) _005fjspx_005ftagPool_005fparamecium_005floginAuthorize.get(org.paramecium.security.taglib.LoginAuthorizeTag.class);
    _jspx_th_paramecium_005floginAuthorize_005f0.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005floginAuthorize_005f0.setParent(null);
    int _jspx_eval_paramecium_005floginAuthorize_005f0 = _jspx_th_paramecium_005floginAuthorize_005f0.doStartTag();
    if (_jspx_eval_paramecium_005floginAuthorize_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("我登录了<br>");
        int evalDoAfterBody = _jspx_th_paramecium_005floginAuthorize_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_paramecium_005floginAuthorize_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005floginAuthorize.reuse(_jspx_th_paramecium_005floginAuthorize_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize.reuse(_jspx_th_paramecium_005floginAuthorize_005f0);
    return false;
  }

  private boolean _jspx_meth_paramecium_005floginAuthorize_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:loginAuthorize
    org.paramecium.security.taglib.LoginAuthorizeTag _jspx_th_paramecium_005floginAuthorize_005f1 = (org.paramecium.security.taglib.LoginAuthorizeTag) _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin.get(org.paramecium.security.taglib.LoginAuthorizeTag.class);
    _jspx_th_paramecium_005floginAuthorize_005f1.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005floginAuthorize_005f1.setParent(null);
    _jspx_th_paramecium_005floginAuthorize_005f1.setIfNotLogin(true);
    int _jspx_eval_paramecium_005floginAuthorize_005f1 = _jspx_th_paramecium_005floginAuthorize_005f1.doStartTag();
    if (_jspx_eval_paramecium_005floginAuthorize_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("我没有登录<br>");
        int evalDoAfterBody = _jspx_th_paramecium_005floginAuthorize_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_paramecium_005floginAuthorize_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin.reuse(_jspx_th_paramecium_005floginAuthorize_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005floginAuthorize_0026_005fifNotLogin.reuse(_jspx_th_paramecium_005floginAuthorize_005f1);
    return false;
  }

  private boolean _jspx_meth_paramecium_005fresourceAuthorize_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:resourceAuthorize
    org.paramecium.security.taglib.ResourceAuthorizeTag _jspx_th_paramecium_005fresourceAuthorize_005f0 = (org.paramecium.security.taglib.ResourceAuthorizeTag) _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted.get(org.paramecium.security.taglib.ResourceAuthorizeTag.class);
    _jspx_th_paramecium_005fresourceAuthorize_005f0.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005fresourceAuthorize_005f0.setParent(null);
    _jspx_th_paramecium_005fresourceAuthorize_005f0.setIfAllGranted("/system/user#save,/system/user#list");
    int _jspx_eval_paramecium_005fresourceAuthorize_005f0 = _jspx_th_paramecium_005fresourceAuthorize_005f0.doStartTag();
    if (_jspx_eval_paramecium_005fresourceAuthorize_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t我有/system/user#save,/system/user#list权限<br>\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_paramecium_005fresourceAuthorize_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_paramecium_005fresourceAuthorize_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAllGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f0);
    return false;
  }

  private boolean _jspx_meth_paramecium_005fresourceAuthorize_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:resourceAuthorize
    org.paramecium.security.taglib.ResourceAuthorizeTag _jspx_th_paramecium_005fresourceAuthorize_005f1 = (org.paramecium.security.taglib.ResourceAuthorizeTag) _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted.get(org.paramecium.security.taglib.ResourceAuthorizeTag.class);
    _jspx_th_paramecium_005fresourceAuthorize_005f1.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005fresourceAuthorize_005f1.setParent(null);
    _jspx_th_paramecium_005fresourceAuthorize_005f1.setIfAnyGranted("/system/user#save,/system/user#list");
    int _jspx_eval_paramecium_005fresourceAuthorize_005f1 = _jspx_th_paramecium_005fresourceAuthorize_005f1.doStartTag();
    if (_jspx_eval_paramecium_005fresourceAuthorize_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t我包含/system/user#save,/system/user#list其中的权限<br>\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_paramecium_005fresourceAuthorize_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_paramecium_005fresourceAuthorize_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifAnyGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f1);
    return false;
  }

  private boolean _jspx_meth_paramecium_005fresourceAuthorize_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  paramecium:resourceAuthorize
    org.paramecium.security.taglib.ResourceAuthorizeTag _jspx_th_paramecium_005fresourceAuthorize_005f2 = (org.paramecium.security.taglib.ResourceAuthorizeTag) _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted.get(org.paramecium.security.taglib.ResourceAuthorizeTag.class);
    _jspx_th_paramecium_005fresourceAuthorize_005f2.setPageContext(_jspx_page_context);
    _jspx_th_paramecium_005fresourceAuthorize_005f2.setParent(null);
    _jspx_th_paramecium_005fresourceAuthorize_005f2.setIfNotGranted("empty");
    int _jspx_eval_paramecium_005fresourceAuthorize_005f2 = _jspx_th_paramecium_005fresourceAuthorize_005f2.doStartTag();
    if (_jspx_eval_paramecium_005fresourceAuthorize_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t我不包含empty权限<br>\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_paramecium_005fresourceAuthorize_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_paramecium_005fresourceAuthorize_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fparamecium_005fresourceAuthorize_0026_005fifNotGranted.reuse(_jspx_th_paramecium_005fresourceAuthorize_005f2);
    return false;
  }
}
