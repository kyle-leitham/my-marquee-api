package com.github.mymarquee.api;

import java.util.ArrayList;
import java.util.Scanner;

import com.github.mymarquee.model.Movie;

public class MovieListApp {
	private ArrayList<Movie> movies;
	private Scanner scanner;
	
	public MovieListApp() {
		this.movies = new ArrayList<>();
		this.scanner = new Scanner(System.in);
	}

	public void run() {
		runMenu();
	}
	
	private void runMenu() {	
		String menuOption = "0";
		
		while(!menuOption.equals("4")) {
			System.out.println("\nPlease select option: "
					+ "\n1 to Add a Movie"
					+ "\n2 to Delete a Movie"
					+ "\n3 to Show List"
					+ "\n4 to Exit \n");
			menuOption = this.scanner.nextLine();	
			
			switch(menuOption) {
				case "1":
					getMovieInput();
					break;
				case "2":
					deleteMovie();
					break;
				case "3":
					System.out.println("Your list is: ");
					System.out.println(this.movies);
					break;
				case "4":
					break;
				default:
					System.out.println("Inviald input");
			}
		}

	}
	

	private void getMovieInput() {
		System.out.println("Add movie title to movie list: ");
		String movieTitle = this.scanner.nextLine();
		Movie movie = new Movie(movieTitle);
		this.movies.add(movie);
	}
	private void deleteMovie() {
		System.out.println("Current list is: ");
		for(int i=0; i < movies.size(); i++) {
			System.out.println(i+1 +" "+movies.get(i));
		}
		
		System.out.println("Which number movie would you like to delete?");
		int indexToDelete = Integer.parseInt(this.scanner.nextLine());
		movies.remove(indexToDelete -1);
		
	}

}
