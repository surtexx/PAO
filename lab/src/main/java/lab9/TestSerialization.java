package main.java.lab9;

import java.io.*;

public class TestSerialization {
    private static final String FILE_PATH = "src\\main\\java\\lab9\\deserializare\\";
    public static void main(String[] args) {
        Adresa a1 = new Adresa("Strada 1", 1);
        Student s1 = new Student(a1, "Popescu", "Ion", 20);
        System.out.println(s1);
        serializeObjectToFile(s1, "s1.ser");
        Persoana s2 = deserializeObjectFromFile("s1.ser");
        System.out.println(s2);
    }

    private static void serializeObjectToFile(Persoana student, String fileName) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_PATH + fileName));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(student);

        } catch (IOException e) {
            System.out.printf("IOException occured %s", e.getMessage());
        }
    }

    private static Persoana deserializeObjectFromFile(String fileName) {
        try(FileInputStream fileInputStream = new FileInputStream(new File(FILE_PATH + fileName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (Student) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Exception occured %s", e.getMessage());
        }
        return null;
    }
}
