package storage.box;

import parcel.Parcel;
import parcel.ParcelSize;

import java.util.Optional;

public class LockerBox implements Box {

    private Parcel parcel;
    private final ParcelSize size;

    public LockerBox(ParcelSize size) {
        this.size = size;
    }

    @Override
    public Optional<Parcel> giveOutParcel() {
        Optional<Parcel> parcelToBeGivenOut = Optional.ofNullable(parcel);
        parcel = null;
        return parcelToBeGivenOut;
    }

    @Override
    public Parcel getParcel() {
        return parcel;
    }

    @Override
    public void acceptParcel(Parcel newParcel) {
        parcel = newParcel;
    }

    @Override
    public boolean isEmpty() {
        return parcel == null;
    }

    @Override
    public boolean isBigEnough(Parcel parcel) {
        return size.ordinal() >= parcel.size().ordinal();
    }
}
