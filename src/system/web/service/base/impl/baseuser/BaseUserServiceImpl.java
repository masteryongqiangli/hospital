package system.web.service.base.impl.baseuser;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.annotation.Log;
import system.core.dao.impl.BaseDaoImpl;
import system.core.service.impl.CommonServiceImpl;
import system.core.util.DateUtils;
import system.core.util.JSONHelper;
import system.core.util.ResourceUtil;
import system.web.dao.base.baseuser.BaseUserDaoI;
import system.web.dao.base.datadictionary.DataDictionaryDaoI;
import system.web.dao.base.role.RoleDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_Role_Menu;
import system.web.entity.base.Sys_Base_Role_User;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;
import system.web.service.base.baseuser.BaseUserServiceI;

/**
 * 
 * @author renrti
 *
 */
@Service("baseUserService")
@Transactional
public class BaseUserServiceImpl extends CommonServiceImpl implements BaseUserServiceI {
	
	@Autowired
	private DataDictionaryDaoI dataDictionaryDao;
	@Autowired
	private BaseUserDaoI baseUserDao;
	@Autowired
	private RoleDaoI roleDao;

	public JSONObject getDataGridJson(Map<String, String> parms) {
		return baseUserDao.getSysUsers(parms);
	}

	@Override
	public Sys_Base_User login(Sys_Base_User sys_Base_User) {
		List<Sys_Base_User> sys_Base_Users = baseUserDao.findByProperty(Sys_Base_User.class, "userName",
				sys_Base_User.getUserName());
		if (sys_Base_Users.size() > 0 && sys_Base_Users.get(0).getState() == 1) {
			return sys_Base_Users.get(0);
		} else {
			return null;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		ResourceUtil.Session().invalidate();
		// TODO Auto-generated method stub

	}

	public Sys_User getSysUserByUserId(String userId) {
		List<Sys_User> list = baseUserDao.getSys_UserByUserId(userId);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public Sys_User getSysUserByUserName(String userName) {
		List<Sys_User> list = baseUserDao.getSys_UserByUserName(userName);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public JSONObject getuserRoles(Sys_Base_User sys_Base_User) {
		List<Sys_Base_Role_User> userRoles = baseUserDao.getuserRoles(sys_Base_User);
		Map<String, String> map = new HashMap<>();
		List<Sys_Base_Role> listtotal = baseUserDao.getAll(Sys_Base_Role.class);
		List<Sys_Base_Role> listin = new ArrayList<>();
		List<Sys_Base_Role> listnotin = new ArrayList<>();

		for (Sys_Base_Role_User role_user : userRoles) {
			listin.add(role_user.getRole());
		}
		for (Sys_Base_Role sys_Base_Role : listtotal) {
			boolean flag = true;
			for (Sys_Base_Role in : listin) {
				if (in.getRoleId().equals(sys_Base_Role.getRoleId())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				listnotin.add(sys_Base_Role);
			}

		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("In", JSONHelper.parseListtToJSONArray(listin));
		jsonObject.put("NotIn", JSONHelper.parseListtToJSONArray(listnotin));
		return jsonObject;
	}

	public void saveUserRoles(Sys_Base_User sys_Base_User, String[] in) {
		baseUserDao.deleteRoleUsersbyUser(sys_Base_User);
		Map<String, String> map = new HashMap<>();
		List<Sys_Base_Role> listtotal = baseUserDao.getAll(Sys_Base_Role.class);
		List<Sys_Base_Role> listin = new ArrayList<>();
		List<Sys_Base_Role_User> sys_Base_Role_Users = new ArrayList<>();
		for (String roleid : in) {
			for (Sys_Base_Role sys_Base_Role : listtotal) {
				if (roleid.equals(sys_Base_Role.getRoleId()) && !listin.contains(sys_Base_Role)) {
					listin.add(sys_Base_Role);
					Sys_Base_Role_User sys_Base_Role_User = new Sys_Base_Role_User();
					sys_Base_Role_User.setBaseuser(sys_Base_User);
					sys_Base_Role_User.setRole(sys_Base_Role);
					sys_Base_Role_Users.add(sys_Base_Role_User);
					break;
				}
			}
		}

		baseUserDao.batchsave(sys_Base_Role_Users);
		// TODO Auto-generated method stub

	}

	@Override
	public JSONArray getBaseUserList() {
		JSONArray array = new JSONArray();
		List<Sys_Base_User> users = baseDaoImpl.getAll(Sys_Base_User.class);
		for (Sys_Base_User sys_Base_User : users) {
			JSONObject a = new JSONObject();
			a.put("userId", sys_Base_User.getUserId());
			a.put("userName", sys_Base_User.getUserName());
			a.put("gener", sys_Base_User.getGener());
			array.add(a);
		}
		return array;
	}

	@Override
	public JSONObject getHomeData() throws ParseException {
		/*List<Map<String, Object>> list = baseUserDao.getHomeData();
		JSONArray type1 = new JSONArray();
		JSONArray type2 = new JSONArray();
		JSONArray type3 = new JSONArray();
		JSONArray dates=new JSONArray();
		if (list.size() > 0) {
			String maxDate = list.get(0).get("maxDate").toString();
			String minDate = list.get(0).get("minDate").toString();
			int days = (int) list.get(0).get("days");
			for (int i = 0; i <days; i++) {
				Calendar calendars = DateUtils.parseCalendar(minDate, "yyyy-MM-dd");
				calendars.add(Calendar.DAY_OF_MONTH, i);
				String date = DateUtils.formatDate(calendars);
				
				Object flag1=null;
				boolean tag1=true;
				Object flag2=null;
				boolean tag2=true;
				Object flag3=null;
				boolean tag3=true;
				for (int j = 0; j < list.size(); j++) {
					if (tag1&&(int)list.get(j).get("evalType")==1&&date.equals(list.get(j).get("date").toString())) {
						flag1 = Integer.parseInt(list.get(j).get("count").toString());
						tag1=false;
					}
					if (tag2&&(int)list.get(j).get("evalType")==2&&date.equals(list.get(j).get("date").toString())) {
						flag2 =  Integer.parseInt(list.get(j).get("count").toString());
						tag2=false;
					}
					if (tag3&&(int)list.get(j).get("evalType")==3&&date.equals(list.get(j).get("date").toString())) {
						flag3 =  Integer.parseInt(list.get(j).get("count").toString());
						tag3=false;
					} 
					 
					 }
				
				 if (flag1!=null||flag2!=null||flag3!=null) {
					 type1.add(flag1);
					type2.add(flag2);
					type3.add(flag3); 
					dates.add(date);
					 
				} 
				 
				
			}
		}
		JSONObject object=new JSONObject();
		object.put("type1", type1);
		object.put("type2", type2);
		object.put("type3",type3);
		object.put("dates",dates);
		return object;*/
		return null;
	}

	@Override
	public JSONObject getSelects() {
		JSONObject jsonObject=new JSONObject();
		String [] codes={"village"};
		Map<String,List<Sys_Base_DataDictionary>> selects= dataDictionaryDao.getSelects(codes);
	    for (int i = 0; i < codes.length; i++) {
			jsonObject.put(codes[i], JSONHelper.parseListtToJSONArray(selects.get(codes[i])));
		}
		  return jsonObject;
	}
}
