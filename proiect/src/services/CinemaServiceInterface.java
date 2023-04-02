package services;

import entities.Cinema;
import entities.Movie;

import java.time.LocalDateTime;

public interface CinemaServiceInterface {
    public Cinema[] getCinemas();
    public void addCinema(Cinema c);
    public void removeCinema(Cinema c);
    public void addMovie(Cinema c, Movie m);
    public void removeMovie(Cinema c, Movie m);
    public void streamMovie(Cinema c, Movie m, LocalDateTime date, int room, int price);
    public void stopStreaming(Cinema c, Movie m, LocalDateTime date);
    public void changePrice(Cinema c, Movie m, int price);
    public void changeRoom(Cinema c, Movie m, int room);
    public void changeDate(Cinema c, Movie m, LocalDateTime date);
}
