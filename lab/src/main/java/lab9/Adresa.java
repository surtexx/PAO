package main.java.lab9;

import java.io.Serializable;

public class Adresa implements Serializable {
    private String strada;
    private int numar;

    public Adresa(String strada, int numar) {
        this.strada = strada;
        this.numar = numar;
    }

    public String getStrada() {
        return strada;
    }

    public int getNumar() {
        return numar;
    }

    @Override
    public String toString() {
        return "Adresa{" +
                "strada='" + strada + '\'' +
                ", numar=" + numar +
                '}';
    }
}
