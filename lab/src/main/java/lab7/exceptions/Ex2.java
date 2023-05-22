package main.java.lab7.exceptions;

public class Ex2 {
    public static void main(String[] args) {
        try{
            divide(1, 0);
        }
        catch(MyArithmeticException e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("Finally block");
        }
    }

    private static double divide(int a, int b) throws MyArithmeticException{
        if(b == 0){
            throw new MyArithmeticException("Division by zero");
        }
        return (double)a/b;
    }
}
