package operations;

import user.User;

import java.util.Optional;

public record ParcelOperationCommand(User user, Optional<String> accessCode, OperationType operationType) {
}
