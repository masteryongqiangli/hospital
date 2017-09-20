package system.web.service.base.baseuser;

import java.text.ParseException;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.service.CommonServiceI;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;

public interface BaseUserServiceI extends CommonServiceI {

	public JSONObject getDataGridJson(Map<String, String> parms, String userRole);

	/**
	 * 用户登录
	 * 
	 * @param sys_Base_User
	 * @return
	 */
	public Sys_Base_User login(Sys_Base_User sys_Base_User);

	public Sys_User getSysUserByUserId(String userId);

	public Sys_User getSysUserByUserName(String userName);

	public void logout();

	public JSONObject getuserRoles(Sys_Base_User sys_Base_User);

	public void saveUserRoles(Sys_Base_User sys_Base_User, String[] in);

	public JSONArray getBaseUserList();

	public JSONObject getHomeData() throws ParseException;

	public JSONObject getSelects(int flag1, String flag2);

	public boolean submitDate(String changeDate,String xmlPath);
}
