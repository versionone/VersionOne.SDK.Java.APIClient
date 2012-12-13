package com.versionone.apiclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValueStringizer {
    private final String valueQuoteCharacter;

    public ValueStringizer() {
        this("'");
    }

    public ValueStringizer(String valueQuoteCharacter) {
        this.valueQuoteCharacter = valueQuoteCharacter;
    }

    public String stringize(Object value) {
        String valueString = value != null ? format(value) : "";
        valueString = valueString.replace("'", "''").replace("\"", "\"\""); // VersionOne value encoding
        return valueQuoteCharacter + String.format("%1$s", valueString) + valueQuoteCharacter;
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
