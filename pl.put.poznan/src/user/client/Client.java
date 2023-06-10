package user.client;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import privilege.PrivilegeService;
import storage.Storage;
import storage.box.Box;
import user.User;

import java.util.Optional;

public class Client implements User {

    private final PrivilegeService privilegeService = PrivilegeService.getInstance();

    @Override
    public void putParcel(ParcelOperationCommand command, Parcel parcel, Storage storage) {
        if (!privilegeService.validate(parcel, storage, command)) {
            return;
        }

        Optional<Box> availableBox = storage.getBoxes()
                .stream()
                .filter(box -> box.isEmpty() && box.isBigEnough(parcel))
                .findFirst();

        if (availableBox.isEmpty()) {
            return;
        }

        availableBox.get().acceptParcel(parcel);
    }

    @Override
    public Optional<Parcel> getParcel(ParcelOperationCommand command, Storage storage) {
        if (command.accessCode().isEmpty()) {
            return Optional.empty();
        }

        Optional<Box> boxWithParcel = storage.getBoxes()
                .stream()
                .filter(box -> !box.isEmpty())
                .filter(box -> privilegeService.validate(box.getParcel(), storage, command))
                .findFirst();

        if (boxWithParcel.isEmpty()) {
            return Optional.empty();
        }

        return boxWithParcel.get().giveOutParcel();
    }
}
