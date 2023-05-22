package main.java.lab7;

public class TestOrder {
    private static final String PROCESSING_STATUS = "processing";
    private static final String COMPLETE_STATUS = "complete";
    private static final String RECEIVED_STATUS = "received";
    public static void main(String[] args) {
        Product p1 = new Product("p1", 1);
        Status s1 = new Status("processing");
        Order o1 = new Order(p1, s1);
        System.out.println("Estimated delivery time for order " + o1 + " is " + estimateDeliveryTime(o1.getStatus().getCurrentStatus()) + " days\n");
    }

    public static int estimateDeliveryTime(String status){
        switch (status.toLowerCase()){
            case RECEIVED_STATUS:
                return 3;
            case PROCESSING_STATUS:
                return 1;
            case COMPLETE_STATUS:
                return 0;
            default:
                return -1;
        }
    }
}
