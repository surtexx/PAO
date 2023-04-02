import services.Service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Service s = Service.getInstance();
        s.menu();
    }
}