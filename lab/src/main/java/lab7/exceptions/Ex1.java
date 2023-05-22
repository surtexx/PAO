package main.java.lab7.exceptions;

import java.util.Stack;

public class Ex1 {
    public static void main(String[] args) {
        try{
            m1();
        }
        catch(StackOverflowError e){
            System.out.println("dezastru");
        }
        finally{
            System.out.println("Finally block");
        }
    }
    private static void m1(){
        m1();
    }
}
