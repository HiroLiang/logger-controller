package hiroliang.tools.loggercontroller.demo.markers.config;

import hiroliang.tools.loggercontroller.marker.MarkerConfig;

import java.time.LocalDateTime;

public class CustomMarkerConfig implements MarkerConfig {
    /*
     * 時間戳記：
     *     可在需要時更新時間。
     */
    @Override
    public LocalDateTime getTimestamp() {
        return LocalDateTime.of(2024, 5, 1, 0, 0, 0);
    }

    /*
     * 過期天數：
     *     時間戳後過幾天會過期。
     *
     *     例外 -1 ： 皆當未過期 ，0 ；皆當過期。
     */
    @Override
    public Integer getExpirationDays() {
        return -1;
    }
}
