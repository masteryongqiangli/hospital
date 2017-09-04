package business.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import system.core.service.CommonServiceI;
import net.sf.json.JSONObject;
import business.entity.Sys_Base_bloodEnter;
import business.service.impl.bloodEnterServiceImpl;

public interface bloodEnterService extends CommonServiceI{

	/**
	 * 获取血样录入历史记录
	 * @return
	 */
	public JSONObject getBloodEnterList(Map<String, String> map);
	
	public JSONObject getVillage();
	
	public String getvillageNum(String text);
	
	public boolean doDelete(String bloodId);
	
	public JSONObject getOneBlood(String bloodEnterId);
	
}
