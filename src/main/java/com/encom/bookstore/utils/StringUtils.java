package com.encom.bookstore.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
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
}
