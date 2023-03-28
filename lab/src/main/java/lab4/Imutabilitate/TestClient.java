package main.java.lab4.Imutabilitate;

public class TestClient {
    public static void main(String[] args){
        Adresa adresa = new Adresa("Maniu", 33);
        Client client = new Client(adresa);
        System.out.println(client);
        Adresa badAddress = client.getAdresa();
        adresa.setNumar(66);
        System.out.println("dupa modificare numar adresa:\n" + client);
        System.out.println("dupa modificare numar adresa:\n" + adresa);

        ClientRecord clientRecord = new ClientRecord(adresa);
        System.out.println(clientRecord.adresa());
        System.out.println(clientRecord.toString());
    }
}
