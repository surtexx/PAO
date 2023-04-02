package entities;

import java.util.Arrays;

public class Romance_Movie extends Movie {
    boolean explicit_nudity;

    public Romance_Movie(String title, String director, int release_year, int duration, Actor[] actors,
                         double rating, int oscar_awards, boolean explicit_nudity)
    {
        super(title, director, release_year, duration, actors, rating, oscar_awards);
        this.explicit_nudity = explicit_nudity;
    }

    public Romance_Movie(){
        super();
        this.explicit_nudity = false;
    }

    public Romance_Movie(Romance_Movie film){
        super(film);
        this.explicit_nudity = film.explicit_nudity;
    }

    public boolean isExplicit_Nudity() {
        return explicit_nudity;
    }

    public void setExplicit_Nudity(boolean explicit_nudity) {
        this.explicit_nudity = explicit_nudity;
    }

    public String toString(){
        return super.toString() + (explicit_nudity ? "Explicit nudity\n" : "No explicit nudity\n");
    }

    public boolean equals(Movie m){
        if(m instanceof Romance_Movie)
            return this.title.equals(m.title) && this.director.equals(m.director) &&
                    this.release_year == m.release_year && this.duration == m.duration &&
                    Arrays.equals(this.actors, m.actors) && this.rating == m.rating &&
                    this.oscar_awards == m.oscar_awards && this.explicit_nudity == ((Romance_Movie) m).explicit_nudity;
        else
            return false;
    }
}
