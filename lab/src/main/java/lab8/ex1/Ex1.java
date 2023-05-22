package main.java.lab8.ex1;

import java.io.FileReader;
import java.io.FileWriter;

public class Ex1 {
    private static final String BASE_PATH = "src\\main\\java\\lab8\\ex1\\";
    public static void main(String[] args) {
        try(FileReader fr = new FileReader(BASE_PATH + "f1.txt"); FileWriter fw = new FileWriter(BASE_PATH + "f2.txt")) {
            int i;
            while((i=fr.read())!=-1)
                fw.write((char)i);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
