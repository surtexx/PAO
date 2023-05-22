package main.java.lab8.ex1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Ex2 {
    private static final String BASE_PATH = "src\\main\\java\\lab8\\ex1\\";
    public static void main(String[] args) {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(BASE_PATH + "f4.txt"));
            DataInputStream dis = new DataInputStream(new FileInputStream(BASE_PATH + "f4.txt"))) {
            dos.writeInt(2);
            int size = dis.readInt();
            double[] doubleRead = new double[size];
            for(int i = 0; i < size; i++)
                doubleRead[i] = dis.readDouble();
            for(double d : doubleRead)
                System.out.println(d);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
