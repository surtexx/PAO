package entities;

import java.util.Arrays;

public class Thriller_Movie extends Movie {
    protected String[] subgenres;
    protected int pegi_rating;
    public Thriller_Movie(String title, String director, int release_year, int duration, Actor[] actors,
                          double rating, int oscar_awards, String[] subgenres, int pegi_rating)
    {
        super(title, director, release_year, duration, actors, rating, oscar_awards);
        this.subgenres = subgenres;
        if(pegi_rating != 3 && pegi_rating != 7 && pegi_rating != 12 && pegi_rating != 16 && pegi_rating != 18)
            throw new IllegalArgumentException("Invalid PEGI rating for movie " + title + ". (Possible values: 3, 7, 12, 16, 18)");
        else
            this.pegi_rating = pegi_rating;
    }

    public Thriller_Movie(){
        super();
        this.subgenres = null;
        this.pegi_rating = 0;
    }

    public Thriller_Movie(Thriller_Movie film){
        super(film);
        this.subgenres = film.subgenres;
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
    public String[] getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(String[] subgenres) {
        this.subgenres = subgenres;
    }

    public String toString(){
        StringBuilder output = new StringBuilder(super.toString());
        output.append("Subgenres: ");
        for(int i = 0; i < this.subgenres.length; i++) {
            output.append(this.subgenres[i]);
            if (i < this.subgenres.length - 1)
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
        if(m instanceof Thriller_Movie)
            return this.title.equals(m.title) && this.director.equals(m.director) &&
                    this.release_year == m.release_year && this.duration == m.duration &&
                    Arrays.equals(this.actors, m.actors) && this.rating == m.rating &&
                    this.oscar_awards == m.oscar_awards && this.subgenres == ((Thriller_Movie) m).subgenres &&
                    this.pegi_rating == ((Thriller_Movie) m).pegi_rating;
        else
            return false;
    }
}
