package com.versionone.apiclient.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValueStringizer {

	String valueWrapper = "'";
    public ValueStringizer() {
    }
    
    public ValueStringizer(String valueWrapper) {
    	this.valueWrapper = valueWrapper;
    }

    public String stringize(Object value) {
        String valueString = value != null ? format(value) : "";
        valueString = valueString.replace("'", "''").replace("\"", "\"\"");

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
