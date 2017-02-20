package com.example.http;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.util.Log;




/**
 * http post请求
 * @author simen
 *
 */
public class HttpRequestRun {
	
	public final static String HTTPERRO = "HTTP_request_erro";
	
	public final static int HTTPCONNECTOUTOFTIME = 15000;//连接超时
	public final static int HTTPREQUESTOUTOFTIME = 10000;//请求超时
	
	public final static String UNIONCODE = "utf-8";//编码格式

	
	private static class GetInstance{
		public static final HttpRequestRun asyncRequest = new HttpRequestRun();
	}

	/**
	 * 内部类加载
	 * @return
	 */
	public static HttpRequestRun getInstance() {
		return GetInstance.asyncRequest;
	}
	/**
	 * Http POST 请求
	 * 
	 * @param url
	 */
	public String httpPostExecute(String url,
			Set<Map.Entry<String, Object>> params) {
		String result;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(HTTPCONNECTOUTOFTIME);//设置连接超时
		PostMethod postMethod = new PostMethod(url);//设置请求的url
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HTTPREQUESTOUTOFTIME);//设置请求超时
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, UNIONCODE);//设置请求的参数的编码格式
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());//使用系统提供的默认的恢复策略
		try {
			if(params!=null){
				for(Map.Entry<String, Object> param:params){//设置额外的请求参数
					postMethod.addParameter(param.getKey(), String.valueOf(param.getValue()));
				}
			}
			int statueCode = httpClient.executeMethod(postMethod);//获取请求的状态标识符
			if(statueCode == HttpStatus.SC_OK){//如果请求成功
				String values = new String(postMethod.getResponseBody(),UNIONCODE);
				result = values;//将成功返回的数据转成中文编码的字符串
			}else{
				result = HTTPERRO;//网络请求失败
			}
		} catch (HttpException e) {
			result = HTTPERRO;//网络请求失败
			e.printStackTrace();
			Log.d(HTTPERRO, e.getMessage());
		} catch (IOException e) {
			result = HTTPERRO;//网络请求失败
			e.printStackTrace();
			Log.d(HTTPERRO, e.getMessage());
		} catch (Exception e) {
			result = HTTPERRO;//网络请求失败
			e.printStackTrace();
			Log.d(HTTPERRO, e.getMessage());
		}finally{
			postMethod.releaseConnection();//释放请求
		}
		return result;
	}
	
	
	 /**  
     * 将InputStream转换成某种字符编码的String  
     * @param in  
     * @param encoding  
     * @return  
     * @throws Exception  
     */  
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), encoding);
	}

	/*private static InputStream getInoutStream(String s){
		ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
		return is;
	}
	
	private static String getString(InputStream is) throws IOException{
		ByteArrayOutputStream asdf = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while((count = is.read(data, count, 1024))!=-1){
			asdf.write(data,0,count);
		}
		data = null;
		return new String(asdf.toByteArray(),"utf-8");
	}*/
}
