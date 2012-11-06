package com.versionone.apiclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValueStringizer {
    private final String valueWrapper;

    public ValueStringizer() {
        this("'");
    }

    public ValueStringizer(String valueWrapper) {
        this.valueWrapper = valueWrapper;
    }

    public String stringize(Object value) {
        String valueString = value != null ? format(value) : "";
        valueString = valueString.replace("'", "''").replace("\"", "\"\"");

        try {
            valueString = URLEncoder.encode(valueString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            valueString = valueString.replace("+", "%2B");
            valueString = valueString.replace(" ", "+");
            valueString = valueString.replace("&", "%26");
            valueString = valueString.replace("#", "%23");
        }

        return String.format("%1$s%2$s%1$s", valueWrapper, valueString);
    }

    private static String format(Object value) {
        if(value instanceof Date) {
            Date date = (Date) value;
            Format dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return dateFormat.format(date);
        }

        return value.toString();
    }
}
