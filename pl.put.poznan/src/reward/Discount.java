package reward;

public class Discount implements Reward {
    private final double value;

    public Discount(double value) {
        this.value = value;
    }

    public double validate() {
        return this.value;
    }
}
