package main.java.lab4.Imutabilitate;

public final class Client {
    private Adresa adresa;
    public Adresa getAdresa() {
        return adresa;
    }
    public Client(Adresa adresa){
        this.adresa = new Adresa(adresa);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Client c = (Client) o;
        return c.adresa.equals(this.adresa);
    }

    @Override
    public String toString() {
        return "Client{" +
                "adresa=" + adresa +
                '}';
    }

    @Override
    public int hashCode() {
        return adresa.hashCode();
    }
}
