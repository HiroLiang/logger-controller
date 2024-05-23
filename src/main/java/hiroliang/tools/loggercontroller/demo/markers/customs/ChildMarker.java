package hiroliang.tools.loggercontroller.demo.markers.customs;

import hiroliang.tools.loggercontroller.marker.StandardMarker;

import java.time.LocalDateTime;

public class ChildMarker extends StandardMarker {
    public ChildMarker(String name, LocalDateTime timestamp, Integer expirationDays) {
        super(name, timestamp, expirationDays);
    }
}
