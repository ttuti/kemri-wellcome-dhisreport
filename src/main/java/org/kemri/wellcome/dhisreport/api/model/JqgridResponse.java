package org.kemri.wellcome.dhisreport.api.model;

import java.io.Serializable;
import java.util.List;

public class JqgridResponse<T extends Serializable> {

	/**
	* Current page
	*/
	private String page;
	
	/**
	* Total pages
	*/
	private String total;
	
	/**
	* Total number of records
	*/
	private String records;
	
	/**
	* Contains the actual data
	*/
	private List<T> rows;
	
	public JqgridResponse() {}
	
	public JqgridResponse(String page, String total, String records,List<T> rows) {
		super();
		this.page = page;
		this.total = total;
		this.records = records;
		this.setRows(rows);
	}
	
	@Override
	public String toString() {		
		return "JqgridResponse [page=" + page + ", total=" + total+ ", records=" + records + "]";
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
}
