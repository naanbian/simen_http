package com.example.http;
import java.io.Serializable;


/**
 * 网络请求实体基类，封装解析，成功与失败的回调，序列化
 * @author simen
 * @param <T>
 */
public class HttpResultBastEntity<T> implements HttpRequestListener,Serializable{
	
	/**
	 * 序列化ID
	 */
//	private static final long serialVersionUID = 1L;
	protected GetDataCallBack<T> callback;
	protected HttpErroResult erroCallback;
	
	protected String erroMsg = "";//错误信息
	
	/**
	 * 实例化，子类重写，传入需要输出的实体类型
	 * @param callback
	 * @param erroCallback
	 */
	public HttpResultBastEntity(GetDataCallBack<T> callback,HttpErroResult erroCallback) {
		this.callback = callback;
		this.erroCallback = erroCallback;
	}
	

	/**
	 * 网络请求成功
	 */
	@Override
	public void onComplete(String values) {
		T s = this.parse(values);
		if(s!=null){
			callback.httpResult(s);//解析成功，返回实体
		}else{
			erroCallback.httpResultErro(erroMsg);//解析异常,或者服务器异常，返回异常信息
		}
	}

	/**
	 * 网络请求失败
	 */
	@Override
	public void onException(String errrMsg) {
		erroCallback.httpResultErro(errrMsg);
	}
	
	/**
	 * 解析，需要子类重写实现
	 * @param values
	 * @return
	 */
	protected T parse(String values) {
		return null;
	}
	
	/**
	 * 成功
	 * @author simen
	 * @param <T>
	 */
	public interface GetDataCallBack<T>{
		void httpResult(T result);
	}
	
	
	/**
	 * 失败（与成功分开写是因为，有些界面会公用一个失败回调，集中处理）
	 * @author simen
	 *
	 */
	public interface HttpErroResult{
		void httpResultErro(String errMsg);
	}

	
}
