/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

/**
 * Exception of attachment length
 */
public class AttachmentLengthException extends V1Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creating exception object
     *
     * @param message text of error
     */
    public AttachmentLengthException(String message) {
        super(message);
    }

    /**
     * Creating exception object
     *
     * @param message        text of error
     * @param innerException inner exception
     */
    public AttachmentLengthException(String message, Exception innerException) {
        super(message, innerException);
    }
}
