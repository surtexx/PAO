package services;

import entities.*;
import persistence.CustomerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class CustomerService implements CustomerServiceInterface {
    private final Cinema[] cinemas;
    private static CustomerService instance;
    private final CustomerRepository customerRepository;
    private CustomerService(){
        customerRepository = new CustomerRepository();
        cinemas = customerRepository.getAllCinemas();
    }

    public static CustomerService getInstance(){
        if(instance == null){
            instance = new CustomerService();
        }
        return instance;
    }

    public Cinema[] getAllCinemas(){
        return cinemas;
    }

    public Movie[] getAllMovies(){
        int max_movies = 0;
        for(Cinema c : cinemas){
            max_movies += c.getListed_movies().length;
        }
        Movie[] movies = new Movie[max_movies];
        int index = 0;
        for(Cinema c : cinemas){
            for(Movie m : c.getListed_movies()){
                movies[index] = m;
                index++;
            }
        }
        Movie[] new_movies = new Movie[0];
        for(Movie m : movies){
            boolean found = false;
            for(Movie m2 : new_movies){
                if(m.toString().equals(m2.toString())){
                    found = true;
                    break;
                }
            }
            if(!found){
                Movie[] new_new_movies = new Movie[new_movies.length + 1];
                System.arraycopy(new_movies, 0, new_new_movies, 0, new_movies.length);
                new_new_movies[new_new_movies.length - 1] = m;
                new_movies = new_new_movies;
            }
        }
        return new_movies;
    }

    public HashMap<Movie, LocalDateTime> getAllStreamingMovies(){
        HashMap<Movie, LocalDateTime> movies = new HashMap<>();
        for(Cinema c : cinemas){
            HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
            for(Movie m : streaming_dates.keySet()){
                movies.put(m, streaming_dates.get(m));
            }
        }
        return movies;
    }

    public Movie[] getMoviesFromCinema(Cinema c){
        return c.getListed_movies();
    }

    public HashMap<Movie, LocalDateTime> getStreamingMoviesFromCinema(Cinema c){
        return c.getStreaming_dates();
    }

    public Movie[] getAllMoviesByGenre(String genre){
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        Movie[] movies = new Movie[0];
        switch (genre.toLowerCase()) {
            case "action":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getListed_movies()) {
                        if (m instanceof Action_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            System.arraycopy(movies, 0, new_movies, 0, movies.length);
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "comedy":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getListed_movies()) {
                        if (m instanceof Comedy_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            System.arraycopy(movies, 0, new_movies, 0, movies.length);
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "romance":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getListed_movies()) {
                        if (m instanceof Romance_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            System.arraycopy(movies, 0, new_movies, 0, movies.length);
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "thriller":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getListed_movies()) {
                        if (m instanceof Thriller_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            System.arraycopy(movies, 0, new_movies, 0, movies.length);
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "other":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getListed_movies()) {
                        if (m instanceof Other_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            System.arraycopy(movies, 0, new_movies, 0, movies.length);
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
        }
        return movies;
    }

    public HashMap<Movie, LocalDateTime> getAllStreamingMoviesByGenre(String genre) {
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        HashMap<Movie, LocalDateTime> movies = new HashMap<>();
        switch (genre.toLowerCase()) {
            case "action":
                for (Cinema c : cinemas) {
                    HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
                    for (Movie m : streaming_dates.keySet()) {
                        if (m instanceof Action_Movie) {
                            movies.put(m, streaming_dates.get(m));
                        }
                    }
                }
                break;
            case "comedy":
                for (Cinema c : cinemas) {
                    HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
                    for (Movie m : streaming_dates.keySet()) {
                        if (m instanceof Comedy_Movie) {
                            movies.put(m, streaming_dates.get(m));
                        }
                    }
                }
                break;
            case "romance":
                for (Cinema c : cinemas) {
                    HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
                    for (Movie m : streaming_dates.keySet()) {
                        if (m instanceof Romance_Movie) {
                            movies.put(m, streaming_dates.get(m));
                        }
                    }
                }
                break;
            case "thriller":
                for (Cinema c : cinemas) {
                    HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
                    for (Movie m : streaming_dates.keySet()) {
                        if (m instanceof Thriller_Movie) {
                            movies.put(m, streaming_dates.get(m));
                        }
                    }
                }
                break;
            case "other":
                for (Cinema c : cinemas) {
                    HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
                    for (Movie m : streaming_dates.keySet()) {
                        if (m instanceof Other_Movie) {
                            movies.put(m, streaming_dates.get(m));
                        }
                    }
                }
                break;
        }
        return movies;
    }

    public Movie[] getMoviesByGenreFromCinema(Cinema c, String genre) {
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        Movie[] movies = new Movie[0];
        switch (genre.toLowerCase()) {
            case "action":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Action_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        System.arraycopy(movies, 0, new_movies, 0, movies.length);
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "comedy":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Comedy_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        System.arraycopy(movies, 0, new_movies, 0, movies.length);
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "romance":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Romance_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        System.arraycopy(movies, 0, new_movies, 0, movies.length);
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "thriller":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Thriller_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        System.arraycopy(movies, 0, new_movies, 0, movies.length);
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "other":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Other_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        System.arraycopy(movies, 0, new_movies, 0, movies.length);
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
        }
        return movies;
    }

    public HashMap<Movie, LocalDateTime> getStreamingMoviesByGenreFromCinema(Cinema c, String genre) {
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        HashMap<Movie, LocalDateTime> movies = new HashMap<>();
        HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
        switch (genre.toLowerCase()) {
            case "action":
                for (Movie m : streaming_dates.keySet()) {
                    if (m instanceof Action_Movie) {
                        movies.put(m, c.getStreaming_dates().get(m));
                    }
                }
                break;
            case "comedy":
                for (Movie m : streaming_dates.keySet()) {
                    if (m instanceof Comedy_Movie) {
                        movies.put(m, c.getStreaming_dates().get(m));
                    }
                }
                break;
            case "romance":
                for (Movie m : streaming_dates.keySet()) {
                    if (m instanceof Romance_Movie) {
                        movies.put(m, c.getStreaming_dates().get(m));
                    }
                }
                break;
            case "thriller":
                for (Movie m : streaming_dates.keySet()) {
                    if (m instanceof Thriller_Movie) {
                        movies.put(m, c.getStreaming_dates().get(m));
                    }
                }
                break;
            case "other":
                for (Movie m : streaming_dates.keySet()) {
                    if (m instanceof Other_Movie) {
                        movies.put(m, c.getStreaming_dates().get(m));
                    }
                }
                break;
        }
        return movies;
    }

    public Actor getDetailsAboutActor(String name) {
        Movie[] movies = getAllMovies();
        for(Movie m : movies) {
            for(Actor a : m.getActors()) {
                if(a.getName().equalsIgnoreCase(name))
                    return a;
            }
        }
        return null;
    }

    public Movie getDetailsAboutMovie(String name) {
        Movie[] movies = getAllMovies();
        for(Movie m : movies) {
            if(m.getTitle().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    public HashMap<Movie, LocalDateTime> getStreamingMoviesByDate(LocalDate date) {
        HashMap<Movie, LocalDateTime> movies = new HashMap<>();
        for(Cinema c : cinemas) {
            HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
            for(Movie m : streaming_dates.keySet()) {
                if(streaming_dates.get(m).toLocalDate().equals(date)) {
                    movies.put(m, streaming_dates.get(m));
                }
            }
        }
        return movies;
    }

    public Movie[] getAllMoviesByActor(String name) {
        Movie[] all_movies = getAllMovies();
        Movie[] movies = new Movie[0];
        for(Movie m : all_movies) {
            for(Actor a : m.getActors()) {
                if(a.getName().equalsIgnoreCase(name)) {
                    Movie[] new_movies = new Movie[movies.length + 1];
                    System.arraycopy(movies, 0, new_movies, 0, movies.length);
                    new_movies[new_movies.length - 1] = m;
                    movies = new_movies;
                }
            }
        }
        return movies;
    }

    public HashMap<Movie, LocalDateTime> getStreamingMoviesByActor(String name) {
        HashMap<Movie, LocalDateTime> movies = new HashMap<>();
        for(Cinema c : cinemas) {
            HashMap<Movie, LocalDateTime> streaming_dates = c.getStreaming_dates();
            for(Movie m : streaming_dates.keySet()) {
                for(Actor a : m.getActors()) {
                    if(a.getName().equalsIgnoreCase(name)) {
                        movies.put(m, streaming_dates.get(m));
                    }
                }
            }
        }
        return movies;
    }
    public void buyTicket(Cinema c, Movie m) {
        HashMap<Movie, Integer> seats = c.getSeats_available();
        HashMap<Movie, LocalDateTime> dates = c.getStreaming_dates();
        if(c.getSeats_available().get(m) > 0) {
            seats.put(m, seats.get(m) - 1);
            c.setSeats_available(seats);
            customerRepository.buyTicket(c, m, dates.get(m));
            System.out.println("Ticket bought successfully");
        }
        else
            System.out.println("No more seats available for this movie");
    }
}
