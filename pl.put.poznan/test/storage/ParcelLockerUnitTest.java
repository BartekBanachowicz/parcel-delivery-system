package storage;

import operations.ParcelOperationCommand;
import org.junit.jupiter.api.Test;
import parcel.Parcel;
import storage.box.Box;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static operations.OperationType.GET;
import static operations.OperationType.PUT;
import static org.mockito.Mockito.*;

class ParcelLockerUnitTest {

    private final List<Box> boxes = new ArrayList<>();
    private final ParcelLocker sut = ParcelLocker.of(boxes);

    @Test
    public void shouldReturnEmptyOnInvalidCommand() {
        //given
        User user = mock(User.class);
        ParcelOperationCommand command = new ParcelOperationCommand(user, Optional.empty(), PUT);

        //when
        sut.giveOutParcel(command);

        //then
        verifyNoInteractions(user);
    }

    @Test
    public void shouldGiveOutParcel() {
        //given
        User user = mock(User.class);
        ParcelOperationCommand command = new ParcelOperationCommand(user, Optional.empty(), GET);

        //when
        sut.giveOutParcel(command);

        //then
        verify(user).getParcel(command, sut);
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