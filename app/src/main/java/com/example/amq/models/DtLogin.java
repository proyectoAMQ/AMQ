package com.example.amq.models;

public class DtLogin {
	private String email;
	private String pass;

	public DtLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DtLogin(String email, String pass) {
		this.email = email;
		this.pass = pass;
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
}
