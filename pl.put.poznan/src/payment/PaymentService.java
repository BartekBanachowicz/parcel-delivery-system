package payment;

import log.EventLog;
import log.ParcelEvent;
import parcel.Parcel;
import parcel.ParcelSize;
import reward.RewardsService;
import user.User;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

import static parcel.ParcelStatus.PAID;

public class PaymentService {

    private final RewardsService rewardsService;
    private final EventLog eventLog;
    private final Clock clock;

    public PaymentService(RewardsService rewardsService, EventLog eventLog, Clock clock) {
        this.rewardsService = rewardsService;
        this.eventLog = eventLog;
        this.clock = clock;
    }

    public void executeCardPayment(Parcel parcel, User user, String rewardId) {
        double reward = Optional.ofNullable(rewardId).isPresent() ? rewardsService.validateReward(rewardId) : 0.0;
        double finalPrice = getFinalPrice(parcel.size(), reward);
        //CALL TO CARDS OPERATOR
        eventLog.registerNewParcelEvent(new ParcelEvent(parcel.parcelId(), null, PAID, user, ZonedDateTime.now(clock)));
    }

    public void executeBlikPayment(Parcel parcel, User user, String rewardId) {
        double reward = Optional.ofNullable(rewardId).isPresent() ? rewardsService.validateReward(rewardId) : 0.0;
        double finalPrice = getFinalPrice(parcel.size(), reward);
        //CALL TO BLIK OPERATOR
        eventLog.registerNewParcelEvent(new ParcelEvent(parcel.parcelId(), null, PAID, user, ZonedDateTime.now(clock)));
    }

    private double getFinalPrice(ParcelSize parcelSize, double discount) {
        double normalPrice = getPriceForSize(parcelSize);
        return normalPrice - (normalPrice*(discount/100));
    }

    private double getPriceForSize(ParcelSize parcelSize) {
        return switch (parcelSize) {
            case A -> 6.5;
            case B -> 12.75;
            case C -> 18.99;
        };
    }
}
