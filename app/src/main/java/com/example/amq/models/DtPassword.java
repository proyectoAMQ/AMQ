package com.example.amq.models;

public class DtPassword {
	
//	private String oldPassword;

    private  String token;

//    @ValidPassword
    private String newPassword;

	public DtPassword(String token, String newPassword) {
		this.token = token;
		this.newPassword = newPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
