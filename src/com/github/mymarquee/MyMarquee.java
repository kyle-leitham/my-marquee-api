package com.github.mymarquee;

import com.github.mymarquee.model.Movie;

public class MyMarquee {

	public static void main(String[] args) {
		Movie myMovie = new Movie("My Movie");
		System.out.println(myMovie.getTitle());

	}

}
