package com.example.http;

/**
 * 网络请求回调
 * @author simen
 *
 */
public interface HttpRequestListener {
	/**
	 * 
	 * @param values
	 */
	public void onComplete(String values);

	/**
	 * 发生异常的时候执行该方法
	 * 
	 * @param exceptionInfo
	 */
	public void onException(String errrMsg);
	
	
}
