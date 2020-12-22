package com.github.mymarquee.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/movies")
public class MovieController {
	@PostMapping
	@ResponseBody
	public String createMovie() {
		return "Movie created";
	}
}
