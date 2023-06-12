package parcel;

import storage.Storage;

import java.util.HashMap;
import java.util.Optional;

class ParcelRepository {
    private final HashMap<String, Parcel> parcelsMap = new HashMap<>();
    private final HashMap<String, Storage> destinationsMap = new HashMap<>();

    void addNewParcel(Parcel parcel, Storage destination) {
        parcelsMap.put(parcel.parcelId(), parcel);
        destinationsMap.put(parcel.parcelId(), destination);
    }

    public Optional<Storage> getDestination(String parcelId) {
        return Optional.ofNullable(destinationsMap.get(parcelId));
    }

    public void changeDestination(String parcelId, Storage newDestination) {
        destinationsMap.put(parcelId, newDestination);
    }
}
