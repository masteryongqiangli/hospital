package system.web.dao.base.baseuser;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import system.core.dao.BaseDaoI;
import system.web.entity.base.Sys_Base_Role_User;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;

public interface BaseUserDaoI extends BaseDaoI {
	/**
	 *用户登录
	 * @param sys_Base_User
	 * @return
	 */
	 public Sys_Base_User login( Sys_Base_User sys_Base_User);
	 public  JSONObject getUsers(Map<String, String> parms);
	 public JSONObject getSysUsers(Map<String, String> parms);
	 public List getSys_UserByUserId(String userId);
	 public List getSys_UserByUserName(String userName);
	 public List<Sys_Base_Role_User> getuserRoles(Sys_Base_User sys_Base_User) ;
	 public void deleteRoleUsersbyUser(Sys_Base_User sys_Base_User);
	 public List<Map<String, Object>> getHomeData();
}
