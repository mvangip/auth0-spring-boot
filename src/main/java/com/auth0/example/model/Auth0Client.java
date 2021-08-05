package com.auth0.example.model;

public class Auth0Client {

	private String clientId;
	private String clientName;

	public Auth0Client() {
	}

	public Auth0Client(String clientId, String clientName) {
		this.clientId = clientId;
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
