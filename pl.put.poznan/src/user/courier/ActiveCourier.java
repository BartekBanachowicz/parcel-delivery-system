package user.courier;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import parcel.ParcelService;
import privilege.PrivilegeService;
import storage.Storage;
import storage.box.Box;

import java.util.Optional;

public class ActiveCourier implements CourierState {

    private final PrivilegeService privilegeService;
    private final ParcelService parcelService;

    public ActiveCourier(PrivilegeService privilegeService, ParcelService parcelService) {
        this.privilegeService = privilegeService;
        this.parcelService = parcelService;
    }

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        Optional<Box> boxWithParcel = storage.getBoxes()
                .stream()
                .filter(box -> !box.isEmpty())
                .filter(box -> privilegeService.noValidPrivilegesPresent(box.getParcel(), storage, command))
                .findFirst();

        if (boxWithParcel.isEmpty()) {
            return Optional.empty();
        }

        return boxWithParcel.get().giveOutParcel();
    }

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        Optional<Box> availableBox = storage.getBoxes()
                .stream()
                .filter(box -> box.isEmpty() && box.isBigEnough(parcel))
                .findFirst();

        if (availableBox.isEmpty()) {
            return;
        }

        availableBox.get().acceptParcel(parcel);
        parcelService.isFinalDestination(parcel, storage);
        //TODO: privilegeService.addNewPrivilege();
    }
}
