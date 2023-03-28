package main.java.lab4.Imutabilitate;

public class Adresa {
    String strada;
    int numar;

    public Adresa(String strada, int numar){
        this.strada = strada;
        this.numar = numar;
    }

    public Adresa(){
        this.strada = "Nedefinita";
        this.numar = 0;
    }

    public Adresa(Adresa adresa){
        this.strada = adresa.strada;
        this.numar = adresa.numar;
    }

    public String getStrada(){
        return this.strada;
    }

    public int getNumar(){
        return this.numar;
    }

    public void setStrada(String strada){
        this.strada = strada;
    }

    public void setNumar(int numar){
        this.numar = numar;
    }

    @Override
    public String toString() {
        return "Adresa{" +
                "strada='" + strada + '\'' +
                ", numar=" + numar +
                '}';
    }
}
