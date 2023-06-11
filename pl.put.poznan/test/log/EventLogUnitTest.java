package log;

import org.junit.jupiter.api.Test;
import parcel.ParcelStatus;
import storage.Storage;
import user.User;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static parcel.ParcelStatus.DELIVERED;

class EventLogUnitTest {

    @Test
    void shouldRegisterAndGetNewParcelEvent() {
        //given
        EventLog sut = new EventLog();
        String randomId = UUID.randomUUID().toString();
        ZonedDateTime currentTime = ZonedDateTime.now();
        ParcelEvent parcelEvent = getParcelEvent(randomId, currentTime, DELIVERED);

        //when
        assertDoesNotThrow(() -> sut.registerNewParcelEvent(parcelEvent));
        Optional<ParcelEvent> parcelLatestEvent = sut.getLatestEvent(randomId);

        //then
        assertTrue(parcelLatestEvent.isPresent());
        assertEquals(parcelLatestEvent.get().parcelId(), randomId);
        assertEquals(parcelLatestEvent.get().status(), DELIVERED);
        assertEquals(parcelLatestEvent.get().dateTime(), currentTime);
    }

    @Test
    void shouldGetLatestParcelStatus() {
        //given
        EventLog sut = new EventLog();
        String randomId = UUID.randomUUID().toString();
        ZonedDateTime currentTime = ZonedDateTime.now();
        for (int i = 0; i < 4; i++) {
            currentTime = currentTime.plusDays(1);
            sut.registerNewParcelEvent(getParcelEvent(randomId, currentTime, DELIVERED));
        }

        //when
        Optional<ParcelEvent> parcelLatestEvent = sut.getLatestEvent(randomId);

        //then
        assertTrue(parcelLatestEvent.isPresent());
        assertEquals(parcelLatestEvent.get().parcelId(), randomId);
        assertEquals(parcelLatestEvent.get().status(), DELIVERED);
        assertEquals(parcelLatestEvent.get().dateTime(), currentTime);
    }

    @Test
    void shouldGetEmptyStatus() {
        //given
        EventLog sut = new EventLog();
        String randomId = UUID.randomUUID().toString();

        //when
        Optional<ParcelEvent> parcelLatestEvent = sut.getLatestEvent(randomId);

        //then
        assertTrue(parcelLatestEvent.isEmpty());
    }

    private ParcelEvent getParcelEvent(String parcelId, ZonedDateTime time, ParcelStatus status) {
        return new ParcelEvent(
                parcelId,
                mock(Storage.class),
                status,
                mock(User.class),
                time
        );
    }
}