package services;

import entities.Actor;
import entities.Cinema;
import entities.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public interface CustomerServiceInterface {
    public Cinema[] getAllCinemas();
    public Movie[] getAllMovies();
    public HashMap<Movie, LocalDateTime> getAllStreamingMovies();
    public Movie[] getMoviesFromCinema(Cinema c);
    public HashMap<Movie, LocalDateTime> getStreamingMoviesFromCinema(Cinema c);
    public Movie[] getAllMoviesByGenre(String genre);
    public HashMap<Movie, LocalDateTime> getAllStreamingMoviesByGenre(String genre);
    public Movie[] getMoviesByGenreFromCinema(Cinema c, String genre);
    public HashMap<Movie, LocalDateTime> getStreamingMoviesByGenreFromCinema(Cinema c, String genre);
    public Actor getDetailsAboutActor(String name);
    public Movie getDetailsAboutMovie(String title);
    public HashMap<Movie, LocalDateTime> getStreamingMoviesByDate(LocalDate date);
    public Movie[] getAllMoviesByActor(String name);
    public HashMap<Movie, LocalDateTime> getStreamingMoviesByActor(String name);
    public void buyTicket(Cinema c, Movie m);
}
