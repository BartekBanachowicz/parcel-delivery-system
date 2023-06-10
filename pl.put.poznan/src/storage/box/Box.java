package storage.box;

import parcel.Parcel;
import user.User;

import java.util.Optional;

public interface Box {
    Optional<Parcel> giveOutParcel();
    Parcel getParcel();
    void acceptParcel(Parcel parcel);
    boolean isEmpty();
    boolean isBigEnough(Parcel parcel);
}
