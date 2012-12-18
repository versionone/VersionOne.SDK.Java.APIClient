package com.versionone.apiclient;

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
}
