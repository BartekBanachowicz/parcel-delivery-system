package user;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;

import java.util.Optional;

public interface User {
    Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage);
    void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage);
}
