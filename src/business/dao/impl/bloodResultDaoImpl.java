package business.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.catalina.tribes.util.UUIDGenerator;
import org.hibernate.SQLQuery;
import org.jeecgframework.core.util.GetConnection;
import org.springframework.stereotype.Repository;

import business.dao.bloodResultDao;
import business.entity.Sys_Base_bloodResult;
import system.core.dao.impl.BaseDaoImpl;
import system.core.util.CriteriaPageUtil;
import system.core.util.StringUtil;
import system.web.entity.base.Sys_User;

@Repository
public class bloodResultDaoImpl extends BaseDaoImpl implements bloodResultDao{
	@Override
	public JSONObject getBloodResultList(Map<String, String> parammMap){
		StringBuffer sql = getSysUserSql();
		if (StringUtil.isNotEmpty(parammMap.get("bloodNumber"))) {
			sql.append(" and a.bloodNumber like '%"+parammMap.get("bloodNumber")+"%'");
		}
		if (StringUtil.isNotEmpty(parammMap.get("blooderName"))) {
			sql.append(" and b.blooderName like '%"+parammMap.get("blooderName")+"%'");
		}
		/*SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.addEntity(Sys_Base_bloodResult.class);
		return CriteriaPageUtil.getPageJson(query, parammMap);*/
		GetConnection connection = new GetConnection();
		return connection.getJsonObjectPageBySql(sql.toString(), parammMap.get("page").toString(), parammMap.get("rows").toString());
	}
	
	public StringBuffer getSysUserSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.id,a.bloodEnterId,b.bloodNumber,a.ALB,a.ALP,a.ALT,a.AST,a.CK,a.CK_MB,a.CRE,a.DBIL,a.GGT,a.GLU,a.HBDH,a.HDL_C,a.LDH,a.LDL_C,a.LDL_C,a.TBIL,a.TC,a.TG,a.TP,a.UA,a.UREA,b.blooderName FROM dbo.Sys_Base_bloodResult a");
		sql.append( " LEFT JOIN dbo.Sys_Base_bloodEnter b ON a.bloodEnterId = b.id");
		sql.append(" where a.state is NULL ");
		return sql;
	}
	
	public boolean importBloodResult(Map<String, Object> map){
		GetConnection connection = new GetConnection();
		StringBuffer sql = new StringBuffer();
		String bloodEnterId = connection.getcol("select id from Sys_Base_bloodEnter where blooderName = '"+map.get("name")+"';");
		sql.append("insert into Sys_Base_bloodResult (id,ALB,ALP,ALT,[AST],[CK],[CK_MB],[CRE],[DBIL],[GGT],[GLU],[HBDH],[HDL_C],[LDH],[LDL_C],[TBIL],[TC],[TG],[TP],[UA],[UREA],[bloodEnterId]) ");
		sql.append("values ('"+system.core.util.UUIDGenerator.generate().toString()+"',");
		sql.append("'"+(map.get("ALB")==null?"":map.get("ALB"))+"',");
		sql.append("'"+(map.get("ALP")==null?"":map.get("ALP"))+"',");
		sql.append("'"+(map.get("ALT")==null?"":map.get("ALT"))+"',");
		sql.append("'"+(map.get("AST")==null?"":map.get("AST"))+"',");
		sql.append("'"+(map.get("CK")==null?"":map.get("CK"))+"',");
		sql.append("'"+(map.get("CK-MB")==null?"":map.get("CK-MB"))+"',");
		sql.append("'"+(map.get("CRE")==null?"":map.get("CRE"))+"',");
		sql.append("'"+(map.get("DBIL")==null?"":map.get("DBIL"))+"',");
		sql.append("'"+(map.get("GGT")==null?"":map.get("GGT"))+"',");
		sql.append("'"+(map.get("GLU")==null?"":map.get("GLU"))+"',");
		sql.append("'"+(map.get("HBDH")==null?"":map.get("HBDH"))+"',");
		sql.append("'"+(map.get("HDL-C")==null?"":map.get("HDL-C"))+"',");
		sql.append("'"+(map.get("LDH")==null?"":map.get("LDH"))+"',");
		sql.append("'"+(map.get("LDL-C")==null?"":map.get("LDL-C"))+"',");
		sql.append("'"+(map.get("TBIL")==null?"":map.get("TBIL"))+"',");
		sql.append("'"+(map.get("TC")==null?"":map.get("TC"))+"',");
		sql.append("'"+(map.get("TG")==null?"":map.get("TG"))+"',");
		sql.append("'"+(map.get("TP")==null?"":map.get("TP"))+"',");
		sql.append("'"+(map.get("UA")==null?"":map.get("UA"))+"',");
		sql.append("'"+(map.get("UREA")==null?"":map.get("UREA"))+"',");
		sql.append("'"+bloodEnterId+"');");
		
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
		String resulrTime = dateFormat.format( now ); 
		sql.append("update Sys_Base_bloodEnter set [bloodResultTime] = '"+resulrTime+"';");
		return connection.insert(sql.toString());
	}
	
	public JSONObject getBloodInfo(String bloodId){
		GetConnection connection = new GetConnection();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from [Sys_Base_bloodEnter] where id = '"+bloodId+"'");
		return connection.getJsonObjectBySql(buffer.toString());
	}
	
	
	public boolean doDelete(String bloodResultId){
		GetConnection connection = new GetConnection();
		StringBuffer buffer = new StringBuffer();
		buffer.append("update [Sys_Base_bloodResult] set [state] = 1 where [id] = '"+bloodResultId+"'");
		return connection.insert(buffer.toString());
	}
	
	public String getResultId(String bloodId){
		GetConnection connection = new GetConnection();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id from [Sys_Base_bloodResult] where [bloodEnterId] = '"+bloodId+"'");
		return connection.getcol(buffer.toString());
	}
}
