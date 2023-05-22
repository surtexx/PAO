package main.java.lab7;

public class Status {
    private String currentStatus;
    public Status(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    public String getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "Status{" +
                "currentStatus='" + currentStatus + '\'' +
                '}';
    }
}
