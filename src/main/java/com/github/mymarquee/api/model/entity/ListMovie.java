package com.github.mymarquee.api.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lists_movies")
public class ListMovie {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="related_list_id", nullable=false)
	private MyMarqueeList relatedList;
	
	private String imdbMovieId;
	private String tmdbMovieId;
	
	protected ListMovie() {}
	
	public ListMovie(MyMarqueeList relatedList, String tmdbMovieId, String imdbMovieId) {
		this.relatedList = relatedList;
		this.tmdbMovieId = tmdbMovieId;
		this.imdbMovieId = imdbMovieId;
	}
	
	public Long getId() {
		return this.id;
	}
	  
	public MyMarqueeList getRelatedList() {
		return this.relatedList;
	}
	
	public String getImdbMovieId() {
		return this.imdbMovieId;
	}
	
	public String getTmdbMovieId() {
		return this.tmdbMovieId;
	}
	
	public String toString() {
		return "ID: " + this.id + " IMDb ID: " + this.imdbMovieId + " TMDb ID: " + this.tmdbMovieId;
	}
}
