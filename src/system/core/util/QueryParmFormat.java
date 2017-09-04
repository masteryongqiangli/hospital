package system.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParmFormat {
       public static Map<String, String> Format( Map<String, String[]> parms){
    	   Map<String, String> map=new HashMap<String, String>();
    	   for (Entry<String, String[]> entry : parms.entrySet()) {
    		   String[] array=entry.getValue();
    		   String value="";
				for (int i = 0; i <array.length; i++) {
					value+=array[i]+",";
				}
				 map.put(entry.getKey(), value.substring(0,value.length()-1));
			}
    	   return map;
       }
}
