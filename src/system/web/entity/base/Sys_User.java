package system.web.entity.base;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Sys_User {
	
	private String userId;
	private String userName;
	private String idCardNumber;
	private String gener;
	private String birthDate;
	private String age;
	private String realName;
	private String email;
	private String village;
	private String town;
	private String district;
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	private String phone;
	private String roleIdList;
	private String roleCodeList;
	private String roleNameList;
	@Id
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getGener() {
		return gener;
	}
	public void setGener(String gener) {
		this.gener = gener;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRoleIdList() {
		return roleIdList;
	}
	public void setRoleIdList(String roleIdList) {
		this.roleIdList = roleIdList;
	}
	public String getRoleCodeList() {
		return roleCodeList;
	}
	public void setRoleCodeList(String roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
	public String getRoleNameList() {
		return roleNameList;
	}
	public void setRoleNameList(String roleNameList) {
		this.roleNameList = roleNameList;
	}
}
