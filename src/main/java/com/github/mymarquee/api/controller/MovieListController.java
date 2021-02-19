package com.github.mymarquee.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.github.mymarquee.api.model.entity.List;
import com.github.mymarquee.api.model.entity.ListMovie;
import com.github.mymarquee.api.model.repository.ListRepository;

@Controller
@RequestMapping("/api/v1/movie-lists")
public class MovieListController {
	private ListRepository listRepo;
	
	@Autowired
	public MovieListController(ListRepository listRepo) {
		this.listRepo = listRepo;
	}
	
	@GetMapping
	@ResponseBody
	public String getLists() {
		StringBuilder message = new StringBuilder();
		message.append("Lists:" + System.lineSeparator());
		for (List list : this.listRepo.findAll()) {
	        message.append(list.getName() + System.lineSeparator());
	    }
		return message.toString();
	}

	@PostMapping
	@ResponseBody
	public String createList() {
		List list = this.listRepo.save(new List("Test"));
		list.setName(list.getName().concat(list.getId().toString()));
		this.listRepo.save(list);
		return "List " + list.getName() + " created";
	}
  
	@DeleteMapping("/{listID}")
	@ResponseBody
	public String deleteList(@PathVariable("listID") Long id) {
		if (!this.listRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Not Found");
		}
		this.listRepo.deleteById(id);
		return "List " + id + " deleted";
	}
  
	@GetMapping("/{listID}")
	@ResponseBody
	public String getList(@PathVariable("listID") Long id) {
		return getListByIdOrRespondNotFound(id).getName();	
	}
	
	@GetMapping("/{listID}/movies")
	@ResponseBody
	public String getMoviesOnList(@PathVariable("listID") Long listId) {
		return getListByIdOrRespondNotFound(listId).getMovies().toString();
	}
  
	@PostMapping("/{listID}/movies/{movieID}")
	@ResponseBody
	public String addMovieToList(@PathVariable("listID") Long listId, @PathVariable("movieID") String movieId) {
		List list = getListByIdOrRespondNotFound(listId);
		ListMovie movie = new ListMovie(list, movieId, movieId);
		list.getMovies().add(movie);
		this.listRepo.save(list);
		return "Movie " + movieId + " added to List " + listId;
	}
  
	@DeleteMapping("/{listID}/movies/{movieID}")
	@ResponseBody
	public String removeMovieFromList(@PathVariable("listID") Long listId, @PathVariable("movieID") String movieId) {
		List list = getListByIdOrRespondNotFound(listId);
		Optional<ListMovie> movie = list.getMovies().stream().filter(m -> m.getTmdbMovieId().equals(movieId)).findFirst();
		if (movie.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Not Found");
		}
		list.getMovies().remove(movie.get());
		this.listRepo.save(list);
		return "Movie " + movieId + " removed from List " + listId;
	}
	
	private List getListByIdOrRespondNotFound(Long id) {
		Optional<List> list = this.listRepo.findById(id);
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Not Found");
		}
		return list.get();
	}
}