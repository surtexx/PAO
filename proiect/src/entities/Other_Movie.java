package entities;

import java.util.Arrays;
public class Other_Movie extends Movie{
    protected String[] genres;
    protected int pegi_rating;

    public Other_Movie(String title, String director, int release_year, int duration, Actor[] actors,
                       double rating, int oscar_awards, String[] genres, int pegi_rating)
    {
        super(title, director, release_year, duration, actors, rating, oscar_awards);
        this.genres = genres;
        if(pegi_rating != 3 && pegi_rating != 7 && pegi_rating != 12 && pegi_rating != 16 && pegi_rating != 18)
            throw new IllegalArgumentException("Invalid PEGI rating for movie " + title + ". (Possible values: 3, 7, 12, 16, 18)");
        else
            this.pegi_rating = pegi_rating;
    }

    public Other_Movie(){
        super();
        this.genres = null;
        this.pegi_rating = 0;
    }

    public Other_Movie(Other_Movie film){
        super(film);
        this.genres = film.genres;
        this.pegi_rating = film.pegi_rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getPegi_rating() {
        return pegi_rating;
    }

    public void setPegi_rating(int pegi_rating) {
        if(pegi_rating != 3 && pegi_rating != 7 && pegi_rating != 12 && pegi_rating != 16 && pegi_rating != 18)
            throw new IllegalArgumentException("Invalid PEGI rating for movie " + title + ". (Possible values: 3, 7, 12, 16, 18)");
        else
            this.pegi_rating = pegi_rating;
    }

    public String toString(){
        StringBuilder output = new StringBuilder(super.toString());
        output.append("Genres: ");
        for(int i = 0; i < this.genres.length; i++) {
            output.append(this.genres[i]);
            if (i < this.genres.length - 1)
                output.append(", ");
            else
                output.append(".");
        }
        output.append("PEGI rating: ");
        output.append(this.pegi_rating);
        output.append("\n");
        return output.toString();
    }

    public boolean equals(Movie m){
        if(m instanceof Other_Movie)
            return this.title.equals(m.title) && this.director.equals(m.director) &&
                    this.release_year == m.release_year && this.duration == m.duration &&
                    Arrays.equals(this.actors, m.actors) && this.rating == m.rating &&
                    this.oscar_awards == m.oscar_awards && this.genres == ((Other_Movie) m).genres &&
                    this.pegi_rating == ((Other_Movie) m).pegi_rating;
        else
            return false;
    }
}
