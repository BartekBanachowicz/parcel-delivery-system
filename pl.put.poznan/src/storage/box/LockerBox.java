package storage.box;

import parcel.Parcel;
import parcel.ParcelSize;
import user.User;

import java.util.Optional;

public class LockerBox implements Box {

    private Parcel parcel;
    private final ParcelSize size;

    public LockerBox(ParcelSize size) {
        this.size = size;
    }

    @Override
    public Optional<Parcel> giveOutParcel(User user) {
        if (parcel == null) {
            return Optional.empty();
        }
        user.getParcel(parcel);
        return Optional.ofNullable(parcel);
    }

    @Override
    public void acceptParcel(User user, Parcel parcel) {
        if ((this.parcel != null) || (size.ordinal() < parcel.size().ordinal())) {
            throw new RuntimeException();
        }
        user.putParcel(parcel);
        //verify lack of parcel and size
    }
}
