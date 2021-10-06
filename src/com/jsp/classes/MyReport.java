package com.jsp.classes;

public class MyReport {

	private int rId;
	private int userId;
	private String issue;
	public MyReport(int rId, int userId, String issue) {
		super();
		this.rId = rId;
		this.userId = userId;
		this.issue = issue;
	}
	public MyReport() {
		super();
	}
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	
}
