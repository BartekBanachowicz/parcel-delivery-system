package user.courier;

import parcel.Parcel;
import privilege.PrivilegeService;
import storage.Storage;

public class ActiveCourier implements CourierState {
    private final PrivilegeService privilegeService = PrivilegeService.getInstance();

    @Override
    public void getParcel(Parcel parcel) {

    }

    @Override
    public void putParcel(Parcel parcel) {

    }
}
