package user.courier;

import parcel.Parcel;
import storage.Storage;
import user.User;

public class Courier implements User {
    private CourierState state;

    public Courier(CourierState state) {
        this.state = state;
    }

    @Override
    public void getParcel(Parcel parcel) {
        state.getParcel(parcel);
    }

    @Override
    public void putParcel(Parcel parcel) {
        state.putParcel(parcel);
    }

    public void changeState(CourierState newState) {
        this.state = newState;
    }
}
