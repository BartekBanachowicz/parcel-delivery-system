package user.courier;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;
import user.UserDeactivatedException;

import java.util.Optional;

public class DeactivatedCourier implements CourierState {

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        throw new UserDeactivatedException();
    }

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        throw new UserDeactivatedException();
    }
}
