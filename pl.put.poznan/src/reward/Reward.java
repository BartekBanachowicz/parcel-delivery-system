package reward;

public interface Reward {
    int validate() throws RewardIsNotValidException;
}
