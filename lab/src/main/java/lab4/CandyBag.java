package main.java.lab4;

public class CandyBag {
    private CandyBox[] candyBoxes;
    public CandyBag() {
        this.candyBoxes = new CandyBox[0];
    }
    public CandyBag(CandyBox[] candyBoxes) {
        this.candyBoxes = candyBoxes;
    }
    // method to test equality for all Lindt objects
    public boolean test_equality_lindt(){
        for(int i = 0; i < candyBoxes.length; i++){
            if(candyBoxes[i] instanceof Lindt){
                for(int j = i + 1; j < candyBoxes.length; j++){
                    if(candyBoxes[j] instanceof Lindt){
                        if(!candyBoxes[i].equals(candyBoxes[j])){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
