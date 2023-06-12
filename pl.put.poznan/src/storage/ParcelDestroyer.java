package storage;

import log.EventLog;
import log.ParcelEvent;
import operations.ParcelOperationCommand;
import parcel.Parcel;
import privilege.ForbiddenOperation;
import storage.box.AbstractBox;
import storage.box.Box;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static parcel.ParcelStatus.DESTROYED;

public class ParcelDestroyer implements Storage {

    private final EventLog eventLog;
    private final Clock clock;

    public ParcelDestroyer(EventLog eventLog, Clock clock) {
        this.eventLog = eventLog;
        this.clock = clock;
    }

    @Override
    public Optional<Parcel> giveOutParcel(ParcelOperationCommand command) {
        throw new ForbiddenOperation();
    }

    @Override
    public void acceptParcel(ParcelOperationCommand command, Parcel parcel) {
        if (GET.equals(command.operationType())) {
            return;
        }

        command.user().putParcel(command, parcel, this);
        eventLog.registerNewParcelEvent(
                new ParcelEvent(
                        parcel.parcelId(),
                        this,
                        DESTROYED,
                        command.user(),
                        ZonedDateTime.now(clock)
                )
        );
    }

    @Override
    public void executePostGiveOutAction(Box box) {
        
    }

    @Override
    public List<Box> getBoxes() {
        return List.of(new AbstractBox());
    }
}
