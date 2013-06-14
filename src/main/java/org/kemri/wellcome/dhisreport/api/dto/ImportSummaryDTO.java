package org.kemri.wellcome.dhisreport.api.dto;

import java.io.Serializable;

public class ImportSummaryDTO implements Serializable {

	private static final long serialVersionUID = -888578840266941977L;
	
	private String id;
	private String report;
	private String date;
	private String status;
	private String complete;
	private String conflicts;
	private String count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getConflicts() {
		return conflicts;
	}
	public void setConflicts(String conflicts) {
		this.conflicts = conflicts;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	

}
