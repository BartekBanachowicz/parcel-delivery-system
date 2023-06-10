package parcel;

import storage.Storage;

public class ParcelService {
    private final static ParcelService parcelService = new ParcelService();
    public static ParcelService getInstance() {return parcelService;}

    public boolean isFinalDestination(Parcel parcel, Storage storage) {
        return false;
    }
}
