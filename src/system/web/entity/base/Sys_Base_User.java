package system.web.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import system.core.util.IdcardUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_User")
public class Sys_Base_User implements Serializable {
	private String userId;
	private String userName;
	private String password;
	private String idCardNumber;
	private String gener;
	private Date birthDate;
	private String realName;
	private String email;
	private String phone;
	//private Sys_Base_Icon icon;
	private int state;
	private int version;
	private Sys_Base_DataDictionary  district;
	private Sys_Base_DataDictionary town;
	private Sys_Base_DataDictionary village;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(length=50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@OneToOne
	public Sys_Base_DataDictionary getTown() {
		return town;
	}

	public void setTown(Sys_Base_DataDictionary town) {
		this.town = town;
	}
	@OneToOne
	public Sys_Base_DataDictionary getVillage() {
		return village;
	}

	public void setVillage(Sys_Base_DataDictionary village) {
		this.village = village;
	}

	@OneToOne
	public Sys_Base_DataDictionary getDistrict() {
		return district;
	}

	public void setDistrict(Sys_Base_DataDictionary district) {
		this.district = district;
	}

	@Column(length=50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	 
	@Column(length=50)
	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	@Column(length=4)
	public String getGener() {
		return gener;
	}

	public void setGener(String gener) {
		this.gener = gener;
	}

	@Column
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	

	@Column(length=50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(length=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=50)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
	@Column
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
    /**
     * 版本
     * @return
     */
	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	

	

}
