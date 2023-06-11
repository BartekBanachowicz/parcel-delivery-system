package log;

import parcel.ParcelStatus;
import storage.Storage;
import user.User;

import java.time.ZonedDateTime;

public record ParcelEvent(String parcelId, Storage place, ParcelStatus status, User actor, ZonedDateTime dateTime) {
}
