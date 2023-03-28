package main.java.lab2;

public class Profesor {
    private String nume;
    private String materie;

    public Profesor(String nume, String materie) {
        this.nume = nume;
        this.materie = materie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }
    public String toString() {
        return "Profesor{nume=" + nume + ", materie=" + materie + "}";
    }
}
