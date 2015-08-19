package com.versionone.apiclient;

import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.IV1Configuration;
import com.versionone.util.XPathFactoryInstanceHolder;
import org.apache.commons.lang.NullArgumentException;
import org.w3c.dom.Document;

import javax.xml.xpath.*;
import java.io.Reader;

/**
 * Class to access to VersionOne server configuration
 */
public class V1Configuration implements IV1Configuration {

	 private final String EffortTrackingKey = "EffortTracking";
     private final String StoryTrackingLevelKey = "StoryTrackingLevel";
     private final String DefectTrackingLevelKey = "DefectTrackingLevel";
     private final String MaxAttachmentSizeKey = "MaximumAttachmentSize";
     private final String CapacityPlanningKey = "CapacityPlanning";
	
    private IAPIConnector _connector;
    private Document _doc;
    private V1Connector _v1connector;

    /**
     * Creates V1Configuration object
     *
     * @param connector used to access to VersionOne server
     */
    public V1Configuration(IAPIConnector connector) {
    	if (connector == null)
    		throw new NullArgumentException("connector");
    	this._connector = connector;
    }

    public V1Configuration(V1Connector connector) {
    	if (connector == null)
    		throw new NullArgumentException("_v1connector");

        this._v1connector = connector;
    }
    
    
    /**
     * Gets EffortTracking
     *
     * @return <code>true</code> if EffortTracking is enabled, <code>false</code> - otherwise.
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public boolean isEffortTracking() throws ConnectionException, APIException {
        final String value = getSetting(EffortTrackingKey);
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
        final String value = getSetting(StoryTrackingLevelKey);
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
        final String value = getSetting(DefectTrackingLevelKey);
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
        final String value = getSetting(MaxAttachmentSizeKey);
        if (value != null && value.length() > 0) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        return Integer.MAX_VALUE;
    }
    
    /**
     * Gets capacity planning setting.
     *
     * @return String Capacity planning setting
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    public String getCapacityPlanning() throws ConnectionException, APIException {
        final String value = getSetting(CapacityPlanningKey);
        if (value != null && value.length() > 0) {
            return value;
        }
        else {
        	return null;
        }
    }

    private String getSetting(String keyToFind) throws ConnectionException, APIException {
        try {
            XPathFactory factory = XPathFactoryInstanceHolder.get();
            XPath xpath = factory.newXPath();

            XPathExpression expr = xpath.compile("//Configuration/Setting[@key=\"" + keyToFind + "\"]/@value");
            return (String) expr.evaluate(get_doc(), XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            //do nothing
        }
        return null;
    }

    private Document get_doc() throws ConnectionException, APIException {
    	
    	final Reader reader;
    	
    	if (_doc == null) {
            // Build the request document
    		if (_connector!= null){
    				
    			reader = _connector.getData();
    		}else{
    			_v1connector.useConfigAPI();
    			reader = _v1connector.getData();
    		}
    		_doc = XMLHandler.buildDocument(reader, "");
        }
        return _doc;
    }
}
