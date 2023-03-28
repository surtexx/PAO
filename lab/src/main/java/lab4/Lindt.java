package main.java.lab4;

public class Lindt extends CandyBox{
    private float lungime;
    private float latime;
    private float inaltime;
    public Lindt(String flavor, String origin, float lungime, float latime, float inaltime){
        super(flavor,origin);
        this.lungime = lungime;
        this.latime = latime;
        this.inaltime = inaltime;
    }
    public float getVolume(){
        return lungime*latime*inaltime;
    }
}
