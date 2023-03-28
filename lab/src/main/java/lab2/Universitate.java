package main.java.lab2;

import java.util.Arrays;

public class Universitate {
    private String nume;
    private Profesor[] profesori;
    private Departament[] departamente;

    public Universitate(String nume, Profesor[] profesori) {
        this.nume = nume;
        this.profesori = profesori;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public Universitate(String nume, Profesor[] profesori, Departament[] departamente) {
        this.nume = nume;
        this.profesori = profesori;
        this.departamente = Arrays.copyOf(departamente, departamente.length);
    }
    public void setDepartamente(Departament[] departamente) {
        this.departamente = Arrays.copyOf(departamente, departamente.length);
    }
    public String toString() {
        return "Universitate{nume=" + nume + ", profesori= " + Arrays.toString(profesori) + ", departamente= " + Arrays.toString(departamente) + "}";
    }
    public Departament[] getDepartamente() {
        return departamente;
    }
}
