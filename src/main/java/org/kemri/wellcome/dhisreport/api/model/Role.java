package org.kemri.wellcome.dhisreport.api.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author Timothy Tuti
 */
@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends AbstractPersistentEntity implements Identifiable{

	private static final long serialVersionUID = 8325896895395767553L;
	public static final String TABLE_NAME = "roles";
	
	@Column(name = "active")
	private int isActive=1;
	
	@Column(name="uid", nullable=false, unique = true)
	private String uid;
	
	@Column(name = "role_name", nullable = false, unique = true)
	private String roleName;
	
	@Column(name = "role_description", length = 16000, nullable = true)
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> users;
	
	@Column(name = "name",nullable=false, unique = true)
	private String name;
	
	public Role(){
		if(uid == null){
    		uid = UUID.randomUUID().toString();
    	}
    	if(name == null){
    		name = roleName;
    	}
	}
	
	/**
	 * 
	 * @return the name of the role
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * 
	 * @param roleName
	 * 	the name of the role
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/**
	 * 
	 * @return the description of the role
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description 
	 * 	the description of the role
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return list of all users with current role
	 */
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String getUid() {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		return uid;
	}

	@Override
	public void setUid(String uid) {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		this.uid=uid;		
	}

	@Override
	public String getName() {
		name = roleName;
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}
}
