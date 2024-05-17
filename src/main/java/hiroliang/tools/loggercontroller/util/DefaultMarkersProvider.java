package hiroliang.tools.loggercontroller.util;

import hiroliang.tools.loggercontroller.marker.DefaultMarkers;
import hiroliang.tools.loggercontroller.marker.StandardMarkerList;
import hiroliang.tools.loggercontroller.util.annotations.AsDefaultMarkers;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DefaultMarkersProvider {
    @Getter
    private final DefaultMarkers defaultMarkers;

    private final ApplicationContext applicationContext;

    @Autowired
    public DefaultMarkersProvider(ApplicationContext applicationContext) {
        log.info("Initializing DefaultMarkers...");
        this.applicationContext = applicationContext;
        this.defaultMarkers = getDefaultMarkersImpl();
        log.info("Use DefaultMarkers: {}", this.defaultMarkers.getClass().getSimpleName());
    }

    private DefaultMarkers getDefaultMarkersImpl() {
        Map<String, DefaultMarkers> beans = applicationContext.getBeansOfType(DefaultMarkers.class);
        for (DefaultMarkers defaultMarkersImpl : beans.values()) {
            if(defaultMarkersImpl.getClass().isAnnotationPresent(AsDefaultMarkers.class))
                return defaultMarkersImpl;
        }
        return new StandardMarkerList();
    }
}
