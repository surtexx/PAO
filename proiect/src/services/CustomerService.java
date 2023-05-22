package services;

import data_types.Gender;
import entities.*;
import persistence.CinemaRepository;
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
                for(int i = 0; i < new_movies.length; i++)
                    new_new_movies[i] = new_movies[i];
                new_new_movies[new_new_movies.length - 1] = m;
                new_movies = new_new_movies;
            }
        }
        return new_movies;
    }

    public Movie[] getAllStreamingMovies(){
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas){
            for(Movie m : c.getStreaming_dates().keySet()){
                Movie[] new_movies = new Movie[movies.length + 1];
                for(int i = 0; i < movies.length; i++)
                    new_movies[i] = movies[i];
                new_movies[new_movies.length - 1] = m;
                movies = new_movies;
            }
        }
        return movies;
    }

    public Movie[] getMoviesFromCinema(Cinema c){
        return c.getListed_movies();
    }

    public Movie[] getStreamingMoviesFromCinema(Cinema c){
        return c.getStreaming_dates().keySet().toArray(new Movie[0]);
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
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
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
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
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
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
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
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
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
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
        }
        return movies;
    }

    public Movie[] getAllStreamingMoviesByGenre(String genre) {
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        Movie[] movies = new Movie[0];
        switch (genre.toLowerCase()) {
            case "action":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getStreaming_dates().keySet()) {
                        if (m instanceof Action_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "comedy":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getStreaming_dates().keySet()) {
                        if (m instanceof Comedy_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "romance":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getStreaming_dates().keySet()) {
                        if (m instanceof Romance_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "thriller":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getStreaming_dates().keySet()) {
                        if (m instanceof Thriller_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
                        }
                    }
                }
                break;
            case "other":
                for (Cinema c : cinemas) {
                    for (Movie m : c.getStreaming_dates().keySet()) {
                        if (m instanceof Other_Movie) {
                            Movie[] new_movies = new Movie[movies.length + 1];
                            for(int i = 0; i < movies.length; i++)
                                new_movies[i] = movies[i];
                            new_movies[new_movies.length - 1] = m;
                            movies = new_movies;
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
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "comedy":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Comedy_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "romance":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Romance_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "thriller":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Thriller_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "other":
                for (Movie m : c.getListed_movies()) {
                    if (m instanceof Other_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
        }
        return movies;
    }

    public Movie[] getStreamingMoviesByGenreFromCinema(Cinema c, String genre) {
        if(!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Comedy") &&
                !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Thriller") &&
                !genre.equalsIgnoreCase("Other"))
            return null;
        Movie[] movies = new Movie[0];
        switch (genre.toLowerCase()) {
            case "action":
                for (Movie m : c.getStreaming_dates().keySet()) {
                    if (m instanceof Action_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "comedy":
                for (Movie m : c.getStreaming_dates().keySet()) {
                    if (m instanceof Comedy_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "romance":
                for (Movie m : c.getStreaming_dates().keySet()) {
                    if (m instanceof Romance_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "thriller":
                for (Movie m : c.getStreaming_dates().keySet()) {
                    if (m instanceof Thriller_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
                    }
                }
                break;
            case "other":
                for (Movie m : c.getStreaming_dates().keySet()) {
                    if (m instanceof Other_Movie) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
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

    public Movie[] getStreamingMoviesByDate(LocalDate date) {
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas) {
            for(Movie m : c.getStreaming_dates().keySet()) {
                if(c.getStreaming_dates().get(m).toLocalDate().equals(date)) {
                    Movie[] new_movies = new Movie[movies.length + 1];
                    for(int i = 0; i < movies.length; i++)
                        new_movies[i] = movies[i];
                    new_movies[new_movies.length - 1] = m;
                    movies = new_movies;
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
                    for(int i = 0; i < movies.length; i++)
                        new_movies[i] = movies[i];
                    new_movies[new_movies.length - 1] = m;
                    movies = new_movies;
                }
            }
        }
        return movies;
    }

    public Movie[] getStreamingMoviesByActor(String name) {
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas) {
            for(Movie m : c.getStreaming_dates().keySet()) {
                for(Movie m2 : movies)
                    if(m2.equals(m))
                        continue;
                for(Actor a : m.getActors()) {
                    if(a.getName().equalsIgnoreCase(name)) {
                        Movie[] new_movies = new Movie[movies.length + 1];
                        for(int i = 0; i < movies.length; i++)
                            new_movies[i] = movies[i];
                        new_movies[new_movies.length - 1] = m;
                        movies = new_movies;
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
