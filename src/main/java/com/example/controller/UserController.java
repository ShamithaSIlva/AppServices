package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.UserRepository;

@Controller
public class UserController
{
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/login")
	public String login( @PathVariable String username, @PathVariable String password )
	{
		//userRepository.findByUserNameAndPassword( username, password )
		return "login";
	}
}
