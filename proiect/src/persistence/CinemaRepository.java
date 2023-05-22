package persistence;

import entities.*;
import data_types.Gender;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CinemaRepository extends BaseRepository {
    private final String cinemasSelectSQL = "SELECT * from cinemas";
    private final String moviesSelectSQL = "SELECT * from movies";
    private final String actorsSelectSQL = "SELECT * from actors";
    private final String cinemasMoviesSelectSQL = "SELECT m.* " +
            "FROM movies m " +
            "JOIN cinema_movies cm ON m.id = cm.id_movie " +
            "WHERE cm.id_cinema = ?";
    private final String cinemasSeatsSelectSQL = "SELECT seats_number FROM cinema_seats_per_room WHERE id_cinema = ?";
    private final String actorsFromMovieSelectSQL = "SELECT a.* " +
            "FROM actors a " +
            "JOIN movie_actors ma ON a.id = ma.id_actor " +
            "WHERE ma.id_movie = ?";

    private final String streamingMoviesSQL = "SELECT * FROM streaming_movies where id_cinema = ?";
    private final String cinemasInsertSQL = "INSERT INTO cinemas (name, nr_rooms) VALUES (?, ?)";
    private final String cinemasMoviesInsertSQL = "INSERT INTO cinema_movies (id_cinema, id_movie) VALUES (?, ?)";
    private final String cinemasSeatsInsertSQL = "INSERT INTO cinema_seats_per_room (id_cinema, room_number, seats_number) VALUES (?, ?, ?)";
    private final String actionMovieInsertSQL = "INSERT INTO movies (title, director, release_year, duration, " +
            "rating, oscar_awards, type, pegi_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String comedyMovieInsertSQL = "INSERT INTO movies (title, director, release_year, duration, " +
            "rating, oscar_awards, type, adults_only, romance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String romanceMovieInsertSQL = "INSERT INTO movies (title, director, release_year, duration, " +
            "rating, oscar_awards, type, explicit_nudity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String thrillerMovieInsertSQL = "INSERT INTO movies (title, director, release_year, duration, " +
            "rating, oscar_awards, type, subgenres, pegi_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String otherMovieInsertSQL = "INSERT INTO movies (title, director, release_year, duration, " +
            "rating, oscar_awards, type, genres, pegi_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String movieStreamInsertSQL = "INSERT INTO streaming_movies (id_cinema, id_movie, date, price, room, seats_available) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final String actorAddSQL = "INSERT INTO actors (first_name, last_name, age, gender, oscar_awards) " +
            "VALUES (?, ?, ?, ?, ?)";

    private final String insertMovieActorsSQL = "INSERT INTO movie_actors (id_movie, id_actor) VALUES (?, ?)";

    private final String priceUpdateSQL = "UPDATE streaming_movies SET price = ? WHERE id_cinema = ? AND id_movie = ?";

    private final String roomUpdateSQL = "UPDATE streaming_movies SET room = ?, seats_available = ? WHERE id_cinema = ? AND id_movie = ?";

    private final String dateUpdateSQL = "UPDATE streaming_movies SET date = ? WHERE id_cinema = ? AND id_movie = ?";


    private final String deleteMovieActorsSQL = "DELETE FROM movie_actors WHERE id_movie = ?";
    private final String movieDeleteSQL = "DELETE FROM cinema_movies WHERE id_cinema = ? AND id_movie = ?";
    private final String allMovieDeleteSQL = "DELETE FROM cinema_movies WHERE id_movie = ?";
    private final String allStreamingMovieDeleteSQL = "DELETE FROM streaming_movies WHERE id_movie = ?";
    private final String streamingMovieDeleteSQL = "DELETE FROM streaming_movies WHERE id_cinema = ? AND id_movie = ?";
    private final String movieStreamDeleteSQL = "DELETE FROM streaming_movies WHERE id_cinema = ? AND id_movie = ? AND date = ?";
    private final String cinemasDeleteSQL = "DELETE FROM cinemas WHERE id = ?";
    private final String cinemasMoviesDeleteSQL = "DELETE FROM cinema_movies WHERE id_cinema = ?";
    private final String cinemasSeatsDeleteSQL = "DELETE FROM cinema_seats_per_room WHERE id_cinema = ?";
    private final String removeActorSQL = "DELETE FROM actors WHERE id = ?";
    private final String movieRemoveSQL = "DELETE FROM movies WHERE id = ?";
    private final String actorMovieRemoveSQL = "DELETE FROM movie_actors WHERE id_actor = ?";

    private final String FIX_INCREMENT_MOVIES = "alter table movies AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_ACTORS = "alter table actors AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_CINEMAS = "alter table cinemas AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_CINEMA_MOVIES = "alter table cinema_movies AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_CINEMA_SEATS = "alter table cinema_seats_per_room AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_STREAMING_MOVIES = "alter table streaming_movies AUTO_INCREMENT = 1";
    private final String FIX_INCREMENT_MOVIE_ACTORS = "alter table movie_actors AUTO_INCREMENT = 1";

    // Metoda pentru a obtine numarul de randuri dintr-un ResultSet
    private int getResultSetRowCount(ResultSet resultSet) throws SQLException {
        int rowCount = 0;
        if (resultSet.last()) {
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }
        return rowCount;
    }

    // Metoda pentru a inchide ResultSet
    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Metoda pentru a inchide PreparedStatement
    private void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cinema[] getCinemas() {
        PreparedStatement cinemasStatement = null;
        PreparedStatement moviesStatement = null;
        PreparedStatement seatsStatement = null;
        PreparedStatement actorsStatement = null;
        PreparedStatement streamingStatement = null;
        ResultSet cinemasResult = null;
        ResultSet moviesResult = null;
        ResultSet seatsResult = null;
        ResultSet actorsResult = null;
        ResultSet streamingResultSet = null;

        try {
            cinemasStatement = db.prepareStatement(cinemasSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cinemasResult = cinemasStatement.executeQuery();

            // Construieste un vector de obiecte Cinema
            int cinemaCount = getResultSetRowCount(cinemasResult);
            Cinema[] cinemas = new Cinema[cinemaCount];
            int cinemaIndex = 0;

            while (cinemasResult.next()) {
                HashMap<Movie, Integer> prices = new HashMap<>();
                HashMap<Movie, Integer> rooms = new HashMap<>();
                HashMap<Movie, Integer> seats_available = new HashMap<>();
                HashMap<Movie, LocalDateTime> dates = new HashMap<>();

                int cinemaId = cinemasResult.getInt("id");
                String cinemaName = cinemasResult.getString("name");

                // Obtine lista de filme pentru fiecare cinematograf
                moviesStatement = db.prepareStatement(cinemasMoviesSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                moviesStatement.setInt(1, cinemaId);
                moviesResult = moviesStatement.executeQuery();

                // Construieste un vector de obiecte Movie
                int movieCount = getResultSetRowCount(moviesResult);
                Movie[] movies = new Movie[movieCount];
                int movieIndex = 0;

                while (moviesResult.next()) {
                    int movieId = moviesResult.getInt("id");
                    String title = moviesResult.getString("title");
                    String director = moviesResult.getString("director");
                    int releaseYear = moviesResult.getInt("release_year");
                    int duration = moviesResult.getInt("duration");
                    double rating = moviesResult.getDouble("rating");
                    int oscarAwards = moviesResult.getInt("oscar_awards");
                    String type = moviesResult.getString("type");
                    int pegiRating = moviesResult.getInt("pegi_rating");
                    boolean adultsOnly = moviesResult.getBoolean("adults_only");
                    boolean romance = moviesResult.getBoolean("romance");
                    boolean explicitNudity = moviesResult.getBoolean("explicit_nudity");
                    String[] subgenres = null;
                    String[] genres = null;
                    try {
                        genres = moviesResult.getString("genres").split(", ");

                    } catch (NullPointerException ignored) {
                        ;
                    }
                    try {
                        subgenres = moviesResult.getString("subgenres").split(", ");
                    } catch (NullPointerException ignored) {
                        ;
                    }

                    // Obtine lista de actori pentru fiecare film
                    actorsStatement = db.prepareStatement(actorsFromMovieSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    actorsStatement.setInt(1, movieId);
                    actorsResult = actorsStatement.executeQuery();

                    // Construieste un vector de obiecte Actor
                    int actorCount = getResultSetRowCount(actorsResult);
                    Actor[] actors = new Actor[actorCount];
                    int actorIndex = 0;

                    while (actorsResult.next()) {
                        int actorId = actorsResult.getInt("id");
                        String firstName = actorsResult.getString("first_name");
                        String lastName = actorsResult.getString("last_name");
                        int age = actorsResult.getInt("age");
                        String gender_str = actorsResult.getString("gender");
                        Gender gender = null;
                        if (gender_str.equals("M"))
                            gender = Gender.M;
                        else
                            gender = Gender.F;
                        int oscarAwardsActor = actorsResult.getInt("oscar_awards");

                        actors[actorIndex] = new Actor(firstName,
                                lastName, age, gender, oscarAwardsActor);
                        actorIndex++;
                    }
                    // Adauga obiectul Movie in vectorul de filme
                    switch (type) {
                        case "action" ->
                                movies[movieIndex] = new Action_Movie(title, director, releaseYear, duration, actors, rating,
                                        oscarAwards, pegiRating);
                        case "comedy" ->
                                movies[movieIndex] = new Comedy_Movie(title, director, releaseYear, duration, actors, rating,
                                        oscarAwards, adultsOnly, romance);
                        case "romance" -> movies[movieIndex] = new Romance_Movie(title, director, releaseYear, duration, actors, rating,
                                oscarAwards, explicitNudity);
                        case "thriller" -> movies[movieIndex] = new Thriller_Movie(title, director, releaseYear, duration, actors, rating,
                                oscarAwards, subgenres, pegiRating);
                        default -> movies[movieIndex] = new Other_Movie(title, director, releaseYear, duration, actors, rating,
                                oscarAwards, genres, pegiRating);
                    }
                    movieIndex++;
                }

                // Obtine lista de locuri pentru fiecare cinematograf
                seatsStatement = db.prepareStatement(cinemasSeatsSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                seatsStatement.setInt(1, cinemaId);
                seatsResult = seatsStatement.executeQuery();

                // Construieste un vector de locuri
                int seatsCount = getResultSetRowCount(seatsResult);
                int[] seats = new int[seatsCount];
                int seatsIndex = 0;

                while (seatsResult.next()) {
                    int seatsNumber = seatsResult.getInt("seats_number");

                    // Adauga numarul de locuri in vectorul de locuri
                    seats[seatsIndex] = seatsNumber;
                    seatsIndex++;
                }

                // Adauga obiectul Cinema in vectorul de cinematografe
                cinemas[cinemaIndex] = new Cinema(cinemaName, movies, seats.length, seats);
                cinemaIndex++;

                Movie[] allMovies = getMovies();
                if(allMovies.length == 0)
                    continue;
                streamingStatement = db.prepareStatement(streamingMoviesSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                streamingStatement.setInt(1, cinemaId);
                streamingResultSet = streamingStatement.executeQuery();
                while (streamingResultSet.next()) {
                    int movieIdStreaming = streamingResultSet.getInt("id_movie");
                    int price = streamingResultSet.getInt("price");
                    int room = streamingResultSet.getInt("room");
                    int seats_av = streamingResultSet.getInt("seats_available");
                    LocalDateTime date = streamingResultSet.getTimestamp("date").toLocalDateTime();

                    prices.put(allMovies[movieIdStreaming-1], price);
                    rooms.put(allMovies[movieIdStreaming-1], room);
                    seats_available.put(allMovies[movieIdStreaming-1], seats_av);
                    dates.put(allMovies[movieIdStreaming-1], date);
                }
                cinemas[cinemaIndex - 1].setPrices(prices);
                cinemas[cinemaIndex - 1].setRoom_numbers(rooms);
                cinemas[cinemaIndex - 1].setSeats_available(seats_available);
                cinemas[cinemaIndex - 1].setStreaming_dates(dates);
            }

            return cinemas;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(cinemasResult);
            closeResultSet(moviesResult);
            closeResultSet(seatsResult);
            closeResultSet(actorsResult);
            closeStatement(cinemasStatement);
            closeStatement(moviesStatement);
            closeStatement(seatsStatement);
            closeStatement(actorsStatement);
        }

        return null;
    }

    public Movie[] getMovies(){
        PreparedStatement moviesStatement = null;
        PreparedStatement actorsStatement = null;
        ResultSet moviesResult = null;
        ResultSet actorsResult = null;

        try {
            moviesStatement = db.prepareStatement(moviesSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            moviesResult = moviesStatement.executeQuery();

            // Construiește un vector de obiecte Movie
            int movieCount = getResultSetRowCount(moviesResult);
            Movie[] movies = new Movie[movieCount];
            int movieIndex = 0;

            while (moviesResult.next()) {
                int movieId = moviesResult.getInt("id");
                String title = moviesResult.getString("title");
                String director = moviesResult.getString("director");
                int releaseYear = moviesResult.getInt("release_year");
                int duration = moviesResult.getInt("duration");
                double rating = moviesResult.getDouble("rating");
                int oscarAwards = moviesResult.getInt("oscar_awards");
                String type = moviesResult.getString("type");
                int pegiRating = moviesResult.getInt("pegi_rating");
                boolean adultsOnly = moviesResult.getBoolean("adults_only");
                boolean romance = moviesResult.getBoolean("romance");
                boolean explicitNudity = moviesResult.getBoolean("explicit_nudity");
                String[] subgenres = null;
                String[] genres = null;
                try {
                    genres = moviesResult.getString("genres").split(", ");

                } catch (NullPointerException ignored) {
                    ;
                }
                try {
                    subgenres = moviesResult.getString("subgenres").split(", ");
                } catch (NullPointerException ignored) {
                    ;
                }


                // Obține lista de actori pentru fiecare film
                actorsStatement = db.prepareStatement(actorsFromMovieSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                actorsStatement.setInt(1, movieId);
                actorsResult = actorsStatement.executeQuery();

                // Construiește un vector de obiecte Actor
                int actorCount = getResultSetRowCount(actorsResult);
                Actor[] actors = new Actor[actorCount];
                int actorIndex = 0;

                while (actorsResult.next()) {
                    int actorId = actorsResult.getInt("id");
                    String firstName = actorsResult.getString("first_name");
                    String lastName = actorsResult.getString("last_name");
                    int age = actorsResult.getInt("age");
                    String gender_str = actorsResult.getString("gender");
                    Gender gender = null;
                    if (gender_str.equals("M"))
                        gender = Gender.M;
                    else
                        gender = Gender.F;
                    int oscarAwardsActor = actorsResult.getInt("oscar_awards");

                    actors[actorIndex] = new Actor(firstName, lastName, age, gender, oscarAwardsActor);
                    actorIndex++;
                }

                // Adaugă obiectul Movie în vectorul de filme
                switch (type) {
                    case "action" ->
                            movies[movieIndex] = new Action_Movie(title, director, releaseYear, duration, actors, rating,
                                    oscarAwards, pegiRating);
                    case "comedy" ->
                            movies[movieIndex] = new Comedy_Movie(title, director, releaseYear, duration, actors, rating,
                                    oscarAwards, adultsOnly, romance);
                    case "romance" ->
                            movies[movieIndex] = new Romance_Movie(title, director, releaseYear, duration, actors, rating,
                                    oscarAwards, explicitNudity);
                    case "thriller" ->
                            movies[movieIndex] = new Thriller_Movie(title, director, releaseYear, duration, actors, rating,
                                    oscarAwards, subgenres, pegiRating);
                    default ->
                            movies[movieIndex] = new Other_Movie(title, director, releaseYear, duration, actors, rating,
                                    oscarAwards, genres, pegiRating);
                }

                movieIndex++;
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(actorsResult);
            closeResultSet(moviesResult);
            closeStatement(actorsStatement);
            closeStatement(moviesStatement);
        }

        return null;
    }

    public Actor[] getActors(){
        PreparedStatement actorsStatement = null;
        ResultSet actorsResult = null;

        try {
            actorsStatement = db.prepareStatement(actorsSelectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            actorsResult = actorsStatement.executeQuery();

            // Construiește un vector de obiecte Actor
            int actorCount = getResultSetRowCount(actorsResult);
            Actor[] actors = new Actor[actorCount];
            int actorIndex = 0;

            while (actorsResult.next()) {
                int actorId = actorsResult.getInt("id");
                String firstName = actorsResult.getString("first_name");
                String lastName = actorsResult.getString("last_name");
                int age = actorsResult.getInt("age");
                String gender_str = actorsResult.getString("gender");
                Gender gender = null;
                if (gender_str.equals("M"))
                    gender = Gender.M;
                else
                    gender = Gender.F;
                int oscarAwards = actorsResult.getInt("oscar_awards");

                actors[actorIndex] = new Actor(firstName, lastName, age, gender, oscarAwards);
                actorIndex++;
            }
            return actors;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(actorsResult);
            closeStatement(actorsStatement);
        }

        return null;
    }

    public void addCinema(Cinema c) {
        PreparedStatement cinemasStatement = null;
        PreparedStatement moviesStatement = null;
        PreparedStatement seatsStatement = null;

        String name = c.getName();
        int nr_rooms = c.getNr_rooms();
        Movie[] listed_movies = c.getListed_movies();
        int[] nr_seats = c.getNr_seats();

        try {
            db.setAutoCommit(false);

            cinemasStatement = db.prepareStatement(cinemasInsertSQL, Statement.RETURN_GENERATED_KEYS);
            cinemasStatement.setString(1, name);
            cinemasStatement.setInt(2, nr_rooms);
            cinemasStatement.executeUpdate();

            // Obtine ID-ul cinematografului adaugat
            ResultSet generatedKeys = cinemasStatement.getGeneratedKeys();
            generatedKeys.next();
            int cinemaId = generatedKeys.getInt(1);

            // Insereaza filmele in tabela "cinema_movies"
            moviesStatement = db.prepareStatement(cinemasMoviesInsertSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (Movie movie : listed_movies) {
                moviesStatement.setInt(1, cinemaId);
                moviesStatement.setInt(2, Arrays.asList(listed_movies).indexOf(movie) + 1);
                moviesStatement.executeUpdate();
            }

            // Insereaza numarul de locuri in tabela "cinema_seats_per_room"
            seatsStatement = db.prepareStatement(cinemasSeatsInsertSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int i = 1;
            for (int nr_seat : nr_seats) {
                seatsStatement.setInt(1, cinemaId);
                seatsStatement.setInt(2, i);
                seatsStatement.setInt(3, nr_seat);
                seatsStatement.executeUpdate();
                i++;
            }

            // Realizeaza commit pentru a confirma tranzactia
            db.commit();
            System.out.println("Cinema added");

        } catch (SQLException e) {
            e.printStackTrace();

            // Realizeaza rollback in caz de eroare
            if (db != null) {
                try {
                    db.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            closeStatement(cinemasStatement);
            closeStatement(moviesStatement);
            closeStatement(seatsStatement);
        }
    }

    public void removeCinema(Cinema c) {
        PreparedStatement cinemasStatement = null;
        PreparedStatement moviesStatement = null;
        PreparedStatement seatsStatement = null;

        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        try {
            db.setAutoCommit(false);

            // sterge inregistrarile din tabela "cinemas" pentru cinematograful dat
            cinemasStatement = db.prepareStatement(cinemasDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cinemasStatement.setInt(1, cinema_id);
            cinemasStatement.executeUpdate();

            // sterge inregistrarile din tabela "cinema_movies" pentru filmele asociate cinematografului dat
            moviesStatement = db.prepareStatement(cinemasMoviesDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            moviesStatement.setInt(1, cinema_id);
            moviesStatement.executeUpdate();

            // sterge inregistrarile din tabela "cinema_seats_per_room" pentru locurile cinematografului dat
            seatsStatement = db.prepareStatement(cinemasSeatsDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            seatsStatement.setInt(1, cinema_id);
            seatsStatement.executeUpdate();

            // Realizeaza commit pentru a confirma tranzactia
            db.commit();
            Statement statement = db.prepareStatement(FIX_INCREMENT_CINEMAS);
            statement.execute(FIX_INCREMENT_CINEMAS);

            statement = db.prepareStatement(FIX_INCREMENT_MOVIES);
            statement.execute(FIX_INCREMENT_MOVIES);

            statement = db.prepareStatement(FIX_INCREMENT_CINEMA_SEATS);
            statement.execute(FIX_INCREMENT_CINEMA_SEATS);

            System.out.println("Cinema removed");

        } catch (SQLException e) {
            e.printStackTrace();

            // Realizeaza rollback in caz de eroare
            if (db != null) {
                try {
                    db.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            closeStatement(cinemasStatement);
            closeStatement(moviesStatement);
            closeStatement(seatsStatement);
        }
    }

    public void addMovie(Movie m) {
        PreparedStatement statement = null;
        try {
            String insertSQL;
            String type;
            if (m instanceof Action_Movie) {
                type = "action";
            } else if (m instanceof Comedy_Movie) {
                type = "comedy";
            } else if (m instanceof Romance_Movie) {
                type = "romance";
            } else if (m instanceof Thriller_Movie) {
                type = "thriller";
            } else {
                type = "other";
            }
            insertSQL = switch (type) {
                case "action" -> actionMovieInsertSQL;
                case "comedy" -> comedyMovieInsertSQL;
                case "romance" -> romanceMovieInsertSQL;
                case "thriller" -> thrillerMovieInsertSQL;
                default -> otherMovieInsertSQL;
            };

            // Prepara instructia SQL cu valorile corespunzatoare din obiectul Movie
            statement = db.prepareStatement(insertSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, m.getTitle());
            statement.setString(2, m.getDirector());
            statement.setInt(3, m.getRelease_year());
            statement.setInt(4, m.getDuration());
            statement.setDouble(5, m.getRating());
            statement.setInt(6, m.getOscar_awards());

            // Seteaza parametrii suplimentari in functie de tipul filmului
            switch (type) {
                case "action" -> {
                    statement.setString(7, type);
                    statement.setInt(8, ((Action_Movie) m).getPegi_rating());
                }
                case "comedy" -> {
                    statement.setString(7, type);
                    statement.setBoolean(8, ((Comedy_Movie) m).isAdults_Only());
                    statement.setBoolean(9, ((Comedy_Movie) m).isRomance());
                }
                case "romance" -> {
                    statement.setString(7, type);
                    statement.setBoolean(8, ((Romance_Movie) m).isExplicit_Nudity());
                }
                case "thriller" -> {
                    statement.setString(7, type);
                    statement.setString(8, String.join(", ", ((Thriller_Movie) m).getSubgenres()));
                    statement.setInt(9, ((Thriller_Movie) m).getPegi_rating());
                }
                default -> {
                    statement.setString(7, type);
                    statement.setString(8, String.join(", ", ((Other_Movie) m).getGenres()));
                    statement.setInt(9, ((Other_Movie) m).getPegi_rating());
                }
            }

            // Executa instructiunea SQL de inserare
            statement.executeUpdate();

            Actor[] actors = getActors();
            int[] actor_ids = new int[m.getActors().length];
            int index = -1;
            for (Actor actor : m.getActors()) {
                index++;
                for (int i = 0; i < actors.length; i++)
                    if (actor.toString().equals(actors[i].toString())) {
                        int actor_id = i+1;
                        actor_ids[index] = actor_id;
                        break;
                    }
            }
            Movie[] movies = getMovies();
            int movie_id = -1;
            for (int i = 0; i < movies.length; i++) {
                if (movies[i].getTitle().equals(m.getTitle())) {
                    movie_id = i+1;
                    break;
                }
            }
            // Insereaza actorii in tabela movie_actors
            for (int actor_id : actor_ids){
                    statement = db.prepareStatement(insertMovieActorsSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    statement.setInt(1, movie_id);
                    statement.setInt(2, actor_id);
                    statement.executeUpdate();
            }
            System.out.println("Movie added");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void removeMovie(Movie m){
        PreparedStatement statement = null;
        Movie[] movies = getMovies();
        int movie_id = 0;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }
        try {
            statement = db.prepareStatement(movieRemoveSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, movie_id);
            statement.executeUpdate();

            statement = db.prepareStatement(allMovieDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, movie_id);
            statement.executeUpdate();

            statement = db.prepareStatement(allStreamingMovieDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, movie_id);
            statement.executeUpdate();

            statement = db.prepareStatement(deleteMovieActorsSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, movie_id);
            statement.executeUpdate();

            statement = db.prepareStatement(FIX_INCREMENT_MOVIES);
            statement.execute();
            System.out.println("Movie removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeStatement(statement);
        }
    }

    public void addMovieToCinema(Cinema c, Movie m){
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }
        try {
            statement = db.prepareStatement(cinemasMoviesInsertSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, cinema_id);
            statement.setInt(2, movie_id);
            statement.executeUpdate();
            System.out.println("Movie added to cinema successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Movie already added to cinema.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeStatement(statement);
        }
    }
    public void removeMovieFromCinema(Cinema c, Movie m){
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }
        try { 
            statement = db.prepareStatement(movieDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, cinema_id); // Seteaza id-ul cinema-ului
            statement.setInt(2, movie_id); // Seteaza id-ul filmului

            statement.executeUpdate();

            statement = db.prepareStatement(streamingMovieDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, cinema_id); // Seteaza id-ul cinema-ului
            statement.setInt(2, movie_id); // Seteaza id-ul filmului

            statement.executeUpdate();

            statement = db.prepareStatement(FIX_INCREMENT_CINEMA_MOVIES);
            statement.execute();

            statement = db.prepareStatement(FIX_INCREMENT_STREAMING_MOVIES);
            statement.execute();

            System.out.println("Movie removed");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void addActor(Actor a){
        PreparedStatement actorInsertStatement = null;

        try {
            actorInsertStatement = db.prepareStatement(actorAddSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String[] name = a.getName().split(" ");
            String first_name = name[0];
            String last_name = name[1];
            actorInsertStatement.setString(1, first_name);
            actorInsertStatement.setString(2, last_name);
            actorInsertStatement.setInt(3, a.getAge());
            actorInsertStatement.setString(4, a.getGender().toString());
            actorInsertStatement.setInt(5, a.getOscar_awards());

            actorInsertStatement.executeUpdate();
            System.out.println("Actor added");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(actorInsertStatement);
        }
    }

    public void removeActor(Actor a){
        PreparedStatement removeActorStatement = null;
        Actor[] actors = getActors();
        int actor_id = -1;
        for (int i = 0; i < actors.length; i++) {
            if (actors[i].toString().equals(a.toString())) {
                actor_id = i+1;
                break;
            }
        }
        try {
            removeActorStatement = db.prepareStatement(removeActorSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            removeActorStatement.setInt(1, actor_id);
            removeActorStatement.executeUpdate();

            removeActorStatement = db.prepareStatement(actorMovieRemoveSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            removeActorStatement.setInt(1, actor_id);
            removeActorStatement.executeUpdate();

            PreparedStatement statement = db.prepareStatement(FIX_INCREMENT_ACTORS);
            statement.execute();

            statement = db.prepareStatement(FIX_INCREMENT_MOVIE_ACTORS);
            statement.execute();

            System.out.println("Actor removed");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(removeActorStatement);
        }
    }

    public void streamMovie(Cinema c, Movie m, LocalDateTime date, int room, int price) {
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i + 1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i + 1;
                break;
            }
        }
        try {
            statement = db.prepareStatement(movieStreamInsertSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, cinema_id); // Setează id-ul cinema-ului
            statement.setInt(2, movie_id); // Setează id-ul filmului
            statement.setTimestamp(3, Timestamp.valueOf(date)); // Setează data și ora
            statement.setInt(4, price); // Setează prețul
            statement.setInt(5, room); // Setează numărul sălii
            statement.setInt(6, c.getNr_seats()[room-1]); // Setează numărul de locuri disponibile

            // Execută instrucțiunea SQL de inserare
            statement.executeUpdate();

            System.out.println("Movie streamed");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void stopStreaming(Cinema c, Movie m, LocalDateTime date) {
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }

        try {
            statement = db.prepareStatement(movieStreamDeleteSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, cinema_id); // Setează id-ul cinema-ului
            statement.setInt(2, movie_id); // Setează id-ul filmului
            statement.setTimestamp(3, Timestamp.valueOf(date)); // Setează data și ora

            statement.executeUpdate();
            statement = db.prepareStatement(FIX_INCREMENT_STREAMING_MOVIES);
            statement.execute();

            System.out.println("Movie stopped streaming");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void changePrice(Cinema c, Movie m, int price) {
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }

        try {
            statement = db.prepareStatement(priceUpdateSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, price); // Setează noul preț
            statement.setInt(2, cinema_id); // Setează id-ul cinema-ului
            statement.setInt(3, movie_id); // Setează id-ul filmului

            // Execută instrucțiunea SQL de actualizare
            statement.executeUpdate();

            System.out.println("Price changed");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void changeRoom(Cinema c, Movie m, int room) {
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }

        try {
            statement = db.prepareStatement(roomUpdateSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, room); // Setează noul număr de sală
            statement.setInt(2, c.getNr_seats()[room-1]); // Setează id-ul cinema-ului
            statement.setInt(3, cinema_id); // Setează id-ul filmului
            statement.setInt(4, movie_id); // Setează numărul de locuri disponibile în noua sală

            // Execută instrucțiunea SQL de actualizare
            statement.executeUpdate();

            System.out.println("Room changed");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }

    public void changeDate(Cinema c, Movie m, LocalDateTime date) {
        PreparedStatement statement = null;
        Cinema[] cinemas = getCinemas();
        int cinema_id = -1;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinema_id = i+1;
                break;
            }
        }
        Movie[] movies = getMovies();
        int movie_id = -1;
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movie_id = i+1;
                break;
            }
        }
        try {
            statement = db.prepareStatement(dateUpdateSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setTimestamp(1, Timestamp.valueOf(date)); // Setează noua dată și oră
            statement.setInt(2, cinema_id); // Setează id-ul cinema-ului
            statement.setInt(3, movie_id); // Setează id-ul filmului

            // Execută instrucțiunea SQL de actualizare
            statement.executeUpdate();

            System.out.println("Date changed");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeStatement(statement);
        }
    }
}
