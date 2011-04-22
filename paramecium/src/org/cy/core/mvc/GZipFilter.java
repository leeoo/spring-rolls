package org.cy.core.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * 功 能 描 述:<br>
 * 采用GZip压缩传输
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22上午09:42:52
 * <br>项 目 信 息:paramecium:org.cy.core.mvc.GZipFilter.java
 */
public class GZipFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		GZipResponse zipResponse = getGZipResponse(request, response);
		if (zipResponse!=null){
			chain.doFilter(request, zipResponse);
			zipResponse.flush();
		}else{
			chain.doFilter(request, response);
		}
	}

	public void destroy() {

	}

	/**
	 * 判断浏览器是否支持GZIP
	 * 
	 * @param request
	 * @return
	 */
	private GZipResponse getGZipResponse(final ServletRequest request,final ServletResponse response) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpServletResponse response2 = (HttpServletResponse) response;
		String encoding = request2.getHeader("Accept-Encoding");
		if (encoding.indexOf("gzip") != -1) {
			GZipResponse zipResponse = new GZipResponse(response2);
			response2.setHeader("Content-Encoding", "gzip");
			return zipResponse;
		}
		return null;
	}

	private class GZipResponse extends HttpServletResponseWrapper {
		private GZipStream stream;
		private PrintWriter writer;

		public GZipResponse(HttpServletResponse response) throws IOException {
			super(response);
			stream = new GZipStream(response.getOutputStream());
		}

		public ServletOutputStream getOutputStream() throws IOException {
			return stream;
		}

		public PrintWriter getWriter() throws IOException {
			if (writer == null) {
				writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
			}
			return writer;
		}

		public void flush() throws IOException {
			if (writer != null) {
				writer.flush();
			}
			stream.finish();
		}

	}

	private class GZipStream extends ServletOutputStream {

		private GZIPOutputStream zipStream;

		public GZipStream(OutputStream out) throws IOException {
			zipStream = new GZIPOutputStream(out);
		}

		public void flush() throws IOException {
			zipStream.flush();
		}

		public void write(byte[] b, int off, int len) throws IOException {
			zipStream.write(b, off, len);
		}

		public void write(byte[] b) throws IOException {
			zipStream.write(b);
		}

		public void write(int arg0) throws IOException {
			zipStream.write(arg0);
		}

		public void finish() throws IOException {
			zipStream.finish();
		}

		public void close() throws IOException {
			zipStream.close();
		}

	}

}
