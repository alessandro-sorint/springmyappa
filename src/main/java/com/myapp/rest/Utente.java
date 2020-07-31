package com.myapp.rest;

import java.io.Serializable;

public class Utente implements Serializable{
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
