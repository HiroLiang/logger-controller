package hiroliang.tools.loggercontroller.util;

import hiroliang.tools.loggercontroller.marker.MarkerConfig;
import hiroliang.tools.loggercontroller.marker.StandardMarker;
import hiroliang.tools.loggercontroller.marker.model.MarkerDto;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

import java.time.LocalDateTime;

public class MarkerFactory implements IMarkerFactory {

    public static StandardMarker create(MarkerConfig config, Class<?> clazz) {
        try {
            return (StandardMarker) clazz
                    .getConstructor(String.class, LocalDateTime.class, Integer.class)
                    .newInstance(substringMarker(clazz.getSimpleName()), config.getTimestamp(), config.getExpirationDays());
        } catch (Exception e){
            System.out.println("Error creating marker " + clazz);
            return null;
        }
    }

    public static StandardMarker create(String className, LocalDateTime timestamp, Integer expirationDays) {
        try {
            Class<?> clazz = Class.forName(className);
            return (StandardMarker) clazz
                    .getConstructor(String.class, LocalDateTime.class, Integer.class)
                    .newInstance(substringMarker(clazz.getSimpleName()), timestamp, expirationDays);
        } catch (Exception e){
            System.out.println("Error creating marker " + className);
            return null;
        }
    }

    @Override
    public Marker getMarker(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Marker name must not be null");
        } else {
            return new StandardMarker(name, LocalDateTime.now(), -1);
        }
    }

    @Override
    public boolean exists(String marker) {
        return false;
    }

    @Override
    public boolean detachMarker(String s) {
        return false;
    }

    @Override
    public Marker getDetachedMarker(String s) {
        return null;
    }

    private static String substringMarker(final String marker) {
        return marker.substring(0, marker.indexOf("Marker"));
    }
}
