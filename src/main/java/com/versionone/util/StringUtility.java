package com.versionone.util;

public class StringUtility {
    public static boolean IsNullOrEmpty(String value){
        if(value == null || value.isEmpty()) return true;
        return false;
    }
}
