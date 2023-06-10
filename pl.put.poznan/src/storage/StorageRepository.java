package storage;

import java.util.ArrayList;
import java.util.List;

class StorageRepository {

    private static final StorageRepository STORAGE_REPOSITORY = new StorageRepository();

    static StorageRepository getInstance() {
        return STORAGE_REPOSITORY;
    }

    private final List<ParcelLocker> parcelLockerList;
    private final List<ExternalStorage> externalStorageList;
    private final List<IntermediateStorage> intermediateStorageList;
    private final List<ParcelDestroyer> parcelDestroyerList;

    public StorageRepository() {
        this.parcelLockerList = new ArrayList<>();
        this.externalStorageList = new ArrayList<>();
        this.intermediateStorageList = new ArrayList<>();
        this.parcelDestroyerList = new ArrayList<>();
    }


    List<ParcelLocker> getParcelLockerList() {
        return parcelLockerList;
    }

    List<ExternalStorage> getExternalStorageList() {
        return externalStorageList;
    }

    List<IntermediateStorage> getIntermediateStorageList() {
        return intermediateStorageList;
    }

    List<ParcelDestroyer> getParcelDestroyerList() {
        return parcelDestroyerList;
    }

    void addNewParcelLocker(ParcelLocker parcelLocker) {
        this.parcelLockerList.add(parcelLocker);
    }

    void addNewExternalStorage(ExternalStorage externalStorage) {
        this.externalStorageList.add(externalStorage);
    }

    void addNewIntermediateStorage(IntermediateStorage intermediateStorage) {
        this.intermediateStorageList.add(intermediateStorage);
    }

    void addNewParcelDestroyer(ParcelDestroyer parcelDestroyer) {
        this.parcelDestroyerList.add(parcelDestroyer);
    }
}
