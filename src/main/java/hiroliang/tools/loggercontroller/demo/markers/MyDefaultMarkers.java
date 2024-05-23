package hiroliang.tools.loggercontroller.demo.markers;

import hiroliang.tools.loggercontroller.demo.markers.config.CustomMarkerConfig;
import hiroliang.tools.loggercontroller.demo.markers.customs.ChildMarker;
import hiroliang.tools.loggercontroller.marker.DefaultMarkers;
import hiroliang.tools.loggercontroller.marker.StandardMarker;
import hiroliang.tools.loggercontroller.util.MarkerFactory;
import hiroliang.tools.loggercontroller.util.annotations.AsDefaultMarkers;
import org.slf4j.Marker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@AsDefaultMarkers // 標註為自己要的 Default ，若沒有會使用工具預設 ( 只有 Default marker )
@Component // 須讓自定義的 Default 給 Spring 監管
public class MyDefaultMarkers implements DefaultMarkers {

    /*
     * 複寫介面方法，回傳要使用的預設 Marker List
     */
    @Override
    public List<Marker> getMarkers() {
        return List.of(getCustomMarker());
    }

    private StandardMarker getCustomMarker() {
        CustomMarkerConfig customMarkerConfig = new CustomMarkerConfig();
        return Objects.requireNonNull(MarkerFactory.create(customMarkerConfig, CustomsMarker.class))
                .addChild(MarkerFactory.create(customMarkerConfig, ChildMarker.class));
    }
}
