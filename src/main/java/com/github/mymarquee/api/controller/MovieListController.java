package com.github.mymarquee.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.mymarquee.api.model.entity.MyMarqueeList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mymarquee.api.model.entity.ListMovie;
import com.github.mymarquee.api.model.json.ListJson;
import com.github.mymarquee.api.model.json.MovieJson;
import com.github.mymarquee.api.model.repository.ListRepository;
import com.github.tmdb.api.response.json.TmdbMovieJson;

@RestController
@RequestMapping("/api/v1/movie-lists")
public class MovieListController {
	@Autowired
	private ListRepository listRepo;
	
	@GetMapping
	public List<ListJson> getLists() {
		List<ListJson> listsJson = new ArrayList<ListJson>();
		for (MyMarqueeList list : this.listRepo.findAll()) {
			ListJson listJson = new ListJson(list);
			listJson.add(linkTo(methodOn(MovieListController.class).getList(list.getId())).withSelfRel());
			listsJson.add(listJson);
	    }
		return listsJson;
	}

	@PostMapping
	public String createList(@RequestBody ListJson listJson) {
		MyMarqueeList list = this.listRepo.save(new MyMarqueeList(listJson.getName()));
		return "List " + list.getName() + " created"; //Return link to created resource
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
		ListJson listJson = new ListJson(getListByIdOrRespondNotFound(id));
		listJson.add(linkTo(methodOn(MovieListController.class).getList(id)).withSelfRel());
		listJson.add(linkTo(methodOn(MovieListController.class).getMoviesOnList(id)).withRel("movies"));
		return listJson;
	}
	
	@GetMapping("/{listID}/movies")
	public List<MovieJson> getMoviesOnList(@PathVariable("listID") Long listId) {
		List<MovieJson> moviesJson = new ArrayList<MovieJson>();
		for (ListMovie listMovie : getListByIdOrRespondNotFound(listId).getMovies()) {
			final HttpClient httpClient = HttpClientBuilder.create().build();
			String url = "https://api.themoviedb.org/3/movie/" + listMovie.getTmdbMovieId() + "?api_key=";
			final HttpGet httpGet = new HttpGet(url);
			HttpResponse response = null;
			try {
			    response = httpClient.execute(httpGet);
			} catch (IOException ex) {
				System.out.println(ex);
			}
			BufferedReader reader = null;
			try {
			    reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			} catch (IOException ex) {
				System.out.println(ex);
			}
			String line = "";
		    try {
		    	line = reader.readLine();
		    } catch (IOException ex) {
		    	System.out.println(ex);
		    }
		    System.out.println(line);
			ObjectMapper mapper = new ObjectMapper();
			TmdbMovieJson json = new TmdbMovieJson();
			try {
				json = mapper.readValue(line, TmdbMovieJson.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			MovieJson movieJson = new MovieJson(listMovie, json);
			moviesJson.add(movieJson);
		}
		return moviesJson;
	}
  
	@PostMapping("/{listID}/movies")
	public String addMovieToList(@PathVariable("listID") Long listId, @RequestBody MovieJson movieJson) {
		MyMarqueeList list = getListByIdOrRespondNotFound(listId);
		ListMovie listMovie = new ListMovie(list, String.valueOf((movieJson.getMovieData().id)), movieJson.getMovieData().imdb_id);
		list.getMovies().add(listMovie);
		this.listRepo.save(list);
		return "Movie added to List " + listId; //Return link to created resource
	}
  
	@DeleteMapping("/{listID}/movies/{movieID}")
	public String removeMovieFromList(@PathVariable("listID") Long listId, @PathVariable("movieID") Long movieId) {
		MyMarqueeList list = getListByIdOrRespondNotFound(listId);
		Optional<ListMovie> movie = list.getMovies().stream().filter(m -> m.getId().equals(movieId)).findFirst();
		if (movie.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Not Found");
		}
		list.getMovies().remove(movie.get());
		this.listRepo.save(list);
		return "Movie " + movieId + " removed from List " + listId;
	}
	
	private MyMarqueeList getListByIdOrRespondNotFound(Long id) {
		Optional<MyMarqueeList> list = this.listRepo.findById(id);
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Not Found");
		}
		return list.get();
	}
}