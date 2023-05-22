package services;

import entities.Actor;
import entities.Cinema;
import entities.Movie;

import java.time.LocalDateTime;

public interface CinemaServiceInterface {
    public Cinema[] getCinemas();
    public Movie[] getMovies();
    public Actor[] getActors();
    public void addCinema(Cinema c);
    public void removeCinema(Cinema c);
    public void addMovie(Movie m);
    public void removeMovie(Movie m);
    public void addActor(Actor a);
    public void removeActor(Actor a);
    public void addMovieToCinema(Cinema c, Movie m);
    public void removeMovieFromCinema(Cinema c, Movie m);
    public void streamMovie(Cinema c, Movie m, LocalDateTime date, int room, int price);
    public void stopStreaming(Cinema c, Movie m, LocalDateTime date);
    public void changePrice(Cinema c, Movie m, int price);
    public void changeRoom(Cinema c, Movie m, int room);
    public void changeDate(Cinema c, Movie m, LocalDateTime date);
}
