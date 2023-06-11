package user.courier;

import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import storage.Storage;
import user.User;
import user.UserDeactivatedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class DeactivatedCourierUnitTest {

    private final User sut = new Courier(new DeactivatedCourier());

    @Test
    void shouldThrowOnGetParcel() {
        //given
        ParcelOperationCommand parcelOperationCommand = mock(ParcelOperationCommand.class);
        Storage storage = mock(Storage.class);

        //when & then
        assertThrows(UserDeactivatedException.class, () -> sut.getParcel(parcelOperationCommand, storage));
    }

    @Test
    void shouldThrowOnPutParcel() {
        //given
        ParcelOperationCommand parcelOperationCommand = mock(ParcelOperationCommand.class);
        Parcel parcel = mock(Parcel.class);
        Storage storage = mock(Storage.class);

        //when & then
        assertThrows(UserDeactivatedException.class, () -> sut.putParcel(parcelOperationCommand, parcel, storage));
    }
}