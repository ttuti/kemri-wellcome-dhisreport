package org.kemri.wellcome.dhisreport.api.model;

import java.util.ArrayList;

public class JqGridFilter {
	
	private String source;
	private String groupOp;
	private ArrayList<Rule> rules;

	public JqGridFilter() {
		super();
	}

	public JqGridFilter(String source) {
		super();
		this.setSource(source);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * Inner class containing field rules
	 */
	public static class Rule {
		
		private String junction;
		private String field;
		private String op;
		private String data;

		public Rule() {
		}

		public Rule(String junction, String field, String op, String data) {
			super();
			this.setJunction(junction);
			this.setField(field);
			this.setOp(op);
			this.setData(data);
		}

		public String getJunction() {
			return junction;
		}

		public void setJunction(String junction) {
			this.junction = junction;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}
	}

}
