package main.java.lab4.Imutabilitate;

public class TestImutabilitateString {
    public static void main(String[] args) {
        String s1="abc";
        StringBuilder sb1 = new StringBuilder(s1);
        appendString(s1, "def");
        appendString(sb1, "def");
    }

    private static void appendString(Object existing, String toAppend) {
        if(existing instanceof String){
            existing = existing + toAppend;
        }
        else if(existing instanceof StringBuilder){
            ((StringBuilder) existing).append(toAppend);
        }
        else{
            throw new IllegalArgumentException("Argumentul nu este de tipul String sau StringBuilder");
        }
        System.out.println(existing);
    }
}
