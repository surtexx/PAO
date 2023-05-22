package main.java.lab7.enums;

public class TestOrder {
    public static void main(String[] args) {
        Product p1 = new Product("p1", 1);
        Order o1 = new Order(p1, Status.RECEIVED);
        System.out.println(o1.getStatus().name());
        System.out.println(o1.getStatus().ordinal());
        System.out.println(o1.getStatus().estimateDeliveryTime());

        for(Status status:Status.values())
            System.out.println(status);
        System.out.println(Status.fromString("received"));
    }
}
