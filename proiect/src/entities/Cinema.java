package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Arrays;
public class Cinema {
    String name;
    protected Movie[] listed_movies;
    protected int nr_rooms;
    protected int[] nr_seats;
    protected HashMap<Movie, Integer> prices;
    protected HashMap<Movie, LocalDateTime> streaming_dates;
    protected HashMap<Movie, Integer> room_numbers;
    protected HashMap<Movie, Integer> seats_available;

    public Cinema(String name, Movie[] listed_movies, int nr_rooms, int[] nr_seats) {
        this.name = name;
        this.listed_movies = listed_movies;
        this.nr_rooms = nr_rooms;
        this.nr_seats = nr_seats;
        this.prices = new HashMap<Movie, Integer>();
        this.streaming_dates = new HashMap<Movie, LocalDateTime>();
        this.room_numbers = new HashMap<Movie, Integer>();
        this.seats_available = new HashMap<Movie, Integer>();
    }

    public Cinema(){
        this.name = "Unknown";
        this.listed_movies = new Movie[0];
        this.nr_rooms = 0;
        this.nr_seats = new int[0];
        this.prices = new HashMap<Movie, Integer>();
        this.streaming_dates = new HashMap<Movie, LocalDateTime>();
        this.room_numbers = new HashMap<Movie, Integer>();
        this.seats_available = new HashMap<Movie, Integer>();
    }

    public Cinema(Cinema c){
        this.name = c.name;
        this.listed_movies = c.listed_movies;
        this.nr_rooms = c.nr_rooms;
        this.nr_seats = c.nr_seats;
        this.prices = c.prices;
        this.streaming_dates = c.streaming_dates;
        this.room_numbers = c.room_numbers;
        this.seats_available = c.seats_available;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Movie[] getListed_movies() {
        return listed_movies;
    }
    public void setListed_movies(Movie[] listed_movies) {
        this.listed_movies = listed_movies;
    }

    public int getNr_rooms() {
        return nr_rooms;
    }

    public void setNr_rooms(int nr_rooms) {
        this.nr_rooms = nr_rooms;
    }

    public int[] getNr_seats() {
        return nr_seats;
    }

    public void setNr_seats(int[] nr_seats) {
        this.nr_seats = nr_seats;
    }

    public HashMap<Movie, Integer> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<Movie, Integer> prices) {
        for(Movie m: prices.keySet())
            if(prices.get(m) < 0)
                throw new IllegalArgumentException("ERROR: Price must be positive");
            else if(Arrays.asList(this.listed_movies).contains(m))
                throw new IllegalArgumentException("ERROR: Movie not in the list of streaming movies");
        this.prices = prices;
    }

    public HashMap<Movie, LocalDateTime> getStreaming_dates() {
        return streaming_dates;
    }

    public void setStreaming_dates(HashMap<Movie, LocalDateTime> streaming_dates) {
        for(Movie m: streaming_dates.keySet()){
            if(streaming_dates.get(m).isBefore(LocalDateTime.now()))
                throw new IllegalArgumentException("ERROR: Date must be in the future");
            else if(Arrays.asList(this.listed_movies).contains(m))
                throw new IllegalArgumentException("ERROR: Movie not in the list of streaming movies");
            this.streaming_dates.put(m, streaming_dates.get(m));
        }
    }

    public HashMap<Movie, Integer> getRoom_numbers() {
        return room_numbers;
    }

    public void setRoom_numbers(HashMap<Movie, Integer> room_numbers) {
        for(Movie m: room_numbers.keySet()){
            if(room_numbers.get(m) < 0 || room_numbers.get(m) > this.nr_rooms)
                throw new IllegalArgumentException("ERROR: Room number must be between 1 and the number of rooms");
            else if(Arrays.asList(this.listed_movies).contains(m))
                throw new IllegalArgumentException("ERROR: Movie not in the list of streaming movies");
        }
        this.room_numbers = room_numbers;
    }

    public HashMap<Movie, Integer> getSeats_available() {
        return seats_available;
    }

    public void setSeats_available(HashMap<Movie, Integer> seats_available) {
        for(Movie m: seats_available.keySet()){
            if(seats_available.get(m) < 0 || seats_available.get(m) > this.nr_seats[this.room_numbers.get(m)])
                throw new IllegalArgumentException("ERROR: Number of seats available must be between 0 and the number of seats in the room");
            else if(Arrays.asList(this.listed_movies).contains(m))
                throw new IllegalArgumentException("ERROR: Movie not in the list of streaming movies");
        }
        this.seats_available = seats_available;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Cinema: ");
        s.append(name).append("\n");
        s.append("Listed movies: ");
        for (Movie m: listed_movies) {
            if (m != listed_movies[listed_movies.length - 1])
                s.append(m.getTitle()).append(", ");
            else
                s.append(m.getTitle()).append("\n");
        }
        s.append("Number of rooms: ").append(nr_rooms).append("\n");
        s.append("Room / number of seats in the room:\n    ");
        for (int i = 0; i < nr_seats.length; i++) {
            if(i<nr_seats.length-1)
                s.append(i + 1).append(" / ").append(nr_seats[i]).append("\n    ");
            else
                s.append(i + 1).append(" / ").append(nr_seats[i]).append("\n");
        }
        return s.toString();
    }
}
