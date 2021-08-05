package com.auth0.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.mvclogin")
public class ApplicationProperties {

	private String managementAPIClientId;
	private String managementAPIClientSecret;
	private String managementAPIGrantType;
	private String auth0Domain;

	public String getManagementAPIClientId() {
		return managementAPIClientId;
	}

	public void setManagementAPIClientId(String managementAPIClientId) {
		this.managementAPIClientId = managementAPIClientId;
	}

	public String getManagementAPIClientSecret() {
		return managementAPIClientSecret;
	}

	public void setManagementAPIClientSecret(String managementAPIClientSecret) {
		this.managementAPIClientSecret = managementAPIClientSecret;
	}

	public String getManagementAPIGrantType() {
		return managementAPIGrantType;
	}

	public void setManagementAPIGrantType(String managementAPIGrantType) {
		this.managementAPIGrantType = managementAPIGrantType;
	}

	public String getAuth0Domain() {
		return auth0Domain;
	}

	public void setAuth0Domain(String auth0Domain) {
		this.auth0Domain = auth0Domain;
	}

}
