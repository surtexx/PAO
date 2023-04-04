import services.Service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Service s = Service.getInstance();
        try{
            s.menu();
        }
        catch (NullPointerException e) {
            System.out.println("No user selected.");
        }
    }
}