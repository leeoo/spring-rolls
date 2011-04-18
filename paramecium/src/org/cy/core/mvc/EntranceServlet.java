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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doPost(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doPut(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doDelete(request, response);
	}
	
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doHead(request, response);
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doOptions(request, response);
	}
	
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		super.doTrace(request, response);
	}
	
}
