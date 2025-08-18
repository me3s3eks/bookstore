package com.encom.bookstore.utils;

import org.mapstruct.Named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final int ISBN_MAX_LENGTH = 13;

    private static final Pattern ISBN_PATTERN = Pattern
        .compile("^(?:97[89][-]?)?(?:\\d{1,5}[-]?\\d{1,7}[-]?\\d{1,7}[-]?[\\dXx]{1})$");

    private static final Pattern ISBN_DISALLOWED_CHARS_PATTERN = Pattern
        .compile("[^\\dXx]");

    private static final Pattern HYPHEN_PATTERN = Pattern.compile("-");

    public static boolean hasValidIsbn(String checkedString) {
        Matcher matcherIsbn = ISBN_PATTERN.matcher(checkedString);
        if (matcherIsbn.find()) {
             return normalizeIsbn(checkedString).length() <= ISBN_MAX_LENGTH;
        }
        return false;
    }

    public static String normalizeIsbn(String isbn) {
        return ISBN_DISALLOWED_CHARS_PATTERN
            .matcher(isbn)
            .replaceAll("")
            .toUpperCase();
    }

    public static String pathVariableToBookTypeString(String pathVariable) {
        return HYPHEN_PATTERN
            .matcher(pathVariable)
            .replaceAll("_")
            .toUpperCase();
    }
}
