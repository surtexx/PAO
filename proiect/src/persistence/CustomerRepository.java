package persistence;

import data_types.Gender;
import entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class CustomerRepository extends BaseRepository{
    private final String cinemasSelectSQL = "SELECT * FROM cinemas";
    private final String moviesSelectSQL = "SELECT * from movies";
    private final String cinemasMoviesSelectSQL = "SELECT m.* " +
            "FROM movies m " +
            "JOIN cinema_movies cm ON m.id = cm.id_movie " +
            "WHERE cm.id_cinema = ?";
    private final String actorsFromMovieSelectSQL = "SELECT a.* " +
            "FROM actors a " +
            "JOIN movie_actors ma ON a.id = ma.id_actor " +
            "WHERE ma.id_movie = ?";
    private final String cinemasSeatsSelectSQL = "SELECT seats_number FROM cinema_seats_per_room WHERE id_cinema = ?";
    private final String streamingMoviesSQL = "SELECT * FROM streaming_movies where id_cinema = ?";

    private final String seatsUpdateSQL = "UPDATE streaming_movies SET seats_available = seats_available - 1 " +
            "WHERE id_cinema = ? AND id_movie = ? AND date = ?";
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

    public Cinema[] getAllCinemas() {
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

                Movie[] allMovies = getAllMovies();
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

    public Movie[] getAllMovies(){
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

    public void buyTicket(Cinema c, Movie m, LocalDateTime d){
        PreparedStatement statement = null;
        Cinema[] cinemas = getAllCinemas();
        Movie[] movies = getAllMovies();
        int cinemaId = 0;
        int movieId = 0;
        for (int i = 0; i < cinemas.length; i++) {
            if (cinemas[i].toString().equals(c.toString())) {
                cinemaId = i + 1;
                break;
            }
        }
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].toString().equals(m.toString())) {
                movieId = i + 1;
                break;
            }
        }
        try{
            statement = db.prepareStatement(seatsUpdateSQL);
            statement.setInt(1, cinemaId);
            statement.setInt(2, movieId);
            statement.setTimestamp(3, Timestamp.valueOf(d));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeStatement(statement);
        }
    }
}
