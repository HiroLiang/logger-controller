package hiroliang.tools.loggercontroller.util;

import hiroliang.tools.loggercontroller.marker.StandardMarker;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class MarkerHandler {

    private static final Map<String, Marker> markers = new HashMap<>();

    private final DefaultMarkersProvider provider;

    @Autowired
    public MarkerHandler(DefaultMarkersProvider provider) {
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        MarkerHandler.cleanMarkers();
        setAllMarkers(provider.getDefaultMarkers().getMarkers().iterator());
        log.info("Initialized Marker Handler...");
        log.info("Default markers: {}", markers);
    }

    public static void init(List<StandardMarker> markerList) {
        MarkerHandler.cleanMarkers();
        setAllMarkers(markerList.stream().map(marker -> (Marker) marker).toList().iterator());
        log.info("Update Marker Handler...");
        log.info("Use new markers: {}", markers);
    }

    public static Marker getMarker(String name) {
        Marker marker = MarkerHandler.markers.get(name);
        if (marker == null)
            return MarkerHandler.markers.get("Default");
        return marker;
    }

    public static List<Marker> getModelList() {
        return new ArrayList<>(MarkerHandler.markers.values());
    }

    private static void setAllMarkers(Iterator<Marker> markerIterator) {
        while (markerIterator.hasNext()) {
            final Marker marker = markerIterator.next();
            MarkerHandler.markers.put(marker.getName(), marker);
            if(marker.hasReferences())
                setAllMarkers(marker.iterator());
        }
    }

    private static void cleanMarkers() {
        MarkerHandler.markers.clear();
        MarkerHandler.markers.put("Default", new StandardMarker("Default", LocalDateTime.now(), -1));
    }
}