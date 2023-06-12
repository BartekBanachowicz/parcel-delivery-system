package parcel;

import log.EventLog;
import org.junit.jupiter.api.Test;
import payment.PaymentService;
import privilege.PrivilegeService;
import storage.Storage;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParcelServiceUnitTest {
    private final PrivilegeService privilegeService = mock(PrivilegeService.class);
    private final PaymentService paymentService = mock(PaymentService.class);
    private final EventLog eventLog = mock(EventLog.class);
    private final ParcelRepository parcelRepository = mock(ParcelRepository.class);
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final ParcelService sut = new ParcelService(paymentService, eventLog, privilegeService, parcelRepository, clock);

    @Test
    void shouldReturnTrueIfFinalDestination() {
        //given
        String parcelId = "id";
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        when(parcel.parcelId()).thenReturn(parcelId);
        when(parcelRepository.getDestination(parcelId)).thenReturn(Optional.of(storage));

        //when
        boolean result = sut.isFinalDestination(parcel, storage);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfNotFinalDestination() {
        //given
        String parcelId = "id";
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        Storage storage2 = mock(Storage.class);
        when(parcel.parcelId()).thenReturn(parcelId);
        when(parcelRepository.getDestination(parcelId)).thenReturn(Optional.of(storage2));

        //when
        boolean result = sut.isFinalDestination(parcel, storage);

        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfNoEventsExist() {
        //given
        String parcelId = "id";
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);
        when(parcel.parcelId()).thenReturn(parcelId);
        when(parcelRepository.getDestination(parcelId)).thenReturn(Optional.empty());

        //when
        boolean result = sut.isFinalDestination(parcel, storage);

        //then
        assertFalse(result);
    }
}