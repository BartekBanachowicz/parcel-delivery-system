package payment.command;

import parcel.Parcel;
import parcel.ParcelService;
import payment.PaymentService;
import user.User;

import java.util.Optional;

public record CardPaymentCommand(Parcel parcel, User user, String rewardId, PaymentService paymentService,
                                 ParcelService parcelService) implements PaymentCommand {
    @Override
    public void execute() {
        paymentService.executeCardPayment(parcel, user, rewardId);
        parcelService.generateFirstPrivilege(parcel, user);
    }
}
