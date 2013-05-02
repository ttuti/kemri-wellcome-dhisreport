package org.kemri.wellcome.dhisreport.api.dto;

public class ReportExecute {
	
	private Integer reportDefinationId;	
	private String date;	
	private String location;
	private String reportDefinitionName;
	private String frequency;
	
	public Integer getReportDefinationId() {
		return reportDefinationId;
	}
	public void setReportDefinationId(Integer reportDefinationId) {
		this.reportDefinationId = reportDefinationId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReportDefinitionName() {
		return reportDefinitionName;
	}
	public void setReportDefinitionName(String reportDefinitionName) {
		this.reportDefinitionName = reportDefinitionName;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
}
