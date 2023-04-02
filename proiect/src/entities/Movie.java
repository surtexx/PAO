package entities;

import java.util.Arrays;
import java.util.Comparator;

public abstract class Movie {
    protected String title;
    protected String director;
    protected int release_year;
    protected int duration;
    protected Actor[] actors;
    protected double rating;
    protected int oscar_awards;

    public Movie(String title, String director, int release_year, int duration, Actor[] actors,
                 double rating, int oscar_awards)
    {
        this.title = title;
        this.director = director;
        this.release_year = release_year;
        this.duration = duration;
        Arrays.sort(actors, Comparator.comparing(Actor::getName));
        this.actors = actors;
        this.oscar_awards = oscar_awards;
        if(rating > 10 || rating < 0)
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        else
            this.rating = rating;
    }

    public Movie(){
        this.title = "";
        this.director = "";
        this.release_year = 0;
        this.duration = 0;
        this.actors = null;
        this.oscar_awards = 0;
        this.rating = (double) 0;
    }

    public Movie(Movie film){
        this.title = film.title;
        this.director = film.director;
        this.release_year = film.release_year;
        this.duration = film.duration;
        this.actors = film.actors;
        this.oscar_awards = film.oscar_awards;
        this.rating = film.rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Actor[] getActors() {
        return actors;
    }

    public void setActors(Actor[] actors) {
        Arrays.sort(actors, Comparator.comparing(Actor::getName));
        this.actors = actors;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public int getOscar_awards(){
        return oscar_awards;
    }

    public void setOscar_awards(int oscar_awards){
        this.oscar_awards = oscar_awards;
    }

    public String toString(){
        StringBuilder output = new StringBuilder("Title: " + this.title + "\nDirector: " + this.director + "\nRelease Year: " +
                this.release_year + "\nDuration: " + this.duration + "\nActors:\n   ");
        for(int i = 0; i < this.actors.length; i++){
            output.append(this.actors[i].getName());
            if(i < this.actors.length - 1)
                output.append("\n   ");
            else
                output.append("\n");
        }
        output.append("Rating: " + this.rating + "\nOscar Awards: " + this.oscar_awards + "\n");
        return output.toString();
    }

    public abstract boolean equals(Movie m);
}
