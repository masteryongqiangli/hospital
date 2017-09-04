package system.web.entity.base;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_Menu")
public class Sys_Base_Menu implements Serializable {

	private String menuId;
	private String menuName;
	private String menuUrl;
	private int orderNum;
	//private Sys_Base_Icon icon;
	private Sys_Base_Menu parent_menu;
	private int state;
	private int version;

	/**
	 * 获取菜单id
	 * 
	 * @return
	 */
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(length=50)

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取菜单名称
	 * 
	 * @return
	 */
	@Column(length=50)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 获取菜单路径
	 * 
	 * @return
	 */
	@Column(length=50)
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	@Column
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	@Column
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	 
	 

	/**
	 * 获取父级菜单
	 * 
	 * @return
	 */
	@OneToOne
	public Sys_Base_Menu getParent_menu() {
		return parent_menu;
	}

	public void setParent_menu(Sys_Base_Menu parent_menu) {
		this.parent_menu = parent_menu;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
}
