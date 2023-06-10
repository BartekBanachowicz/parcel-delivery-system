package user.courier;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;
import user.User;

import java.util.Optional;

public class Courier implements User {
    private CourierState state;

    public Courier(CourierState state) {
        this.state = state;
    }

    public void changeState(CourierState newState) {
        this.state = newState;
    }

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        return state.getParcel(command, storage);
    }

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        state.putParcel(command, parcel, storage);
    }
}
