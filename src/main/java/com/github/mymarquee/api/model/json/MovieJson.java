package com.github.mymarquee.api.model.json;

import org.springframework.hateoas.RepresentationModel;

import com.github.mymarquee.api.model.entity.ListMovie;
import com.github.tmdb.api.response.json.TmdbMovieJson;

public class MovieJson extends RepresentationModel<MovieJson> {
	private Long id;
	private TmdbMovieJson movieData;
	
	protected MovieJson() {}
	
	public MovieJson(ListMovie listMovie, TmdbMovieJson movieData) {
		this.id = listMovie.getId();
		this.movieData = movieData;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public TmdbMovieJson getMovieData() {
		return this.movieData;
	}
	
	public void setMovieDate(TmdbMovieJson movieData) {
		this.movieData = movieData;
	}
}
