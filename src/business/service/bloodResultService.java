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
	 * @param userDistrict 
	 * @param userDistrict2 
	 * @return
	 */
	public JSONObject getBloodResultList(Map<String, String> map, String userRole, String userDistrict);
	
	public boolean readExcel(MultipartFile file,String village,String newPath);
	
	public JSONObject getBloodInfo(String bloodId);
	
	public boolean doDelete(String bloodResultId);
	
	public String getResultId(String bloodId);
}
