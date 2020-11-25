package com.github.mymarquee.model;

public class Movie {
	
	private String title;
	
	public String getTitle() {
		return this.title;
	}
	
	public Movie(String title) {
		this.title = title;
	}
	
	public String toString() {
		return this.title;
	}

}
