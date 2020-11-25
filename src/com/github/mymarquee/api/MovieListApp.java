package com.github.mymarquee.api;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.mymarquee.model.Movie;

public class MovieListApp {
	private ArrayList<Movie> movies;
	
	public MovieListApp() {
		this.movies = new ArrayList<>();
	}

	public void run() {

		getMovieInput();
		System.out.println("Your list is: ");
		System.out.println(movies);
	}

	public void getMovieInput() {
		System.out.println("How many movies to input?");
		Scanner scanner = new Scanner(System.in);
		int numMovies = Integer.parseInt(scanner.nextLine());
		
		for(int i=0;i<numMovies;i++) {
		
			System.out.println("Add movie title to movie list: ");
			String movieTitle = scanner.nextLine();
			Movie movie = new Movie(movieTitle);
			this.movies.add(movie);
		}
		scanner.close();	
	}
}
