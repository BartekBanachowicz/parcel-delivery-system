package storage;

import parcel.Parcel;
import storage.box.Box;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ParcelLocker implements Storage {
    private final List<Box> boxes;
    private final HashMap<String, Box> parcelsMap;

    private ParcelLocker(List<Box> boxesList) {
        boxes = boxesList;
        parcelsMap = new HashMap<>();
    }

    public static ParcelLocker of(List<Box> boxesList) {
        return new ParcelLocker(boxesList);
    }

    @Override
    public Optional<Parcel> giveOutParcel(User user, String accessCode) {
        Optional<Box> box = Optional.ofNullable(parcelsMap.get(accessCode));
        if(box.isEmpty()) {
            return Optional.empty();
        }
        return box.get().giveOutParcel(user);
    }

    @Override
    public List<Parcel> giveOutParcels(User user) {
        List<Parcel> parcels = new ArrayList<>();
        for (Box box : boxes) {
            box.giveOutParcel(user).ifPresent(parcels::add);
        }
        return parcels;
    }

    @Override
    public void acceptParcel(User user, Parcel parcel) {

    }
}
