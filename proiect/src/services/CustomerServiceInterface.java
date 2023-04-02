package services;

import entities.Actor;
import entities.Cinema;
import entities.Movie;

import java.time.LocalDateTime;

public interface CustomerServiceInterface {
    public Cinema[] getAllCinemas();
    public Movie[] getAllMovies();
    public Movie[] getAllStreamingMovies();
    public Movie[] getMoviesFromCinema(Cinema c);
    public Movie[] getStreamingMoviesFromCinema(Cinema c);
    public Movie[] getAllMoviesByGenre(String genre);
    public Movie[] getAllStreamingMoviesByGenre(String genre);
    public Movie[] getMoviesByGenreFromCinema(Cinema c, String genre);
    public Movie[] getStreamingMoviesByGenreFromCinema(Cinema c, String genre);
    public Actor getDetailsAboutActor(String name);
    public Movie getDetailsAboutMovie(String title);
    public Movie[] getStreamingMoviesByDate(LocalDateTime date);
    public Movie[] getAllMoviesByActor(String name);
    public Movie[] getStreamingMoviesByActor(String name);
    public void buyTicket(Cinema c, Movie m);
}
