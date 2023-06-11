package log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EventLog {
    private final List<ParcelEvent> parcelEvents = new ArrayList<>();

    public void registerNewParcelEvent(ParcelEvent parcelEvent) {
        parcelEvents.add(parcelEvent);
    }

    public Optional<ParcelEvent> getLatestEvent(String parcelId) {
        return parcelEvents.stream()
                .filter(parcelEvent -> parcelEvent.parcelId().equals(parcelId))
                .max(Comparator.comparing(ParcelEvent::dateTime));
    }
}
