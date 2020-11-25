package com.github.mymarquee.api;

import com.github.mymarquee.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MyMarqueeApi {

	public static void main(String[] args) {
		Movie myMovie = new Movie("My Movie");
		Movie myMovie1 = new Movie("My Movie1");
		Movie myMovie2 = new Movie("My Movie2");
		
		List<Movie> movies = new ArrayList<>();
		
		movies.add(myMovie);
		movies.add(myMovie1);
		movies.add(myMovie2);
		
		System.out.println(movies);

	}

}
