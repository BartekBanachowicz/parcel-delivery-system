package storage;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.box.Box;
import user.User;

import java.util.List;
import java.util.Optional;

public interface Storage {
    Optional<Parcel> giveOutParcel(ParcelOperationCommand command);
    void acceptParcel(ParcelOperationCommand command, Parcel parcel);
    List<Box> getBoxes();
}
