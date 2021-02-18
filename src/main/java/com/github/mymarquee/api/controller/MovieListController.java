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
	
	@GetMapping
	@ResponseBody
	public String getLists() {
		return "Lists";
	}

	@PostMapping
	@ResponseBody
	public String createList() {
		return "List created";
	}
  
	@DeleteMapping("/{listID}")
	@ResponseBody
	public String deleteList(@PathVariable("listID") String id) {
		return "List " + id + " deleted";
	}
  
	@GetMapping("/{listID}")
	@ResponseBody
	public String getList(@PathVariable("listID") String id) {
		return "List " + id;
	}
  
	@PostMapping("/{listID}/movies/{movieID}")
	@ResponseBody
	public String addMovieToList(@PathVariable("listID") String listId, @PathVariable("movieID") String movieId) {
		return "Movie " + movieId + " added to List " + listId;
	}
  
	@DeleteMapping("/{listID}/movies/{movieID}")
	@ResponseBody
	public String removeMovieFromList(@PathVariable("listID") String listId, @PathVariable("movieID") String movieId) {
		return "Movie " + movieId + " removed from List " + listId;
	}
}