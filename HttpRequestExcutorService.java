package com.example.http;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 异步网络请求线程池
 * 
 * @author simen
 * 
 */
public class HttpRequestExcutorService {

	ExecutorService service;//线程池

	/**
	 * 内部类
	 * @author simen
	 */
	private static class GetInstance {
		public final static HttpRequestExcutorService asyncRequest = new HttpRequestExcutorService();
	}

	/**
	 * 内部类加载单例
	 * @return
	 */
	public static HttpRequestExcutorService getInstance() {
		return GetInstance.asyncRequest;
	}

	/**
	 * 初始化
	 * 定长线程池，最大并发5，多则等待
	 */
	public HttpRequestExcutorService() {
		service = Executors.newFixedThreadPool(5);
	}

	public void stop() {
		try {
			if (service != null) {
				service.shutdown();
				service.shutdownNow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initService() {
		try {
			if (service != null) {
				service = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 网络请求，加入线程池管理队列
	 * @param url
	 * @param requestMethod
	 * @param params
	 * @param listner
	 */
	public void addHttpRequestQueue(Thread item) {
		service.execute(item);
	}
}
