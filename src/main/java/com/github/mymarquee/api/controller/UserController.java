package com.github.mymarquee.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {
	@PostMapping
	@ResponseBody
	public String createUser() {
		return "User created";
	}
  
	@DeleteMapping("/{userID}")
	@ResponseBody
	public String deleteUser(@PathVariable("userID") String id) {
		return "User " + id + " deleted";
	}
}
