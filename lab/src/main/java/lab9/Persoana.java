package main.java.lab9;

import java.io.Serializable;

public class Persoana implements Serializable {
    private Adresa adresa;
    private String nume;
    private int varsta;
    private String prenume;

    public Persoana(Adresa adresa, String nume, String prenume, int varsta){
        this.adresa = adresa;
        this.nume = nume;
        this.varsta = varsta;
        this.prenume = prenume;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "adresa=" + adresa +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", varsta=" + varsta +
                '}';
    }
}
