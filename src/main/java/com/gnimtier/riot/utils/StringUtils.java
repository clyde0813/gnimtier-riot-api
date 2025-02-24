package com.gnimtier.riot.utils;

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String removeWhitespace(String str) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int index = 0;

        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(chars[i])) {
                chars[index++] = chars[i];
            }
        }
        return new String(chars, 0, index);
    }
}