package main.java.lab2.siruri;

public class TestString {
    public static void main(String[] args) {
        String textBlock = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                """;
        System.out.println(textBlock);
        String[] cuvinte = textBlock.split(" |  \\n");
        for (String cuvant : cuvinte) {
            System.out.println(cuvant);
        }

        String s1 = "abcde";
        System.out.println(s1.substring(1, 3));
    }
}
