package reward;

public class Discount implements Reward {
    private final int value;

    public Discount(int value) {
        this.value = value;
    }

    public int validate() {
        return this.value;
    }
}
