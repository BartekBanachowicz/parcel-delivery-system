package user.courier;

import log.EventLog;
import notification.NotificationService;
import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import parcel.ParcelService;
import privilege.PrivilegeService;
import storage.Storage;
import storage.StorageFullException;
import storage.box.Box;
import user.client.Client;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ActiveCourierUnitTest {

    private final EventLog eventLog = mock(EventLog.class);
    private final PrivilegeService privilegeService = mock(PrivilegeService.class);
    private final ParcelService parcelService = mock(ParcelService.class);
    private final NotificationService notificationService = mock(NotificationService.class);
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final ActiveCourier sut = new ActiveCourier(privilegeService, parcelService, notificationService,eventLog, clock);

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
        verify(box).acceptParcel(parcel);
        verifyNoInteractions(privilegeService);
    }

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