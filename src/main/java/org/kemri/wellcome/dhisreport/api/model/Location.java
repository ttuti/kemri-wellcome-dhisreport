package org.kemri.wellcome.dhisreport.api.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name=Location.TABLE_NAME)
public class Location implements Serializable, Identifiable {

	private static final long serialVersionUID = 6896560388542527847L;

	public static final String TABLE_NAME ="location";
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name="code",unique=true,nullable=false)
	protected String code;
	
	@Column(name="name",unique=true,nullable=false)
	protected String name;
	
	@Column(name="uid") 
	protected String uid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		return uid;
	}

	public void setUid(String uid) {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		this.uid = uid;
	}
}
