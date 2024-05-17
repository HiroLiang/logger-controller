package hiroliang.tools.loggercontroller.marker;

import java.time.LocalDateTime;

public interface MarkerConfig {
    LocalDateTime getTimestamp();
    Integer getExpirationDays();
}
