package com.revature.models;

//Class for converting HTTP login requests into an object
public class LoginTemplate {
	
	String username;
	String password;
	
	public LoginTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginTemplate(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginTemplate [username=" + username + ", password=" + password + "]";
	}
	
	
}
