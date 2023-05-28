package user.client;

import parcel.Parcel;
import privilege.PrivilegeService;
import storage.Storage;
import user.User;

public class Client implements User {

    private final PrivilegeService privilegeService = PrivilegeService.getInstance();

    @Override
    public void getParcel(Parcel parcel) {
        privilegeService.validate(this, parcel);
    }

    @Override
    public void putParcel(Parcel parcel) {
        privilegeService.validate(this, parcel);
    }
}
