package privilege;

import operations.ParcelOperationCommand;
import parcel.Parcel;
import storage.Storage;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final Clock clock;

    public PrivilegeService(PrivilegeRepository privilegeRepository, Clock clock) {
        this.privilegeRepository = privilegeRepository;
        this.clock = clock;
    }

    public boolean validate(Parcel parcel, Storage storage, ParcelOperationCommand command) {

        Optional<Privilege> sufficientPrivilege = privilegeRepository
                .getPrivileges()
                .stream()
                .filter(privilege -> privilege.target().equals(parcel) &&
                                    privilege.operationType().equals(command.operationType()) &&
                                    privilege.actor().equals(command.user()) &&
                                    privilege.storage().equals(storage) &&
                                    privilege.accessCode().equals(command.accessCode()) &&
                                    privilege.isValid(ZonedDateTime.now(clock)))
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

    private void dropPrivilege(Privilege privilege) {
        privilegeRepository.dropPrivilege(privilege);
    }

    public boolean noValidPrivilegesPresent(Parcel parcel, Storage storage, ParcelOperationCommand command) {
        Optional<Privilege> sufficientPrivilege = privilegeRepository
                .getPrivileges()
                .stream()
                .filter(privilege -> privilege.storage().equals(storage)
                        && privilege.target().equals(parcel)
                        && privilege.operationType().equals(command.operationType())
                        && privilege.isValid(ZonedDateTime.now(clock))
                )
                .findFirst();
        return sufficientPrivilege.isEmpty();
    }
}
