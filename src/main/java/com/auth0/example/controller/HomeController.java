package com.auth0.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth0.example.model.Auth0Client;
import com.auth0.example.service.ApiService;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	ApiService apiService;

	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal OidcUser principal) throws Exception {
		if (principal != null) {
			model.addAttribute("profile", principal.getClaims());
			Map<String, String> auth0ClientIdNameMap = apiService.getAuth0ClientIdNameMap();
			model.addAttribute("clientIdNameMap", auth0ClientIdNameMap);
			model.addAttribute("auth0Client", new Auth0Client());
		}
		return "index";
	}
}