package storage;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.box.Box;

import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static operations.OperationType.PUT;

public class ParcelDestroyer implements Storage{

    private final List<Box> boxes;

    private ParcelDestroyer(List<Box> boxes) {
        this.boxes = boxes;
    }

    public static ParcelDestroyer of(List<Box> boxesList) {
        return new ParcelDestroyer(boxesList);
    }

    @Override
    public Optional<Parcel> giveOutParcel(ParcelOperationCommand command) {
        if (PUT.equals(command.operationType())) {
            return Optional.empty();
        }
        return command.user().getParcel(command, this);
    }

    @Override
    public void acceptParcel(ParcelOperationCommand command, Parcel parcel) {
        if (GET.equals(command.operationType())) {
            return;
        }
        command.user().putParcel(command, parcel, this);
    }

    @Override
    public List<Box> getBoxes() {
        return boxes;
    }
}
