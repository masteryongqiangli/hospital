package system.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;


/**
 * JSON和JAVA的POJO的相互转换
 * @author  
 * JSONHelper.java
 */
public final class JSONHelper {
	private static final Logger logger = Logger.getLogger(JSONHelper.class);

	 public static JSONObject parseObjectToJSONObejct(Object object) {
		 try {
			 JsonConfig jsonConfig = new JsonConfig(); 
			 jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			 jsonConfig.registerJsonValueProcessor(Object.class, new JsonObjectValueProcessor());
			return JSONObject.fromObject(object,jsonConfig);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
		
	}
	 public static JSONArray parseObjectsToJSONArray(Object object[]){
		 JSONArray jsonArray=new JSONArray();
		 for (int i = 0; i < object.length; i++) {
			jsonArray.add(parseObjectToJSONObejct(object[i]));
		}
		 return jsonArray;
	 }
	 public static JSONArray parseListToJSONArray(List<Object>list){
		 JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			jsonArray.add(parseObjectToJSONObejct(list.get(i)));
			
		}
		 return jsonArray;
	 }
	 public static  <T> JSONArray parseListtToJSONArray(List<T> list){
		 JSONArray jsonArray=new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			jsonArray.add(parseObjectToJSONObejct(list.get(i)));
			
		}
		 return jsonArray;
	 }
}
