package user.courier;

import parcel.Parcel;
import storage.Storage;
import user.UserDeactivatedException;

public class DeactivatedCourier implements CourierState{

    @Override
    public void getParcel(Parcel parcel) {
        throw new UserDeactivatedException();
    }

    @Override
    public void putParcel(Parcel parcel) {
        throw new UserDeactivatedException();
    }
}
