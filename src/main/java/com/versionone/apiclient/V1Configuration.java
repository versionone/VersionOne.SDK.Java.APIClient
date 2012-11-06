/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

import org.w3c.dom.Document;

import javax.xml.xpath.*;
import java.io.Reader;

/**
 * Class to access to VersionOne server configuration
 */
public class V1Configuration implements IV1Configuration {

    private final IAPIConnector _connector;
    private Document _doc;

    /**
     * Creates V1Configuration object
     *
     * @param connector used to access to VersionOne server
     */
    public V1Configuration(IAPIConnector connector) {
        this._connector = connector;
    }

    /**
     * Gets EffortTracking
     *
     * @return <code>true</code> if EffortTracking is enabled, <code>false</code> - otherwise.
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public boolean isEffortTracking() throws ConnectionException, APIException {
        final String value = getSetting("EffortTracking");
        return Boolean.parseBoolean(value);
    }

    /**
     * Gets level of Story tracking.
     *
     * @return Story tracking level
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public TrackingLevel getStoryTrackingLevel() throws ConnectionException, APIException {
        final String value = getSetting("StoryTrackingLevel");
        if (value != null && value.length() > 0) {
            return TrackingLevel.valueOf(value);
        }
        return TrackingLevel.On;
    }

    /**
     * Gets level of Defect tracking.
     *
     * @return Defect tracking level
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public TrackingLevel getDefectTrackingLevel() throws ConnectionException, APIException {
        final String value = getSetting("DefectTrackingLevel");
        if (value != null && value.length() > 0) {
            return TrackingLevel.valueOf(value);
        }
        return TrackingLevel.On;
    }

    /**
     * Gets maximum size of attachment file.
     *
     * @return maximum size
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public int getMaxAttachmentSize() throws ConnectionException, APIException {
        final String value = getSetting("MaximumAttachmentSize");
        if (value != null && value.length() > 0) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        return Integer.MAX_VALUE;
    }

    private String getSetting(String keyToFind) throws ConnectionException, APIException {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            XPathExpression expr = xpath.compile("//Configuration/Setting[@key=\"" + keyToFind + "\"]/@value");
            return (String) expr.evaluate(get_doc(), XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            //do nothing
        }
        return null;
    }

    private Document get_doc() throws ConnectionException, APIException {
        if (_doc == null) {
            // Build the request document
            final Reader reader = _connector.getData();
            _doc = XMLHandler.buildDocument(reader, "");
        }
        return _doc;
    }
}
