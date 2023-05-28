package storage.box;

import parcel.Parcel;
import user.User;

import java.util.Optional;

public interface Box {
    Optional<Parcel> giveOutParcel(User user);
    void acceptParcel(User user, Parcel parcel);
}
