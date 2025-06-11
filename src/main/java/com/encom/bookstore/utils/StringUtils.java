package com.encom.bookstore.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.Named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final int ISBN_MAX_LENGTH = 13;

    public static String getBookCategoryFromRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String regex = "^/category/(?<category>[^/]+)/books$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestUri);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public static boolean hasValidIsbn(String checkedString) {
        String regexIsbn = "^(?:97[89][-]?)?(?:\\d{1,5}[-]?\\d{1,7}[-]?\\d{1,7}[-]?[\\dXx]{1})$";
        Pattern patternIsbn = Pattern.compile(regexIsbn);
        Matcher matcherIsbn = patternIsbn.matcher(checkedString);
        return matcherIsbn.find();
    }

    @Named("normalizeIsbn")
    public static String normalizeIsbn(String isbn) {
        return isbn.replaceAll("[^\\dXx]", "").toUpperCase();
    }
}
