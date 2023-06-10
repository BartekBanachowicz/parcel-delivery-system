package privilege;

import operations.OperationType;
import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;
import user.User;

import java.util.Optional;

public class PrivilegeService {
    private static final PrivilegeService privilegeService = new PrivilegeService();

    public static PrivilegeService getInstance() {
        return privilegeService;
    }

    public boolean validate(Parcel parcel, Storage storage, ParcelOperationCommand command) {
        Privilege privilegeToCompare = new Privilege(parcel, command.user(), storage, command.operationType(), command.accessCode());
        Optional<Privilege> sufficientPrivilege = PrivilegeRepository.getInstance()
                .getPrivileges()
                .stream()
                .filter(privilege -> privilege.equals(privilegeToCompare))
                .findFirst();

        if (sufficientPrivilege.isPresent()) {
            dropPrivilege(sufficientPrivilege.get());
            return true;
        } else {
            return false;
        }
    }

    public void addNewPrivilege(Privilege privilege) {
        PrivilegeRepository.getInstance().addNewPrivilege(privilege);
    }

    public void dropPrivilege(Privilege privilege) {
        PrivilegeRepository.getInstance().dropPrivilege(privilege);
    }

    public boolean noPrivilegesPresent(Parcel parcel, Storage storage, ParcelOperationCommand command) {
        Privilege privilegeToCompare = new Privilege(parcel, command.user(), storage, command.operationType(), command.accessCode());
        Optional<Privilege> sufficientPrivilege = PrivilegeRepository.getInstance()
                .getPrivileges()
                .stream()
                .filter(privilege -> privilege.storage().equals(storage)
                        && privilege.target().equals(parcel)
                        && privilege.operationType().equals(command.operationType())
                )
                .findFirst();
        return sufficientPrivilege.isEmpty();
    }
}
