package hiroliang.tools.loggercontroller.util;

import hiroliang.tools.loggercontroller.marker.MarkerConfig;
import hiroliang.tools.loggercontroller.marker.StandardMarker;

import java.time.LocalDateTime;

public class MarkerFactory {

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

    private static String substringMarker(final String marker) {
        return marker.substring(0, marker.indexOf("Marker"));
    }
}
