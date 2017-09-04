package business.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
import system.core.service.CommonServiceI;

public interface bloodResultService extends CommonServiceI{
	
	/**
	 * 获取血样检验结果列表
	 * @param map
	 * @return
	 */
	public JSONObject getBloodResultList(Map<String, String> map);
	
	public boolean readExcel(MultipartFile file);
	
	public JSONObject getBloodInfo(String bloodId);
	
	public boolean doDelete(String bloodResultId);
	
	public String getResultId(String bloodId);
}
