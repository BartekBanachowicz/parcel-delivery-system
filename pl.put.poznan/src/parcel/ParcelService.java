package parcel;

import log.EventLog;
import log.ParcelEvent;
import payment.PaymentService;
import payment.command.PaymentMethod;
import privilege.Privilege;
import privilege.PrivilegeService;
import storage.Storage;
import user.User;

import java.util.Optional;
import java.util.UUID;

import static operations.OperationType.PUT;
import static parcel.ParcelStatus.UNKNOWN;

public class ParcelService {

    public ParcelService(PaymentService paymentService, EventLog eventLog, PrivilegeService privilegeService) {
        this.paymentService = paymentService;
        this.eventLog = eventLog;
        this.privilegeService = privilegeService;
    }

    private final PaymentService paymentService;
    private final EventLog eventLog;
    private final PrivilegeService privilegeService;
    private final ParcelRepository parcelRepository = ParcelRepository.getInstance();

    public String registerNewParcel(ParcelSize size, Storage destination, PaymentMethod paymentMethod, String rewardId, User user) {
        String parcelId = UUID.randomUUID().toString();
        Parcel parcel = new Parcel(parcelId, size);
        parcelRepository.addNewParcel(parcel, destination);
        paymentMethod.getCommand(parcel, user, rewardId, paymentService, this).execute();
        return parcelId;
    }

    public void changeFinalDestination(String parcelId, Storage newDestination) {
        parcelRepository.changeDestination(parcelId, newDestination);
    }

    public ParcelStatus getParcelStatus(String parcelId) {
        Optional<ParcelEvent> latestEvent = eventLog.getLatestEvent(parcelId);
        if (latestEvent.isPresent()) {
            return latestEvent.get().status();
        } else {
            return UNKNOWN;
        }
    }

    public boolean isFinalDestination(Parcel parcel, Storage storage) {
        Optional<Storage> destination = parcelRepository.getDestination(parcel.parcelId());
        return destination.isPresent() && storage.equals(destination.get());
    }

    public void generateFirstPrivilege(Parcel parcel, User actor) {
        privilegeService.addNewPrivilege(new Privilege(parcel, actor, null, PUT, null));
    }
}
