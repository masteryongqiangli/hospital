package system.web.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "Sys_Base_Role_Menu")
public class Sys_Base_Role_Menu implements Serializable {
	private String id;
	private Sys_Base_Role role;
	private Sys_Base_Menu menu;
    private boolean addAuth;
    private boolean delAuth;
    private boolean updateAuth;
    private boolean lookAuth;
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

	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId"  )
	public Sys_Base_Role getRole() {
		return role;
	}

	public void setRole(Sys_Base_Role role) {
		this.role = role;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "menuId")
	public Sys_Base_Menu getMenu() {
		return menu;
	}

	public void setMenu(Sys_Base_Menu menu) {
		this.menu = menu;
	}
     @Column
	public boolean isAddAuth() {
		return addAuth;
	}

	public void setAddAuth(boolean addAuth) {
		this.addAuth = addAuth;
	}
	@Column
	public boolean isDelAuth() {
		return delAuth;
	}

	public void setDelAuth(boolean delAuth) {
		this.delAuth = delAuth;
	}
	@Column
	public boolean isUpdateAuth() {
		return updateAuth;
	}

	public void setUpdateAuth(boolean updateAuth) {
		this.updateAuth = updateAuth;
	}
	@Column
	public boolean isLookAuth() {
		return lookAuth;
	}

	public void setLookAuth(boolean lookAuth) {
		this.lookAuth = lookAuth;
	}

}
