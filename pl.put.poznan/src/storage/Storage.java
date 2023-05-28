package storage;

import parcel.Parcel;
import user.User;

import java.util.List;
import java.util.Optional;

public interface Storage {
    Optional<Parcel> giveOutParcel(User user, String accessCode);
    List<Parcel> giveOutParcels(User user);
    void acceptParcel(User user, Parcel parcel);
}
