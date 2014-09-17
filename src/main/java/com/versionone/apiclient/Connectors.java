package com.versionone.apiclient;

import java.net.URI;
import java.net.URISyntaxException;

public final class Connectors implements IConnectors {

    private V1APIConnector _dataConnector;
    private V1APIConnector _metaConnector;
    private V1APIConnector _metaConnectorWithProxy;
    private V1APIConnector _dataConnectorWithProxy;
    private IUrls _urls;
    private ICredentials _credentials;

    public Connectors() throws Exception {

        _urls = new Urls();
        _credentials = new Credentials();

        createConnectors();
    }

    public Connectors(IUrls urls, ICredentials credentials) throws Exception {

        if (null == urls || null == credentials)
            throw new IllegalArgumentException("Urls and credentials are required to be non-null.");
        _urls = urls;
        _credentials = credentials;

        createConnectors();

    }

    private void createConnectors() {

        _dataConnector = new V1APIConnector(
                _urls.getDataUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password()
        );

        _metaConnector = new V1APIConnector(_urls.getMetaUrl());

    }

    public V1APIConnector getDataConnector(){
        return _dataConnector;
    }
    public V1APIConnector getMetaConnector(){
        return _metaConnector;
    }

    public V1APIConnector getMetaConnectorWithProxy() throws URISyntaxException {
        if (_metaConnectorWithProxy != null) return _metaConnectorWithProxy;
        _metaConnectorWithProxy = new V1APIConnector(
                _urls.getMetaUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password(),
                getProxyProvider());
        return _metaConnectorWithProxy;
    }

    public V1APIConnector getDataConnectorWithProxy() throws URISyntaxException {
        if (_dataConnectorWithProxy != null) return _dataConnectorWithProxy;
        _dataConnectorWithProxy = new V1APIConnector(
                _urls.getDataUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password(),
                getProxyProvider()
            );
        return _dataConnectorWithProxy;
    }

    public V1APIConnector getConfigConnector() {
        return new V1APIConnector(
                _urls.getConfigUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password());
    }

    public V1APIConnector getConfigConnectorWithProxy() throws URISyntaxException {
        return new V1APIConnector(
                _urls.getConfigUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password(),
                getProxyProvider());
    }

    private ProxyProvider getProxyProvider() throws URISyntaxException {
        URI proxyUri = new URI(_urls.getProxyUrl());
        return new ProxyProvider(proxyUri, _credentials.getProxyUserName(), _credentials.getProxyPassword());
    }

}
