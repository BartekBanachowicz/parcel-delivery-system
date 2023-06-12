package privilege;

import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import storage.Storage;
import user.User;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrivilegeServiceUnitTest {

    private final PrivilegeRepository privilegeRepository = mock(PrivilegeRepository.class);
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final PrivilegeService sut = new PrivilegeService(privilegeRepository, clock);

    @Test
    void shouldReturnFalseIfPrivilegeNotPresent() {
        //given
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        ParcelOperationCommand command = mock(ParcelOperationCommand.class);

        //when
        boolean result = sut.validate(parcel, storage, command);

        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfPrivilegeNotValid() {
        //given
        Parcel parcel = mock(Parcel.class);
        User user = mock(User.class);
        Storage storage = mock(Storage.class);
        Optional<String> accessCode = Optional.of("SDM");
        ParcelOperationCommand command = new ParcelOperationCommand(user, accessCode, GET);
        Privilege privilege = new Privilege(parcel, user, storage, GET, accessCode, ZonedDateTime.now(clock).minusDays(1));
        when(privilegeRepository.getPrivileges()).thenReturn(List.of(privilege));

        //when
        boolean result = sut.validate(parcel, storage, command);

        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueIfPrivilegeValid() {
        //given
        Parcel parcel = mock(Parcel.class);
        User user = mock(User.class);
        Storage storage = mock(Storage.class);
        Optional<String> accessCode = Optional.of("SDM");
        ParcelOperationCommand command = new ParcelOperationCommand(user, accessCode, GET);
        Privilege privilege = new Privilege(parcel, user, storage, GET, accessCode, ZonedDateTime.now(clock).plusDays(1));
        when(privilegeRepository.getPrivileges()).thenReturn(List.of(privilege));

        //when
        boolean result = sut.validate(parcel, storage, command);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfValidPrivilegesExist() {
        //given
        Parcel parcel = mock(Parcel.class);
        User user = mock(User.class);
        Storage storage = mock(Storage.class);
        Optional<String> accessCode = Optional.of("SDM");
        ParcelOperationCommand command = new ParcelOperationCommand(user, accessCode, GET);
        Privilege privilege = new Privilege(parcel, user, storage, GET, accessCode, ZonedDateTime.now(clock).plusDays(1));
        when(privilegeRepository.getPrivileges()).thenReturn(List.of(privilege));

        //when
        boolean result = sut.noValidPrivilegesPresent(parcel, storage, command);

        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueIfValidPrivilegesNotExist() {
        //given
        Parcel parcel = mock(Parcel.class);
        User user = mock(User.class);
        Storage storage = mock(Storage.class);
        Optional<String> accessCode = Optional.of("SDM");
        ParcelOperationCommand command = new ParcelOperationCommand(user, accessCode, GET);
        Privilege privilege = new Privilege(parcel, user, storage, GET, accessCode, ZonedDateTime.now(clock).minusDays(1));
        when(privilegeRepository.getPrivileges()).thenReturn(List.of(privilege));

        //when
        boolean result = sut.noValidPrivilegesPresent(parcel, storage, command);

        //then
        assertTrue(result);
    }
}