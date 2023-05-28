package user;

import parcel.Parcel;
import storage.Storage;

public interface User {
    void getParcel(Parcel parcel);

    void putParcel(Parcel parcel);
}
