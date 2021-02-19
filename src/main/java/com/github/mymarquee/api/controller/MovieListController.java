package com.github.mymarquee.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.mymarquee.api.model.entity.List;
import com.github.mymarquee.api.model.entity.ListMovie;
import com.github.mymarquee.api.model.json.ListJson;
import com.github.mymarquee.api.model.json.MovieJson;
import com.github.mymarquee.api.model.repository.ListRepository;

@RestController
@RequestMapping("/api/v1/movie-lists")
public class MovieListController {
	@Autowired
	private ListRepository listRepo;
	
	@GetMapping
	public java.util.List<ListJson> getLists() {
		java.util.List<ListJson> listsJson = new ArrayList<ListJson>();
		for (List list : this.listRepo.findAll()) {
			listsJson.add(new ListJson(list));
	    }
		return listsJson;
	}

	@PostMapping
	public String createList(@RequestBody ListJson listJson) {
		List list = this.listRepo.save(new List(listJson.getName()));
		return "List " + list.getName() + " created";
	}
  
	@DeleteMapping("/{listID}")
	public String deleteList(@PathVariable("listID") Long id) {
		if (!this.listRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Not Found");
		}
		this.listRepo.deleteById(id);
		return "List " + id + " deleted";
	}
  
	@GetMapping("/{listID}")
	public ListJson getList(@PathVariable("listID") Long id) {
		return new ListJson(getListByIdOrRespondNotFound(id));
	}
	
	@GetMapping("/{listID}/movies")
	public java.util.List<MovieJson> getMoviesOnList(@PathVariable("listID") Long listId) {
		java.util.List<MovieJson> moviesJson = new ArrayList<MovieJson>();
		for (ListMovie listMovie : getListByIdOrRespondNotFound(listId).getMovies()) {
			moviesJson.add(new MovieJson(listMovie));
		}
		return moviesJson;
	}
  
	@PostMapping("/{listID}/movies")
	public String addMovieToList(@PathVariable("listID") Long listId, @RequestBody MovieJson movieJson) {
		List list = getListByIdOrRespondNotFound(listId);
		ListMovie listMovie = new ListMovie(list, movieJson.getTmdbMovieId(), movieJson.getImdbMovieId());
		list.getMovies().add(listMovie);
		this.listRepo.save(list);
		return "Movie added to List " + listId;
	}
  
	@DeleteMapping("/{listID}/movies/{movieID}")
	public String removeMovieFromList(@PathVariable("listID") Long listId, @PathVariable("movieID") Long movieId) {
		List list = getListByIdOrRespondNotFound(listId);
		Optional<ListMovie> movie = list.getMovies().stream().filter(m -> m.getId().equals(movieId)).findFirst();
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