package storage;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import privilege.ForbiddenOperation;
import storage.box.AbstractBox;
import storage.box.Box;

import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;

public class ParcelDestroyer implements Storage {

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
    }

    @Override
    public void executePostGiveOutAction(Box box) {
        throw new ForbiddenOperation();
    }

    @Override
    public List<Box> getBoxes() {
        return List.of(new AbstractBox());
    }
}
