package reward.condition;

import reward.Reward;
import reward.RewardIsNotValidException;

import java.time.OffsetDateTime;

public class TimeCondition extends RewardCondition {
    private final OffsetDateTime expirationDateTime;

    public TimeCondition(Reward reward, OffsetDateTime expirationDateTime) {
        this.reward = reward;
        this.expirationDateTime = expirationDateTime;
    }

    @Override
    public double validate() throws RewardIsNotValidException {
        if (OffsetDateTime.now().isAfter(this.expirationDateTime)) {
            throw new RewardIsNotValidException("This reward has expired");
        }

        return super.validate();
    }
}
