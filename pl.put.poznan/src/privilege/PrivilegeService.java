package privilege;

import parcel.Parcel;
import storage.ParcelLocker;
import storage.Storage;
import user.User;

public class PrivilegeService {
    private static final PrivilegeService privilegeService = new PrivilegeService();

    public static PrivilegeService getInstance() {
        return privilegeService;
    }

    public void validate(User user, Parcel parcel) {

    }
}
