package com.pat.s1ac.domain.validator.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlHandler {
    public static boolean isNotValidURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false; // Null or empty strings are not valid URLs
        }
        try {
            new URL(url).toURI(); // Check if URL and URI syntax are valid
            return true;
        } catch (MalformedURLException | IllegalArgumentException | URISyntaxException e) {
            return false; // Invalid URL
        }
    }
}
