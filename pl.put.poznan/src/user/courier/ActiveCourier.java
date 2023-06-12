package user.courier;

import log.EventLog;
import log.ParcelEvent;
import notification.NotificationService;
import operations.ParcelOperationCommand;
import parcel.Parcel;
import parcel.ParcelService;
import privilege.Privilege;
import privilege.PrivilegeService;
import storage.Storage;
import storage.StorageFullException;
import storage.box.Box;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static operations.OperationType.GET;
import static parcel.ParcelStatus.*;

public class ActiveCourier implements CourierState {

    private final PrivilegeService privilegeService;
    private final ParcelService parcelService;
    private final NotificationService notificationService;
    private final EventLog eventLog;
    private final Clock clock;

    public ActiveCourier(PrivilegeService privilegeService, ParcelService parcelService, NotificationService notificationService, EventLog eventLog, Clock clock) {
        this.privilegeService = privilegeService;
        this.parcelService = parcelService;
        this.notificationService = notificationService;
        this.eventLog = eventLog;
        this.clock = clock;
    }

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        Optional<Box> boxWithParcel = storage.getBoxes()
                .stream()
                .filter(box -> !box.isEmpty())
                .filter(box -> privilegeService.noValidPrivilegesPresent(box.getParcel(), storage, command))
                .findFirst();

        if (boxWithParcel.isEmpty()) {
            return Optional.empty();
        }

        storage.executePostGiveOutAction(boxWithParcel.get());
        eventLog.registerNewParcelEvent(
                new ParcelEvent(
                        boxWithParcel.get().getParcel().parcelId(),
                        storage,
                        IN_TRANSPORT,
                        this,
                        ZonedDateTime.now(clock)
                )
        );
        return boxWithParcel.get().giveOutParcel();
    }

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        Optional<Box> availableBox = storage.getBoxes()
                .stream()
                .filter(box -> box.isEmpty() && box.isBigEnough(parcel))
                .findFirst();

        if (availableBox.isEmpty()) {
            throw new StorageFullException();
        }

        eventLog.registerNewParcelEvent(
                new ParcelEvent(
                        parcel.parcelId(),
                        storage,
                        IN_STORAGE,
                        this,
                        ZonedDateTime.now(clock)
                )
        );
        availableBox.get().acceptParcel(parcel);
        if(parcelService.isFinalDestination(parcel, storage)){
            String accessCode = UUID.randomUUID().toString().substring(0, 5);
            privilegeService.addNewPrivilege(
                    new Privilege(
                            parcel,
                            parcel.parcelReceiver(),
                            storage,
                            GET,
                            Optional.of(accessCode),
                            ZonedDateTime.now(clock).plusDays(2)
                    )
            );
            notificationService.sendParcelDeliveredNotification(parcel.parcelReceiver(), parcel, accessCode);
        }
    }
}
