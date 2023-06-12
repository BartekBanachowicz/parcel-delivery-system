package user.client;

import log.EventLog;
import operations.OperationType;
import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import privilege.ForbiddenOperation;
import privilege.PrivilegeService;
import storage.Storage;
import storage.StorageFullException;
import storage.box.Box;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientUnitTest {
    private final EventLog eventLog = mock(EventLog.class);
    private final PrivilegeService privilegeService = mock(PrivilegeService.class);
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final Client sut = new Client(privilegeService, eventLog, clock);

    @Test
    void shouldThrowOnPutWhenStorageIsFull() {
        //given
        ParcelOperationCommand command = mock(ParcelOperationCommand.class);
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        when(privilegeService.validate(parcel, null, command)).thenReturn(true);
        when(storage.getBoxes()).thenReturn(List.of());

        //when
        assertThrows(StorageFullException.class, () -> sut.putParcel(command, parcel, storage));

        //then
        verify(storage).getBoxes();
    }

    @Test
    void shouldThrowOnPutWhenUserHasNoPrivilege() {
        //given
        ParcelOperationCommand command = mock(ParcelOperationCommand.class);
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        when(privilegeService.validate(parcel, null, command)).thenReturn(false);

        //when
        assertThrows(ForbiddenOperation.class, () -> sut.putParcel(command, parcel, storage));

        //then
        verify(privilegeService).validate(parcel,null,command);
    }

    @Test
    void shouldNotThrowOnPut() {
        //given
        ParcelOperationCommand command = mock(ParcelOperationCommand.class);
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        Box box = mock(Box.class);
        when(privilegeService.validate(parcel, null, command)).thenReturn(true);
        when(storage.getBoxes()).thenReturn(List.of(box));
        when(box.isEmpty()).thenReturn(true);
        when(box.isBigEnough(parcel)).thenReturn(true);

        //when
        assertDoesNotThrow(() -> sut.putParcel(command, parcel, storage));

        //then
        verify(privilegeService).validate(parcel,null,command);
        verify(box).acceptParcel(parcel);
    }

    @Test
    void shouldReturnEmptyWhenAccessCodeNotPresent() {
        //given
        Storage storage = mock(Storage.class);
        ParcelOperationCommand command = new ParcelOperationCommand(sut, Optional.empty(), GET);

        //when
        Optional<Parcel> parcel = sut.getParcel(command, storage);

        //then
        assertTrue(parcel.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoBoxesAvailable() {
        //given
        String accessCode = "SDM";
        Storage storage = mock(Storage.class);
        ParcelOperationCommand command = new ParcelOperationCommand(sut, Optional.of(accessCode), GET);
        when(storage.getBoxes()).thenReturn(List.of());

        //when
        Optional<Parcel> parcel = sut.getParcel(command, storage);

        //then
        assertTrue(parcel.isEmpty());
    }
}