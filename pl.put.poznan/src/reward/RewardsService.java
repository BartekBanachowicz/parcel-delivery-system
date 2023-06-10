package reward;

import reward.condition.TimeCondition;

import java.time.OffsetDateTime;

public class RewardsService {
    private final RewardsRepository rewardsRepository;

    public RewardsService(RewardsRepository rewardsRepository) {
        this.rewardsRepository = rewardsRepository;
    }

    public int grantNewReward(int value) {
        return this.rewardsRepository.add(new Discount(value));
    }

    public int grantNewReward(int value, OffsetDateTime expirationDateTime) {
        return this.rewardsRepository.add(new TimeCondition(new Discount(value), expirationDateTime));
    }

    public int validateReward(int rewardId) throws RewardDoesntExistException, RewardIsNotValidException {
        return this.rewardsRepository.get(rewardId).validate();
    }

    public void invalidateReward(int rewardId) throws RewardDoesntExistException {
        this.rewardsRepository.delete(rewardId);
    }
}
