package com.example.http;

import java.util.Map;
import java.util.Set;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;



/**
 * 网络请求执行线成
 * @author simen
 *
 */
public class HttpRequestDo {
	
	private static class SingleInstance{
		public static final HttpRequestDo instance = new HttpRequestDo();
	}
	
	/**
	 * 内部类加载单例
	 * @return
	 */
	public static HttpRequestDo getInstance() {
		return SingleInstance.instance;
	}
	
	
	public void httpRequest(final String url, final String requestMethod, final Set<Map.Entry<String, Object>> params, final HttpRequestListener listner){
		HttpRequestExcutorService.getInstance().addHttpRequestQueue(request(url, requestMethod, params, listner));
	}
	
	/**
	 * 异步请求，消息处理
	 * @param url
	 * @param requestMethod
	 * @param params
	 * @param listner
	 * @return
	 */
	private Thread request(final String url, final String requestMethod, final Set<Map.Entry<String, Object>> params, final HttpRequestListener listner) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj != null&&listner!=null) {
					String result = (String) msg.obj;
					if (TextUtils.isEmpty(result) || ((String) msg.obj).equals(HttpRequestRun.HTTPERRO)) {
						listner.onException(HttpRequestRun.HTTPERRO);// 网络请求失败
					} else {
						listner.onComplete((String) msg.obj);
					}
				}
				super.handleMessage(msg);
			}
		};
		return new Thread(new Runnable() {
			@Override
			public void run() {
				String result = "";
				if (requestMethod.equals("POST")) {
					result = HttpRequestRun.getInstance().httpPostExecute(url, params);
				} else if (requestMethod.equals("GET")) {
					result = HttpRequestRun.getInstance().httpPostExecute(url, params);
				}
				sendToHandler(handler, result);
			}
		}, url);
	}

	/**
	 * 消息发送
	 * @param handler
	 * @param msg
	 */
	public void sendToHandler(Handler handler, String msg) {
		Message message = handler.obtainMessage();
		message.obj = msg;
		handler.sendMessage(message);
	}

}
