/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class Attachments implements IAttachments {
    private final IAPIConnector _connector;

    public Attachments(IAPIConnector connector) {
        _connector = connector;
    }

    /**
     * Getting reader
     *
     * @param key path to the data on server
     * @return  the stream for reading data
     * @throws ConnectionException if any connection problems occur
     */
    public InputStream getReader(String key) throws ConnectionException {
        if (key != null && key.length() > 0) {
            final String path = key.substring(key.lastIndexOf('/') + 1);
            _connector.beginRequest(path, null);
            return _connector.endRequest(path);
        }
        return null;
    }

    /**
     * Getting writer
     *
     * @param key path to the data on server
     * @param contentType Content-type of HTTP header
     * @return  the stream for writing data
     * @throws ConnectionException if any connection problems occur
     */
    public OutputStream getWriter(String key, String contentType) throws ConnectionException {
        if(key != null && key.length()>0){
            return _connector.beginRequest(key.substring(key.lastIndexOf('/') + 1), contentType);
        }
        return null;
    }


    /**
     * Setting Writer
     *
     * @param key path to the data on server
     * @throws ConnectionException if any connection problems occur
     * @throws AttachmentLengthException if connection problem occurs
     *         by attachment size is too big
     */
    public void setWriter(String key) throws ConnectionException, AttachmentLengthException {
        try {
            if (key != null && key.length()>0){
                _connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
            }
        } catch (ConnectionException e) {
            if (e.getServerResponseCode() == HttpURLConnection.HTTP_ENTITY_TOO_LARGE) {
                throw new AttachmentLengthException(e.getMessage());
            }
            throw e;

        }
    }
}