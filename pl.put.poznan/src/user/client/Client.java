package user.client;

import log.EventLog;
import log.ParcelEvent;
import operations.ParcelOperationCommand;
import parcel.Parcel;
import privilege.ForbiddenOperation;
import privilege.PrivilegeService;
import storage.Storage;
import storage.StorageFullException;
import storage.box.Box;
import user.User;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

import static parcel.ParcelStatus.DELIVERED;
import static parcel.ParcelStatus.IN_STORAGE;

public class Client implements User {

    private final PrivilegeService privilegeService;
    private final EventLog eventLog;
    private final Clock clock;

    public Client(PrivilegeService privilegeService, EventLog eventLog, Clock clock) {
        this.privilegeService = privilegeService;
        this.eventLog = eventLog;
        this.clock = clock;
    }

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        if (!privilegeService.validate(parcel, null, command)) {
            throw new ForbiddenOperation();
        }

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
    }

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        if (command.accessCode().isEmpty()) {
            return Optional.empty();
        }

        Optional<Box> boxWithParcel = storage.getBoxes()
                .stream()
                .filter(box -> !box.isEmpty())
                .filter(box -> privilegeService.validate(box.getParcel(), storage, command))
                .findFirst();

        if (boxWithParcel.isEmpty()) {
            return Optional.empty();
        }

        eventLog.registerNewParcelEvent(
                new ParcelEvent(
                        boxWithParcel.get().getParcel().parcelId(),
                        storage,
                        DELIVERED,
                        this,
                        ZonedDateTime.now(clock)
                )
        );

        storage.executePostGiveOutAction(boxWithParcel.get());
        return boxWithParcel.get().giveOutParcel();
    }
}
