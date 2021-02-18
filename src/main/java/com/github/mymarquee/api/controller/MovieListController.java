package com.github.mymarquee.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/movie-lists")
public class MovieListController {
	
	@GetMapping("/")
	@ResponseBody
	public String getLists() {
		return "Lists";
	}

	@PostMapping("/")
	@ResponseBody
	public String createList() {
		return "List created";
	}
  
	@DeleteMapping("/{listId}")
	@ResponseBody
	public String deleteList(@PathVariable("listId") String id) {
		return "List " + id + " deleted";
	}
  
	@GetMapping("/{listId}")
	@ResponseBody
	public String getList(@PathVariable("listId") String id) {
		return "List " + id;
	}
  
	@PostMapping("/{listId}/movies/{movieId}")
	@ResponseBody
	public String addMovieToList(@PathVariable("listId") String listId, @PathVariable("movieId") String movieId) {
		return "Movie " + movieId + " added to List " + listId;
	}
  
	@DeleteMapping("/{listId}/movies/{movieId}")
	@ResponseBody
	public String removeMovieFromList(@PathVariable("listId") String listId, @PathVariable("movieId") String movieId) {
		return "Movie " + movieId + " removed from List " + listId;
	}
}