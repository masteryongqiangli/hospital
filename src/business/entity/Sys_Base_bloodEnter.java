package business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_bloodEnter")
public class Sys_Base_bloodEnter {
	private String id;
	private String bloodNumber;
	private String blooderName;
	private Integer blooderAge;
	private String blooderIdCard;
	@Column(length=50)
	public String getBlooderIdCard() {
		return blooderIdCard;
	}
	public void setBlooderIdCard(String blooderIdCard) {
		this.blooderIdCard = blooderIdCard;
	}
	private String blooderDistrict;
	private String blooderContry;
	private String bloodStartTime;
	private String bloodAriveTime;
	private String bloodResultTime;
	private String bloodOperator;
	private String bloodTransformer;
	private Integer state;
	@Column(length=50)
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
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
	public String getBloodNumber() {
		return bloodNumber;
	}
	public void setBloodNumber(String bloodNumber) {
		this.bloodNumber = bloodNumber;
	}
	@Column(length=50)
	public String getBlooderName() {
		return blooderName;
	}
	public void setBlooderName(String blooderName) {
		this.blooderName = blooderName;
	}
	@Column(length=50)
	public Integer getBlooderAge() {
		return blooderAge;
	}
	@Column 
	public String getBlooderDistrict() {
		return blooderDistrict;
	}
	public void setBlooderDistrict(String blooderDistrict) {
		this.blooderDistrict = blooderDistrict;
	}
	public void setBlooderAge(Integer blooderAge) {
		this.blooderAge = blooderAge;
	}
	@Column 
	public String getBlooderContry() {
		return blooderContry;
	}
	public void setBlooderContry(String blooderContry) {
		this.blooderContry = blooderContry;
	}
	@Column 
	public String getBloodStartTime() {
		return bloodStartTime;
	}
	public void setBloodStartTime(String bloodStartTime) {
		this.bloodStartTime = bloodStartTime;
	}
	@Column 
	public String getBloodAriveTime() {
		return bloodAriveTime;
	}
	public void setBloodAriveTime(String bloodAriveTime) {
		this.bloodAriveTime = bloodAriveTime;
	}
	@Column 
	public String getBloodResultTime() {
		return bloodResultTime;
	}
	public void setBloodResultTime(String bloodResultTime) {
		this.bloodResultTime = bloodResultTime;
	}
	@Column 
	public String getBloodOperator() {
		return bloodOperator;
	}
	public void setBloodOperator(String bloodOperator) {
		this.bloodOperator = bloodOperator;
	}
	@Column 
	public String getBloodTransformer() {
		return bloodTransformer;
	}
	public void setBloodTransformer(String bloodTransformer) {
		this.bloodTransformer = bloodTransformer;
	}
	
}
