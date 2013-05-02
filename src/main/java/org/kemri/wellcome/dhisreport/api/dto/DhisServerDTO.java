package org.kemri.wellcome.dhisreport.api.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DhisServerDTO {
	
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	@Pattern(regexp="^((ftp|http|https)://[\\w@.\\-\\_]+(:\\d{1,5})?(/[\\w#!:.?+=&%@!\\_\\-/]+)*){1}$")
	private String url;
	
	@NotNull
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
