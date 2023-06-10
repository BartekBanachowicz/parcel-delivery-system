package storage;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.box.Box;

import java.util.List;
import java.util.Optional;

public class IntermediateStorage implements Storage{

    @Override
    public Optional<Parcel> giveOutParcel(ParcelOperationCommand command) {
        return Optional.empty();
    }

    @Override
    public void acceptParcel(ParcelOperationCommand command, Parcel parcel) {

    }

    @Override
    public List<Box> getBoxes() {
        return null;
    }
}
