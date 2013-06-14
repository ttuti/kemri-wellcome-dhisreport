package org.kemri.wellcome.dhisreport.api.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;

/**
 * 
 * @author Timothy Tuti
 * 
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User extends AbstractPersistentEntity implements Identifiable
{
	
	private static final long serialVersionUID = -5421056767995701774L;
	public static final String TABLE_NAME = "users";
	
	@Column(name = "enabled")
	private Integer enabled = 1;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name="username", nullable=false, unique = true)
	private String username;
	
	@Column(name="uid", nullable=false, unique = true)
	private String uid;
	
	@Column(name = "password",length = 100, nullable = false)
	private String password;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "name", nullable=false, unique = true)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "authorities", 
			   joinColumns = { @JoinColumn(name = "username", referencedColumnName = "username") }, 
			   inverseJoinColumns = { @JoinColumn(name = "authority", referencedColumnName = "role_name") })
	private List<Role> roles;
	
	public User (){
		if(uid == null){
    		uid = UUID.randomUUID().toString();
    	}
    	if(name == null){
    		name = username;
    	}
	}
	
	/**
	 * @return the password of the user
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * 
	 * @param password to set for the current user
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}	
	
	/**
	 * 
	 * @return user status
	 */
	public Integer getEnabled()
	{
		return enabled;
	}
	
	/**
	 * 
	 * @param enabled
	 * 	user status to set
	 */
	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}
	
	/**
	 * 
	 * @return the email of the user
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * 
	 * @param email
	 * 	email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * 
	 * @return user's first name
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * 
	 * @param firstname
	 * 	user's first name to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * @return user's last name
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * 
	 * @param lastname
	 * 	the last name of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * 
	 * @return list of user roles
	 */
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * 
	 * @param roles
	 *  the roles of the user to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * 
	 * @return the username of the user - same as the email
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @param username 
	 *  the user name to set. Should be the same as the user email
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUid() {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		return uid;
	}

	public void setUid(String uid) {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		this.uid=uid;		
	}

	public String getName() {
		this.name=this.username;
		return name;
	}

	public void setName(String name) {		
		this.name=name;
	}	
}
