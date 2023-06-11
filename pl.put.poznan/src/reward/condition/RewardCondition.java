package reward.condition;

import reward.Reward;
import reward.RewardIsNotValidException;

public abstract class RewardCondition implements Reward {
    protected Reward reward;

    @Override
    public double validate() throws RewardIsNotValidException {
        return this.reward.validate();
    }
}
