/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Provides a method to determine a file's MIME type based on its filename
 */
public class MimeType {
    static FileNameMap fileNameMap = URLConnection.getFileNameMap();
    static Properties customMap = new Properties();
    static String MAPPING_FILE = "MimeType.properties";

    static {
        try {
            customMap.load(MimeType.class.getResourceAsStream(MAPPING_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Cannot find mime type mapping file: " + MAPPING_FILE, e);
        }
    }

    /**
     * Return the file's MIME type, based on it's name.
     *
     * @param fileName Name of the file
     * @return MIME type
     */
    public static String resolve(String fileName) {
        String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
        String res = customMap.getProperty(fileExt);
        if (res == null)
            res = fileNameMap.getContentTypeFor(fileName);
        if (res == null)
            res = "application/octet-stream";
        return res;
    }
}
