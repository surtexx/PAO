package main.java.lab2.siruri;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSiruri {
    public static void main(String[] args) {
        // == vs equals()
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2)); // true dar executie mai lenta. ia caracter cu caracter
        String s3 = new String("abc");
        System.out.println(s1 == s3); // false pentru ca new creeaza explicit un nou obiect
        s3 = s3.intern(); // adauga in pool
        System.out.println(s1 == s3);

        String nrTelefon = "0777121212";
        System.out.println(nrTelefon.matches("(076|077)[0-9]{7}"));

        Pattern pattern = Pattern.compile("(076|077)[0-9]{7}");
        Matcher matcher = pattern.matcher(nrTelefon);
        System.out.println(matcher.find());

        System.out.println(s1.toUpperCase());

        String s5 = "qwe";
        String s6 = "qwe";
        System.out.println(s5 == s6);
        System.out.println(s5.toUpperCase() == s6.toUpperCase());


    }
}
