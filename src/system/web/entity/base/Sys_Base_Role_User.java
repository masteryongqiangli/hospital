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
@Table(name = "Sys_Base_Role_User")
public class Sys_Base_Role_User implements Serializable {
	private String id;
	private Sys_Base_User baseuser;
	private Sys_Base_Role role;

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
	@JoinColumn(name = "userId")
	public Sys_Base_User getBaseuser() {
		return baseuser;
	}

	public void setBaseuser(Sys_Base_User baseuser) {
		this.baseuser = baseuser;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId")
	public Sys_Base_Role getRole() {
		return role;
	}

	public void setRole(Sys_Base_Role role) {
		this.role = role;
	}

}
