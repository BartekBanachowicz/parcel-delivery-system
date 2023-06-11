package payment.command;

import parcel.Parcel;
import parcel.ParcelService;
import payment.PaymentService;
import user.User;

public enum PaymentMethod {
    CARD {
        @Override
        public PaymentCommand getCommand(Parcel parcel, User user, String rewardId, PaymentService paymentService, ParcelService parcelService) {
            return new CardPaymentCommand(parcel, user, rewardId, paymentService, parcelService);
        }
    },
    BLIK {
        @Override
        public PaymentCommand getCommand(Parcel parcel, User user, String rewardId, PaymentService paymentService, ParcelService parcelService) {
            return new BlikPaymentCommand(parcel, user, rewardId, paymentService, parcelService);
        }
    };

    public abstract PaymentCommand getCommand(Parcel parcel, User user, String rewardId, PaymentService paymentService, ParcelService parcelService);
}
