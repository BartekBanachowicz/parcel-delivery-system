package reward;

import java.util.ArrayList;
import java.util.List;

class RewardDoesntExistException extends Exception {
    public RewardDoesntExistException(int rewardId) {
        super(String.format("Reward with id %d doesn't exist", rewardId));
    }
}

class RewardsRepository {
    private final List<Reward> rewards;

    public RewardsRepository() {
        this.rewards = new ArrayList<>();
    }

    public int add(Reward reward) {
        int rewardId = this.rewards.size();
        this.rewards.add(rewardId, reward);
        return rewardId;
    }

    public Reward get(int rewardId) throws RewardDoesntExistException {
        try {
            return this.rewards.get(rewardId);
        } catch (IndexOutOfBoundsException exception) {
            throw new RewardDoesntExistException(rewardId);
        }
    }

    public void patch(int rewardId, Reward reward) throws RewardDoesntExistException {
        try {
            this.rewards.set(rewardId, reward);
        } catch (IndexOutOfBoundsException exception) {
            throw new RewardDoesntExistException(rewardId);
        }
    }

    public void delete(int rewardId) throws RewardDoesntExistException {
        try {
            this.rewards.remove(rewardId);
        } catch (IndexOutOfBoundsException exception) {
            throw new RewardDoesntExistException(rewardId);
        }
    }
}
