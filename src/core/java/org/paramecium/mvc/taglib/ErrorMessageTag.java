package org.paramecium.mvc.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ServletConstant;
/**
 * 功能描述(Description):<br><b>
 * 错误信息
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-11-11下午11:40:19</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.mvc.taglib.ErrorMessageTag.java</b>
 */
public class ErrorMessageTag extends TagSupport{

	private final static Log logger = LoggerFactory.getLogger();
	private static final long serialVersionUID = 5899444990055568100L;

	public int doStartTag() throws JspException {
		String message = (String) this.pageContext.getRequest().getAttribute(ServletConstant.ERROR_MESSAGE);
		if(message==null||message.isEmpty()){
			message =  this.pageContext.getRequest().getParameter(ServletConstant.ERROR_MESSAGE);
		}
		if(message!=null&&!message.isEmpty()){
			try {
				this.pageContext.getOut().write(message);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SKIP_BODY;
	}


}
