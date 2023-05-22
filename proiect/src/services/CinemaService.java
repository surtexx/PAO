package services;

import data_types.Gender;
import entities.*;
import persistence.CinemaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CinemaService implements CinemaServiceInterface{

    private final CinemaRepository cinemaRepository;
    private static CinemaService instance;
    private CinemaService(){
        cinemaRepository = new CinemaRepository();
    }
    public static CinemaService getInstance(){
        if(instance == null){
            instance = new CinemaService();
        }
        return instance;
    }
    public Cinema[] getCinemas() {
        return cinemaRepository.getCinemas();
    }

    public Movie[] getMovies() {
        return cinemaRepository.getMovies();
    }

    public Actor[] getActors() {
        return cinemaRepository.getActors();
    }

    public void addCinema(Cinema c) {
        cinemaRepository.addCinema(c);
    }

    public void removeCinema(Cinema c) {
        cinemaRepository.removeCinema(c);
    }

    public void addMovie(Movie m) {
        cinemaRepository.addMovie(m);
    }

    public void removeMovie(Movie m) {
        cinemaRepository.removeMovie(m);
    }

    public void addActor(Actor a) {
        cinemaRepository.addActor(a);
    }

    public void removeActor(Actor a) {
        cinemaRepository.removeActor(a);
    }

    public void addMovieToCinema(Cinema c, Movie m) {
        cinemaRepository.addMovieToCinema(c, m);
    }

    public void removeMovieFromCinema(Cinema c, Movie m) {
        cinemaRepository.removeMovieFromCinema(c, m);
    }

    public void streamMovie(Cinema c, Movie m, LocalDateTime date, int room, int price) {
        cinemaRepository.streamMovie(c, m, date, room, price);
    }

    public void stopStreaming(Cinema c, Movie m, LocalDateTime date) {
        cinemaRepository.stopStreaming(c, m, date);
    }

    public void changePrice(Cinema c, Movie m, int price) {
        cinemaRepository.changePrice(c, m, price);
    }

    public void changeRoom(Cinema c, Movie m, int room) {
        cinemaRepository.changeRoom(c, m, room);
    }

    public void changeDate(Cinema c, Movie m, LocalDateTime date) {
        cinemaRepository.changeDate(c, m, date);
    }
}
