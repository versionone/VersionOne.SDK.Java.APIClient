package com.versionone.apiclient.interfaces;

import java.net.URISyntaxException;

import com.versionone.apiclient.V1APIConnector;

/**
 * Created with IntelliJ IDEA.
 * User: GJohnson
 * Date: 12/18/12
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConnectors {
    V1APIConnector getDataConnector();
    V1APIConnector getMetaConnector();
    V1APIConnector getMetaConnectorWithProxy() throws URISyntaxException;
    V1APIConnector getDataConnectorWithProxy() throws URISyntaxException;
    V1APIConnector getConfigConnector();
    V1APIConnector getConfigConnectorWithProxy() throws URISyntaxException;
}
