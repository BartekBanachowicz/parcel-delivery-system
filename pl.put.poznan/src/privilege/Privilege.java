package privilege;

import operations.OperationType;
import parcel.Parcel;
import storage.Storage;
import user.User;

import java.util.Optional;

public record Privilege(
        Parcel target,
        User actor,
        Storage storage,
        OperationType operationType,
        Optional<String> accessCode) {
}
