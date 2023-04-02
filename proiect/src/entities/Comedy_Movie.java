package entities;

import java.util.Arrays;

public class Comedy_Movie extends Movie {
    boolean adults_only;
    boolean romance;

    public Comedy_Movie(String title, String director, int release_year, int duration, Actor[] actors,
                        double rating, int oscar_awards, boolean adults_only, boolean romance)
    {
        super(title, director, release_year, duration, actors, rating, oscar_awards);
        this.adults_only = adults_only;
        this.romance = romance;
    }

    public Comedy_Movie(){
        super();
        this.adults_only = false;
        this.romance = false;
    }

    public Comedy_Movie(Comedy_Movie film){
        super(film);
        this.adults_only = film.adults_only;
        this.romance = film.romance;
    }

    public boolean isAdults_Only() {
        return adults_only;
    }

    public void adults_only(boolean adults_only) {
        this.adults_only = adults_only;
    }

    public boolean isRomance() {
        return romance;
    }

    public void setRomance(boolean romance) {
        this.romance = romance;
    }

    public String toString(){
        return super.toString() + (adults_only ? "Adults only\n" : "Not adults only\n") + (romance ? "Romance\n"
                : "Not romance\n");
    }

    public boolean equals(Movie m){
        if(m instanceof Comedy_Movie)
            return this.title.equals(m.title) && this.director.equals(m.director) &&
                    this.release_year == m.release_year && this.duration == m.duration &&
                    Arrays.equals(this.actors, m.actors) && this.rating == m.rating &&
                    this.oscar_awards == m.oscar_awards && this.adults_only == ((Comedy_Movie) m).adults_only &&
                    this.romance == ((Comedy_Movie) m).romance;
        else
            return false;
    }
}
