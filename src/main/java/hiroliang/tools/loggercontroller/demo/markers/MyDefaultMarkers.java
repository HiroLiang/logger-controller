package hiroliang.tools.loggercontroller.demo.markers;

import hiroliang.tools.loggercontroller.marker.DefaultMarkers;
import hiroliang.tools.loggercontroller.util.annotations.AsDefaultMarkers;
import org.slf4j.Marker;
import org.springframework.stereotype.Component;

import java.util.List;

@AsDefaultMarkers
@Component
public class MyDefaultMarkers implements DefaultMarkers {
    @Override
    public List<Marker> getMarkers() {
        return List.of();
    }
}
