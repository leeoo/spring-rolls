package com.wisdom.core.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

public class StringView extends AbstractView {

	private static Log log = LogFactory.getLog(StringView.class);

	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.write(getHtmlFromMap(model));
		out.flush();
		out.close();
	}

	private String getHtmlFromMap(Map<?, ?> model) {
		String restr = "";
		if (model != null && !model.isEmpty()) {
			// 取第一个元素
			Object obj = model.get(model.keySet().iterator().next());
			if (obj instanceof String) {
				restr = (String) obj;
			}
		}
		log.debug(" html : " + restr);
		return restr;
	}
}