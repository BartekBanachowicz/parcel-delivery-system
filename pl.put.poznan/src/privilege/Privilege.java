package privilege;

import operations.OperationType;
import parcel.Parcel;
import storage.Storage;
import user.User;

import java.time.ZonedDateTime;
import java.util.Optional;

public record Privilege(
        Parcel target,
        User actor,
        Storage storage,
        OperationType operationType,
        Optional<String> accessCode,
        ZonedDateTime expirationTime) {

    boolean isValid(ZonedDateTime time) {
        return expirationTime.isAfter(time);
    }
}
