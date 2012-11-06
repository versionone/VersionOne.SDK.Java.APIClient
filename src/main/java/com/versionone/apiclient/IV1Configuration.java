/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

/**
 * Interface to access to VersionOne server configuration
 */
public interface IV1Configuration {

    /**
     * Enumeration of tracking levels
     */
    public enum TrackingLevel {
        /**
         * Track Detail Estimate and ToDo at PrimaryWorkitem level only
         */
        On,
        /**
         * Track Detail Estimate and ToDo at Task/Test level only
         */
        Off,
        /**
         * Track Detail Estimate and ToDo at both the PrimaryWorkitem and the Task/Test levels
         */
        Mix
    }

    /**
     * Gets EffortTracking
     *
     * @return <code>true</code> if EffortTracking is enabled, <code>false</code> - otherwise.
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    boolean isEffortTracking() throws ConnectionException, APIException;

    /**
     * Gets level of Story tracking.
     *
     * @return Story tracking level
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    TrackingLevel getStoryTrackingLevel() throws ConnectionException, APIException;

    /**
     * Gets level of Defect tracking.
     *
     * @return Defect tracking level
     * @throws APIException if any problems occur with reading settings
     * @throws ConnectionException if any connection problems occur
     */
    TrackingLevel getDefectTrackingLevel() throws ConnectionException, APIException;

    /**
     * Gets maximum size of attachment file.
     *
     * @return maximum size
     * @throws APIException if any problems occur with building XML document
     * @throws ConnectionException if any connection problems occur
     */
    int getMaxAttachmentSize() throws ConnectionException, APIException;
}
