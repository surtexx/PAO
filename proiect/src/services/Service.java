package services;

import data_types.Gender;
import entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Service {
    private static final Scanner scanner = new Scanner(System.in);
    private static Service instance;
    private static CustomerService customerService;
    private static CinemaService cinemaService;
    Audit audit = Audit.getInstance();
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

    private boolean check_date_availability(Cinema c, Movie m, LocalDateTime actual_d, LocalDateTime d, int room){
        Movie[] dates = c.getStreaming_dates().keySet().toArray(new Movie[0]);
        for (Movie m1 : dates){
            if(m1.toString().equals(m.toString()) && c.getStreaming_dates().get(m1) == actual_d && c.getRoom_numbers().get(m1) == room)
                continue;
            if(room == c.getRoom_numbers().get(m1)){
                LocalDateTime date = c.getStreaming_dates().get(m1);
                if(date.compareTo(d) == 0)
                    return false;
                if(date.compareTo(d) < 0 && date.plusMinutes(m1.getDuration() + 15).compareTo(d) > 0)
                    return false;
                if(date.compareTo(d) > 0 && d.plusMinutes(m.getDuration() + 15).compareTo(date) > 0)
                    return false;
            }
        }
        return true;
    }

    public void menu(){
        while(true) {
            listMenu();
            chooseOption();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Press any key...");
            try {
                input.readLine();
            }
            catch (Exception ignored) {}
        }
    }

    private void chooseOption() {
        if(customerService != null) {
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid choice");
                scanner.nextLine();
                return;
            }
            switch (choice) {
                case 1 -> {
                    Cinema[] cinemas = customerService.getAllCinemas();
                    for (Cinema c : cinemas)
                        System.out.println("---- " + c.getName() + " ----");
                    audit.write("Listed all cinemas");
                }
                case 2 -> {
                    Movie[] movies = customerService.getAllMovies();
                    for (Movie m : movies)
                        System.out.println("---- " + m.getTitle() + " ----");
                    audit.write("Listed all movies");
                }
                case 3 -> {
                    HashMap<Movie, LocalDateTime> streaming_movies = customerService.getAllStreamingMovies();
                    for (Movie m : streaming_movies.keySet())
                        System.out.println("---- " + m.getTitle() + " (" + streaming_movies.get(m) + ") ----");
                    audit.write("Listed all streaming movies");
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
                        scanner.nextLine();
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
                    audit.write("Listed all movies from " + c.getName());
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
                        scanner.nextLine();
                        break;
                    }
                    while (choice_cinema2 < 1 || choice_cinema2 > all_cinemas2.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema2 = scanner.nextInt();
                    }
                    Cinema c2 = all_cinemas2[choice_cinema2 - 1];
                    HashMap<Movie, LocalDateTime> streaming_movies_from_cinema = customerService.getStreamingMoviesFromCinema(c2);
                    for (Movie m : streaming_movies_from_cinema.keySet())
                        System.out.println("---- " + m.getTitle() + " (" + streaming_movies_from_cinema.get(m) + ") ----");
                    audit.write("Listed all streaming movies from " + c2.getName());
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
                    audit.write("Listed all movies by genre " + genre);
                }
                case 7 -> {
                    System.out.println("Choose genre(Action, Comedy, Romance, Thriller, Other): ");
                    String genre2 = scanner.next();
                    HashMap<Movie, LocalDateTime> streaming_movies_by_genre = customerService.getAllStreamingMoviesByGenre(genre2);
                    if (streaming_movies_by_genre == null)
                        System.out.println("No movies found!");
                    else
                        for (Movie m : streaming_movies_by_genre.keySet())
                            System.out.println("---- " + m.getTitle() + " (" + streaming_movies_by_genre.get(m) + ") ----");
                    audit.write("Listed all streaming movies by genre " + genre2);
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
                        scanner.nextLine();
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
                    audit.write("Listed all movies by genre " + genre3 + " from " + c3.getName());
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
                        scanner.nextLine();
                        break;
                    }
                    while (choice_cinema4 < 1 || choice_cinema4 > all_cinemas4.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema4 = scanner.nextInt();
                    }
                    Cinema c4 = all_cinemas4[choice_cinema4 - 1];
                    System.out.println("Choose genre(Action, Comedy, Romance, Thriller, Other): ");
                    String genre4 = scanner.next();
                    HashMap<Movie, LocalDateTime> streaming_movies_by_genre_from_cinema = customerService.getStreamingMoviesByGenreFromCinema(c4, genre4);
                    if (streaming_movies_by_genre_from_cinema == null)
                        System.out.println("No movies found!");
                    else
                        for (Movie m : streaming_movies_by_genre_from_cinema.keySet())
                            System.out.println("---- " + m.getTitle() + " (" + streaming_movies_by_genre_from_cinema.get(m) + ") ----");
                    audit.write("Listed all streaming movies by genre " + genre4 + " from " + c4.getName());
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
                    audit.write("Listed all movies by actor " + actor_first_name + " " + actor_last_name);
                }
                case 11 -> {
                    System.out.println("Write actor's name: ");
                    String actor_first_name1 = scanner.next();
                    String actor_last_name1 = scanner.next();
                    HashMap<Movie, LocalDateTime> streaming_movies_by_actor = customerService.getStreamingMoviesByActor(actor_first_name1 + " " + actor_last_name1);
                    if (streaming_movies_by_actor.size() == 0)
                        System.out.println("No movies found with this actor!");
                    else
                        for (Movie m : streaming_movies_by_actor.keySet())
                            System.out.println("---- " + m.getTitle() + " (" + streaming_movies_by_actor.get(m) + ") ----");
                    audit.write("Listed all streaming movies by actor " + actor_first_name1 + " " + actor_last_name1);
                }
                case 12 -> {
                    System.out.println("Write date (yyyy-MM-dd): ");
                    boolean valid_date = false;
                    LocalDate date = null;
                    while(!valid_date)
                        try {
                            date = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            valid_date = true;
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                        }
                    HashMap<Movie, LocalDateTime> streaming_movies_by_date = customerService.getStreamingMoviesByDate(date);
                    if (streaming_movies_by_date.size() == 0)
                        System.out.println("No movies found in that date!");
                    else
                        for (Movie m : streaming_movies_by_date.keySet())
                            System.out.println("---- " + m.getTitle() + " (" + streaming_movies_by_date.get(m) + ") ----");
                    audit.write("Listed all streaming movies by date " + date);
                }
                case 13 -> {
                    System.out.println("Write actor's name: ");
                    String actor_first_name1 = scanner.next();
                    String actor_last_name1 = scanner.next();
                    Actor a = customerService.getDetailsAboutActor(actor_first_name1 + " " + actor_last_name1);
                    if(a == null)
                        System.out.println("No actor found with this name!");
                    else
                        System.out.println(a);
                    audit.write("Listed details about actor " + actor_first_name1 + " " + actor_last_name1);
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
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > all_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie movie = all_movies[choice_movie - 1];
                    System.out.println(customerService.getDetailsAboutMovie(movie.getTitle()));
                    audit.write("Listed details about movie " + movie.getTitle());
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
                        scanner.nextLine();
                        break;
                    }
                    HashMap<Movie, LocalDateTime> all_movies6 = customerService.getStreamingMoviesFromCinema(all_cinemas5[choice_cinema5 - 1]);
                    if (all_movies6.size() == 0) {
                        System.out.println("This cinema isn't streaming any movie at the moment! Please come again later");
                        break;
                    }
                    while (choice_cinema5 < 1 || choice_cinema5 > all_cinemas5.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema5 = scanner.nextInt();
                    }
                    Cinema c5 = all_cinemas5[choice_cinema5 - 1];
                    System.out.println("Choose movie: ");
                    Movie[] all_movies5 = customerService.getStreamingMoviesFromCinema(c5).keySet().toArray(new Movie[0]);
                    int i6 = 1;
                    for (Movie m5 : all_movies5) {
                        System.out.println(i6 + ". " + m5.getTitle() + " (" + c5.getStreaming_dates().get(m5) + ")");
                        i6++;
                    }
                    int choice_movie5;
                    try {
                        choice_movie5 = scanner.nextInt();
                    }
                    catch (Exception e){
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie5 < 1 || choice_movie5 > all_movies5.length) {
                        System.out.println("Invalid choice!");
                        choice_movie5 = scanner.nextInt();
                    }
                    Movie m5 = all_movies5[choice_movie5 - 1];
                    customerService.buyTicket(c5, m5);
                    audit.write("Bought ticket for movie " + m5.getTitle() + " from cinema " + c5.getName());
                }
                case 16 -> {
                    try {
                        audit.fileWriter.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
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
                scanner.nextLine();
                return;
            }
            switch (choice) {
                case 1 -> {
                    Cinema[] cinemas = cinemaService.getCinemas();
                    for (Cinema c : cinemas)
                        System.out.println("---- " + c.getName() + " ----");
                    audit.write("Listed all cinemas");
                }
                case 2 -> {
                    Movie[] movies = cinemaService.getMovies();
                    for (Movie m : movies)
                        System.out.println("---- " + m.getTitle() + " ----");
                    audit.write("Listed all movies");
                }
                case 3-> {
                    Actor[] actors = cinemaService.getActors();
                    for (Actor a : actors)
                        System.out.println("---- " + a.getName() + " ----");
                    audit.write("Listed all actors");
                }
                case 4 -> {
                    System.out.println("Write cinema's name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    Movie[] movies = cinemaService.getMovies();
                    for (int i=0;i<movies.length;i++)
                        System.out.println(i+1 + ". " + movies[i].getTitle());
                    System.out.println("Write movies IDs for the cinema (one space between each ID): ");
                    String[] movies_ids = scanner.nextLine().split(" ");
                    Set<String> movies_ids_set = new HashSet<>(Arrays.asList(movies_ids));
                    Movie[] movies1 = new Movie[movies_ids_set.size()];
                    int j = 0;
                    for (String s : movies_ids_set) {
                        movies1[j] = movies[Integer.parseInt(s) - 1];
                        j++;
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
                    audit.write("Added cinema " + c.getName());
                }
                case 5 -> {
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
                        scanner.nextLine();
                        break;
                    }
                    while (choice_cinema < 1 || choice_cinema > all_cinemas.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema = scanner.nextInt();
                    }
                    Cinema c1 = all_cinemas[choice_cinema - 1];
                    cinemaService.removeCinema(c1);
                    audit.write("Removed cinema " + c1.getName());
                }
                case 6 ->{
                    System.out.println("Write the movie's type (action, comedy, romance, thriller, other): ");
                    scanner.nextLine();
                    String type;
                    do {
                        type = scanner.nextLine();
                        if (!type.equalsIgnoreCase("action") && !type.equalsIgnoreCase("comedy") && !type.equalsIgnoreCase("romance") && !type.equalsIgnoreCase("other") && !type.equalsIgnoreCase("thriller"))
                            System.out.println("Invalid type! Please try again");
                    }while (!type.equalsIgnoreCase("action") && !type.equalsIgnoreCase("comedy") && !type.equalsIgnoreCase("romance") && !type.equalsIgnoreCase("other") && !type.equalsIgnoreCase("thriller"));

                    System.out.println("Write movie's name: ");
                    String movie_title = scanner.nextLine();
                    System.out.println("Write the movie director's name: ");
                    String director_name = scanner.nextLine();
                    System.out.println("Write the year the movie got released: ");
                    int year = scanner.nextInt();
                    System.out.println("Write the movie's duration: ");
                    int duration = scanner.nextInt();
                    System.out.println("Write the movie's rating: ");
                    double rating = scanner.nextDouble();
                    System.out.println("Write the movie's number of Oscar awards: ");
                    int oscar_awards = scanner.nextInt();
                    Actor[] actors = cinemaService.getActors();
                    for (int i=0;i<actors.length;i++)
                        System.out.println(i+1 + ". " + actors[i].getName());
                    System.out.println("Write actors IDs for the movie (one space between each ID): ");
                    scanner.nextLine();
                    String[] actors_ids = scanner.nextLine().split(" ");
                    Set<String> actors_ids_set = new HashSet<>(Arrays.asList(actors_ids));
                    Actor[] actors1 = new Actor[actors_ids_set.size()];
                    int j = 0;
                    for (String s : actors_ids_set) {
                        actors1[j] = actors[Integer.parseInt(s) - 1];
                        j++;
                    }
                    Movie m;
                    switch (type) {
                        case "action" -> {
                            int pegi_rating;
                            System.out.println("Write the movie's PEGI rating: ");
                            do {
                                try {
                                    pegi_rating = scanner.nextInt();
                                    m = new Action_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, pegi_rating);
                                    break;
                                }
                                catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (true);
                        }
                        case "comedy" -> {
                            boolean adults_only = false;
                            boolean romance = false;
                            System.out.println("Is the movie for adults only? (y/n): ");
                            String answer;
                            do {
                                answer = scanner.nextLine();
                                if (answer.equalsIgnoreCase("y"))
                                    adults_only = true;
                                else if (!answer.equalsIgnoreCase("n")) {
                                    System.out.println("Invalid answer! Please try again");
                                }
                            }while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
                            System.out.println("Is the movie a romantic comedy? (y/n): ");
                            do {
                                answer = scanner.nextLine();
                                if (answer.equalsIgnoreCase("y"))
                                    romance = true;
                                else if (!answer.equalsIgnoreCase("n"))
                                    System.out.println("Invalid answer! Please try again");
                                }while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
                                m = new Comedy_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, adults_only, romance);
                            }
                        case "romance" -> {
                            boolean explicit_nudity = false;
                            System.out.println("Does the movie contain explicit nudity? (y/n): ");
                            String answer;
                            do {
                                answer = scanner.nextLine();
                                if (answer.equalsIgnoreCase("y"))
                                    explicit_nudity = true;
                                else if (!answer.equalsIgnoreCase("n"))
                                    System.out.println("Invalid answer! Please try again");
                            } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
                            m = new Romance_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, explicit_nudity);
                        }
                        case "thriller" -> {
                            String[] subgenres;
                            int pegi_rating;
                            System.out.println("Write the movie's subgenres (one space between each subgenre): ");
                            scanner.nextLine();
                            subgenres = scanner.nextLine().split(" ");
                            System.out.println("Write the movie's PEGI rating: ");
                            do {
                                try {
                                    pegi_rating = scanner.nextInt();
                                    m = new Thriller_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, subgenres, pegi_rating);
                                    break;
                                }
                                catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (true);
                        }
                        default -> {
                            String[] genres;
                            int pegi_rating;
                            System.out.println("Write the movie's subgenres (one space between each subgenre): ");
                            genres = scanner.nextLine().split(" ");
                            System.out.println("Write the movie's PEGI rating: ");
                            do {
                                try {
                                    pegi_rating = scanner.nextInt();
                                    m = new Other_Movie(movie_title, director_name, year, duration, actors1, rating, oscar_awards, genres, pegi_rating);
                                    break;
                                }
                                catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (true);
                        }
                    }
                    cinemaService.addMovie(m);
                    audit.write("Added movie " + m.getTitle());
                }

                case 7 -> {
                    Movie[] movies = cinemaService.getMovies();
                    int i = 1;
                    for (Movie m : movies) {
                        System.out.println(i + ". " + m.getTitle());
                        i++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie;
                    try{
                        choice_movie = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie m = movies[choice_movie - 1];
                    cinemaService.removeMovie(m);
                    audit.write("Removed movie " + m.getTitle());
                }

                case 8 -> {
                    System.out.println("Write actor's first name: ");
                    String actor_first_name = scanner.next();
                    System.out.println("Write actor's last name: ");
                    String actor_last_name = scanner.next();
                    System.out.println("Write actor's age: ");
                    int actor_age = scanner.nextInt();
                    System.out.println("Write actor's gender (M/F): ");
                    String line = scanner.next();
                    Gender gender = null;
                    while (!line.equalsIgnoreCase("M") && !line.equalsIgnoreCase("F")) {
                        System.out.println("Invalid gender. Try again: ");
                        line = scanner.next();
                    }
                    if (line.equalsIgnoreCase("M"))
                        gender = Gender.M;
                    else if (line.equalsIgnoreCase("F"))
                        gender = Gender.F;
                    System.out.println("Write actor's number of Oscar awards: ");
                    int actor_oscar_awards = scanner.nextInt();
                    Actor a = new Actor(actor_first_name, actor_last_name, actor_age, gender, actor_oscar_awards);
                    cinemaService.addActor(a);
                    audit.write("Added actor " + a.getName());
                }

                case 9 ->{
                    Actor[] actors = cinemaService.getActors();
                    int i = 1;
                    for (Actor a : actors) {
                        System.out.println(i + ". " + a.getName());
                        i++;
                    }
                    System.out.println("Choose actor: ");
                    int choice_actor;
                    try{
                        choice_actor = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_actor < 1 || choice_actor > actors.length) {
                        System.out.println("Invalid choice!");
                        choice_actor = scanner.nextInt();
                    }
                    Actor a = actors[choice_actor - 1];
                    cinemaService.removeActor(a);
                    audit.write("Removed actor " + a.getName());
                }

                case 10 -> {
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
                        scanner.nextLine();
                        break;
                    }
                    while (choice_cinema1 < 1 || choice_cinema1 > all_cinemas1.length) {
                        System.out.println("Invalid choice!");
                        choice_cinema1 = scanner.nextInt();
                    }
                    Cinema c2 = all_cinemas1[choice_cinema1 - 1];
                    Movie[] movies = cinemaService.getMovies();
                    for (int i = 0; i < movies.length; i++)
                        System.out.println(i + 1 + ". " + movies[i].getTitle());
                    System.out.println("Choose movie: ");
                    int choice_movie;
                    try{
                        choice_movie = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie movie = movies[choice_movie - 1];
                    if (movie != null){
                        cinemaService.addMovieToCinema(c2, movie);
                        audit.write("Added movie " + movie.getTitle() + " to cinema " + c2.getName());
                    }
                }
                case 11 -> {
                    Cinema[] all_cinemas2 = cinemaService.getCinemas();
                    int i2 = 1;
                    for (Cinema c3 : all_cinemas2) {
                        System.out.println(i2 + ". " + c3.getName());
                        i2++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema2;
                    try{
                        choice_cinema2 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                    try{
                        choice_movie = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie < 1 || choice_movie > listed_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie = scanner.nextInt();
                    }
                    Movie m = listed_movies[choice_movie - 1];
                    cinemaService.removeMovieFromCinema(c3, m);
                    audit.write("Removed movie " + m.getTitle() + " from cinema " + c3.getName());
                }
                case 12 -> {
                    Cinema[] all_cinemas3 = cinemaService.getCinemas();
                    int i3 = 1;
                    for (Cinema c4 : all_cinemas3) {
                        System.out.println(i3 + ". " + c4.getName());
                        i3++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema3;
                    try{
                        choice_cinema3 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                    try{
                        choice_movie2 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                            do {
                                date = LocalDateTime.parse(date_string, formatter);
                                if (date.isBefore(LocalDateTime.now())){
                                    System.out.println("Invalid date! You must choose a date in the future!");
                                    date_string = scanner.next();
                                }
                                else
                                    ok = true;
                            } while (date.isBefore(LocalDateTime.now()));
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                            date_string = scanner.next();
                        }
                    }
                    System.out.println("Write the price of the streaming: ");
                    int price = scanner.nextInt();
                    int room;
                    System.out.println("Write the room number: ");
                    do {
                        room = scanner.nextInt();
                        if(room < 1 || room > c4.getNr_seats().length)
                            System.out.printf("Invalid room number! You must choose between 1 and %d\n", c4.getNr_seats().length);
                    }while(room < 1 || room > c4.getNr_seats().length);
                    if(!check_date_availability(c4, m2, c4.getStreaming_dates().get(m2), date, room))
                        System.out.println("The room is not available at this date and time!");
                    else
                    {
                        try{
                            cinemaService.streamMovie(c4, m2, date, room, price);
                            audit.write("Streamed movie " + m2.getTitle() + " in cinema " + c4.getName());
                        }
                        catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            scanner.nextLine();
                        }
                    }
                }
                case 13 -> {
                    Cinema[] all_cinemas4 = cinemaService.getCinemas();
                    int i4 = 1;
                    for (Cinema c5 : all_cinemas4) {
                        System.out.println(i4 + ". " + c5.getName());
                        i4++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema4;
                    try{
                        choice_cinema4 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                        System.out.println(j4 + ". " + m3.getTitle() + " " + c5.getStreaming_dates().get(m3));
                        j4++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie3;
                    try{
                        choice_movie3 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie3 < 1 || choice_movie3 > streaming_movies.length) {
                        System.out.println("Invalid choice!");
                        choice_movie3 = scanner.nextInt();
                    }
                    Movie m3 = streaming_movies[choice_movie3 - 1];
                    LocalDateTime date_to_stop = c5.getStreaming_dates().get(m3);
                    try {
                        cinemaService.stopStreaming(c5, m3, date_to_stop);
                        audit.write("Stopped streaming movie " + m3.getTitle() + " in cinema " + c5.getName());
                    }
                    catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                }
                case 14 -> {
                    Cinema[] all_cinemas5 = cinemaService.getCinemas();
                    int i5 = 1;
                    for (Cinema c6 : all_cinemas5) {
                        System.out.println(i5 + ". " + c6.getName());
                        i5++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema5;
                    try{
                        choice_cinema5 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                        System.out.println(j5 + ". " + m4.getTitle() + " " + c6.getStreaming_dates().get(m4));
                        j5++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie4;
                    try{
                        choice_movie4 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie4 < 1 || choice_movie4 > streaming_movies2.length) {
                        System.out.println("Invalid choice!");
                        choice_movie4 = scanner.nextInt();
                    }
                    Movie m4 = streaming_movies2[choice_movie4 - 1];
                    System.out.println("Write the new price of the streaming: ");
                    int new_price = scanner.nextInt();
                    try{
                        cinemaService.changePrice(c6, m4, new_price);
                        audit.write("Changed price of movie " + m4.getTitle() + " in cinema " + c6.getName());
                    }
                    catch(IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                }
                case 15 -> {
                    Cinema[] all_cinemas6 = cinemaService.getCinemas();
                    int i6 = 1;
                    for (Cinema c7 : all_cinemas6) {
                        System.out.println(i6 + ". " + c7.getName());
                        i6++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema6;
                    try{
                        choice_cinema6 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                        System.out.println(j6 + ". " + m5.getTitle() + " " + c7.getStreaming_dates().get(m5));
                        j6++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie5;
                    try{
                        choice_movie5 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
                        break;
                    }
                    while (choice_movie5 < 1 || choice_movie5 > streaming_movies3.length) {
                        System.out.println("Invalid choice!");
                        choice_movie5 = scanner.nextInt();
                    }
                    Movie m5 = streaming_movies3[choice_movie5 - 1];
                    System.out.println("Write the new room of the streaming: ");
                    int new_room;
                    do{
                        new_room = scanner.nextInt();
                        if(new_room < 1 || new_room > c7.getNr_seats().length)
                            System.out.printf("Invalid room number! You must choose between 1 and %d\n",
                                    c7.getNr_seats().length);
                    }while(new_room < 1 || new_room > c7.getNr_seats().length);
                    if(!check_date_availability(c7, m5, c7.getStreaming_dates().get(m5), c7.getStreaming_dates().get(m5), new_room))
                        System.out.println("The room is not available at this date and time!");
                    else{
                        try{
                            cinemaService.changeRoom(c7, m5, new_room);
                            audit.write("Changed room of movie " + m5.getTitle() + " in cinema " + c7.getName());
                        }
                        catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            scanner.nextLine();
                        }
                    }
                }
                case 16 -> {
                    Cinema[] all_cinemas7 = cinemaService.getCinemas();
                    int i7 = 1;
                    for (Cinema c8 : all_cinemas7) {
                        System.out.println(i7 + ". " + c8.getName());
                        i7++;
                    }
                    System.out.println("Choose cinema: ");
                    int choice_cinema7;
                    try{
                        choice_cinema7 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                        System.out.println(j7 + ". " + m6.getTitle() + " " + c8.getStreaming_dates().get(m6));
                        j7++;
                    }
                    System.out.println("Choose movie: ");
                    int choice_movie6;
                    try{
                        choice_movie6 = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid choice");
                        scanner.nextLine();
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
                    LocalDateTime date = null;
                    boolean ok = false;
                    while(!ok) {
                        try {
                            do {
                                date = LocalDateTime.parse(date_string1, formatter1);
                                if (date.isBefore(LocalDateTime.now())){
                                    System.out.println("Invalid date! You must choose a date in the future!");
                                    date_string1 = scanner.next();
                                }
                                else
                                    ok = true;
                            } while (date.isBefore(LocalDateTime.now()));
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date!");
                            System.out.println("Try again: ");
                            date_string1 = scanner.next();
                        }
                    }
                    if(!check_date_availability(c8, m6, c8.getStreaming_dates().get(m6), date, c8.getRoom_numbers().get(m6)))
                        System.out.println("The room is not available at this date and time!");
                    else{
                        try{
                            cinemaService.changeDate(c8, m6, date);
                            audit.write("Changed date of movie " + m6.getTitle() + " in cinema " + c8.getName());
                        }
                        catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            scanner.nextLine();
                        }
                    }
                }
                case 17 -> {
                    try {
                        audit.fileWriter.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
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
            System.out.println("12. List all streaming movies from a date");
            System.out.println("13. List details about an actor");
            System.out.println("14. List details about a movie");
            System.out.println("15. Buy a ticket");
            System.out.println("16. Exit");
        }
        else if(cinemaService != null){
            System.out.println("1. List all cinemas");
            System.out.println("2. List all movies");
            System.out.println("3. List all actors");
            System.out.println("4. Add a cinema");
            System.out.println("5. Remove a cinema");
            System.out.println("6. Add a movie");
            System.out.println("7. Remove a movie");
            System.out.println("8. Add an actor");
            System.out.println("9. Remove an actor");
            System.out.println("10. Add a movie to a cinema");
            System.out.println("11. Remove a movie from a cinema");
            System.out.println("12. Stream a movie");
            System.out.println("13. Stop streaming a movie");
            System.out.println("14. Change price of a movie");
            System.out.println("15. Change room of a movie");
            System.out.println("16. Change date of a movie");
            System.out.println("17. Exit");
        }
    }
}
