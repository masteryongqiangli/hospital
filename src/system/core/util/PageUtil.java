package system.core.util;

import java.util.Map;

import javassist.compiler.ast.NewExpr;
import net.sf.json.JSONObject;

public class PageUtil {
	public static JSONObject getPagePrams(Map< String,String> parms){
		JSONObject aJsonObject=new  JSONObject();
		aJsonObject.put("firstresult",(Integer.parseInt(parms.get("page")) - 1)	* Integer.parseInt((parms.get("rows"))));
		aJsonObject.put("rows", Integer.parseInt(parms.get("rows")));
		aJsonObject.put("page", Integer.parseInt(parms.get("page")));
		return aJsonObject;
	}
}
