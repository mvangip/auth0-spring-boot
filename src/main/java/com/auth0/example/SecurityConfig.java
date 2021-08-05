package com.auth0.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.example.controller.LogoutHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final LogoutHandler logoutHandler;

	public SecurityConfig(LogoutHandler logoutHandler) {
		this.logoutHandler = logoutHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and()
				.oauth2Login();
	}

}
