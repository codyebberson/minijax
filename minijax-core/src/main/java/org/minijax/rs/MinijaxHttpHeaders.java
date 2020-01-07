package org.minijax.rs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.minijax.rs.util.LocaleUtils;
import org.minijax.rs.util.MediaTypeUtils;

public abstract class MinijaxHttpHeaders implements HttpHeaders {
    private List<Locale> acceptableLanguages;
    private List<MediaType> acceptableMediaTypes;
    private Map<String, Cookie> cookies;

    @Override
    public List<MediaType> getAcceptableMediaTypes() {
        if (acceptableMediaTypes == null) {
            acceptableMediaTypes = MediaTypeUtils.parseMediaTypes(getHeaderString("Accept"));
        }
        return acceptableMediaTypes;
    }

    @Override
    public List<Locale> getAcceptableLanguages() {
        if (acceptableLanguages == null) {
            acceptableLanguages = LocaleUtils.parseAcceptLanguage(getHeaderString("Accept-Language"));
        }
        return acceptableLanguages;
    }

    @Override
    public MediaType getMediaType() {
        final String contentType = getHeaderString("Content-Type");
        return contentType == null ? null : MediaType.valueOf(contentType);
    }

    @Override
    public Locale getLanguage() {
        final String languageTag = getHeaderString("Content-Language");
        return languageTag == null ? null : Locale.forLanguageTag(languageTag);
    }

    @Override
    public int getLength() {
        final String contentLength = getHeaderString("Content-Length");
        if (contentLength == null) {
            return -1;
        }
        try {
            return Integer.parseInt(contentLength);
        } catch (final NumberFormatException ex) {
            return -1;
        }
    }

    @Override
    public Date getDate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Cookie> getCookies() {
        if (cookies == null) {
            cookies = new HashMap<>();
            final List<String> cookieStrList = getRequestHeader("Cookie");
            if (cookieStrList != null) {
                for (final String cookieStr : cookieStrList) {
                    final int splitIndex = cookieStr.indexOf('=');
                    if (splitIndex >= 0) {
                        final String key = cookieStr.substring(0, splitIndex);
                        final String value = cookieStr.substring(splitIndex + 1);
                        cookies.put(key, new Cookie(key, value));
                    }
                }
            }
        }
        return cookies;
    }
}