package system.web.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_DataDictionary")
public class Sys_Base_DataDictionary implements Serializable {

	private String dataDicId;
	private String code;
	private String text;
	private String parent_DataDictionary;
	private int orderNum;
	private int state;
	private int version;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(length=50)
	/**
	 * 获取数据字典id
	 * @return
	 */
	public String getDataDicId() {
		return dataDicId;
	}
	 
	public void setDataDicId(String dataDicId) {
		this.dataDicId = dataDicId;
	}
	/**
	 * 获取数据字典文本
	 * @return
	 */
	@Column(length=50)
	public String getText() {
		return text;
	}
	 
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取数据字典code
	 * @return
	 */
	@Column(length=50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取父级数据字典 
	 * @return
	 */
	@Column(length=50)
	public String getParent_DataDictionary() {
		return parent_DataDictionary;
	}
	 
	public void setParent_DataDictionary(String parent_DataDictionary) {
		this.parent_DataDictionary = parent_DataDictionary;
	}
	/**
	 * 排序
	 * @return
	 */
	@Column
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取状态 
	 * @return
	 */
	@Column
	public int getState() {
		return state;
	}
	 
	public void setState(int state) {
		this.state = state;
	}

	public int getVersion() {
		return version;
	}

	@Version
	public void setVersion(int version) {
		this.version = version;
	}

	
	

}
