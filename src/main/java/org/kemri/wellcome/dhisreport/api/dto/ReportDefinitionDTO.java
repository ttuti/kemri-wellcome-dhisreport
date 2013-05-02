package org.kemri.wellcome.dhisreport.api.dto;

import java.io.Serializable;

public class ReportDefinitionDTO implements Serializable {
	
	private static final long serialVersionUID = 3232346236180343538L;

	private String id;

    private String name;

    private String uid;

    private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    

}
