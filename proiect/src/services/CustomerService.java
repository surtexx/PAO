package services;

import data_types.Gender;
import entities.*;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CustomerService implements CustomerServiceInterface {
    private Cinema[] cinemas;
    private static CustomerService instance;

    private CustomerService(){
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
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas){
            for(Movie m : c.getListed_movies()){
                if(movies.length == 0){
                    movies = new Movie[movies.length + 1];
                    movies[movies.length - 1] = m;
                    continue;
                }
                for(Movie mm : movies)
                    if(mm.equals(m))
                        continue;
                Movie[] new_movies = new Movie[movies.length + 1];
                for(int i = 0; i < movies.length; i++)
                    new_movies[i] = movies[i];
                new_movies[new_movies.length - 1] = m;
                movies = new_movies;
            }
        }
        return movies;
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

    public Movie[] getStreamingMoviesByDate(LocalDateTime date) {
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas) {
            for(Movie m : c.getStreaming_dates().keySet()) {
                if(c.getStreaming_dates().get(m).equals(date)) {
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
        Movie[] movies = new Movie[0];
        for(Cinema c : cinemas) {
            for(Movie m : c.getListed_movies()) {
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
        if(c.getSeats_available().get(m) > 0) {
            seats.put(m, seats.get(m) - 1);
            c.setSeats_available(seats);
            System.out.println("Ticket bought successfully");
        }
        else
            System.out.println("No more seats available for this movie");
    }
}
