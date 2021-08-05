package com.auth0.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.example.model.Auth0Client;

@Service
public class ApiService {

	public ResponseEntity<String> getCall(String url) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getManagementApiToken());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return result;
	}

	public ResponseEntity<String> postCall(String url, String requestBody) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getManagementApiToken());

		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result;
	}

	public String getManagementApiToken() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject requestBody = new JSONObject();
		requestBody.put("client_id", "gpk65BOFnOmOFT02x23jeqUUrM0PJE2b");
		requestBody.put("client_secret", "MzbskEBq0qkZwEwwqO7UIbEEumJYK686Qv7SihLwd_OjFPuJz8FoD9jE0DTu-H74");
		requestBody.put("audience", "https://dev-qai1ga3a.us.auth0.com/api/v2/");
		requestBody.put("grant_type", "client_credentials");

		HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

		RestTemplate restTemplate = new RestTemplate();
		HashMap<String, String> result = restTemplate.postForObject("https://dev-qai1ga3a.us.auth0.com/oauth/token",
				request, HashMap.class);
		System.out.println();
		return result.get("access_token");
	}

	public Map<String, String> getAuth0ClientIdNameMap() throws Exception {
		ResponseEntity<String> result = getCall("https://dev-qai1ga3a.us.auth0.com/api/v2/clients");
		Map<String, String> clientIdNameMap = new HashMap<String, String>();
		JSONArray array = new JSONArray(result.getBody());
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			clientIdNameMap.put(object.getString("name"), object.getString("client_id"));
			System.out.println("Name = " + object.getString("name"));
			System.out.println("Client ID = " + object.getString("client_id"));
		}
		return clientIdNameMap;
	}

	public List<String> getRulesByClient(Auth0Client auth0Client) throws Exception {
		ResponseEntity<String> result = getCall("https://dev-qai1ga3a.us.auth0.com/api/v2/rules");
		JSONArray array = new JSONArray(result.getBody());
		List<String> ruleNamesForClient = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String clientName = getRegexString(object.getString("script"),
					"if \\(context.clientName === '(.*)'\\) \\{");
			String clientId = getRegexString(object.getString("script"), "CLIENTS_ENABLED = \\[(.*)\\];");
			if ((clientName != null && clientName.toLowerCase().contains(auth0Client.getClientName().toLowerCase()))
					|| (clientId != null && clientId.contains(auth0Client.getClientId()))) {
				ruleNamesForClient.add(object.getString("name"));
			} else if (clientName == null && clientId == null) { /* Rule at domain level */
				ruleNamesForClient.add(object.getString("name"));
			}
		}

		return ruleNamesForClient;
	}

	private String getRegexString(String source, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(source);
		return (m.find()) ? m.group(1).trim() : null;
	}
}
