package main.java.lab5.interfete;

public class TestWashable {
    public static void main(String[] args) {
        Masina masina = new Masina();
        Fereastra fereastra = new Fereastra();

        spalaObiect(masina);
        spalaObiect(fereastra);
    }

    private static void spalaObiect(Washable washable){
        washable.wash();
    }
}
