package main.java.lab2;

public class Departament {
    private String nume;
    public Departament(String nume) {
        this.nume = nume;
    }
    public Departament(Departament d) {
        this.nume = d.nume;
    }
    public String toString () {
        return "Departament{nume=" + nume + "}";
    }
}
