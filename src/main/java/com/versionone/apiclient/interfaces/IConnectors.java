package com.versionone.apiclient.interfaces;

import java.net.URISyntaxException;

import com.versionone.apiclient.V1APIConnector;

/**
 * @deprecated This interface has been deprecated. Please use V1Connector instead. 
 */
@Deprecated
public interface IConnectors {
    V1APIConnector getDataConnector();
    V1APIConnector getMetaConnector();
    V1APIConnector getMetaConnectorWithProxy() throws URISyntaxException;
    V1APIConnector getDataConnectorWithProxy() throws URISyntaxException;
    V1APIConnector getConfigConnector();
    V1APIConnector getConfigConnectorWithProxy() throws URISyntaxException;
}
