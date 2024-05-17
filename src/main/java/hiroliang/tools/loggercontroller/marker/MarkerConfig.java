package hiroliang.tools.loggercontroller.marker;

import java.time.LocalDateTime;

public interface MarkerConfig {
    String getName();
    LocalDateTime getTimestamp();
    Integer getExpirationDays();
}
