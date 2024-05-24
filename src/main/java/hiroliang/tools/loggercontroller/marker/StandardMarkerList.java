package hiroliang.tools.loggercontroller.marker;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class StandardMarkerList implements DefaultMarkers{
    @Override
    public List<Marker> getMarkers() {
        return List.of(
                new StandardMarker("Default", LocalDateTime.now(), -1)
        );
    }
}
