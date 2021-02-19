package com.github.mymarquee.api.model.json;

import com.github.mymarquee.api.model.entity.ListMovie;

public class MovieJson {
	private Long id;
	private String tmdbMovieId;
	private String imdbMovieId;
	
	protected MovieJson() {}
	
	public MovieJson(ListMovie listMovie) {
		this.id = listMovie.getId();
		this.tmdbMovieId = listMovie.getTmdbMovieId();
		this.imdbMovieId = listMovie.getImdbMovieId();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTmdbMovieId() {
		return this.tmdbMovieId;
	}
	
	public void setTmdbMovieId(String tmdbMovieId) {
		this.tmdbMovieId = tmdbMovieId;
	}
	
	public String getImdbMovieId() {
		return this.imdbMovieId;
	}
	
	public void setImdbMovieId(String imdbMovieId) {
		this.imdbMovieId = imdbMovieId;
	}
}
