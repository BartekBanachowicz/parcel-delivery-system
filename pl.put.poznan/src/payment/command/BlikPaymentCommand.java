package payment.command;

import parcel.Parcel;
import parcel.ParcelService;
import payment.PaymentService;
import user.User;

import java.util.Optional;

public record BlikPaymentCommand(Parcel parcel, User user, String rewardId, PaymentService paymentService,
                                 ParcelService parcelService) implements PaymentCommand {
    @Override
    public void execute() {
        paymentService.executeBlikPayment(parcel, user, rewardId);
        parcelService.generateFirstPrivilege(parcel,user);
    }
}
