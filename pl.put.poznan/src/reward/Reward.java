package reward;

public interface Reward {
    double validate() throws RewardIsNotValidException;
}
