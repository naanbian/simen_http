package com.example.http;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 封装网络请求后的实体写法eg
 * @author simen
 * 有时间还需要写一个公共无返回实体类
 */
public class TestEntity extends HttpResultBastEntity<List<TestEntity>>{
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	/**
	 * 实例化
	 * @param callback
	 * @param erroCallback
	 */
	public TestEntity(HttpResultBastEntity.GetDataCallBack<List<TestEntity>> callback, HttpResultBastEntity.HttpErroResult erroCallback) {
		super(callback, erroCallback);
	}
	
	
	/**
	 * 解析
	 */
	@Override
	protected List<TestEntity> parse(String values) {
		//解析 TODO
		try {
			JSONObject js = new JSONObject(values);
			List<TestEntity> cache = new ArrayList<TestEntity>();
			if(js.getInt("erroCode") != 0){
				erroMsg = js.optString("erroMsg","Serve erro");
			}else{
				//解析 TODO
				return cache;
			}
		} catch (JSONException e) {
			erroMsg = e.getMessage();
			e.printStackTrace();
		}
		
		return null;
	}

	//*********************请求测试******************
	public void testRequest(){
		ErroBack erroBack = new ErroBack();//公用错误回调
		TestEntity ta = new TestEntity(new GetDataCallBack<List<TestEntity>>() {
			@Override
			public void httpResult(List<TestEntity> result) {
				
			}
		},erroBack);
		HttpRequestDo.getInstance().httpRequest("url", "POST", null,ta);
	}
	
	/**
	 * //公用错误回调
	 * @author simen
	 *
	 */
	private class ErroBack implements HttpErroResult{
		@Override
		public void httpResultErro(String errMsg) {
			// TODO  Toast erroMsg
		}
		
	}
	
	
}
