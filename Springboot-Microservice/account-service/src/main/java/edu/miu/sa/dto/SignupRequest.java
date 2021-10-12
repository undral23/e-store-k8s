package edu.miu.sa.dto;

import java.io.Serializable;

public class SignupRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String email;
	private String password;

	public SignupRequest() {
	}

	public SignupRequest(String username, String email, String password) {
		this.setUsername(username);
		this.setEmail(email);
		this.setPassword(password);
	}

	private void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
