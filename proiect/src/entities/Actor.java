package entities;

import data_types.Gender;

public class Actor {
    protected String first_name;
    protected String last_name;
    protected int age;
    protected Gender gender;
    protected int oscar_awards;

    public Actor(String first_name, String last_name, int age, Gender gender, int oscar_awards) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.gender = gender;
        this.oscar_awards = oscar_awards;
    }

    public Actor(){
        this.first_name = "";
        this.last_name = "";
        this.age = 0;
        this.gender = Gender.M;
        this.oscar_awards = 0;
    }

    public Actor(Actor a){
        this.first_name = a.first_name;
        this.last_name = a.last_name;
        this.age = a.age;
        this.gender = a.gender;
        this.oscar_awards = a.oscar_awards;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender(){
        return gender;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public int getOscar_awards() {
        return oscar_awards;
    }

    public void setOscar_awards(int oscar_awards) {
        this.oscar_awards = oscar_awards;
    }

    public String toString(){
        return "Actor: " + getName() + ". Age: " + getAge() + ". Gender:" + getGender() + ". Number of oscars: " + getOscar_awards() + ".";
    }
}
