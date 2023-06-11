package parcel;

import user.User;

public record Parcel (String parcelId, ParcelSize size, User parcelReceiver) {}