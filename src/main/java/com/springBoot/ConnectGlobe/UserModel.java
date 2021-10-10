package com.springBoot.ConnectGlobe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String fullname;
	private String email;
	private String password;
	private String mobileNumber;
	private String gender;
	private boolean active=true;
	private String roles="ROLE_USER";
	public UserModel(int userId, String fullname, String email, String password, String mobileNumber, String gender,
			boolean active,String roles) {
		super();
		this.userId = userId;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.active=active;
		this.roles = roles;
	}
	public UserModel() {
		super();
	}
	
	public UserModel(int id, String name, String password, String phone) {
		this.userId = id;
		this.fullname = name;
		
		this.password = password;
		this.mobileNumber = phone;
		
	}
	public UserModel(int qids) {
		this.userId=qids;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public int getUserId() {
		return userId;
	}
	
	
}
