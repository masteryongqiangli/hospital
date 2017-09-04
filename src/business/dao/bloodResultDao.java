package business.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import system.core.dao.BaseDaoI;

public interface bloodResultDao extends BaseDaoI{
	public JSONObject getBloodResultList(Map<String, String> map);
	
	public boolean importBloodResult(Map<String, Object> onePerson);
	
	public JSONObject getBloodInfo(String bloodId);
	
	public boolean doDelete(String bloodResultId);
	
	public String getResultId(String bloodId);
}
