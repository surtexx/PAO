package services;

import data_types.Gender;
import entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Service {
    private static final Scanner scanner = new Scanner(System.in);
    private static Service instance;
    private static CustomerService customerService;
    private static CinemaService cinemaService;
    private Service(CinemaService cs){
        cinemaService = cs;
    }
    private Service(CustomerService cs){
        customerService = cs;
    }
    public static Service getInstance(){
        if(instance == null) {
            while(true){
                System.out.println("What user are you? (1 - customer, 2 - cinema)");
                int choice;
                try {
                    choice = scanner.nextInt();
                }
                catch (Exception e){
                    System.out.println("Invalid choice");
                    break;
                }
                if(choice == 1) {
                    if(customerService == null && cinemaService == null){
                        CustomerService css = CustomerService.getInstance();
                        instance = new Service(css);
                        break;
                    }
                    else
                        return instance;
                }
                else if(choice == 2) {
                    if (cinemaService == null && customerService == null) {
                        CinemaService cs = CinemaService.getInstance();
                        instance = new Service(cs);
                        break;
                    }
                    else
                        return instance;
                }
                else
                    System.out.println("Invalid choice!");
            }
        }
        return instance;
    }

    public void menu() throws IOException {
        while(true) {
            listMenu();
            chooseOption();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Press any key...");
            try {
                input.readLine();
            }
            catch (Exception e) {

            }
        }
    }

    private void chooseOption(){
        if(customerService != null) {
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid choice");
                return;
            }
            switch (choice) {
                case 1 -> {
                    Cinema[] cinemas = customerService.getAllCinemas();
                    for (Cinema c : cinemas)
                        System.out.println("---- " + c.getName() + " ----");
                }
                case 2 -> {
                    Movie[] movies = customerService.getAllMovies();
                    for (Movie m : movies)
                        System.out.println("---- " + m.getTitle() + " ----");
                }
                case 3 -> {
                    Movie[] streaming_movies = customerService.getAllStreamingMovies();
                    for (Movie m : streaming_movies)
                        System.out.println("---- " + m.getTitle() + " ----");
                }
                case 4 -> {
                    Cinema[] all_cinemas = customerService.getAllCinemas();
                    int i = 1;
                    for (Cinema c : all_cinemas) {
                        System.out.println(i + ". " + c.getName());
                        i++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema;
                    try {
                        choice_cinema = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema < 1 || choice_cinema > all_cinemas.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema = scanner.nextInt();
                    }
                    Cinema c = all_cinemas[choice_cinema - 1];
                    Movie[] movies_from_cinema = customerService.getMoviesFromCinema(c);
                    for (Movie m : movies_from_cinema)
                        System.out.println("---- " + m.getTitle() + " ----");
                }
                case 5 -> {
                    Cinema[] all_cinemas2 = customerService.getAllCinemas();
                    int i2 = 1;
                    for (Cinema c2 : all_cinemas2) {
                        System.out.println(i2 + ". " + c2.getName());
                        i2++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema2;
                    try {
                        choice_cinema2 = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema2 < 1 || choice_cinema2 > all_cinemas2.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema2 = scanner.nextInt();
                    }
                    Cinema c2 = all_cinemas2[choice_cinema2 - 1];
                    Movie[] streaming_movies_from_cinema = customerService.getStreamingMoviesFromCinema(c2);
                    for (Movie m : streaming_movies_from_cinema)
                        System.out.println("---- " + m.getTitle() + " ----");
                }
                case 6 -> {
                    System.out.println("Choose genre (Action, Comedy, Romance, Thriller, Other): ");
                    String genre = scanner.next();
                    Movie[] movies_by_genre = customerService.getAllMoviesByGenre(genre);
                    if (movies_by_genre == null)
                        System.out.println("No movies found!");
                    else
                        for (Movie m : movies_by_genre)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 7 -> {
                    System.out.println("Choose genre(Action, Comedy, Romance, Thriller, Other): ");
                    String genre2 = scanner.next();
                    Movie[] streaming_movies_by_genre = customerService.getAllStreamingMoviesByGenre(genre2);
                    if (streaming_movies_by_genre == null)
                        System.out.println("No movies found!");
                    else
                    for (Movie m : streaming_movies_by_genre)
                        System.out.println("---- " + m.getTitle() + " ----");
                }
                case 8 -> {
                    Cinema[] all_cinemas3 = customerService.getAllCinemas();
                    int i3 = 1;
                    for (Cinema c1 : all_cinemas3) {
                        System.out.println(i3 + ". " + c1.getName());
                        i3++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema3;
                    try {
                        choice_cinema3 = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema3 < 1 || choice_cinema3 > all_cinemas3.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema3 = scanner.nextInt();
                    }
                    Cinema c3 = all_cinemas3[choice_cinema3 - 1];
                    System.out.println("Choose genre(Action, Comedy, Romance, Thriller, Other): ");
                    String genre3 = scanner.next();
                    Movie[] movies_by_genre_from_cinema = customerService.getMoviesByGenreFromCinema(c3, genre3);
                    if (movies_by_genre_from_cinema == null)
                        System.out.println("No movies found!");
                    else
                        for (Movie m : movies_by_genre_from_cinema)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 9 -> {
                    Cinema[] all_cinemas4 = customerService.getAllCinemas();
                    int i4 = 1;
                    for (Cinema c4 : all_cinemas4) {
                        System.out.println(i4 + ". " + c4.getName());
                        i4++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema4;
                    try {
                        choice_cinema4 = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema4 < 1 || choice_cinema4 > all_cinemas4.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema4 = scanner.nextInt();
                    }
                    Cinema c4 = all_cinemas4[choice_cinema4 - 1];
                    System.out.println("Choose genre(Action, Comedy, Romance, Thriller, Other): ");
                    String genre4 = scanner.next();
                    Movie[] streaming_movies_by_genre_from_cinema = customerService.getStreamingMoviesByGenreFromCinema(c4, genre4);
                    if (streaming_movies_by_genre_from_cinema == null)
                        System.out.println("No movies found!");
                    else
                        for (Movie m : streaming_movies_by_genre_from_cinema)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 10 -> {
                    System.out.println("Write actor's name: ");
                    String actor_first_name = scanner.next();
                    String actor_last_name = scanner.next();
                    Movie[] movies_by_actor = customerService.getAllMoviesByActor(actor_first_name + " " + actor_last_name);
                    if (movies_by_actor.length == 0)
                        System.out.println("No movies found with this actor!");
                    else
                        for (Movie m : movies_by_actor)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 11 -> {
                    System.out.println("Write actor's name: ");
                    String actor_first_name1 = scanner.next();
                    String actor_last_name1 = scanner.next();
                    Movie[] streaming_movies_by_actor = customerService.getStreamingMoviesByActor(actor_first_name1 + " " + actor_last_name1);
                    if (streaming_movies_by_actor.length == 0)
                        System.out.println("No movies found with this actor!");
                    else
                        for (Movie m : streaming_movies_by_actor)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 12 -> {
                    System.out.println("Write date (yyyy-MM-dd/HH:mm): ");
                    boolean valid_date = false;
                    LocalDateTime date = null;
                    while(!valid_date)
                        try {
                            date = LocalDateTime.parse(scanner.next(), DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm"));
                            valid_date = true;
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                        }
                    Movie[] streaming_movies_by_date = customerService.getStreamingMoviesByDate(date);
                    if (streaming_movies_by_date.length == 0)
                        System.out.println("No movies found in that date!");
                    else
                        for (Movie m : streaming_movies_by_date)
                            System.out.println("---- " + m.getTitle() + " ----");
                }
                case 13 -> {
                    System.out.println("Write actor's name: ");
                    String actor_first_name1 = scanner.next();
                    String actor_last_name1 = scanner.next();
                    System.out.println(customerService.getDetailsAboutActor(actor_first_name1 + " " + actor_last_name1));
                }
                case 14 -> {
                    Movie[] all_movies = customerService.getAllMovies();
                    int i = 1;
                    for (Movie m : all_movies) {
                        System.out.println(i + ". " + m.getTitle());
                        i++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie;
                    try {
                        choice_movie = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > all_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie movie = all_movies[choice_movie - 1];
                    System.out.println(customerService.getDetailsAboutMovie(movie.getTitle()));
                }
                case 15 -> {
                    Cinema[] all_cinemas5 = customerService.getAllCinemas();
                    int i5 = 1;
                    for (Cinema c5 : all_cinemas5) {
                        System.out.println(i5 + ". " + c5.getName());
                        i5++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema5;
                    try {
                        choice_cinema5 = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    Movie[] all_movies6 = customerService.getStreamingMoviesFromCinema(all_cinemas5[choice_cinema5 - 1]);
                    if (all_movies6.length == 0) {
                        System.out.println("This cinema isn't streaming any movie at the moment! Please come again later");
                        break;
                    }
                    while (choice_cinema5 < 1 || choice_cinema5 > all_cinemas5.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema5 = scanner.nextInt();
                    }
                    Cinema c5 = all_cinemas5[choice_cinema5 - 1];
                    System.out.println("Choose movie: ");
                    Movie[] all_movies5 = customerService.getStreamingMoviesFromCinema(c5);
                    int i6 = 1;
                    for (Movie m5 : all_movies5) {
                        System.out.println(i6 + ". " + m5.getTitle());
                        i6++;
                    }
                    int choice_movie5;
                    try {
                        choice_movie5 = scanner.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie5 < 1 || choice_movie5 > all_movies5.length) {
                        System.out.println("Invalid choice!");
                        choice_movie5 = scanner.nextInt();
                    }
                    Movie m5 = all_movies5[choice_movie5 - 1];
                    customerService.buyTicket(c5, m5);
                }
                case 16 -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
        else if(cinemaService != null){
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid choice");
                return;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("Write cinema's name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Write cinema's number of movies: ");
                    int movies = scanner.nextInt();
                    Movie[] movies1 = new Movie[movies];
                    for (int i = 0; i < movies; i++) {
                        System.out.println("Movie " + i + ": ");
                        System.out.println("    Write movie's name: ");
                        scanner.nextLine();
                        String movie_title = scanner.nextLine();
                        System.out.println("    Write the movie director's name: ");
                        scanner.nextLine();
                        String director_name = scanner.nextLine();
                        System.out.println("    Write the year the movie got released: ");
                        int year = scanner.nextInt();
                        System.out.println("    Write the movie's duration: ");
                        int duration = scanner.nextInt();
                        System.out.println("    Write the movie's rating: ");
                        double rating = scanner.nextDouble();
                        System.out.println("    Write the movie's number of Oscar awards: ");
                        int oscar_awards = scanner.nextInt();
                        System.out.println("    Write the movie's number of actors: ");
                        int actors = scanner.nextInt();
                        Actor[] actors1 = new Actor[actors];
                        for (int j = 0; j < actors; j++) {
                            System.out.println("    Actor " + j + ": ");
                            System.out.println("        Write actor's first name: ");
                            String actor_first_name = scanner.next();
                            System.out.println("        Write actor's last name: ");
                            String actor_last_name = scanner.next();
                            System.out.println("        Write actor's age: ");
                            int actor_age = scanner.nextInt();
                            System.out.println("        Write actor's gender (M/F): ");
                            String line = scanner.next();
                            Gender gender = null;
                            while (!line.equalsIgnoreCase("M") && !line.equalsIgnoreCase("F")) {
                                System.out.println("        Invalid gender. Try again: ");
                                line = scanner.next();
                            }
                            if (line.equalsIgnoreCase("M"))
                                gender = Gender.M;
                            else if (line.equalsIgnoreCase("F"))
                                gender = Gender.F;
                            System.out.println("        Write actor's number of Oscar awards: ");
                            int actor_oscar_awards = scanner.nextInt();
                            Actor a = new Actor(actor_first_name, actor_last_name, actor_age, gender, actor_oscar_awards);
                            actors1[j] = a;
                        }
                        System.out.println("    Write the movie's genre (Action, Comedy, Romance, Thriller, Other): ");
                        String line = scanner.next();
                        switch (line.toLowerCase()) {
                            case "action" -> {
                                System.out.println("    Write the movie's PEGI rating: ");
                                int pegi = scanner.nextInt();
                                boolean ok = false;
                                while (!ok) {
                                    try {
                                        movies1[i] = new Action_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, pegi);
                                        ok = true;
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("    Try again: ");
                                    }
                                }
                            }
                            case "comedy" -> {
                                System.out.println("    Is the movie only for adults? (y/n) ");
                                line = scanner.next();
                                boolean only_for_adults = false;
                                while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                    System.out.println("        Invalid choice. Try again: ");
                                    line = scanner.next();
                                }
                                if (line.equalsIgnoreCase("y"))
                                    only_for_adults = true;
                                System.out.println("    Is the movie romantic? (y/n) ");
                                line = scanner.next();
                                boolean is_romantic = false;
                                while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                    System.out.println("        Invalid choice. Try again: ");
                                    line = scanner.next();
                                }
                                if (line.equalsIgnoreCase("y"))
                                    is_romantic = true;
                                movies1[i] = new Comedy_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, only_for_adults, is_romantic);
                            }
                            case "romance" -> {
                                System.out.println("    Does the movie have explicit nudity? (y/n) ");
                                line = scanner.next();
                                boolean explicit_nudity = false;
                                while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                    System.out.println("        Invalid choice. Try again: ");
                                    line = scanner.next();
                                }
                                if (line.equalsIgnoreCase("y"))
                                    explicit_nudity = true;
                                movies1[i] = new Romance_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, explicit_nudity);
                            }
                            case "thriller" -> {
                                System.out.println("    Write all the subgenres of the movie (separated by a comma): ");
                                line = scanner.next();
                                String[] subgenres = line.split(",");
                                System.out.println("    Write the movie's PEGI rating: ");
                                int pegi1 = scanner.nextInt();
                                boolean ok1 = false;
                                while (!ok1) {
                                    try {
                                        movies1[i] = new Thriller_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, subgenres, pegi1);
                                        ok1 = true;
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("    Try again: ");
                                    }
                                }
                            }
                            case "other" -> {
                                System.out.println("    Write the movie's genres (separated by comma): ");
                                line = scanner.next();
                                String[] genres = line.split(",");
                                System.out.println("    Write the movie's PEGI rating: ");
                                int pegi2 = scanner.nextInt();
                                boolean ok2 = false;
                                while (!ok2) {
                                    try {
                                        movies1[i] = new Other_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, genres, pegi2);
                                        ok2 = true;
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                        System.out.println("    Try again: ");
                                    }
                                }
                            }
                            default -> System.out.println("Invalid genre!");
                        }
                    }
                    System.out.println("Write cinema's number of rooms: ");
                    int rooms = scanner.nextInt();
                    System.out.println("Write cinema's number of seats per room: ");
                    int[] seats = new int[rooms];
                    for (int i = 0; i < rooms; i++) {
                        System.out.println("Room " + i + ": ");
                        int seat_nr = scanner.nextInt();
                        seats[i] = seat_nr;
                    }
                    Cinema c = new Cinema(name, movies1, rooms, seats);
                    cinemaService.addCinema(c);
                }
                case 2 -> {
                    Cinema[] all_cinemas = cinemaService.getCinemas();
                    int i = 1;
                    for (Cinema c1 : all_cinemas) {
                        System.out.println(i + ". " + c1.getName());
                        i++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema;
                    try{
                        choice_cinema = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema < 1 || choice_cinema > all_cinemas.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema = scanner.nextInt();
                    }
                    Cinema c1 = all_cinemas[choice_cinema - 1];
                    cinemaService.removeCinema(c1);
                }
                case 3 -> {
                    Cinema[] all_cinemas1 = cinemaService.getCinemas();
                    int i1 = 1;
                    for (Cinema c2 : all_cinemas1) {
                        System.out.println(i1 + ". " + c2.getName());
                        i1++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema1;
                    try{
                        choice_cinema1 = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema1 < 1 || choice_cinema1 > all_cinemas1.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema1 = scanner.nextInt();
                    }
                    Cinema c2 = all_cinemas1[choice_cinema1 - 1];
                    Movie movie = null;
                    System.out.println("Write movie's name: ");
                    scanner.nextLine();
                    String movie_title = scanner.nextLine();
                    System.out.println("Write the movie director's name: ");
                    scanner.nextLine();
                    String director_name = scanner.nextLine();
                    System.out.println("Write the year the movie got released: ");
                    int year = scanner.nextInt();
                    System.out.println("Write the movie's duration: ");
                    int duration = scanner.nextInt();
                    System.out.println("Write the movie's rating: ");
                    double rating = scanner.nextDouble();
                    System.out.println("Write the movie's number of Oscar awards: ");
                    int oscar_awards = scanner.nextInt();
                    System.out.println("Write the movie's number of actors: ");
                    int actors = scanner.nextInt();
                    Actor[] actors1 = new Actor[actors];
                    for (int j = 0; j < actors; j++) {
                        System.out.println("Actor " + j + ": ");
                        System.out.println("    Write actor's first name: ");
                        String actor_first_name = scanner.next();
                        System.out.println("    Write actor's last name: ");
                        String actor_last_name = scanner.next();
                        System.out.println("    Write actor's age: ");
                        int actor_age = scanner.nextInt();
                        System.out.println("    Write actor's gender (M/F): ");
                        String line = scanner.next();
                        Gender gender = null;
                        while (!line.equalsIgnoreCase("M") && !line.equalsIgnoreCase("F")) {
                            System.out.println("    Invalid gender. Try again: ");
                            line = scanner.next();
                        }
                        if (line.equalsIgnoreCase("M"))
                            gender = Gender.M;
                        else if (line.equalsIgnoreCase("F"))
                            gender = Gender.F;
                        System.out.println("    Write actor's number of Oscar awards: ");
                        int actor_oscar_awards = scanner.nextInt();
                        Actor a = new Actor(actor_first_name, actor_last_name, actor_age, gender, actor_oscar_awards);
                        actors1[j] = a;
                    }
                    System.out.println("Write the movie's genre (Action, Comedy, Romance, Thriller, Other): ");
                    String line = scanner.next();
                    switch (line.toLowerCase()) {
                        case "action" -> {
                            System.out.println("    Write the movie's PEGI rating: ");
                            int pegi = scanner.nextInt();
                            boolean ok = false;
                            while (!ok) {
                                try {
                                    movie = new Action_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, pegi);
                                    ok = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("    Try again: ");
                                }
                            }
                        }
                        case "comedy" -> {
                            System.out.println("    Is the movie only for adults? (y/n) ");
                            line = scanner.next();
                            boolean only_for_adults = false;
                            while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                System.out.println("        Invalid choice. Try again: ");
                                line = scanner.next();
                            }
                            if (line.equalsIgnoreCase("y"))
                                only_for_adults = true;
                            System.out.println("    Is the movie romantic? (y/n) ");
                            line = scanner.next();
                            boolean is_romantic = false;
                            while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                System.out.println("        Invalid choice. Try again: ");
                                line = scanner.next();
                            }
                            if (line.equalsIgnoreCase("y"))
                                is_romantic = true;
                            movie = new Comedy_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, only_for_adults, is_romantic);
                        }
                        case "romance" -> {
                            System.out.println("    Does the movie have explicit nudity? (y/n) ");
                            line = scanner.next();
                            boolean explicit_nudity = false;
                            while (!line.equalsIgnoreCase("y") && !line.equalsIgnoreCase("n")) {
                                System.out.println("        Invalid choice. Try again: ");
                                line = scanner.next();
                            }
                            if (line.equalsIgnoreCase("y"))
                                explicit_nudity = true;
                            movie = new Romance_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, explicit_nudity);
                        }
                        case "thriller" -> {
                            System.out.println("    Write all the subgenres of the movie (separated by a comma): ");
                            scanner.nextLine();
                            line = scanner.nextLine();
                            String[] subgenres = line.split(",");
                            System.out.println("    Write the movie's PEGI rating: ");
                            int pegi1 = scanner.nextInt();
                            boolean ok1 = false;
                            while (!ok1) {
                                try {
                                    movie = new Thriller_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, subgenres, pegi1);
                                    ok1 = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("    Try again: ");
                                }
                            }
                        }
                        case "other" -> {
                            System.out.println("    Write the movie's genres (separated by comma): ");
                            scanner.nextLine();
                            line = scanner.nextLine();
                            String[] genres = line.split(",");
                            System.out.println("    Write the movie's PEGI rating: ");
                            int pegi2 = scanner.nextInt();
                            boolean ok2 = false;
                            while (!ok2) {
                                try {
                                    movie = new Other_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, genres, pegi2);
                                    ok2 = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("    Try again: ");
                                }
                            }
                        }
                        default -> System.out.println("Invalid genre!");
                    }
                    if (movie != null)
                        cinemaService.addMovie(c2, movie);
                }
                case 4 -> {
                    Cinema[] all_cinemas2 = cinemaService.getCinemas();
                    int i2 = 1;
                    for (Cinema c3 : all_cinemas2) {
                        System.out.println(i2 + ". " + c3.getName());
                        i2++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema2;
                    choice_cinema2 = scanner.nextInt();
                    try{
                        choice_cinema2 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema2 < 1 || choice_cinema2 > all_cinemas2.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema2 = scanner.nextInt();
                    }
                    Cinema c3 = all_cinemas2[choice_cinema2 - 1];
                    Movie[] listed_movies = c3.getListed_movies();
                    if(listed_movies.length == 0){
                        System.out.println("No movies to remove!");
                        break;
                    }
                    int j2 = 1;
                    for (Movie m : listed_movies) {
                        System.out.println(j2 + ". " + m.getTitle());
                        j2++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie;
                    choice_movie = scanner.nextInt();
                    try{
                        choice_movie = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > listed_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie m = listed_movies[choice_movie - 1];
                    cinemaService.removeMovie(c3, m);
                }
                case 5 -> {
                    Cinema[] all_cinemas3 = cinemaService.getCinemas();
                    int i3 = 1;
                    for (Cinema c4 : all_cinemas3) {
                        System.out.println(i3 + ". " + c4.getName());
                        i3++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema3;
                    choice_cinema3 = scanner.nextInt();
                    try{
                        choice_cinema3 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema3 < 1 || choice_cinema3 > all_cinemas3.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema3 = scanner.nextInt();
                    }
                    Cinema c4 = all_cinemas3[choice_cinema3 - 1];
                    Movie[] listed_movies2 = c4.getListed_movies();
                    if(listed_movies2.length == 0){
                        System.out.println("No movies to stream!");
                        break;
                    }
                    int j3 = 1;
                    for (Movie m2 : listed_movies2) {
                        System.out.println(j3 + ". " + m2.getTitle());
                        j3++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie2;
                    choice_movie2 = scanner.nextInt();
                    try{
                        choice_movie2 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie2 < 1 || choice_movie2 > listed_movies2.length) {
                        System.out.println("Invalid choice!");
                        choice_movie2 = scanner.nextInt();
                    }
                    Movie m2 = listed_movies2[choice_movie2 - 1];
                    System.out.println("Write the date and time of the streaming (yyyy-MM-dd/HH:mm): ");
                    String date_string = scanner.next();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm");
                    LocalDateTime date = null;
                    boolean ok = false;
                    while(!ok) {
                        try {
                            date = LocalDateTime.parse(date_string, formatter);
                            ok = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                            date_string = scanner.next();
                        }
                    }
                    System.out.println("Write the price of the streaming: ");
                    int price = scanner.nextInt();
                    System.out.println("Write the room of the streaming: ");
                    int room = scanner.nextInt();
                    cinemaService.streamMovie(c4, m2, date, room, price);
                }
                case 6 -> {
                    Cinema[] all_cinemas4 = cinemaService.getCinemas();
                    int i4 = 1;
                    for (Cinema c5 : all_cinemas4) {
                        System.out.println(i4 + ". " + c5.getName());
                        i4++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema4;
                    choice_cinema4 = scanner.nextInt();
                    try{
                        choice_cinema4 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema4 < 1 || choice_cinema4 > all_cinemas4.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema4 = scanner.nextInt();
                    }
                    Cinema c5 = all_cinemas4[choice_cinema4 - 1];
                    Movie[] streaming_movies = c5.getStreaming_dates().keySet().toArray(new Movie[0]);
                    if(streaming_movies.length == 0){
                        System.out.println("No movies are streaming at the moment!");
                        break;
                    }
                    int j4 = 1;
                    for (Movie m3 : streaming_movies) {
                        System.out.println(j4 + ". " + m3.getTitle());
                        j4++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie3;
                    choice_movie3 = scanner.nextInt();
                    try{
                        choice_movie3 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie3 < 1 || choice_movie3 > streaming_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie3 = scanner.nextInt();
                    }
                    Movie m3 = streaming_movies[choice_movie3 - 1];
                    LocalDateTime date_to_stop = c5.getStreaming_dates().get(m3);
                    cinemaService.stopStreaming(c5, m3, date_to_stop);
                }
                case 7 -> {
                    Cinema[] all_cinemas5 = cinemaService.getCinemas();
                    int i5 = 1;
                    for (Cinema c6 : all_cinemas5) {
                        System.out.println(i5 + ". " + c6.getName());
                        i5++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema5;
                    choice_cinema5 = scanner.nextInt();
                    try{
                        choice_cinema5 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema5 < 1 || choice_cinema5 > all_cinemas5.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema5 = scanner.nextInt();
                    }
                    Cinema c6 = all_cinemas5[choice_cinema5 - 1];
                    Movie[] streaming_movies2 = c6.getStreaming_dates().keySet().toArray(new Movie[0]);
                    if(streaming_movies2.length == 0){
                        System.out.println("No streaming movies!");
                        break;
                    }
                    int j5 = 1;
                    for (Movie m4 : streaming_movies2) {
                        System.out.println(j5 + ". " + m4.getTitle());
                        j5++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie4;
                    choice_movie4 = scanner.nextInt();
                    try{
                        choice_movie4 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie4 < 1 || choice_movie4 > streaming_movies2.length) {
                        System.out.println("Invalid choice!");
                        choice_movie4 = scanner.nextInt();
                    }
                    Movie m4 = streaming_movies2[choice_movie4 - 1];
                    System.out.println("Write the new price of the streaming: ");
                    int new_price = scanner.nextInt();
                    cinemaService.changePrice(c6, m4, new_price);
                }
                case 8 -> {
                    Cinema[] all_cinemas6 = cinemaService.getCinemas();
                    int i6 = 1;
                    for (Cinema c7 : all_cinemas6) {
                        System.out.println(i6 + ". " + c7.getName());
                        i6++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema6;
                    choice_cinema6 = scanner.nextInt();
                    try{
                        choice_cinema6 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema6 < 1 || choice_cinema6 > all_cinemas6.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema6 = scanner.nextInt();
                    }
                    Cinema c7 = all_cinemas6[choice_cinema6 - 1];
                    Movie[] streaming_movies3 = c7.getStreaming_dates().keySet().toArray(new Movie[0]);
                    if(streaming_movies3.length == 0){
                        System.out.println("No streaming movies");
                        break;
                    }
                    int j6 = 1;
                    for (Movie m5 : streaming_movies3) {
                        System.out.println(j6 + ". " + m5.getTitle());
                        j6++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie5;
                    choice_movie5 = scanner.nextInt();
                    try{
                        choice_movie5 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie5 < 1 || choice_movie5 > streaming_movies3.length) {
                        System.out.println("Invalid choice!");
                        choice_movie5 = scanner.nextInt();
                    }
                    Movie m5 = streaming_movies3[choice_movie5 - 1];
                    System.out.println("Write the new room of the streaming: ");
                    int new_room = scanner.nextInt();
                    cinemaService.changeRoom(c7, m5, new_room);
                }
                case 9 -> {
                    Cinema[] all_cinemas7 = cinemaService.getCinemas();
                    int i7 = 1;
                    for (Cinema c8 : all_cinemas7) {
                        System.out.println(i7 + ". " + c8.getName());
                        i7++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema7;
                    choice_cinema7 = scanner.nextInt();
                    try{
                        choice_cinema7 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_cinema7 < 1 || choice_cinema7 > all_cinemas7.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema7 = scanner.nextInt();
                    }
                    Cinema c8 = all_cinemas7[choice_cinema7 - 1];
                    Movie[] streaming_movies4 = c8.getStreaming_dates().keySet().toArray(new Movie[0]);
                    if(streaming_movies4.length == 0){
                        System.out.println("No movies are streaming in this cinema");
                        break;
                    }
                    int j7 = 1;
                    for (Movie m6 : streaming_movies4) {
                        System.out.println(j7 + ". " + m6.getTitle());
                        j7++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie6;
                    choice_movie6 = scanner.nextInt();
                    try{
                        choice_movie6 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        break;
                    }
                    while (choice_movie6 < 1 || choice_movie6 > streaming_movies4.length) {
                        System.out.println("Invalid choice!");
                        choice_movie6 = scanner.nextInt();
                    }
                    Movie m6 = streaming_movies4[choice_movie6 - 1];
                    System.out.println("Write the new date and time of the streaming (yyyy-MM-dd/HH:mm): ");
                    String date_string1 = scanner.next();
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm");
                    boolean valid_date1 = false;
                    while (!valid_date1) {
                        try {
                            LocalDateTime.parse(date_string1, formatter1);
                            valid_date1 = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                            date_string1 = scanner.next();
                        }
                    }
                    LocalDateTime date1 = LocalDateTime.parse(date_string1, formatter1);
                    cinemaService.changeDate(c8, m6, date1);
                }
                case 10 -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void listMenu(){
        if(customerService != null){
            System.out.println("1. List all cinemas");
            System.out.println("2. List all movies");
            System.out.println("3. List all streaming movies");
            System.out.println("4. List all movies from a cinema");
            System.out.println("5. List all streaming movies from a cinema");
            System.out.println("6. List all movies from a genre");
            System.out.println("7. List all streaming movies from a genre");
            System.out.println("8. List all movies from a genre from a cinema");
            System.out.println("9. List all streaming movies from a genre from a cinema");
            System.out.println("10. List all movies from an actor");
            System.out.println("11. List all streaming movies from an actor");
            System.out.println("12. List all movies from a date");
            System.out.println("13. List details about an actor");
            System.out.println("14. List details about a movie");
            System.out.println("15. Buy a ticket");
            System.out.println("16. Exit");
        }
        else if(cinemaService != null){
            System.out.println("1. Add a cinema");
            System.out.println("2. Remove a cinema");
            System.out.println("3. Add a movie");
            System.out.println("4. Remove a movie");
            System.out.println("5. Stream a movie");
            System.out.println("6. Stop streaming a movie");
            System.out.println("7. Change price of a movie");
            System.out.println("8. Change room of a movie");
            System.out.println("9. Change date of a movie");
            System.out.println("10. Exit");
        }
    }
}
