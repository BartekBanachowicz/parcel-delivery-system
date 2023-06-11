package reward;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

class RewardsRepository {
    private final HashMap<String, Reward> rewards;

    public RewardsRepository() {
        this.rewards = new HashMap<>();
    }

    public String add(Reward reward) {
        String rewardId = UUID.randomUUID().toString();
        this.rewards.put(rewardId, reward);
        return rewardId;
    }

    public Reward get(String rewardId) throws RewardDoesntExistException {
        Optional<Reward> reward = Optional.ofNullable(rewards.get(rewardId));
        if(reward.isEmpty()) {
            throw new RewardDoesntExistException(rewardId);
        }
        return reward.get();
    }

    public void remove(String rewardId) throws RewardDoesntExistException {
        Optional<Reward> reward = Optional.ofNullable(rewards.remove(rewardId));
        if(reward.isEmpty()) {
            throw new RewardDoesntExistException(rewardId);
        }
    }
}
