package hiroliang.tools.loggercontroller.demo.markers;

import hiroliang.tools.loggercontroller.marker.StandardMarker;

import java.time.LocalDateTime;

public class CustomsMarker extends StandardMarker {
    public CustomsMarker(String name, LocalDateTime timestamp, Integer expirationDays) {
        super(name, timestamp, expirationDays);
    }
}
