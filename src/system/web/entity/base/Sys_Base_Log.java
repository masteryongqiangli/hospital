package system.web.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_Log")
public class Sys_Base_Log implements Serializable {
	private String id;
	private String description;
	private String method;
	private int logType;
	private String requsetIp;
	private String browser;
	private Sys_Base_User user;
	private Date date;
	private String parms;
	private String returnValue;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(length=50)
	public String getId() {
		
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length=50)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(columnDefinition="nvarchar(max)")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column
	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	@Column(length=50)
	public String getRequsetIp() {
		return requsetIp;
	}

	public void setRequsetIp(String requsetIp) {
		this.requsetIp = requsetIp;
	}

	@OneToOne
	public Sys_Base_User getUser() {
		return user;
	}

	public void setUser(Sys_Base_User user) {
		this.user = user;
	}

	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(length=50)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
	@Column(columnDefinition="nvarchar(max)")
	public String getParms() {
		return parms;
	}

	public void setParms(String parms) {
		this.parms = parms;
	}
	@Column(columnDefinition="nvarchar(max)")
	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

}
