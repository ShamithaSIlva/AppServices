package com.example.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableResourceServer
@RestController
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
{
	  @RequestMapping("/tasks")
	  public String listAllTasks() {
	    return "Todo list";
	  }
	  
	  @Override
		public void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**").permitAll();
			http.requestMatchers().antMatchers("/tasks")
			.and().authorizeRequests()
			.antMatchers("/tasks").access("hasRole('USER')");
		}  
}

