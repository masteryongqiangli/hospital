package business.dao;

import java.util.Map;

import net.sf.json.JSONObject;
import system.core.dao.BaseDaoI;

public interface bloodEnterDao extends BaseDaoI{
	
	public JSONObject getBloodEnterList(Map<String, String> map);
	
	public JSONObject getVillage();
	
	public String getvillageNum(String text);
	
	public boolean doDelete(String bloodId);
	
	public JSONObject getOneBlood(String bloodEnterId);
}
