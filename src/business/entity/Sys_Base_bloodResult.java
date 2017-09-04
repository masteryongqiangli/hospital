package business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_bloodResult")
public class Sys_Base_bloodResult {
	private String id;
	private String bloodEnterId;
	private String bloodNumber;
	/*private String liverZDB;
	private String liverBDB;
	private String liverQDB;
	private String liverBQB;
	private String liverZDHS;
	private String liverZJDHS;
	private String liverJJDHS;
	private String liverZAM;
	private String bfatZDGC;
	private String bfatGYSZ;
	private String bfatGZDB;
	private String bfatDZDB;
	private String bfatZZDB;
	private String bloodSugar;
	private String renalJG;
	private String renalNSD;*/
	/*private String purineTrione;*/
/*	private String lactateDehydrogenase;
	private String creatineKinase;*/
	private String ALB;
	private String ALP;
	private String ALT;
	private String AST;
	private String CK;
	private String CK_MB;
	private String CRE;
	private String DBIL;
	private String GGT;
	@Column(length=50)
	public String getALB() {
		return ALB;
	}
	public void setALB(String aLB) {
		ALB = aLB;
	}
	@Column(length=50)
	public String getALP() {
		return ALP;
	}
	public void setALP(String aLP) {
		ALP = aLP;
	}
	@Column(length=50)
	public String getALT() {
		return ALT;
	}
	public void setALT(String aLT) {
		ALT = aLT;
	}
	@Column(length=50)
	public String getAST() {
		return AST;
	}
	public void setAST(String aST) {
		AST = aST;
	}
	@Column(length=50)
	public String getCK() {
		return CK;
	}
	public void setCK(String cK) {
		CK = cK;
	}
	@Column(length=50)
	public String getCK_MB() {
		return CK_MB;
	}
	public void setCK_MB(String cK_MB) {
		CK_MB = cK_MB;
	}
	@Column(length=50)
	public String getCRE() {
		return CRE;
	}
	public void setCRE(String cRE) {
		CRE = cRE;
	}
	@Column(length=50)
	public String getDBIL() {
		return DBIL;
	}
	public void setDBIL(String dBIL) {
		DBIL = dBIL;
	}
	@Column(length=50)
	public String getGGT() {
		return GGT;
	}
	public void setGGT(String gGT) {
		GGT = gGT;
	}
	@Column(length=50)
	public String getGLU() {
		return GLU;
	}
	public void setGLU(String gLU) {
		GLU = gLU;
	}
	@Column(length=50)
	public String getHBDH() {
		return HBDH;
	}
	public void setHBDH(String hBDH) {
		HBDH = hBDH;
	}
	@Column(length=50)
	public String getHDL_C() {
		return HDL_C;
	}
	public void setHDL_C(String hDL_C) {
		HDL_C = hDL_C;
	}
	@Column(length=50)
	public String getLDH() {
		return LDH;
	}
	public void setLDH(String lDH) {
		LDH = lDH;
	}
	@Column(length=50)
	public String getLDL_C() {
		return LDL_C;
	}
	public void setLDL_C(String lDL_C) {
		LDL_C = lDL_C;
	}
	@Column(length=50)
	public String getTBIL() {
		return TBIL;
	}
	public void setTBIL(String tBIL) {
		TBIL = tBIL;
	}
	@Column(length=50)
	public String getTC() {
		return TC;
	}
	public void setTC(String tC) {
		TC = tC;
	}
	@Column(length=50)
	public String getTG() {
		return TG;
	}
	public void setTG(String tG) {
		TG = tG;
	}
	@Column(length=50)
	public String getTP() {
		return TP;
	}
	public void setTP(String tP) {
		TP = tP;
	}
	@Column(length=50)
	public String getUA() {
		return UA;
	}
	public void setUA(String uA) {
		UA = uA;
	}
	@Column(length=50)
	public String getUREA() {
		return UREA;
	}
	public void setUREA(String uREA) {
		UREA = uREA;
	}
	@Column(length=50)
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	@Column(length=50)
	public Integer getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Integer printTime) {
		this.printTime = printTime;
	}
	@Column(length=50)
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	private String GLU;
	private String HBDH;
	private String HDL_C;
	private String LDH;
	private String LDL_C;
	private String TBIL;
	private String TC;
	private String TG;
	private String TP;
	private String UA;
	private String UREA;
	private String suggest;
	private Integer printTime;
	private Integer state;
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
	public String getBloodEnterId() {
		return bloodEnterId;
	}
	public void setBloodEnterId(String bloodResultId) {
		this.bloodEnterId = bloodResultId;
	}
	@Column(length=50)
	public String getBloodNumber() {
		return bloodNumber;
	}
	public void setBloodNumber(String bloodNumber) {
		this.bloodNumber = bloodNumber;
	}
}
