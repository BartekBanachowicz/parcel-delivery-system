package privilege;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;

import java.util.Optional;

public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public boolean validate(Parcel parcel, Storage storage, ParcelOperationCommand command) {
        Privilege privilegeToCompare = new Privilege(parcel, command.user(), storage, command.operationType(), command.accessCode());
        Optional<Privilege> sufficientPrivilege = privilegeRepository
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
        privilegeRepository.addNewPrivilege(privilege);
    }

    public void dropPrivilege(Privilege privilege) {
        privilegeRepository.dropPrivilege(privilege);
    }

    public boolean noValidPrivilegesPresent(Parcel parcel, Storage storage, ParcelOperationCommand command) {
        Privilege privilegeToCompare = new Privilege(parcel, command.user(), storage, command.operationType(), command.accessCode());
        Optional<Privilege> sufficientPrivilege = privilegeRepository
                .getPrivileges()
                .stream()
                .filter(privilege -> privilege.storage().equals(storage)
                        && privilege.target().equals(parcel)
                        && privilege.operationType().equals(command.operationType())
                )
                .findFirst();
        //TODO: valid
        return sufficientPrivilege.isEmpty();
    }
}
