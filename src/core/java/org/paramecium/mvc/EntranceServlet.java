package org.paramecium.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.paramecium.security.SecurityThread;
/**
 * 功 能 描 述:<br>
 * 所有请求的入口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午12:58:08
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.EntranceServlet.java
 */
public class EntranceServlet extends HttpServlet{

	private static final long serialVersionUID = 5244417460801082069L;
	
	public EntranceServlet(){
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerExtractor.extract(request, response);
		SecurityThread.endThread();
	}

}
