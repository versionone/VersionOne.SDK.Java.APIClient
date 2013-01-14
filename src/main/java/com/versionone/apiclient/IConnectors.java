package com.versionone.apiclient;

import java.net.URISyntaxException;

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
