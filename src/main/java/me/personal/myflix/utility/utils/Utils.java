package me.personal.myflix.utility.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter EUROPEAN_DATE_FORMATTER = DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING);
    public static final ZoneId ZONE_ROME = ZoneId.of("Europe/Rome");
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String currentFormattedTimestamp() {
        return LocalDateTime.now(ZONE_ROME).format(EUROPEAN_DATE_FORMATTER);
    }

    public static String decapitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

}
