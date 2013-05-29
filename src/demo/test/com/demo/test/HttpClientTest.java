package com.demo.test;

/**
 * 功 能 描 述:<br>
 * http模拟请求测试
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-23下午03:03:41
 * <br>项 目 信 息:paramecium:com.demo.test.HttpClientTest.java
 */
public class HttpClientTest {
	
	/*volatile static int countor = 0;
	volatile static int threadNo = 0;

	synchronized static int printCount() {
		return countor++;
	}
	
	synchronized static int printThreadNo() {
		return threadNo++;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			new Thread(new HttpClientTest().new ThreadHttp(printThreadNo())).start();
		}
	}

	class ThreadHttp implements Runnable {
		volatile int threadNo = 0;
		
		public ThreadHttp(int threadNo){
			this.threadNo = threadNo;
		}
		public void run() {
			while (true) {
				if (countor >= 1000) {
					try {
						System.out.println(this.threadNo + "该线程已经挂起!");
						//Thread.sleep(1000 * 10 * 60);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
					//initI();
					//continue;
				}
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setIntParameter("http.socket.timeout",5000);
				HttpGet httpget = new HttpGet("http://www.baidu.com/");
				HttpResponse response = null;
				try {
					response = httpclient.execute(httpget);
					int code = response.getStatusLine().getStatusCode();
					if(code<400){
						System.out.println(this.threadNo + "线程累计扫描：" + printCount() + " 状态码:" + response.getStatusLine().getStatusCode());
					}else{
						System.err.println("网页有错误，错误码为："+code);
					}
				} catch (ClientProtocolException e) {
				} catch (java.net.SocketTimeoutException e) {
					System.err.println("超时了。。。");
				} catch (IOException e) {
				}
			}
		}

	}*/
	
	public static void main(String[] args) throws Exception {
		/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("username", "admin"));
		formparams.add(new BasicNameValuePair("password", "admin"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpHost target = new HttpHost("127.0.0.1", 80, "http");
		HttpPost httppost = new HttpPost("/login.jhtml");
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(target, httppost, localContext);
		EntityUtils.consume(response.getEntity());*/
	}
	
}
