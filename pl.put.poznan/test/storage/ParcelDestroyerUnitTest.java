package storage;

import log.EventLog;
import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import privilege.ForbiddenOperation;
import user.User;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static operations.OperationType.GET;
import static operations.OperationType.PUT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ParcelDestroyerUnitTest {

    private final EventLog eventLog = mock(EventLog.class);
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final ParcelDestroyer sut = new ParcelDestroyer(eventLog, clock);

    @Test
    public void shouldThrowOnGiveOut() {
        //given
        User user = mock(User.class);
        ParcelOperationCommand command = new ParcelOperationCommand(user, Optional.empty(), PUT);

        //when & then
        assertThrows(ForbiddenOperation.class, () -> sut.giveOutParcel(command));
    }

    @Test
    public void shouldNotAcceptOnInvalidCommand() {
        //given
        User user = mock(User.class);
        Parcel parcel = mock(Parcel.class);
        ParcelOperationCommand command = new ParcelOperationCommand(user, Optional.empty(), GET);

        //when
        sut.acceptParcel(command, parcel);

        //then
        verifyNoInteractions(user);
    }

    @Test
    public void shouldAcceptParcel() {
        //given
        User user = mock(User.class);
        Parcel parcel = mock(Parcel.class);
        ParcelOperationCommand command = new ParcelOperationCommand(user, Optional.empty(), PUT);

        //when
        sut.acceptParcel(command, parcel);

        //then
        verify(user).putParcel(command, parcel, sut);
    }
}