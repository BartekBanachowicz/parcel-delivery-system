package storage;

import parcel.Parcel;
import user.User;

import java.util.List;
import java.util.Optional;

public class IntermediateStorage implements Storage{
    @Override
    public Optional<Parcel> giveOutParcel(User user, String accessCode) {
        return Optional.empty();
    }

    @Override
    public List<Parcel> giveOutParcels(User user) {
        return null;
    }

    @Override
    public void acceptParcel(User user, Parcel parcel) {

    }
}
