package system.core.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonObjectValueProcessor implements JsonValueProcessor{

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig){
		// TODO Auto-generated method stub
		return value==null?"":value;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		// TODO Auto-generated method stub
		return value==null?"":value;
	}

}
