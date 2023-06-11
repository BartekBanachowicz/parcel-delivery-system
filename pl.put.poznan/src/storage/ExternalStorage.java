package storage;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.box.AbstractBox;
import storage.box.Box;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static operations.OperationType.GET;
import static operations.OperationType.PUT;

public class ExternalStorage implements Storage{

    private final Set<Box> boxes;

    public ExternalStorage() {
        this.boxes = new HashSet<>();
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
        boxes.add(new AbstractBox());
        command.user().putParcel(command, parcel, this);
    }

    @Override
    public void executePostGiveOutAction(Box box) {
        boxes.remove(box);
    }

    @Override
    public List<Box> getBoxes() {
        return boxes.stream().toList();
    }
}
