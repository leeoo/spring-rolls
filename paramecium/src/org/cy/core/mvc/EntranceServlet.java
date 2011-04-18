package org.cy.core.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 功 能 描 述:<br>
 * 所有请求的入口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午12:58:08
 * <br>项 目 信 息:paramecium:org.cy.core.mvc.EntranceServlet.java
 */
public class EntranceServlet extends HttpServlet{

	private static final long serialVersionUID = 5244417460801082069L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}
	
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doHead(req, resp);
	}
	
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		super.doOptions(arg0, arg1);
	}
	
	protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		super.doTrace(arg0, arg1);
	}
	
}
