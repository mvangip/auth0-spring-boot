package com.auth0.example;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.example.model.Auth0Client;
import com.auth0.example.service.ApiService;

@Controller
public class Auth0RulesController {

	@Autowired
	ApiService apiService;

	@GetMapping("/clients")
	public String getauth0Clients(HttpServletResponse response, final Authentication authentication, Model model)
			throws Exception {

		Map<String, String> auth0ClientIdNameMap = apiService.getAuth0ClientIdNameMap();
		model.addAttribute("clientIdNameMap", auth0ClientIdNameMap);
		model.addAttribute("auth0Client", new Auth0Client());
		return "clients";
	}

	@GetMapping(value = "/allRules")
	@ResponseBody
	public ResponseEntity<String> allRules(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseEntity<String> result = apiService.getCall("https://dev-qai1ga3a.us.auth0.com/api/v2/rules");
		return result;
	}

	@GetMapping("/rules")
	public String rules(HttpServletResponse response, final Authentication authentication, Model model,
			@RequestParam String clientInfo) throws Exception {
		String[] token = clientInfo.split("~");
		Auth0Client auth0Client = new Auth0Client(token[1], token[0]);
		List<String> rules = this.apiService.getRulesByClient(auth0Client);
		model.addAttribute("rules", rules);
		model.addAttribute("clientName", token[0]);
		return "rules";
	}

}
