package main.java.lab9;

import java.io.Serializable;

public class Student extends Persoana implements Serializable {
    private String facultate = "Info";
    public Student(Adresa adresa, String nume, String prenume, int varsta){
        super(adresa, nume, prenume, varsta);
    }

    @Override
    public String toString() {
        return "Student{" +
                "adresa=" + getAdresa() +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", varsta=" + getVarsta() +
                ", facultate='" + facultate + '\'' +
                '}';
    }
}
