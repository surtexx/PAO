package services;

import data_types.Gender;
import entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CinemaService implements CinemaServiceInterface{
    private Cinema[] cinemas;
    private static CinemaService instance;
    private CinemaService(){
        Actor the_rock = new Actor("Dwayne", "Johnson", 42, Gender.M, 5);
        Actor tom_cruise = new Actor("Tom", "Cruise", 51, Gender.M, 3);
        Actor scarlett_johansson = new Actor("Scarlett", "Johansson", 37, Gender.F, 1);
        Actor charlie_cox = new Actor("Charlie", "Cox", 41, Gender.M, 100);
        Actor[] actors1 = {tom_cruise, the_rock};
        Actor[] actors2 = {scarlett_johansson, charlie_cox};
        Actor[] actors3 = {the_rock, scarlett_johansson};
        Actor[] actors4 = {tom_cruise, charlie_cox};
        Actor[] actors5 = {the_rock, scarlett_johansson, charlie_cox};
        Actor[] actors6 = {tom_cruise, scarlett_johansson, charlie_cox};
        Movie m1 = new Action_Movie("Movie 1", "James Gunn", 2019, 105, actors1, 7.2, 1, 16);
        Movie m2 = new Action_Movie("Movie 2", "Sam Raimi", 2016, 121, actors2, 8.1, 2, 12);
        Movie m3 = new Thriller_Movie("Movie 3", "Christofer Nolan", 2021, 116, actors3, 8.7, 5, new String[]{"Drama", "Action", "Horror"}, 18);
        Movie m4 = new Comedy_Movie("Movie 4", "James Gunn", 2012, 109, actors4, 6.8, 0, false, false);
        Movie m5 = new Romance_Movie("Movie 5", "James Gunn", 2019, 105, actors1, 7.2, 1, true);
        Movie m6 = new Other_Movie("Movie 6", "James Gunn", 2019, 105, actors1, 7.2, 1, new String[]{"SF", "Cyberpunk"}, 18);
        Movie[] movies1 = {m1, m2, m3, m4};
        Movie[] movies2 = {m5, m6};
        Cinema c1 = new Cinema("Cinema City", movies1, 3, new int[]{60, 75, 60});
        Cinema c2 = new Cinema("Happy Cinema", movies2, 4, new int[]{80, 55, 70, 76});
        cinemas = new Cinema[]{c1, c2};
    }
    public static CinemaService getInstance(){
        if(instance == null){
            instance = new CinemaService();
        }
        return instance;
    }
    public Cinema[] getCinemas() {
        return cinemas;
    }

    public void addCinema(Cinema c) {
        if(cinemas == null){
            cinemas = new Cinema[1];
            cinemas[0] = c;
            System.out.println("Cinema added");
            return;
        }
        if(Arrays.asList(cinemas).contains(c)){
            System.out.println("Cinema already exists");
            return;
        }
        Cinema[] newCinemas = new Cinema[cinemas.length + 1];
        System.arraycopy(cinemas, 0, newCinemas, 0, cinemas.length);
        newCinemas[newCinemas.length - 1] = c;
        cinemas = newCinemas;
        System.out.println("Cinema added");
    }

    public void removeCinema(Cinema c) {
        if(!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        Cinema[] newCinemas = new Cinema[cinemas.length - 1];
        int i = 0;
        for(Cinema cinema: cinemas){
            if(cinema != c){
                newCinemas[i] = cinema;
                i++;
            }
        }
        cinemas = newCinemas;
        System.out.println("Cinema removed");
    }

    public void addMovie(Cinema c, Movie m) {
        if(!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        if(Arrays.asList(c.getListed_movies()).contains(m)){
            System.out.println("Movie already exists");
            return;
        }
        Movie[] newMovies = new Movie[c.getListed_movies().length + 1];
        System.arraycopy(c.getListed_movies(), 0, newMovies, 0, c.getListed_movies().length);
        newMovies[newMovies.length - 1] = m;
        c.setListed_movies(newMovies);
        System.out.println("Movie added");
    }

    public void removeMovie(Cinema c, Movie m){
        if(!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        if(!Arrays.asList(c.getListed_movies()).contains(m)) {
            System.out.println("Movie doesn't exist");
            return;
        }
        Movie[] newMovies = new Movie[c.getListed_movies().length - 1];
        int i = 0;
        for(Movie movie: c.getListed_movies()){
            if(movie != m){
                newMovies[i] = movie;
                i++;
            }
        }
        c.setListed_movies(newMovies);
        System.out.println("Movie removed");
    }

    public void streamMovie(Cinema c, Movie m, LocalDateTime date, int room, int price) {
        if(!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        Movie m_test = null;
        for(Movie movie: c.getListed_movies()){
            if(movie.equals(m)){
                m_test = movie;
                break;
            }
        }
        if(m_test == null){
            System.out.println("Movie doesn't exist");
            return;
        }
        for(Movie movie : c.getStreaming_dates().keySet())
            if(c.getRoom_numbers().get(movie) == room &&
                    (c.getStreaming_dates().get(movie).isBefore(date) &&
                            c.getStreaming_dates().get(movie).plusMinutes(movie.getDuration()).
                                    isAfter(date) ||
                            c.getStreaming_dates().get(movie).isAfter(date) &&
                                    c.getStreaming_dates().get(movie).isBefore(date.
                                            plusMinutes(m.getDuration())))){
                System.out.println("Room already taken in that date and time");
                return;
            }
        HashMap <Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
        HashMap <Movie, Integer> streaming_rooms = c.getRoom_numbers();
        HashMap <Movie, Integer> streaming_prices = c.getPrices();
        HashMap <Movie, Integer> seats_available = c.getSeats_available();
        int[] nr_seats = c.getNr_seats();
        Movie new_m;
        if(m instanceof Thriller_Movie)
            new_m = new Thriller_Movie((Thriller_Movie) m);
        else if(m instanceof Action_Movie)
            new_m = new Action_Movie((Action_Movie) m);
        else if(m instanceof Comedy_Movie)
            new_m = new Comedy_Movie((Comedy_Movie)m);
        else if(m instanceof Romance_Movie)
            new_m = new Romance_Movie((Romance_Movie)m);
        else
            new_m = new Other_Movie((Other_Movie)m);
        streaming_dates.put(new_m, date);
        streaming_rooms.put(new_m, room-1);
        streaming_prices.put(new_m, price);
        seats_available.put(new_m, nr_seats[room-1]);
        c.setStreaming_dates(streaming_dates);
        c.setRoom_numbers(streaming_rooms);
        c.setPrices(streaming_prices);
        c.setSeats_available(seats_available);
        System.out.println("Movie streamed");
    }

    public void stopStreaming(Cinema c, Movie m, LocalDateTime date) {
        if (!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        Movie m_test = null;
        for(Movie movie: c.getListed_movies()){
            if(movie.equals(m)){
                m_test = movie;
                break;
            }
        }
        if(m_test == null){
            System.out.println("Movie doesn't exist");
            return;
        }
        if(!c.getStreaming_dates().containsKey(m)){
            System.out.println("Movie not streamed");
            return;
        }
        HashMap <Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
        HashMap <Movie, Integer> streaming_rooms = c.getRoom_numbers();
        HashMap <Movie, Integer> streaming_prices = c.getPrices();
        HashMap <Movie, Integer> seats_available = c.getSeats_available();
        streaming_dates.remove(m);
        streaming_rooms.remove(m);
        streaming_prices.remove(m);
        seats_available.remove(m);
        c.setStreaming_dates(streaming_dates);
        c.setRoom_numbers(streaming_rooms);
        c.setPrices(streaming_prices);
        c.setSeats_available(seats_available);
        System.out.println("Movie stopped streaming");
    }

    public void changePrice(Cinema c, Movie m, int price) {
        if(!Arrays.asList(cinemas).contains(c)) {
            System.out.println("Cinema doesn't exist");
            return;
        }
        Movie m_test = null;
        for(Movie movie: c.getListed_movies()){
            if(movie.equals(m)){
                m_test = movie;
                break;
            }
        }
        if(m_test == null){
            System.out.println("Movie doesn't exist");
            return;
        }
        HashMap <Movie, Integer> streaming_prices = c.getPrices();
        if(streaming_prices.get(m) == null){
            System.out.println("Movie isn't streaming yet");
            return;
        }
        if(price < 0){
            System.out.println("Price can't be negative");
            return;
        }
        streaming_prices.put(m, price);
        c.setPrices(streaming_prices);
        System.out.println("Price changed");
    }

    public void changeRoom(Cinema c, Movie m, int room) {
        if(!Arrays.asList(cinemas).contains(c)){
            System.out.println("Cinema doesn't exist");
            return;
        }
        Movie m_test = null;
        for(Movie movie: c.getListed_movies()){
            if(movie.equals(m)){
                m_test = movie;
                break;
            }
        }
        if(m_test == null){
            System.out.println("Movie doesn't exist");
            return;
        }
        HashMap <Movie, Integer> seats_available = c.getSeats_available();
        HashMap <Movie, Integer> room_numbers = c.getRoom_numbers();
        if(room_numbers.get(m) == null){
            System.out.println("Movie isn't streaming yet");
            return;
        }
        if(room > c.getNr_seats().length || room < 1){
            System.out.println("Room doesn't exist");
            return;
        }
        int[] nr_seats = c.getNr_seats();
        if(nr_seats[room_numbers.get(m)] - seats_available.get(m) > nr_seats[room-1]){
            System.out.println("The new room doesn't have the space to host the current number of reservations");
            return;
        }
        for(Movie movie : c.getStreaming_dates().keySet())
            if(c.getRoom_numbers().get(movie) == room-1 &&
                    (c.getStreaming_dates().get(movie).isBefore(c.getStreaming_dates().get(m)) &&
                    c.getStreaming_dates().get(movie).plusMinutes(movie.getDuration()).
                            isAfter(c.getStreaming_dates().get(m)) ||
                    c.getStreaming_dates().get(movie).isAfter(c.getStreaming_dates().get(m)) &&
                    c.getStreaming_dates().get(movie).isBefore(c.getStreaming_dates().get(m).
                            plusMinutes(m.getDuration())))){
                System.out.println("Room already taken");
                return;
            }
        seats_available.put(m, nr_seats[room-1] - (nr_seats[room_numbers.get(m)] - seats_available.get(m)));
        room_numbers.put(m, room-1);
        c.setRoom_numbers(room_numbers);
        c.setSeats_available(seats_available);

        System.out.println("Room changed");
    }

    public void changeDate(Cinema c, Movie m, LocalDateTime date) {
        if(!Arrays.asList(cinemas).contains(c)){
            System.out.println("Cinema doesn't exist");
            return;
        }
        Movie m_test = null;
        for(Movie movie: c.getListed_movies()){
            if(movie.equals(m)){
                m_test = movie;
                break;
            }
        }
        if(m_test == null){
            System.out.println("Movie doesn't exist");
            return;
        }
        HashMap <Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
        if(streaming_dates.get(m) == null){
            System.out.println("Movie isn't streaming yet");
            return;
        }
        for(Movie movie : c.getStreaming_dates().keySet())
            if(Objects.equals(c.getRoom_numbers().get(movie), c.getRoom_numbers().get(m)) &&
                    (c.getStreaming_dates().get(movie).isBefore(date) &&
                    c.getStreaming_dates().get(movie).plusMinutes(movie.getDuration()).isAfter(date) ||
                    c.getStreaming_dates().get(movie).isAfter(date) &&
                            c.getStreaming_dates().get(movie).isBefore(date.plusMinutes(m.getDuration())))){
                System.out.println("Room already taken in that date");
                return;
            }
        streaming_dates.put(m, date);
        c.setStreaming_dates(streaming_dates);
        System.out.println("Date changed");
    }
}
