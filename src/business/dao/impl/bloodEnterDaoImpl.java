package business.dao.impl;

import java.util.Map;

import org.hibernate.SQLQuery;
import org.jeecgframework.core.util.GetConnection;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;
import business.dao.bloodEnterDao;
import business.entity.Sys_Base_bloodEnter;
import system.core.dao.impl.BaseDaoImpl;
import system.core.util.CriteriaPageUtil;
import system.core.util.ResourceUtil;
import system.core.util.StringUtil;
import system.web.entity.base.Sys_User;
@Repository
public class bloodEnterDaoImpl extends BaseDaoImpl implements bloodEnterDao{
	
	@Override
	public JSONObject getBloodEnterList(Map<String, String> parammMap){
		StringBuffer sql = getSysUserSql();
		if (StringUtil.isNotEmpty(parammMap.get("bloodNumber"))) {
			sql.append(" and bloodNumber like '%"+parammMap.get("bloodNumber")+"%'");
		}
		if (StringUtil.isNotEmpty(parammMap.get("blooderName"))) {
			sql.append(" and blooderName like '%"+parammMap.get("blooderName")+"%'");
		}
		Sys_User sys_User = ResourceUtil.getSys_User();
		if (!sys_User.getUserName().equals("sa")) {
			sql.append(" and blooderDistrict = '"+sys_User.getVillage()+"'");
		}
		sql.append(" ;");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.addEntity(Sys_Base_bloodEnter.class);
		return CriteriaPageUtil.getPageJson(query, parammMap);
	}
	
	public StringBuffer getSysUserSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  id ,bloodNumber ,blooderName , blooderAge ,blooderDistrict ,blooderContry ,");
		sql.append(" bloodStartTime ,bloodAriveTime ,bloodResultTime ,bloodOperator ,bloodTransformer ,state,blooderIdCard");
		sql.append(" FROM    dbo.Sys_Base_bloodEnter where 1=1");
		return sql;
	}
	
	
	public JSONObject getVillage(){
		StringBuffer sqlBuffer =  new StringBuffer();
		GetConnection connection = new GetConnection();
		sqlBuffer.append("SELECT text as dataDicId,text as text FROM dbo.Sys_Base_DataDictionary WHERE parent_DataDictionary = (");
		sqlBuffer.append(" SELECT dataDicId FROM dbo.Sys_Base_DataDictionary WHERE text = '村行政')");
		return connection.getJsonObjectBySql(sqlBuffer.toString());
	}
	
	public boolean doDelete(String bloodId){
		StringBuffer sqlBuffer =  new StringBuffer();
		GetConnection connection = new GetConnection();
		sqlBuffer.append("UPDATE dbo.Sys_Base_bloodEnter SET state = 1 WHERE id = '"+bloodId+"'");
		return connection.insert(sqlBuffer.toString());
	}
	
	public String getvillageNum(String text){
		StringBuffer sqlBuffer =  new StringBuffer();
		GetConnection connection = new GetConnection();
		sqlBuffer.append("SELECT code FROM dbo.Sys_Base_DataDictionary WHERE text = '"+text+"'");
		return connection.getcol(sqlBuffer.toString());
	}
	
	public JSONObject getOneBlood(String bloodEnterId){
		StringBuffer sqlBuffer =  new StringBuffer();
		GetConnection connection = new GetConnection();
		sqlBuffer.append("SELECT a.*,b.bloodNumber,b.blooderName,b.blooderIdCard,b.bloodOperator,b.bloodAriveTime,b.bloodStartTime,b.blooderIdCard,b.bloodResultTime AS bloodCheckTime,");
		sqlBuffer.append(" CONVERT(varchar(100), GETDATE(), 20) AS printTime,");
		sqlBuffer.append(" b.blooderAge,CASE WHEN SUBSTRING(b.blooderIdCard,17,1)%2 = 1 THEN '男' ELSE '女'END AS blooderSex,");
		sqlBuffer.append(" d.text+'乡'+b.blooderDistrict AS bloodCheckHospital");
		sqlBuffer.append(" FROM dbo.Sys_Base_bloodResult a LEFT JOIN dbo.Sys_Base_bloodEnter b  ON a.bloodEnterId = b.id");
		sqlBuffer.append(" LEFT JOIN dbo.Sys_Base_DataDictionary c ON b.blooderDistrict = c.text");
		sqlBuffer.append(" LEFT JOIN dbo.Sys_Base_DataDictionary d ON c.subjection = d.dataDicId");
		sqlBuffer.append(" WHERE b.id = '"+bloodEnterId+"' ");
		return connection.getJsonObjectBySql(sqlBuffer.toString());
	}

	@Override
	public String getBloodNumber(String village) {
		StringBuffer buffer = new StringBuffer();
		GetConnection connection = new GetConnection();
		buffer.append("SELECT TOP 1 SUBSTRING(a.bloodNumber,13 + LEN(b.code), LEN(a.bloodNumber))");
		buffer.append(" FROM    dbo.Sys_Base_bloodEnter a");
		buffer.append(" LEFT JOIN dbo.Sys_Base_DataDictionary b ON a.blooderDistrict = b.text");
		buffer.append(" WHERE   blooderDistrict = '"+village+"'");
		buffer.append(" ORDER BY SUBSTRING(a.bloodNumber,13 + LEN(b.code), LEN(a.bloodNumber)) DESC ");
		return connection.getcol(buffer.toString());
	}

	@Override
	public boolean doGoCheck(String[] parameter) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < parameter.length; i++) {
			buffer.append("update Sys_Base_bloodEnter set state = 1 where id = '"+parameter[i]+"';");
		}
		GetConnection connection = new GetConnection();
		return connection.insert(buffer.toString());
	}
}
