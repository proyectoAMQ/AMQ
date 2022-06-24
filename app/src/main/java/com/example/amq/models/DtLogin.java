package com.example.amq.models;

public class DtLogin {

	private String email;
	private String pass;
	private String pushToken;
	
	public DtLogin(String email, String pass, String pushToken) {
		super();
		this.email = email;
		this.pass = pass;
		this.pushToken = pushToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

}
