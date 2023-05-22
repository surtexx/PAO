package main.java.lab7.enums;

public enum Status {
    RECEIVED("received") {
        @Override
        public int estimateDeliveryTime() {
            return 3;
        }
    },
    PROCESSING("processing"){
        @Override
        public int estimateDeliveryTime() {
            return 1;
        }
    },
    COMPLETE("complete"){
        @Override
        public int estimateDeliveryTime() {
            return 0;
        }
    };
    public abstract int estimateDeliveryTime();
    private String name;
    private Status(String name) {
        this.name = name;
    }
    public static Status fromString(String name) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(name.trim())) {
                return status;
            }
        }
        return null;
    }
};
