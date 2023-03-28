package main.java.lab3;
import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        /*
        Cititi de la tastutura 2 valori: numele si varsta
        Afisati numele
        Daca varsta e impara afisati toate nr impare <= varsta
        Daca varsta e para afisati toate nr pare <= varsta
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numele: ");
        String nume = scanner.nextLine();
        System.out.println("Introduceti varsta: ");
        int varsta = scanner.nextInt();
        System.out.println("Numele este: " + nume);
        if (varsta % 2 == 0) {
            System.out.println("Numerele pare <= varsta sunt: ");
            for (int i = 0; i <= varsta; i+=2) {
                    System.out.print(i + " ");
            }
        } else {
            System.out.println("Numerele impare <= varsta sunt: ");
            for (int i = 1; i <= varsta; i+=2) {
                    System.out.print(i + " ");
            }
        }
        scanner.close();
    }
}
