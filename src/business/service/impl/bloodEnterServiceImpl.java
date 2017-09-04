package business.service.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import business.dao.bloodEnterDao;
import business.entity.Sys_Base_bloodEnter;
import business.service.bloodEnterService;
import system.core.service.impl.CommonServiceImpl;
import system.web.dao.base.baseuser.BaseUserDaoI;

@Service("bloodEnterService")
@Transactional
public class bloodEnterServiceImpl extends CommonServiceImpl implements bloodEnterService{
	@Autowired
	private bloodEnterDao bloodEnterDao;
	
	/**
	 * 获取新增血样列表
	 */
	public JSONObject getBloodEnterList(Map<String, String> map){
		return bloodEnterDao.getBloodEnterList(map);
	}
	
	public JSONObject getVillage(){
		return bloodEnterDao.getVillage();
	}
	
	public String getvillageNum(String text){
		return bloodEnterDao.getvillageNum(text);
	}
	public boolean doDelete(String bloodId){
		return bloodEnterDao.doDelete(bloodId);
	}
	
	public JSONObject getOneBlood(String bloodEnterId){
		return bloodEnterDao.getOneBlood(bloodEnterId);
	}
}
