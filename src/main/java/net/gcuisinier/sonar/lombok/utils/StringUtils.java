package net.gcuisinier.sonar.lombok.utils;

public final class StringUtils {

    private StringUtils() {
        // Utility class
    }

    public static String spaces(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

}
