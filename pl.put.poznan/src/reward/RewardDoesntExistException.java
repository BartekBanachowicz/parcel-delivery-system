package reward;

class RewardDoesntExistException extends RuntimeException {
    public RewardDoesntExistException(String rewardId) {
        super(String.format("Reward with id %s doesn't exist", rewardId));
    }
}
