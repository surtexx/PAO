package entities;

import javax.swing.*;
import java.util.Arrays;

public class Action_Movie extends Movie{
    protected int pegi_rating;

    public Action_Movie(String title, String director, int release_year, int duration, Actor[] actors,
                        double rating, int oscar_awards, int pegi_rating)
    {
        super(title, director, release_year, duration, actors, rating, oscar_awards);
        if(pegi_rating != 3 && pegi_rating != 7 && pegi_rating != 12 && pegi_rating != 16 && pegi_rating != 18)
            throw new IllegalArgumentException("Invalid PEGI rating for movie " + title + ". (Possible values: 3, 7, 12, 16, 18)");
        else
            this.pegi_rating = pegi_rating;
    }

    public Action_Movie(){
        super();
        this.pegi_rating = 0;
    }

    public Action_Movie(Action_Movie film){
        super(film);
        this.pegi_rating = film.pegi_rating;
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
        return super.toString() + "PEGI rating: " +
                this.pegi_rating + "\n";
    }

    public boolean equals(Movie m){
        if(m instanceof Action_Movie)
            return this.title.equals(m.title) && this.director.equals(m.director) &&
                    this.release_year == m.release_year && this.duration == m.duration &&
                    Arrays.equals(this.actors, m.actors) && this.rating == m.rating &&
                    this.oscar_awards == m.oscar_awards && this.pegi_rating == ((Action_Movie) m).pegi_rating;
        else
            return false;
    }

}
