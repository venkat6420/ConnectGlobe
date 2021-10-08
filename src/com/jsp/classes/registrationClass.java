package com.jsp.classes;

public class registrationClass {

	private int id;
	private String fullname;
	private String email;
	private String mobile;
	private String gender;
	private String roles;
	public registrationClass(int id,String fullname, String email, String mobile, String gender, String roles) {
		super();
		this.id=id;
		this.fullname = fullname;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.roles = roles;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public registrationClass() {
		super();
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "registrationClass [fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ", gender="
				+ gender + ", roles=" + roles + "]";
	}
	
}
