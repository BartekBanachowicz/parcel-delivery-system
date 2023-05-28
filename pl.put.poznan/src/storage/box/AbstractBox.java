package storage.box;

import parcel.Parcel;
import user.User;

import java.util.Optional;

public class AbstractBox implements Box {

    @Override
    public Optional<Parcel> giveOutParcel(User user) {
        return Optional.empty();
    }

    @Override
    public void acceptParcel(User user, Parcel parcel) {

    }
}
