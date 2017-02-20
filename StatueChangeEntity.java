package com.example.http;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 无实体输出公用Entity
 * @author simen
 *
 */
public class StatueChangeEntity extends HttpResultBastEntity<String>{

	public StatueChangeEntity(HttpResultBastEntity.GetDataCallBack<String> callback, HttpResultBastEntity.HttpErroResult erroCallback) {
		super(callback, erroCallback);
	}
	
	@Override
	protected String parse(String values) {
		//解析 TODO
		try {
			JSONObject js = new JSONObject(values);
			if(js.getInt("error") != 0){
				erroMsg = js.optString("erroMsg","Serve erro");
			}else{
				//解析 TODO
				return "statuchanged";
			}
		} catch (JSONException e) {
			erroMsg = e.getMessage();
			e.printStackTrace();
		}
		
		return null;
	}
	

}
