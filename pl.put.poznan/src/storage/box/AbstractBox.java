package storage.box;

import parcel.Parcel;

import java.util.Optional;

public class AbstractBox implements Box {


    @Override
    public Optional<Parcel> giveOutParcel() {
        return Optional.empty();
    }

    @Override
    public Parcel getParcel() {
        return null;
    }

    @Override
    public void acceptParcel(Parcel parcel) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isBigEnough(Parcel parcel) {
        return false;
    }
}
