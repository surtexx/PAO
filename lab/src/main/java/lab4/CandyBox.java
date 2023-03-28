package main.java.lab4;

public class CandyBox {
    protected String flavor;
    protected String origin;
    CandyBox() {
        this.flavor = "Unknown";
        this.origin = "Unknown";
    }

    CandyBox(String flavor, String origin) {
        this.flavor = flavor;
        this.origin = origin;
    }

    public float getVolume() {
        return 0.0f;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " has flavor " + this.flavor + " and it is from " + this.origin;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CandyBox)) {
            return false;
        }
        return this.flavor.equals(((CandyBox) obj).flavor) && this.origin.equals(((CandyBox) obj).origin);
    }
}
